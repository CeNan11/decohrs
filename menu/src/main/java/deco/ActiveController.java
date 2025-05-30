package deco;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.Employee;
import entity.EmployeeStatus;
import entity.User;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Duration;
import services.ClockService;
import services.EmployeeService;
import services.FilterData;
import entity.Position;
import entity.Department;
import services.EntityService;

//Correct Import
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
    
public class ActiveController implements FilterableController {
    // ===== FXML Components =====
    @FXML private Label time;
    @FXML private StackPane stackPane;
    @FXML private FlowPane flowPane;
    @FXML private Button next;
    @FXML private Button prev;
    @FXML private Label pageLabel;
    @FXML private Label totalLabel;
    @FXML private HBox auditLogsHBox;
    @FXML private TextField searchField;

    // ===== Class Properties =====
    private ArrayList<Employee> employees;
    private ArrayList<Employee> masterEmployees; // Store the full, unfiltered list
    private User user;
    private static final int ITEMS_PER_PAGE = 19;
    private int currentPage = 0;
    private FilterData lastUsedFilterData = null;
    private ArrayList<Position> positions;
    private ArrayList<Department> departments;

    // ===== Initialization Methods =====
    @FXML
    private void initialize() {
        Platform.runLater(() -> {
            if (employees == null) {
                employees = new ArrayList<>();
            } 
            initializeEmployees();
            updatePage(currentPage);
        });
        
        searchField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                searchEmployeesByName(newValue); // Call search method with new value
            }
        });
    }

    // ===== Data Management Methods =====
    public void setEmployees(ArrayList<Employee> employees) {
        this.employees = employees;
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public Employee getEmployee(Employee employee) {
        return employee;
    }

    public void addNewEmployee(Employee employee) {
        employees.add(employee);
        updatePage(currentPage);
    }


    @FXML
    private void searchEmployeesByName(String searchTerm) {
        searchTerm = searchTerm.trim().toLowerCase(); // Get search term

        if (searchTerm.isEmpty()) {
            // If the search term is empty, reload all employees
            initializeEmployees();
            updatePage(0); // Reset to the first page
            return;
        }

        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            EmployeeService employeeService = new EmployeeService(connection);
            ArrayList<Employee> searchResults = (ArrayList<Employee>) employeeService.searchEmployeesByName(searchTerm);

            // Update the employees list with the search results
            employees = searchResults;

            // Update the page to display the search results (start from page 0)
            updatePage(0);

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the error (e.g., show an error message to the user)
            System.err.println("Error searching employees: " + e.getMessage());
        }
    }

    // ===== Page Management Methods =====
    public void updatePage(int page) {
        currentPage = page;
        updatePageLabels();
        flowPane.getChildren().clear();
        
        int start = currentPage * ITEMS_PER_PAGE;
        int end = Math.min(start + ITEMS_PER_PAGE, employees.size());

        if (user == null || user.getRole() == User.roles.ADMIN) {
            addProfileCardIfNeeded();
        } else {
            end = Math.min(start + ITEMS_PER_PAGE + 1, employees.size());
        }
        addEmployeeCards(start, end);
        updateNavigationButtons();
    }

    private void updatePageLabels() {
        pageLabel.setText("PAGE: " + (currentPage + 1));
        int totalPages = (int) Math.ceil((double) employees.size() / ITEMS_PER_PAGE);
        totalLabel.setText("TOTAL: " + totalPages);
    }

    private void addProfileCardIfNeeded() {
        if (currentPage == 0 && (user == null || user.getRole() == User.roles.ADMIN)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ProfileAdd.fxml"));
                Node profileCardNode = loader.load();
                ProfileAdd controller = loader.getController();
                controller.setUser(user);
                flowPane.getChildren().add(profileCardNode);
            } catch (IOException error) {
                error.printStackTrace();
            }
        }
    }

    private void addEmployeeCards(int start, int end) {
        for (int i = start; i < end; i++) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Profile_Card.fxml"));
                Node card = loader.load();
                ProfileCardController controller = loader.getController();
                Employee employee = employees.get(i);

                controller.initData(
                    employee.getStatus(),
                    employee.getFirstName() + " " + employee.getMiddleName() + " " + employee.getLastName(),
                    employee.getPositionId().toString(),
                    employee.getDepartmentId().toString()
                );

                controller.setUser(user);
                controller.setEmployee(employee);
                if (employee.getStatus() == EmployeeStatus.ACTIVE) {
                    flowPane.getChildren().add(card);
                }
            } catch (IOException error) {
                error.printStackTrace();
            }
        }
    }

    private void updateNavigationButtons() {
        int totalPages = (int) Math.ceil((double) employees.size() / ITEMS_PER_PAGE);
        prev.setDisable(currentPage == 0);
        next.setDisable(currentPage >= totalPages - 1);
    }

    @FXML
    private void prevPage() {
        if (currentPage > 0) {
            updatePage(currentPage - 1);
        }
    }
    
    @FXML
    private void nextPage() {
        int totalPages = (int) Math.ceil((double) employees.size() / ITEMS_PER_PAGE);
        if (currentPage < totalPages - 1) {
            updatePage(currentPage + 1);
        }
    }

    // ===== User Management Methods =====
    public void setUser(User user) {
        this.user = user;

        if (checkAsGuest()) {
            auditLogsHBox.setVisible(false);
        }
    }

    @FXML 
    private boolean checkAsGuest() {
        return user.getRole() == User.roles.GUEST;
    }

    // ===== Navigation Methods =====
    @FXML
    private void navigateToHome() throws IOException {
        Object controller = App.setRoot("Home");
        ((HomeController) controller).setUser(user);
    }

    @FXML
    private void navigateToInactive() throws IOException {
        Object controller = App.setRoot("Inactive");
        ((InactiveController) controller).setUser(user);
    }

    @FXML 
    private void navigateToAuditLogs() throws IOException {
        Object controller = App.setRoot("AuditLogs");
        ((AuditLogsController) controller).setUser(user);
    }

    @FXML 
    private void navigateToLogin() throws IOException {
        App.setRoot("Login");
    }    

    @FXML 
    private void logout() throws IOException {
        navigateToLogin();
    }

    // ===== Filter Methods =====
    @FXML
    private void showFilter() {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("Filter.fxml"));
            Parent overlay = loader.load();
            
            FilterController filterController = loader.getController();
            filterController.setParentController(this);
            if (lastUsedFilterData != null) {
                filterController.setPreviousFilterData(lastUsedFilterData);
            }
            setupFilterOverlay(overlay, filterController);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupFilterOverlay(Parent overlay, FilterController filterController) {
        StackPane backgroundOverlay = createBackgroundOverlay();
        filterController.setOverlay(overlay, stackPane, backgroundOverlay);
        
        setupOverlayInteractions(overlay, backgroundOverlay, filterController);
        setupOverlayPosition(overlay);
        addOverlayToScene(overlay, backgroundOverlay);
        animateOverlay(overlay);
    }

    private StackPane createBackgroundOverlay() {
        StackPane backgroundOverlay = new StackPane();
        backgroundOverlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4);");
        backgroundOverlay.setPrefSize(stackPane.getWidth(), stackPane.getHeight());
        backgroundOverlay.prefWidthProperty().bind(stackPane.widthProperty());
        backgroundOverlay.prefHeightProperty().bind(stackPane.heightProperty());
        return backgroundOverlay;
    }

    private void setupOverlayInteractions(Parent overlay, StackPane backgroundOverlay, FilterController filterController) {
        backgroundOverlay.setOnMouseClicked(event -> filterController.handleClose());
        overlay.setOnMouseClicked(event -> event.consume());
    }

    private void setupOverlayPosition(Parent overlay) {
        StackPane.setAlignment(overlay, Pos.TOP_RIGHT);
        StackPane.setMargin(overlay, new Insets(0, 100, 0, 0));
        overlay.setTranslateY(-400);
    }

    private void addOverlayToScene(Parent overlay, StackPane backgroundOverlay) {
        stackPane.getChildren().addAll(backgroundOverlay, overlay);
    }

    private void animateOverlay(Parent overlay) {
        TranslateTransition slideIn = new TranslateTransition(Duration.millis(300), overlay);
        slideIn.setFromY(-400);
        slideIn.setToY(0);
        slideIn.play();
    }

    // ===== Service Methods =====
    public void setClockService(ClockService clockService) {
        time.textProperty().bind(clockService.timeProperty());
    }

    @Override
    public void applyFilterData(FilterData data) {
        lastUsedFilterData = data;
        if (isFilterEmpty(data)) {
            setEmployees(new ArrayList<>(masterEmployees));
        } else {
            ArrayList<Employee> filtered = new ArrayList<>(FilterData.filterEmployees(masterEmployees, data, positions, departments));
            setEmployees(filtered);
        }
        updatePage(0); // Refresh the UI to show filtered results
    }

    private boolean isFilterEmpty(FilterData data) {
        return (data.getSortBy() == null && data.getOrderBy() == null && data.getPosition() == null &&
                data.getDepartment() == null && data.getGender() == null && data.getCivilStatus() == null &&
                data.getEducationAttainment() == null && data.getDateHired() == null &&
                data.getDateRegularized() == null && data.getDateOfBirth() == null);
    }

    private static final String DB_URL = "jdbc:mysql://localhost:3306/DECOHRS_DB";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    private Connection connection;

    private void initializeEmployees() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            EmployeeService employeeService = new EmployeeService(connection);
            employees = employeeService.getEmployees();
            masterEmployees = new ArrayList<>(employees); // Save the full list
            // Load positions and departments
            EntityService entityService = new EntityService(connection);
            positions = new ArrayList<>(entityService.getPositions());
            departments = new ArrayList<>(entityService.getDepartments());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
