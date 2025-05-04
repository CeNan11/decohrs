package deco;

import java.io.IOException;
import javafx.fxml.FXML;

public class Active {

    @FXML
    private void navigateToLogin() throws IOException {
        App.setRoot("Login");
    }

    @FXML
    private void navigateToAuditLogs() throws IOException {
        App.setRoot("AuditLogs");
    }

    @FXML
    private void navigateToInactive() throws IOException {
        App.setRoot("Inactive");
    }

    @FXML private void logout() throws IOException {
        navigateToLogin();
    }
}