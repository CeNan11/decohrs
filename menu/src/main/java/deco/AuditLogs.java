package deco;

import java.io.IOException;
import javafx.fxml.FXML;

public class AuditLogs {

    @FXML
    private void navigateToLogin() throws IOException {
        App.setRoot("Login");
    }

    @FXML
    private void navigateToActive() throws IOException {
        App.setRoot("Active");
    }

    @FXML
    private void navigateToInactive() throws IOException {
        App.setRoot("Inactive");
    }

    @FXML private void logout() throws IOException {
        navigateToLogin();
    }
}