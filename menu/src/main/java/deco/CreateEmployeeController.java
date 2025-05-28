package deco;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import entity.Employee;
import entity.EmployeeStatus;
import entity.User;
import entity.WorkExperience;
import entity.Child;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.nio.file.Files;

public class CreateEmployeeController {
    // Employee Basic Info
    @FXML private TextField employeeCode;
    @FXML private TextField firstName;
    @FXML private TextField middleName;
    @FXML private TextField lastName;
    @FXML private TextField suffix;
    @FXML private TextField currentAddress;
    @FXML private TextField homeAddress;
    @FXML private DatePicker hireDate;
    @FXML private DatePicker regularizationDate;
    @FXML private TextField contactNumber;

    @FXML private TextField placeOfBirth;
    @FXML private DatePicker dateOfBirth;
    @FXML private ComboBox<String> gender;
    @FXML private ComboBox<String> civilStatus;
    @FXML private ComboBox<String> bloodType;
    @FXML private ComboBox<String> position;
    @FXML private ComboBox<String> department;
    
    // Government IDs
    @FXML private TextField sssNumber;
    @FXML private TextField philHealthNumber;
    @FXML private TextField tinNumber;
    @FXML private TextField pagIbigNumber;

    // Emergency Contact
    @FXML private TextField emergencyContactName;
    @FXML private TextField emergencyContactRelationship;
    @FXML private TextField emergencyContactAddress;
    @FXML private TextField emergencyContactNumber;

    // Family Background
    @FXML private TextField fathersName;
    @FXML private DatePicker fathersBirthdate;
    @FXML private TextField fathersSSS;
    @FXML private TextField mothersName;
    @FXML private DatePicker mothersBirthdate;
    @FXML private TextField mothersSSS;
    @FXML private TextField numberOfSiblings;
    @FXML private TextField spouseName;
    @FXML private TextField spouseBirthPlace;
    @FXML private DatePicker spouseBirthDate;

    // Education
    @FXML private TextField primarySchool;
    @FXML private DatePicker primaryYearGraduated;
    @FXML private TextField tertiarySchool;
    @FXML private DatePicker tertiaryYearGraduated;

    // Birth Certificate
    @FXML private TextField birthCertificateNumber;
    @FXML private Button uploadBirthCertificate;
    private byte[] birthCertificateFile;

    // Children
    @FXML private VBox childrenContainer;
    private List<Child> children = new ArrayList<>();

    @FXML private TextField experienceDuration1;
    @FXML private TextField experienceDuration2;
    @FXML private TextField experienceDuration3;
    @FXML private TextField experienceCompany1;
    @FXML private TextField experienceCompany2;
    @FXML private TextField experienceCompany3;
    @FXML private TextField experiencePosition1;
    @FXML private TextField experiencePosition2;
    @FXML private TextField experiencePosition3;
    @FXML private TextField experienceRemarks1;
    @FXML private TextField experienceRemarks2;
    @FXML private TextField experienceRemarks3;
    private ArrayList<WorkExperience> workExperiences;

    private User user;
    private ArrayList<Employee> employees;

    @FXML
    private void initialize() {
        // Initialize ComboBoxes
        gender.getItems().addAll("Male", "Female", "Other");
        civilStatus.getItems().addAll("Single", "Married", "Widowed", "Separated", "Divorced");
        bloodType.getItems().addAll("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-");
        position.getItems().addAll("Position 1", "Position 2", "Position 3");
        department.getItems().addAll("Department 1", "Department 2", "Department 3");
        
        
        // Initialize employees list if null
        if (employees == null) {
            employees = new ArrayList<>();
        }
    }

    public void setEmployees(ArrayList<Employee> employees) {
        this.employees = employees;
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public void setUser(User user) {
        this.user = user;
        System.out.println("User set in CreateEmployee: " + user.getUsername());
    }

    @FXML
    private void handleUploadBirthCertificate() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Birth Certificate");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("PDF Files", "*.pdf")
        );

