package entity;
/**
 * Base User class representing system users
 */
public class User {
    private String username;
    @SuppressWarnings("unused")
    private String password;
    private roles role;
    
    public enum roles {
        ADMIN,
        EVP,
        GUEST
    }

    public User(String username, String password, roles role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
    
    public void setRole(roles role) { this.role = role; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    
    public String getUsername() { return username; }
    public roles getRole() { return role; }
}