package entity;
/**
 * Base User class representing system users
 */
public class User {
    private String username;
    @SuppressWarnings("unused")
    private String password;
    private UserRole role;
    
    public User(String username, String password, UserRole role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
    
    public void setRole(UserRole role) { this.role = role; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    
    public String getUsername() { return username; }
    public UserRole getRole() { return role; }
}