        File file = fileChooser.showOpenDialog(uploadBirthCertificate.getScene().getWindow());
        if (file != null) {
            try {
                birthCertificateFile = Files.readAllBytes(file.toPath());
                birthCertificateNumber.setText(file.getName());
            } catch (IOException e) {
                showError("Error uploading file", "Could not read the selected file.");
            }
        }
    }

    @FXML
    private void handleAddChild() {
        // Create a new child entry form
        HBox childEntry = new HBox(10);
        TextField childName = new TextField();
        childName.setPromptText("Child's Name");
        DatePicker childBirthDate = new DatePicker();
        childBirthDate.setPromptText("Birth Date");
        TextField childBirthPlace = new TextField();
        childBirthPlace.setPromptText("Birth Place");
        ComboBox<String> childGender = new ComboBox<>();
        childGender.getItems().addAll("Male", "Female", "Other");
        childGender.setPromptText("Gender");
        Button removeButton = new Button("Remove");

        childEntry.getChildren().addAll(childName, childBirthDate, childBirthPlace, childGender, removeButton);
        childrenContainer.getChildren().add(childEntry);

        removeButton.setOnAction(e -> childrenContainer.getChildren().remove(childEntry));
    }

    @FXML
    private void handleSave() {
        try {
            // Create new employee
            Employee employee = new Employee();
            
            // Set basic employee information
            employee.setEmployeeCode(employeeCode.getText());
            employee.setFirstName(firstName.getText());
            employee.setMiddleName(middleName.getText());
            employee.setLastName(lastName.getText());
            employee.setSuffix(suffix.getText());
            employee.setCurrentAddress(currentAddress.getText());
            employee.setHomeAddress(homeAddress.getText());
            employee.setContactNumberPrimary(contactNumber.getText());
            employee.setPlaceOfBirth(placeOfBirth.getText());
            employee.setDateOfBirth(dateOfBirth.getValue() != null ? Date.valueOf(dateOfBirth.getValue()) : null);
            employee.setGender(gender.getValue());
            employee.setCivilStatus(civilStatus.getValue());
            employee.setBloodType(bloodType.getValue());
            employee.setDepartment(department.getValue());
            employee.setPosition(position.getValue());
            
            // Set IDs from ComboBox selections
            // TODO: Implement proper ID mapping from display values
            
            
            // Set dates
            employee.setHireDate(hireDate.getValue() != null ? Date.valueOf(hireDate.getValue()) : null);
            employee.setRegularizationDate(regularizationDate.getValue() != null ? Date.valueOf(regularizationDate.getValue()) : null);
            
            // Family Background

            // Set government IDs
            employee.setSSSNumber(sssNumber.getText());
            employee.setPHICNumber(philHealthNumber.getText());
            employee.setTIN(tinNumber.getText());
            employee.setHDMFNo(pagIbigNumber.getText());
            
            // Set status as ACTIVE for new employees
            employee.setStatus(EmployeeStatus.ACTIVE);
            
            // Save employee to database
            try {

                // Add employee to local list
                if (employees == null) {
                    employees = new ArrayList<>();
                }
                employees.add(employee);

                // Navigate back to active employees list
                FXMLLoader loader = new FXMLLoader(App.class.getResource("Active.fxml"));
                Parent root = loader.load();
                
                // Get the ActiveController and add the new employee
                ActiveController activeController = loader.getController();
                activeController.setUser(user);
                activeController.setEmployees(employees);
                
                // Get the current scene and set the new root
                Stage stage = (Stage) employeeCode.getScene().getWindow();
                stage.getScene().setRoot(root);
                
                // Update the view to show the new employee
                activeController.updatePage(0);

                System.out.println(employee);
            } catch (Exception e) {
                showError("Database Error", "Failed to save employee: " + e.getMessage());
            }

        } catch (Exception e) {
            showError("Error Creating Employee", e.getMessage());
        }
    }

    @FXML
    private void navigateToActive() throws IOException {
        Object controller = App.setRoot("Active");
        ((ActiveController) controller).setUser(user);
    }

    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
} 