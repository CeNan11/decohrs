package deco;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Inactive {
    
    @FXML
    private Label time;

    @SuppressWarnings("unused")
    private ClockService clockService;
    
    public void setClockService(ClockService clockService) {
        this.clockService = clockService;
        time.textProperty().bind(clockService.timeProperty());
    }

    @FXML private void navigateToHome() throws IOException {
        App.setRoot("Home");
    }

    @FXML
    private void navigateToLogin() throws IOException {
        App.setRoot("Login");
    }

    @FXML 
    private void navigateToActive() throws IOException {
        App.setRoot("Active");
    }

    @FXML 
    private void navigateToAuditLogs() throws IOException {
        App.setRoot("AuditLogs");
    }

    @FXML private void logout() throws IOException {
        navigateToLogin();
    }
}
