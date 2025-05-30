package deco;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

import entity.User;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import services.ClockService;
import services.EmployeeService;

public class HomeController {
    
    @FXML private Label time;
    @FXML private Label greet;
    @FXML private Label date;
    @FXML private Label headCount;
    @FXML private Label headCountActive;
    @FXML private Label headCountInactive;
    @FXML private VBox headCountVBox;

    private User user;
    @FXML private HBox auditLogsHBox;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/DECOHRS_DB";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    @FXML
    private void initialize() {
        LocalDate currentDate = LocalDate.now();
        headCountVBox.setVisible(false);
        String dayOfTheWeek = currentDate.getDayOfWeek()
                .getDisplayName(TextStyle.FULL, Locale.ENGLISH)
                .toUpperCase();

        String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("MMMM d, yyyy"));

        date.setText(String.format("It is %s!%n%s", dayOfTheWeek, formattedDate));  
        initializeHeadCount();

        Platform.runLater(() -> {
            if (user.getRole() == User.roles.GUEST) {
                headCountVBox.setVisible(false);
            } else {
                headCountVBox.setVisible(true);
            }
        });
    }

    @FXML
    private void initializeHeadCount() {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            EmployeeService employeeService = new EmployeeService(connection);
            headCount.setText(String.format("%d", employeeService.getEmployeeCount()));
            headCountActive.setText(String.format("%d", employeeService.getEmployeeCountByStatus("Active")));
            headCountInactive.setText(String.format("%d", employeeService.getEmployeeCountByStatus("Inactive")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setUser(User user) {
        this.user = user;
        greet.setText(String.format("Welcome, %s!", user.getUsername()));

        if (checkAsGuest()) {
            auditLogsHBox.setVisible(false);
            
        }
    }

    @SuppressWarnings("unused")
    private ClockService clockService;
    
    public void setClockService(ClockService clockService) {
        this.clockService = clockService;
        time.textProperty().bind(clockService.timeProperty());
    }

    @FXML
    private void navigateToLogin() throws IOException {
        App.setRoot("Login");
    }

    @FXML 
    private void navigateToActive() throws IOException {
        Object controller = App.setRoot("Active");
        ((ActiveController) controller).setUser(user);
    }

    @FXML 
    private void navigateToInactive() throws IOException {
        Object controller = App.setRoot("Inactive");
        ((InactiveController) controller).setUser(user);
    }

    @FXML 
    private void navigateToAuditLogs() throws IOException {
        Object controller = App.setRoot("AuditLogs");
        ((AuditLogsController) controller).setUser(user);
    }

    @FXML private void logout() throws IOException {
        navigateToLogin();
    }

    @FXML private boolean checkAsGuest() {
        if (user.getRole() == User.roles.GUEST) {
            return true;
        } else {
            return false;
        }
    }
}
