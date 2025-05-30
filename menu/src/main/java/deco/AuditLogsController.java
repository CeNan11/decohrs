package deco;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.AuditLog;
import entity.Employee;
import entity.User;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import services.ClockService;
import services.EmployeeService;
import services.EntityService;
import tables.AuditLogTableItem;

public class AuditLogsController {

    @FXML private Label time;
    @FXML private StackPane stackPane;

    private User user;
    private ArrayList<AuditLog> auditLogs;
    private ArrayList<Employee> employees;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/DECOHRS_DB";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    private Connection connection;

    @SuppressWarnings("unused")
    private ClockService clockService;
    
    ObservableList<AuditLogTableItem> logData = FXCollections.observableArrayList();
    @FXML private TableView<AuditLogTableItem> auditLogsTableView;
    @FXML private TableColumn<AuditLogTableItem, String> idColumn;
    @FXML private TableColumn<AuditLogTableItem, String> timestampColumn;
    @FXML private TableColumn<AuditLogTableItem, String> userColumn;
    @FXML private TableColumn<AuditLogTableItem, String> actionColumn;
    @FXML private TableColumn<AuditLogTableItem, String> employeeColumn;


    public void initialize() {
        setupColumns();
        initializeAuditLogsTableView();
    }    
            
    public void setClockService(ClockService clockService) {
        this.clockService = clockService;
        time.textProperty().bind(clockService.timeProperty());
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setAuditLogs(ArrayList<AuditLog> auditLogs) {
        this.auditLogs = auditLogs;
    }


    private void initializeAuditLogsTableView() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            EntityService entityService = new EntityService(connection);
            auditLogs = entityService.getAuditLogs();
            EmployeeService employeeService = new EmployeeService(connection);
            employees = employeeService.getEmployees();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (AuditLog auditLog : auditLogs) {
            logData.add(new AuditLogTableItem(
                String.valueOf(auditLog.getId()),
                auditLog.getDateCreated().toString(),
                auditLog.getPerformedBy(),
                auditLog.getAction(),
                getEmployeeName(String.valueOf(auditLog.getTargetEmployee()))
            ));
        }
        auditLogsTableView.setItems(logData);
    }

    private String getEmployeeName(String employeeId) {
        for (Employee employee : employees) {
            if (employee.getEmployeeId() == Integer.parseInt(employeeId)) {
                return employee.getFirstName() + " " + employee.getLastName();
            }
        }
        return "Unknown Employee";
    }

    public void setupColumns() {
        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        timestampColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCreated_at()));
        userColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPerformed_by()));
        actionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAction()));
        employeeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTarget_employee()));
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