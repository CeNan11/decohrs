package deco;

import java.io.IOException;
import javafx.fxml.FXML;

public class Inactive {
    
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
