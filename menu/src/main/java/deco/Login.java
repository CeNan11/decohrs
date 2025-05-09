package deco;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class Login {

    @FXML
    private Label time;

    public void setClockService(ClockService clockService) {
        time.textProperty().bind(clockService.timeProperty());
    }

    @FXML
    private void navigateToHome() throws IOException {
        App.setRoot("Home");
    }

    @FXML TextField username;
    @FXML PasswordField password;

    @FXML private void login() throws IOException {

        if (username.getText().equals("admin") && password.getText().equals("admin")) {
            navigateToHome();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText("Invalid username or password");
            alert.showAndWait();
        }
    }
}
