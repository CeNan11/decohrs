package deco;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import services.ClockService;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App extends Application {

    private static Scene scene;
    private static ClockService clockService = new ClockService();

    @Override
    public void start(@SuppressWarnings("exports") Stage stage) throws IOException { 
        initializeDatabase();

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

    public static void main(String[] args) {
        launch();
    }

    private static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    private static final String DB_NAME = "DECOHRS_DB";

    private static void initializeDatabase() {
        if (!databaseExists()) {
            System.out.println("Database '" + DB_NAME + "' does not exist. Creating...");
            createDatabase();
            System.out.println("Database '" + DB_NAME + "' created successfully.");
        } else {
            System.out.println("Database '" + DB_NAME + "' already exists.");
        }
    }

    private static boolean databaseExists() {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            return conn.isValid(5);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void createDatabase() {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            conn.createStatement().executeUpdate("CREATE DATABASE " + DB_NAME);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}