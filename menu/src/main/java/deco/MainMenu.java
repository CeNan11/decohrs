package deco;

import java.io.IOException;
import javafx.fxml.FXML;

public class MainMenu {

    @FXML
    private void navigateToLogin() throws IOException {
        App.setRoot("Login");
    }
}