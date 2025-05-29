package deco;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

import entity.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import services.ClockService;

public class HomeController {
    
    @FXML private Label time;
    @FXML private Label greet;
    @FXML private Label date;

    private User user;
    @FXML private HBox auditLogsHBox;

    @FXML
    private void initialize() {
        LocalDate currentDate = LocalDate.now();

        String dayOfTheWeek = currentDate.getDayOfWeek()
                .getDisplayName(TextStyle.FULL, Locale.ENGLISH)
                .toUpperCase();

        String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("MMMM d, yyyy"));

        date.setText(String.format("It is %s!%n%s", dayOfTheWeek, formattedDate));  
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
