package services;

import entity.User;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserService {
    private Connection connection;

    public UserService(Connection connection) {
        this.connection = connection;
    }
    
    // Convert database role to User.roles enum
    private User.roles convertToUserRole(String dbRole) {
        switch (dbRole.toUpperCase()) {
            case "ADMIN":
                return User.roles.ADMIN;
            case "EVP":
                return User.roles.EVP;
            default:
                return User.roles.GUEST;
        }
    }
    
    // Convert User.roles enum to database role
    private String convertToDbRole(User.roles role) {
        switch (role) {
            case ADMIN:
                return "Admin";
            case EVP:
                return "EVP";
            case GUEST:
                return "Employee";
            default:
                return "Employee";
        }
    }
    
    // User registration with role assignment
    public boolean registerUser(String username, String password, User.roles role) {
        String insertUserSQL = "INSERT INTO Users (username, password_hash) VALUES (?, ?)";
        String insertRoleSQL = "INSERT INTO UserRoles (user_id, role_id) VALUES (?, (SELECT role_id FROM Roles WHERE role_name = ?))";
                
        try {
            connection.setAutoCommit(false);
            
            // Insert user
            try (PreparedStatement pstmt = connection.prepareStatement(insertUserSQL, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, username);
                pstmt.setString(2, hashPassword(password));
                
                int affectedRows = pstmt.executeUpdate();
                if (affectedRows == 0) {
                    connection.rollback();
                    return false;
                }
                
                // Get the generated user_id
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int userId = generatedKeys.getInt(1);
                        
                        // Assign role
                        try (PreparedStatement roleStmt = connection.prepareStatement(insertRoleSQL)) {
                            roleStmt.setInt(1, userId);
                            roleStmt.setString(2, convertToDbRole(role));
                            roleStmt.executeUpdate();
                        }
                        
                        connection.commit();
                        return true;
                    }
                }
            }
            
            connection.rollback();
            return false;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        String sql = "SELECT u.username, u.password_hash, r.role_name " +
                    "FROM Users u " +
                    "JOIN UserRoles ur ON u.user_id = ur.user_id " +
                    "JOIN Roles r ON ur.role_id = r.role_id";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    users.add(new User(rs.getString("username"), rs.getString("password_hash"), convertToUserRole(rs.getString("role_name"))));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
    
    // Authenticate user and return User object
    public Optional<User> authenticateUser(String username, String password) {
        String sql = "SELECT u.user_id, u.password_hash, r.role_name " +
                    "FROM Users u " +
                    "JOIN UserRoles ur ON u.user_id = ur.user_id " +
                    "JOIN Roles r ON ur.role_id = r.role_id " +
                    "WHERE u.username = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("password_hash");
                    if (verifyPassword(password, storedHash)) {
                        User.roles role = convertToUserRole(rs.getString("role_name"));
                        return Optional.of(new User(username, password, role));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return Optional.empty();
    }
    
    // Get user roles as User.roles enum
    public List<User.roles> getUserRoles(int userId) {
        List<User.roles> roles = new ArrayList<>();
        String sql = "SELECT r.role_name FROM Roles r " +
                    "JOIN UserRoles ur ON r.role_id = ur.role_id " +
                    "WHERE ur.user_id = ?";
                    
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    roles.add(convertToUserRole(rs.getString("role_name")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return roles;
    }
    
    // Update user role
    public boolean updateUserRole(int userId, User.roles newRole) {
        String sql = "UPDATE UserRoles ur " +
                    "JOIN Roles r ON ur.role_id = r.role_id " +
                    "SET ur.role_id = (SELECT role_id FROM Roles WHERE role_name = ?) " +
                    "WHERE ur.user_id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, convertToDbRole(newRole));
            pstmt.setInt(2, userId);
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Update user password
    public boolean updateUserPassword(int userId, String newPassword) {
        String sql = "UPDATE Users SET password_hash = ? WHERE user_id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, hashPassword(newPassword));
            pstmt.setInt(2, userId);
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Get user by ID as User object
    public Optional<User> getUserById(int userId) {
        String sql = "SELECT u.username, u.password_hash, r.role_name " +
                    "FROM Users u " +
                    "JOIN UserRoles ur ON u.user_id = ur.user_id " +
                    "JOIN Roles r ON ur.role_id = r.role_id " +
                    "WHERE u.user_id = ?";
                    
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String username = rs.getString("username");
                    String passwordHash = rs.getString("password_hash");
                    User.roles role = convertToUserRole(rs.getString("role_name"));
                    return Optional.of(new User(username, passwordHash, role));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return Optional.empty();
    }
    
    // Password verification
    private boolean verifyPassword(String password, String storedHash) {
        String hashedInput = hashPassword(password);
        return hashedInput.equals(storedHash);
    }
    
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            
            // Convert byte array to hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return password; // Fallback to plain text if hashing fails
        }
    }
} 