package deco;

import java.io.IOException;
import javafx.fxml.FXML;

public class Login {

    @FXML
    private void navigateToMainMenu() throws IOException {
        App.setRoot("MainMenu");
    }
}
