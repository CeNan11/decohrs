package deco;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

import entity.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import services.UserService;

public class LoginController {
    private UserService userService;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/DECOHRS_DB";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    @FXML TextField username;
    @FXML TextField passwordTextField;
    @FXML PasswordField password;

    public void initialize() {
        if (userService.getUsers().isEmpty()) {
            System.out.println("No users found, registering users");
            registerUsers();
        } else {
            System.out.println("Users found, not registering users");
        }

        passwordTextField.setVisible(false);
        passwordTextField.textProperty().bindBidirectional(password.textProperty());
    }

    private void registerUsers() {
        User user1 = new User("admin", "admin", User.roles.ADMIN);
        User user2 = new User("evp", "evp", User.roles.EVP);
        User user3 = new User("guest", "guest", User.roles.GUEST);

        userService.registerUser(user1.getUsername(), user1.getPassword(), user1.getRole());
        userService.registerUser(user2.getUsername(), user2.getPassword(), user2.getRole());
        userService.registerUser(user3.getUsername(), user3.getPassword(), user3.getRole());
    }

    public LoginController() {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            userService = new UserService(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            // Show database connection error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText("Connection Failed");
            alert.setContentText("Could not connect to the database. Please check your connection settings.");
            alert.showAndWait();
        }
    }

    @FXML
    private void navigateToHome(User user) throws IOException {
        Object controller = App.setRoot("Home");
        ((HomeController) controller).setUser(user);
    }
    
    @FXML 
    private void login() throws IOException {
        String usernameText = username.getText().trim();
        String passwordText = password.getText().trim();

        if (usernameText.isEmpty() || passwordText.isEmpty()) {
            showError("Login Failed", "Please enter both username and password");
            return;
        }

        Optional<User> userOpt = userService.authenticateUser(usernameText, passwordText);
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            navigateToHome(user);
        } else {
            showError("Login Failed", "Invalid username or password");
        }
    }

    @FXML 
    private void loginAsGuest() throws IOException {
        User user = new User("guest", "guest", User.roles.GUEST);
        navigateToHome(user);
    }

    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML private Button showPasswordButton;

    @FXML
    private void showPassword() {
        if (passwordTextField.isVisible()) {
            passwordTextField.setVisible(false);
            showPasswordButton.setText("Show Password");
        } else {
            passwordTextField.setVisible(true);
            showPasswordButton.setText("Hide Password");
        }
    }
}
