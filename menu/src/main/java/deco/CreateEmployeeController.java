package deco;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.Employee;
import entity.EmployeeStatus;
import entity.FamilyBackground;
import entity.Position;
import entity.User;
import entity.WorkExperience;
import entity.AuditLog;
import entity.Child;
import entity.Department;
import entity.Education;
import entity.EmergencyContact;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import services.EmployeeService;
import services.EntityService;

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
    @FXML private TextField mothersName;
    @FXML private DatePicker mothersBirthdate;
    @FXML private TextField numberOfSiblings;
    @FXML private TextField spouseName;
    @FXML private TextField spouseAddress;
    @FXML private DatePicker spouseBirthDate;

    // Education
    @FXML private TextField primarySchool;
    @FXML private DatePicker primaryYearGraduated;
    @FXML private TextField tertiarySchool;
    @FXML private DatePicker tertiaryYearGraduated;
    @FXML private TextField collegeSchool;
    @FXML private DatePicker collegeYearGraduated;
    @FXML private TextField vocationalSchool;
    @FXML private DatePicker vocationalYearGraduated;
    @FXML private TextField postGraduateSchool;
    @FXML private DatePicker postGraduateYearGraduated;
    @FXML private TextField certificateLicenseName;
    @FXML private DatePicker dateIssued;
    @FXML private DatePicker validUntil;

    // Birth Certificate
    @FXML private TextField birthCertificateNumber;
    @FXML private Button uploadBirthCertificate;
    private byte[] birthCertificateFile;

    // Children
    @FXML private VBox childrenContainer;
    private ArrayList<Child> children = new ArrayList<>();

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

    private User user;
    private ArrayList<Employee> employees;

    private ArrayList<Department> departments;
    private ArrayList<Position> positions;

    private static String localHost = "jdbc:mysql://localhost:3306/DECOHRS_DB";
    private static String username = "root";
    private static String pass = "";

    @FXML
    private void initialize() {
        try {
            Connection connection = DriverManager.getConnection(localHost, username, pass);
            EntityService entityService = new EntityService(connection);
            if (entityService.getDepartments().isEmpty()) {
                departments = createDepartments();

                for (Department department : departments) {
                    entityService.insertDepartment(department);
                }
            } else {
                departments = entityService.getDepartments();
            }

            if (entityService.getPositions().isEmpty()) {
                positions = createPositions();
                for (Position position : positions) {
                    entityService.insertPosition(position);
                }
            } else {
                positions = entityService.getPositions();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Initialize ComboBoxes
        gender.getItems().addAll("Male", "Female", "Other");
        civilStatus.getItems().addAll("Single", "Married", "Widowed", "Separated", "Divorced");
        bloodType.getItems().addAll("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-");

        position.getItems().addAll(positions.stream().map(Position::getPositionTitle).toArray(String[]::new));
        department.getItems().addAll(departments.stream().map(Department::getDepartmentName).toArray(String[]::new));
        
        initializeChildrenTable();

        childGender.getItems().addAll("Male", "Female");

        // Initialize employees list if null
        if (employees == null) {
            employees = new ArrayList<>();
        }
    }

    ObservableList<Child> childrenList = FXCollections.observableArrayList();
    @FXML private TableView<Child> childrenTableView;
    @FXML private TableColumn<Child, String> nameColumn;
    @FXML private TableColumn<Child, String> dateOfBirthColumn;
    @FXML private TableColumn<Child, String> placeOfBirthColumn;
    @FXML private TableColumn<Child, String> genderColumn;
    @FXML private TextField childName;
    @FXML private DatePicker childDateOfBirth;
    @FXML private TextField childPlaceOfBirth;
    @FXML private ComboBox<String> childGender;

    @FXML
    private void initializeChildrenTable() {
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        dateOfBirthColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDateOfBirth().toString()));
        placeOfBirthColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPlaceOfBirth()));
        genderColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGender()));

        childrenTableView.setItems(childrenList);
    }

    @FXML
    private void addChild() {
        String name = childName.getText().trim();
        Date dateOfBirth = childDateOfBirth.getValue() != null ? Date.valueOf(childDateOfBirth.getValue()) : null;
        String placeOfBirth = childPlaceOfBirth.getText().trim();
        String gender = childGender.getValue();

        if (name != null && dateOfBirth != null && placeOfBirth != null && gender != null) {
            Child child = new Child();
            child.setName(name);
            child.setDateOfBirth(dateOfBirth);
            child.setPlaceOfBirth(placeOfBirth);
            child.setGender(gender);
            childrenList.add(child);
            childName.clear();
            childDateOfBirth.setValue(null);
            childPlaceOfBirth.clear();
            childGender.setValue(null);

            children.add(child);
        } else {
            showError("Error", "Please fill in all fields");
        }
    }

    @FXML
    private void removeChild() {
        Child selectedChild = childrenTableView.getSelectionModel().getSelectedItem();
        if (selectedChild != null) {
            childrenList.remove(selectedChild);
            children.remove(selectedChild);
        }
    }
    
    public ArrayList<Department> createDepartments() {
        departments = new ArrayList<>();
        departments.add(new Department(1, "HR"));
        departments.add(new Department(2, "IT"));
        departments.add(new Department(3, "Finance"));
        departments.add(new Department(4, "Marketing"));
        departments.add(new Department(5, "Sales"));

        return departments; 
    }

    public ArrayList<Position> createPositions() {
        positions = new ArrayList<>();
        positions.add(new Position(1, "Position 1"));
        positions.add(new Position(2, "Position 2"));
        positions.add(new Position(3, "Position 3"));
        positions.add(new Position(4, "Position 4"));
        positions.add(new Position(5, "Position 5"));

        return positions;
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

    private Employee saveEmployee() {
        Employee employee = new Employee();
            
        // Set basic employee information
        employee.setEmployeeCode(employeeCode.getText().trim());
        employee.setFirstName(firstName.getText().trim());
        employee.setMiddleName(middleName.getText().trim());
        employee.setLastName(lastName.getText().trim());
        employee.setSuffix(suffix.getText().trim());
        employee.setCurrentAddress(currentAddress.getText().trim());
        employee.setHomeAddress(homeAddress.getText().trim());
        employee.setContactNumberPrimary(contactNumber.getText().trim());
        employee.setPlaceOfBirth(placeOfBirth.getText().trim());
        employee.setDateOfBirth(dateOfBirth.getValue() != null ? Date.valueOf(dateOfBirth.getValue()) : null);
        employee.setGender(gender.getValue());
        employee.setCivilStatus(civilStatus.getValue());
        employee.setBloodType(bloodType.getValue());

        // Set department and position
        try {
            Connection connection = DriverManager.getConnection(localHost, username, pass);
            EntityService entityService = new EntityService(connection);
            employee.setDepartmentId(entityService.getDepartmentByName(department.getValue()).getDepartmentId());
            employee.setPositionId(entityService.getPositionByName(position.getValue()).getPositionId());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Set dates
        employee.setHireDate(hireDate.getValue() != null ? Date.valueOf(hireDate.getValue()) : null);
        employee.setRegularizationDate(regularizationDate.getValue() != null ? Date.valueOf(regularizationDate.getValue()) : null);

        // Set government IDs
        employee.setSSSNumber(sssNumber.getText().trim());
        employee.setPHICNumber(philHealthNumber.getText().trim());
        employee.setTIN(tinNumber.getText().trim());
        employee.setHDMFNo(pagIbigNumber.getText().trim());

        // Set status
        employee.setStatus(EmployeeStatus.ACTIVE);

        return employee;
    }

    private EmergencyContact saveEmergencyContact() {
        EmergencyContact emergencyContact = new EmergencyContact();
        emergencyContact.setName(emergencyContactName.getText().trim());
        emergencyContact.setRelationship(emergencyContactRelationship.getText().trim());
        emergencyContact.setAddress(emergencyContactAddress.getText().trim());
        emergencyContact.setContactNumber(emergencyContactNumber.getText().trim());

        return emergencyContact;
    }

    private FamilyBackground saveFamilyBackground() {
        FamilyBackground familyBackground = new FamilyBackground();
        familyBackground.setFatherName(fathersName.getText().trim());
        familyBackground.setFatherDOB(fathersBirthdate.getValue() != null ? Date.valueOf(fathersBirthdate.getValue()) : null);
        familyBackground.setMotherName(mothersName.getText().trim());
        familyBackground.setMotherDOB(mothersBirthdate.getValue() != null ? Date.valueOf(mothersBirthdate.getValue()) : null);
        familyBackground.setSpouseName(spouseName.getText().trim());
        familyBackground.setSpouseAddress(spouseAddress.getText().trim());
        familyBackground.setSpouseBirthDate(spouseBirthDate.getValue() != null ? Date.valueOf(spouseBirthDate.getValue()) : null);

        return familyBackground;
    }

    private Education saveEducation() {
        Education education = new Education();
        education.setPrimarySchool(primarySchool.getText().trim());
        education.setPrimaryYearGraduated(primaryYearGraduated.getValue() != null ? Date.valueOf(primaryYearGraduated.getValue()) : null);
        education.setTertiarySchool(tertiarySchool.getText().trim());
        education.setTertiaryYearGraduated(tertiaryYearGraduated.getValue() != null ? Date.valueOf(tertiaryYearGraduated.getValue()) : null);
        education.setCollegeSchool(collegeSchool.getText().trim());
        education.setCollegeYearGraduated(collegeYearGraduated.getValue() != null ? Date.valueOf(collegeYearGraduated.getValue()) : null);
        education.setVocationalSchool(vocationalSchool.getText().trim());
        education.setVocationalYearGraduated(vocationalYearGraduated.getValue() != null ? Date.valueOf(vocationalYearGraduated.getValue()) : null);
        education.setPostGraduateSchool(postGraduateSchool.getText().trim());
        education.setPostGraduateYearGraduated(postGraduateYearGraduated.getValue() != null ? Date.valueOf(postGraduateYearGraduated.getValue()) : null);
        education.setCertificateLicenseName(certificateLicenseName.getText().trim());
        education.setDateIssued(dateIssued.getValue() != null ? Date.valueOf(dateIssued.getValue()) : null);
        education.setValidUntil(validUntil.getValue() != null ? Date.valueOf(validUntil.getValue()) : null);

        return education;
    }

    private ArrayList<WorkExperience> saveWorkAllExperience() {
        ArrayList<WorkExperience> workExperiences = new ArrayList<>();
        
        WorkExperience work1 = new WorkExperience();
        work1.setCompanyName(experienceCompany1.getText().trim());
        work1.setPositionHeld(experiencePosition1.getText().trim());
        work1.setDuration(experienceDuration1.getText().trim());
        work1.setRemarks(experienceRemarks1.getText().trim());
        workExperiences.add(work1);

        WorkExperience work2 = new WorkExperience();
        work2.setCompanyName(experienceCompany2.getText().trim());
        work2.setPositionHeld(experiencePosition2.getText().trim());
        work2.setDuration(experienceDuration2.getText().trim());
        work2.setRemarks(experienceRemarks2.getText().trim());
        workExperiences.add(work2);

        WorkExperience work3 = new WorkExperience();    
        work3.setCompanyName(experienceCompany3.getText().trim());
        work3.setPositionHeld(experiencePosition3.getText().trim());
        work3.setDuration(experienceDuration3.getText().trim());
        work3.setRemarks(experienceRemarks3.getText().trim());
        workExperiences.add(work3);

        return workExperiences;
    }

    @FXML
    private void handleSave() {
        try {
            // Create new employee
            Employee employee = saveEmployee();
            EmergencyContact emergencyContact = saveEmergencyContact();
            employee.setEmergencyContact(emergencyContact);
            FamilyBackground familyBackground = saveFamilyBackground();
            employee.setFamilyBackground(familyBackground);
            Education education = saveEducation();
            employee.setEducation(education);
            ArrayList<WorkExperience> workExperiences = saveWorkAllExperience();
            employee.setWorkExperiences(workExperiences);
            employee.setChildren(children);
            
            // Save employee to database
            try {
                Connection connection = DriverManager.getConnection(localHost, username, pass);
                // Insert employee into the database
                EmployeeService employeeService = new EmployeeService(connection);
                int employeeId = employeeService.insertEmployee(employee);

                EntityService entityService = new EntityService(connection);
                
                // Set the employee ID to the employee object
                employee.setEmployeeId(employeeId);
                
                // Insert education into the database
                employeeService.insertEducation(employee.getEmployeeId(), employee.getEducation());    
                
                // Insert work experiences into the database
                employeeService.insertWorkExperience(employee.getEmployeeId(), employee.getWorkExperiences());
                
                // Insert children into the database
                employeeService.insertChildren(employee.getEmployeeId(), employee.getChildren());
                
                // Navigate back to active employees list
                Object controller = App.setRoot("Active");
                ((ActiveController) controller).setUser(user);
                
                entityService.insertAuditLog(new AuditLog(user.getUsername(), employeeId, "Employee created successfully"));
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