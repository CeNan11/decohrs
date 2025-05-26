package deco;

import java.io.IOException;
import java.time.LocalDateTime;

import entity.User;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import services.ClockService;
import tables.AuditLogTableItem;

public class AuditLogsController {

    @FXML private Label time;
    @FXML private StackPane stackPane;

    private User user;


    @SuppressWarnings("unused")
    private ClockService clockService;
    
    @FXML private TableView<AuditLogTableItem> auditLogsTableView;
    @FXML private TableColumn<AuditLogTableItem, String> idColumn;
    @FXML private TableColumn<AuditLogTableItem, String> timestampColumn;
    @FXML private TableColumn<AuditLogTableItem, String> userColumn;
    @FXML private TableColumn<AuditLogTableItem, String> actionColumn;
    @FXML private TableColumn<AuditLogTableItem, String> detailsColumn;

    private final ObservableList<AuditLogTableItem> logData = FXCollections.observableArrayList();

    public void initialize() {
        setupColumns();

        // Add sample data
        logData.add(new AuditLogTableItem(
            "12345",
            LocalDateTime.now().toString(),
            "System",
            "Application Start",
            "Audit system initialized"
            ));

            auditLogsTableView.setItems(logData);
        }    
        
        public void setupColumns() {
            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            
            timestampColumn.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
            
            userColumn.setCellValueFactory(new PropertyValueFactory<>("user"));   
            
            actionColumn.setCellValueFactory(new PropertyValueFactory<>("action"));
            
            detailsColumn.setCellValueFactory(new PropertyValueFactory<>("details"));
        }
    
    public void setClockService(ClockService clockService) {
        this.clockService = clockService;
        time.textProperty().bind(clockService.timeProperty());
    }

    public void setUser(User user) {
        this.user = user;
    }

    @FXML private void navigateToHome() throws IOException {
        Object controller = App.setRoot("Home");
        ((HomeController) controller).setUser(user);
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

    @FXML private void logout() throws IOException {
        navigateToLogin();
    }

        @FXML private void showOverlay() {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("Filter.fxml"));
            Parent overlay = loader.load();

            StackPane backgroundOverlay = new StackPane();
            backgroundOverlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4);");
            backgroundOverlay.setPrefSize(stackPane.getWidth(), stackPane.getHeight());

            backgroundOverlay.prefWidthProperty().bind(stackPane.widthProperty());
            backgroundOverlay.prefHeightProperty().bind(stackPane.heightProperty());

            backgroundOverlay.setOnMouseClicked(event -> {
            handleClose(overlay, backgroundOverlay);
            });

            overlay.setOnMouseClicked(event -> event.consume());

            StackPane.setAlignment(overlay, Pos.TOP_RIGHT);
            StackPane.setMargin(overlay, new Insets(0, 100, 0, 0));

            overlay.setTranslateY(-400);
            stackPane.getChildren().addAll(backgroundOverlay, overlay);

            TranslateTransition slideIn = new TranslateTransition(Duration.millis(300), overlay);
            slideIn.setFromY(-400);
            slideIn.setToY(0);
            slideIn.play();
            
            FilterController controller = loader.getController();
            controller.setOverlay(overlay, stackPane, backgroundOverlay);

        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    private void handleClose(Parent overlay, StackPane backgroundOverlay) {
        TranslateTransition slideOut = new TranslateTransition(Duration.millis(300), overlay);
        slideOut.setToY(-400); // Slide up
        slideOut.setOnFinished(event -> {
            stackPane.getChildren().removeAll(backgroundOverlay, overlay);
        });
        slideOut.play();
    }

}