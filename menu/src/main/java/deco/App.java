package deco;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static Scene scene;
    private static ClockService clockService = new ClockService();

    @Override
    public void start(@SuppressWarnings("exports") Stage stage) throws IOException {
        scene = new Scene(loadFXML("Login"));
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setMinHeight(800);
        stage.setMinWidth(1440);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/deco_images/decoLogo.png")));
        stage.setTitle("DECOHRS");
        stage.show();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

    public static Object setRoot(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        Parent root = loader.load();

        Object controller = loader.getController();

        switch (fxml) {
            case "Home":
                if (controller instanceof HomeController) {
                    ((HomeController) controller).setClockService(clockService);
                }
                break;
            case "Active":
                if (controller instanceof ActiveController) {
                    ((ActiveController) controller).setClockService(clockService);
                }
                break;
            case "Inactive":
                if (controller instanceof InactiveController) {
                    ((InactiveController) controller).setClockService(clockService);
                }
                break;
            case "AuditLogs":
                if (controller instanceof AuditLogsController) {
                    ((AuditLogsController) controller).setClockService(clockService);
                }
                break;
        }

        if (scene == null) {
            scene = new Scene(root);
        } else {
            scene.setRoot(root);
        }
        return controller;
    }
}