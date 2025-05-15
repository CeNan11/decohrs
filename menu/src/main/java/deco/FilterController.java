package deco;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class FilterController {

    private ActiveController parentController;

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

    public void initialize() {
        sortByComboBox.getItems().addAll("Name", "Age", "Department", "Position", "Gender", "Civil Status", "Education Attainment", "Date Hired", "Date Regularized", "Date of Birth");
        orderByComboBox.getItems().addAll("Ascending", "Descending");
        positionComboBox.getItems().addAll("Manager", "Supervisor", "Employee", "Intern");
        departmentComboBox.getItems().addAll("HR", "IT", "Finance", "Marketing", "Sales");
        genderComboBox.getItems().addAll("Male", "Female");
        civilStatusComboBox.getItems().addAll("Single", "Married", "Divorced", "Widowed");
        eduAttainComboBox.getItems().addAll("High School", "Bachelor's", "Master's", "PhD");
    }

    // Method to receive the parent controller reference
    public void setParentController(ActiveController controller) {
        this.parentController = controller;
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
            
            parentController.applyFilterData(data); // Send to Active controller
        } else {
            System.err.println("Parent controller reference is null!");
        }

        handleClose(); // Close the overlay after applying
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