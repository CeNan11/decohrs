package deco;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import services.FilterData;
import services.EntityService;
import entity.Department;
import entity.Education;
import entity.Position;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class FilterController {

    private Parent overlay;
    private StackPane parentStack;
    private Parent backgroundOverlay;

    @FXML ComboBox<String> sortByComboBox; 
    @FXML ComboBox<String> orderByComboBox;
    @FXML ComboBox<String> positionComboBox;
    @FXML ComboBox<String> departmentComboBox;
    @FXML ComboBox<String> genderComboBox;
    @FXML ComboBox<String> civilStatusComboBox;
    @FXML ComboBox<String> eduAttainComboBox;
    @FXML DatePicker dateHiredDatePicker;
    @FXML DatePicker dateRegularizedDatePicker;
    @FXML DatePicker dateOfBirthDatePicker;

    private Connection connection;
    private String localHost = "jdbc:mysql://localhost:3306/DECOHRS_DB";
    private String username = "root";
    private String pass = "";

    private FilterableController parentController;
    private FilterData lastFilterData = null;

    public void initialize() {
        sortByComboBox.getItems().addAll("Name", "Age", "Department", "Position", "Gender", "Civil Status", 
        "Education Attainment", "Date Hired", "Date Regularized", "Date of Birth");
        orderByComboBox.getItems().addAll("Ascending", "Descending");
        List<Position> positions = new ArrayList<>();
        List<Department> departments = new ArrayList<>();

        try {
            connection = DriverManager.getConnection(localHost, username, pass);
            EntityService entityService = new EntityService(connection);
            positions = entityService.getPositions();
            departments = entityService.getDepartments();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        positionComboBox.getItems().addAll(positions.stream().map(Position::getPositionTitle).collect(Collectors.toList()));
        departmentComboBox.getItems().addAll(departments.stream().map(Department::getDepartmentName).collect(Collectors.toList()));
        genderComboBox.getItems().addAll("Male", "Female", "Other");
        civilStatusComboBox.getItems().addAll("Single", "Married", "Divorced", "Widowed");
        eduAttainComboBox.getItems().addAll(Education.getHighestAttainmentList());
    }

    public void setParentController(FilterableController controller) {
        this.parentController = controller;
    }

    public void setPreviousFilterData(FilterData data) {
        this.lastFilterData = data;
        if (data != null) {
            setFilterUIFromData(data);
        }
    }

    private void setFilterUIFromData(FilterData data) {
        sortByComboBox.setValue(data.getSortBy());
        orderByComboBox.setValue(data.getOrderBy());
        positionComboBox.setValue(data.getPosition());
        departmentComboBox.setValue(data.getDepartment());
        genderComboBox.setValue(data.getGender());
        civilStatusComboBox.setValue(data.getCivilStatus());
        eduAttainComboBox.setValue(data.getEducationAttainment());
        dateHiredDatePicker.setValue(data.getDateHired());
        dateRegularizedDatePicker.setValue(data.getDateRegularized());
        dateOfBirthDatePicker.setValue(data.getDateOfBirth());
    }

    private FilterData getCurrentFilterData() {
        FilterData data = new FilterData();
        data.setSortBy(sortByComboBox.getValue());
        data.setOrderBy(orderByComboBox.getValue());
        data.setPosition(positionComboBox.getValue());
        data.setDepartment(departmentComboBox.getValue());
        data.setGender(genderComboBox.getValue());
        data.setCivilStatus(civilStatusComboBox.getValue());
        data.setEducationAttainment(eduAttainComboBox.getValue());
        data.setDateHired(dateHiredDatePicker.getValue());
        data.setDateRegularized(dateRegularizedDatePicker.getValue());
        data.setDateOfBirth(dateOfBirthDatePicker.getValue());
        return data;
    }

    @SuppressWarnings("exports")
    public void setOverlay(Parent overlay, StackPane parentStack, Parent backgroundOverlay) {
        this.overlay = overlay;
        this.parentStack = parentStack;
        this.backgroundOverlay = backgroundOverlay;
    }

    @FXML
    private void applyFilters() {
        if (parentController != null) {
            FilterData data = getCurrentFilterData();
            lastFilterData = data;
            parentController.applyFilterData(data);
        } else {
            System.err.println("Parent controller is null!");
        }
        handleClose();
    }

    @FXML
    private void cancelFilters() {
        // Clear all filters and restore normal view
        if (parentController != null) {
            parentController.applyFilterData(new FilterData());
        }
        handleClose();
    }

    @FXML
    public void handleClose() {
        // Slide out animation
        TranslateTransition slideOut = new TranslateTransition(Duration.millis(300), overlay);
        slideOut.setFromY(0);
        slideOut.setToY(-400); // Slide out to top

        slideOut.setOnFinished(event -> {
            parentStack.getChildren().removeAll(backgroundOverlay, overlay);
        });
        slideOut.play();
    }
}