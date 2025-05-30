package deco;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;

import org.springframework.beans.BeanUtils;

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
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import services.EmployeeService;
import services.EntityService;


public class EditEmployeeController {
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
    private Employee employee;

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
            
            departments = entityService.getDepartments();
            positions = entityService.getPositions();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        gender.getItems().addAll("Male", "Female", "Other");
        civilStatus.getItems().addAll("Single", "Married", "Widowed", "Separated", "Divorced");
        bloodType.getItems().addAll("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-");

        position.getItems().addAll(positions.stream().map(Position::getPositionTitle).toArray(String[]::new));
        department.getItems().addAll(departments.stream().map(Department::getDepartmentName).toArray(String[]::new));

        childGender.getItems().addAll("Male", "Female");
        
        Platform.runLater(() -> {
            setEmployeeData(employee);
            newEmployee = new Employee(employee);
            transferButton.setText(employee.getStatus() == EmployeeStatus.ACTIVE ? "TRANSFER TO INACTIVE" : "TRANSFER TO ACTIVE");
        });
    }
    
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setUser(User user) {
        this.user = user;
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
        Child child = childrenTableView.getSelectionModel().getSelectedItem();
        if (child != null) {
            children.remove(child);
            childrenList.remove(child);
        }
    }

    @FXML   
    private void navigateToProfile() throws IOException {
        if (newEmployee.getStatus() == EmployeeStatus.ACTIVE) {
            Object controller = App.setRoot("ProfileActive");
            ((ProfileActiveController) controller).setEmployee(newEmployee);
            ((ProfileActiveController) controller).setUser(user);
        } else {
            Object controller = App.setRoot("ProfileInactive");
            ((ProfileInactiveController) controller).setEmployee(employee);
            ((ProfileInactiveController) controller).setUser(user);
        }
    }

    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private boolean confirmationDialog(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        return alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK;
    }

    @FXML
    private void handleSave() {
        if (!confirmationDialog("Are you sure you want to save the changes?", "Save Changes")) {
            return;
        }

        try {
            Connection connection = DriverManager.getConnection(localHost, username, pass);
            EntityService entityService = new EntityService(connection);

        boolean workChanged = checkIfChangedandSaveWorkAllExperience();
        boolean educationChanged = checkIfChangedandSaveEducation();
        boolean employeeChanged = checkIfChangedandSaveEmployee();
        boolean emergencyChanged = checkIfChangedandSaveEmergencyContact();
        boolean familyChanged = checkIfChangedandSaveFamilyBackground();
        boolean statusChanged = newEmployee.getStatus() != employee.getStatus();
        boolean childrenChanged = checkIfChangedandSaveChildren();

        if (employeeChanged || emergencyChanged || familyChanged || statusChanged ||
        (workChanged && educationChanged) || (workChanged && childrenChanged) || (educationChanged && childrenChanged)) {
            System.out.println("Employee has changed");
            updateEmployee();
            updateWorkExperience();
            updateEducation();
            updateChildren();
            entityService.insertAuditLog(new AuditLog("Employee updated successfully", employee.getEmployeeId(), user.getUsername()));
        }
        if (workChanged) {
            System.out.println("Work Experience has changed");
            updateWorkExperience();
            entityService.insertAuditLog(new AuditLog("Work Experience updated successfully", employee.getEmployeeId(), user.getUsername()));
        }
        if (educationChanged) {
            System.out.println("Education has changed");
            updateEducation();
            entityService.insertAuditLog(new AuditLog("Education updated successfully", employee.getEmployeeId(), user.getUsername()));
        }
        if (childrenChanged) {
            System.out.println("Children has changed");
            updateChildren();
            entityService.insertAuditLog(new AuditLog("Children updated successfully", employee.getEmployeeId(), user.getUsername()));
        }


        // Optionally: always print the newEmployee for debugging
        System.out.println(newEmployee);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            navigateToProfile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML private Button transferButton;

    private void updateChildren() {
        try {
            Connection connection = DriverManager.getConnection(localHost, username, pass);
            EmployeeService employeeService = new EmployeeService(connection);
            for (Child child : newEmployee.getChildren()) {
                employeeService.updateChild(child);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void updateEmployee() {
        try {
            Connection connection = DriverManager.getConnection(localHost, username, pass);
            EmployeeService employeeService = new EmployeeService(connection);
            employeeService.updateEmployee(newEmployee);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateEducation() {
        try {
            Connection connection = DriverManager.getConnection(localHost, username, pass);
            EmployeeService employeeService = new EmployeeService(connection);
            employeeService.updateEducation(newEmployee.getEducation());
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateWorkExperience() {
        try {
            Connection connection = DriverManager.getConnection(localHost, username, pass);
            EmployeeService employeeService = new EmployeeService(connection);
            for (WorkExperience workExperience : newEmployee.getWorkExperiences()) {
                employeeService.updateWorkExperience(workExperience);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void transferEmployee() {
        if (newEmployee.getStatus() == EmployeeStatus.ACTIVE) {
            System.out.println("Transferring to Inactive");
            transferButton.setText("TRANSFER TO ACTIVE");
            newEmployee.setStatus(EmployeeStatus.INACTIVE);
        } else {
            System.out.println("Transferring to Active");
            transferButton.setText("TRANSFER TO INACTIVE");
            newEmployee.setStatus(EmployeeStatus.ACTIVE);
        }
    }

    private Employee newEmployee;

    private boolean checkIfChangedandSaveChildren() {
        ArrayList<Child> oldChildren = employee.getChildren();
        ArrayList<Child> newChildren = new ArrayList<>();
        boolean isChanged = false;

        for (Child child : children) {
            Child newChild = new Child();
            BeanUtils.copyProperties(child, newChild);
            newChild.setEmployeeId(employee.getEmployeeId());
            newChildren.add(newChild);
        }

        if (!Objects.equals(oldChildren, newChildren)) {
            isChanged = true;
        }

        if (isChanged) {
            newEmployee.setChildren(newChildren);
        }
        return isChanged;
    }

    private boolean checkIfChangedandSaveEmployee() {
        Employee oldEmployee = this.employee;
        Employee newEmp = new Employee();
        boolean isChanged = false;

        // Make sure newEmp is an actual copy of the data, not a blank object
        BeanUtils.copyProperties(oldEmployee, newEmp);

        if (!Objects.equals(getTextOrNull(employeeCode), oldEmployee.getEmployeeCode())) {
            newEmp.setEmployeeCode(getTextOrNull(employeeCode));
            isChanged = true;
        }
        if (!Objects.equals(getTextOrNull(firstName), oldEmployee.getFirstName())) {
            newEmp.setFirstName(getTextOrNull(firstName));
            isChanged = true;
        }
        if (!Objects.equals(getTextOrNull(middleName), oldEmployee.getMiddleName())) {
            newEmp.setMiddleName(getTextOrNull(middleName));
            isChanged = true;
        }
        if (!Objects.equals(getTextOrNull(lastName), oldEmployee.getLastName())) {
            newEmp.setLastName(getTextOrNull(lastName));
            isChanged = true;
        }
        if (!Objects.equals(getTextOrNull(suffix), oldEmployee.getSuffix())) {
            newEmp.setSuffix(getTextOrNull(suffix));
            isChanged = true;
        }
        if (!Objects.equals(getTextOrNull(currentAddress), oldEmployee.getCurrentAddress())) {
            newEmp.setCurrentAddress(getTextOrNull(currentAddress));
            isChanged = true;
        }
        if (!Objects.equals(getTextOrNull(homeAddress), oldEmployee.getHomeAddress())) {
            newEmp.setHomeAddress(getTextOrNull(homeAddress));
            isChanged = true;
        }
        if (!Objects.equals(getTextOrNull(contactNumber), oldEmployee.getContactNumberPrimary())) {
            newEmp.setContactNumberPrimary(getTextOrNull(contactNumber));
            isChanged = true;
        }
        if (!Objects.equals(getTextOrNull(placeOfBirth), oldEmployee.getPlaceOfBirth())) {
            newEmp.setPlaceOfBirth(getTextOrNull(placeOfBirth));
            isChanged = true;
        }
        if (!Objects.equals(getDateOrNull(dateOfBirth), oldEmployee.getDateOfBirth())) {
            newEmp.setDateOfBirth(getDateOrNull(dateOfBirth));
            isChanged = true;
        }
        if (!Objects.equals(gender.getValue(), oldEmployee.getGender())) {
            newEmp.setGender(gender.getValue());
            isChanged = true;
        }
        if (!Objects.equals(civilStatus.getValue(), oldEmployee.getCivilStatus())) {
            newEmp.setCivilStatus(civilStatus.getValue());
            isChanged = true;
        }
        if (!Objects.equals(bloodType.getValue(), oldEmployee.getBloodType())) {
            newEmp.setBloodType(bloodType.getValue());
            isChanged = true;
        }
        Integer newDeptId = departments.stream().filter(d -> d.getDepartmentName().equals(department.getValue())).findFirst().orElse(null) != null ? departments.stream().filter(d -> d.getDepartmentName().equals(department.getValue())).findFirst().orElse(null).getDepartmentId() : null;
        if (!Objects.equals(newDeptId, oldEmployee.getDepartmentId())) {
            newEmp.setDepartmentId(newDeptId);
            isChanged = true;
        }
        Integer newPosId = positions.stream().filter(p -> p.getPositionTitle().equals(position.getValue())).findFirst().orElse(null) != null ? positions.stream().filter(p -> p.getPositionTitle().equals(position.getValue())).findFirst().orElse(null).getPositionId() : null;
        if (!Objects.equals(newPosId, oldEmployee.getPositionId())) {
            newEmp.setPositionId(newPosId);
            isChanged = true;
        }
        if (!Objects.equals(getDateOrNull(hireDate), oldEmployee.getHireDate())) {
            newEmp.setHireDate(getDateOrNull(hireDate));
            isChanged = true;
        }
        if (!Objects.equals(getDateOrNull(regularizationDate), oldEmployee.getRegularizationDate())) {
            newEmp.setRegularizationDate(getDateOrNull(regularizationDate));
            isChanged = true;
        }
        if (!Objects.equals(getTextOrNull(sssNumber), oldEmployee.getSSSNumber())) {
            newEmp.setSSSNumber(getTextOrNull(sssNumber));
            isChanged = true;
        }
        if (!Objects.equals(getTextOrNull(philHealthNumber), oldEmployee.getPHICNumber())) {
            newEmp.setPHICNumber(getTextOrNull(philHealthNumber));
            isChanged = true;
        }
        if (!Objects.equals(getTextOrNull(tinNumber), oldEmployee.getTIN())) {
            newEmp.setTIN(getTextOrNull(tinNumber));
            isChanged = true;
        }
        if (!Objects.equals(getTextOrNull(pagIbigNumber), oldEmployee.getHDMFNo())) {
            newEmp.setHDMFNo(getTextOrNull(pagIbigNumber));
            isChanged = true;
        }

        if (isChanged) {
            newEmployee = newEmp;
        }
        return isChanged;
    }

    private String getTextOrNull(TextField field) {
        String text = field.getText();
        return (text != null && !text.trim().isEmpty()) ? text.trim() : null;
    }

    private Date getDateOrNull(DatePicker picker) {
        return picker.getValue() != null ? Date.valueOf(picker.getValue()) : null;
    }
    private boolean checkIfChangedandSaveEmergencyContact() {
        EmergencyContact oldEmergencyContact = employee.getEmergencyContact();
        EmergencyContact newEmergencyContact = new EmergencyContact();
        boolean isChanged = false;
    
        // Make sure newEmergencyContact is an actual copy of the data, not a blank object
        BeanUtils.copyProperties(oldEmergencyContact, newEmergencyContact);
    
        //Check if fields have changes
        if (!Objects.equals(getTextOrNull(emergencyContactName), oldEmergencyContact.getName())) {
            newEmergencyContact.setName(getTextOrNull(emergencyContactName));
            isChanged = true;
        }
        if (!Objects.equals(getTextOrNull(emergencyContactRelationship), oldEmergencyContact.getRelationship())) {
            newEmergencyContact.setRelationship(getTextOrNull(emergencyContactRelationship));
            isChanged = true;
        }
        if (!Objects.equals(getTextOrNull(emergencyContactAddress), oldEmergencyContact.getAddress())) {
            newEmergencyContact.setAddress(getTextOrNull(emergencyContactAddress));
            isChanged = true;
        }
        if (!Objects.equals(getTextOrNull(emergencyContactNumber), oldEmergencyContact.getContactNumber())) {
            newEmergencyContact.setContactNumber(getTextOrNull(emergencyContactNumber));
            isChanged = true;
        }
    
        if (isChanged) {
            newEmployee.setEmergencyContact(newEmergencyContact);
        }
    
        return isChanged;
    }
    
    private boolean checkIfChangedandSaveFamilyBackground() {
        FamilyBackground oldFB = employee.getFamilyBackground();
        FamilyBackground newFB = new FamilyBackground();
        boolean isChanged = false;

        // Make sure newFB is an actual copy of the data, not a blank object
        BeanUtils.copyProperties(oldFB, newFB);

        if (!Objects.equals(getTextOrNull(fathersName), oldFB.getFatherName())) {
            newFB.setFatherName(getTextOrNull(fathersName));
            isChanged = true;
        }
        if (!Objects.equals(getDateOrNull(fathersBirthdate), oldFB.getFatherDOB())) {
            newFB.setFatherDOB(getDateOrNull(fathersBirthdate));
            isChanged = true;
        }
        if (!Objects.equals(getTextOrNull(mothersName), oldFB.getMotherName())) {
            newFB.setMotherName(getTextOrNull(mothersName));
            isChanged = true;
        }
        if (!Objects.equals(getDateOrNull(mothersBirthdate), oldFB.getMotherDOB())) {
            newFB.setMotherDOB(getDateOrNull(mothersBirthdate));
            isChanged = true;
        }
        if (!Objects.equals(getTextOrNull(spouseName), oldFB.getSpouseName())) {
            newFB.setSpouseName(getTextOrNull(spouseName));
            isChanged = true;
        }
        if (!Objects.equals(getTextOrNull(spouseAddress), oldFB.getSpouseAddress())) {
            newFB.setSpouseAddress(getTextOrNull(spouseAddress));
            isChanged = true;
        }
        if (!Objects.equals(getDateOrNull(spouseBirthDate), oldFB.getSpouseBirthDate())) {
            newFB.setSpouseBirthDate(getDateOrNull(spouseBirthDate));
            isChanged = true;
        }

        if (isChanged) {
            newEmployee.setFamilyBackground(newFB);
        }
        return isChanged;
    }

    private boolean checkIfChangedandSaveEducation() {
        Education oldEducation = employee.getEducation();
        Education newEducation = new Education();
        boolean isChanged = false;

        // Make sure newEducation is an actual copy of the data, not a blank object
        BeanUtils.copyProperties(oldEducation, newEducation);

        if (!Objects.equals(getTextOrNull(primarySchool), oldEducation.getPrimarySchool())) {
            newEducation.setPrimarySchool(getTextOrNull(primarySchool));
            isChanged = true;
        }
        if (!Objects.equals(getDateOrNull(primaryYearGraduated), oldEducation.getPrimaryYearGraduated())) {
            newEducation.setPrimaryYearGraduated(getDateOrNull(primaryYearGraduated));
            isChanged = true;
        }
        if (!Objects.equals(getTextOrNull(tertiarySchool), oldEducation.getTertiarySchool())) {
            newEducation.setTertiarySchool(getTextOrNull(tertiarySchool));
            isChanged = true;
        }
        if (!Objects.equals(getDateOrNull(tertiaryYearGraduated), oldEducation.getTertiaryYearGraduated())) {
            newEducation.setTertiaryYearGraduated(getDateOrNull(tertiaryYearGraduated));
            isChanged = true;
        }
        if (!Objects.equals(getTextOrNull(collegeSchool), oldEducation.getCollegeSchool())) {
            newEducation.setCollegeSchool(getTextOrNull(collegeSchool));
            isChanged = true;
        }
        if (!Objects.equals(getDateOrNull(collegeYearGraduated), oldEducation.getCollegeYearGraduated())) {
            newEducation.setCollegeYearGraduated(getDateOrNull(collegeYearGraduated));
            isChanged = true;
        }
        if (!Objects.equals(getTextOrNull(vocationalSchool), oldEducation.getVocationalSchool())) {
            newEducation.setVocationalSchool(getTextOrNull(vocationalSchool));
            isChanged = true;
        }
        if (!Objects.equals(getDateOrNull(vocationalYearGraduated), oldEducation.getVocationalYearGraduated())) {
            newEducation.setVocationalYearGraduated(getDateOrNull(vocationalYearGraduated));
            isChanged = true;
        }
        if (!Objects.equals(getTextOrNull(postGraduateSchool), oldEducation.getPostGraduateSchool())) {
            newEducation.setPostGraduateSchool(getTextOrNull(postGraduateSchool));
            isChanged = true;
        }
        if (!Objects.equals(getDateOrNull(postGraduateYearGraduated), oldEducation.getPostGraduateYearGraduated())) {
            newEducation.setPostGraduateYearGraduated(getDateOrNull(postGraduateYearGraduated));
            isChanged = true;
        }
        if (!Objects.equals(getTextOrNull(certificateLicenseName), oldEducation.getCertificateLicenseName())) {
            newEducation.setCertificateLicenseName(getTextOrNull(certificateLicenseName));
            isChanged = true;
        }
        if (!Objects.equals(getDateOrNull(dateIssued), oldEducation.getDateIssued())) {
            newEducation.setDateIssued(getDateOrNull(dateIssued));
            isChanged = true;
        }
        if (!Objects.equals(getDateOrNull(validUntil), oldEducation.getValidUntil())) {
            newEducation.setValidUntil(getDateOrNull(validUntil));
            isChanged = true;
        }

        if (isChanged) {
            newEducation.setEmployeeId(employee.getEmployeeId());
            newEmployee.setEducation(newEducation);
        }
        return isChanged;
    }

    private boolean checkIfChangedandSaveWorkAllExperience() throws NullPointerException {
        boolean isChanged = false;
        if (employee.getWorkExperiences().size() == 0) {
            return false;
        }
        ArrayList<WorkExperience> oldWorkExperiences = employee.getWorkExperiences();
        ArrayList<WorkExperience> newWorkExperiences = new ArrayList<>();

        // Helper to process each work experience
        for (int i = 0; i < oldWorkExperiences.size() && i < 3; i++) {
            WorkExperience oldWork = oldWorkExperiences.get(i);
            WorkExperience newWork = new WorkExperience();
            BeanUtils.copyProperties(oldWork, newWork);
            boolean localChanged = false;
            String newCompany = null, newPosition = null, newDuration = null, newRemarks = null;
            switch (i) {
                case 0:
                    newCompany = getTextOrNull(experienceCompany1);
                    newPosition = getTextOrNull(experiencePosition1);
                    newDuration = getTextOrNull(experienceDuration1);
                    newRemarks = getTextOrNull(experienceRemarks1);
                    break;
                case 1:
                    newCompany = getTextOrNull(experienceCompany2);
                    newPosition = getTextOrNull(experiencePosition2);
                    newDuration = getTextOrNull(experienceDuration2);
                    newRemarks = getTextOrNull(experienceRemarks2);
                    break;
                case 2:
                    newCompany = getTextOrNull(experienceCompany3);
                    newPosition = getTextOrNull(experiencePosition3);
                    newDuration = getTextOrNull(experienceDuration3);
                    newRemarks = getTextOrNull(experienceRemarks3);
                    break;
            }
            if (!Objects.equals(newCompany, oldWork.getCompanyName())) {
                newWork.setCompanyName(newCompany);
                localChanged = true;
            }
            if (!Objects.equals(newPosition, oldWork.getPositionHeld())) {
                newWork.setPositionHeld(newPosition);
                localChanged = true;
            }
            if (!Objects.equals(newDuration, oldWork.getDuration())) {
                newWork.setDuration(newDuration);
                localChanged = true;
            }
            if (!Objects.equals(newRemarks, oldWork.getRemarks())) {
                newWork.setRemarks(newRemarks);
                localChanged = true;
            }
            if (localChanged) {
                isChanged = true;
            }
            newWorkExperiences.add(newWork);
        }
        // If any changes, update newEmployee
        if (isChanged) {
            for (WorkExperience work : newWorkExperiences) {
                work.setEmployeeId(employee.getEmployeeId());
            }
            newEmployee.setWorkExperiences(newWorkExperiences);
        }
        return isChanged;
    }

    private void setEmployeeData(Employee employee) {
        employeeCode.setText(employee.getEmployeeCode());
        firstName.setText(employee.getFirstName());
        middleName.setText(employee.getMiddleName());
        lastName.setText(employee.getLastName());
        suffix.setText(employee.getSuffix());
        currentAddress.setText(employee.getCurrentAddress());
        homeAddress.setText(employee.getHomeAddress());
        if (employee.getHireDate() != null) {
            hireDate.setValue(employee.getHireDate().toLocalDate());
        }
        if (employee.getRegularizationDate() != null) {
            regularizationDate.setValue(employee.getRegularizationDate().toLocalDate());
        }
        contactNumber.setText(employee.getContactNumberPrimary());
        placeOfBirth.setText(employee.getPlaceOfBirth());
        if (employee.getDateOfBirth() != null) {
            dateOfBirth.setValue(employee.getDateOfBirth().toLocalDate());
        }
        gender.setValue(employee.getGender());
        civilStatus.setValue(employee.getCivilStatus());
        bloodType.setValue(employee.getBloodType());
        position.setValue(positions.stream().filter(p -> p.getPositionId() == employee.getPositionId()).findFirst().orElse(null).getPositionTitle());
        department.setValue(departments.stream().filter(d -> d.getDepartmentId() == employee.getDepartmentId()).findFirst().orElse(null).getDepartmentName());
        sssNumber.setText(employee.getSSSNumber());
        philHealthNumber.setText(employee.getPHICNumber());
        tinNumber.setText(employee.getTIN());
        pagIbigNumber.setText(employee.getHDMFNo());
        emergencyContactName.setText(employee.getEmergencyContact().getName());
        emergencyContactRelationship.setText(employee.getEmergencyContact().getRelationship());
        emergencyContactAddress.setText(employee.getEmergencyContact().getAddress());
        emergencyContactNumber.setText(employee.getEmergencyContact().getContactNumber());
        fathersName.setText(employee.getFamilyBackground().getFatherName());
        if (employee.getFamilyBackground().getFatherDOB() != null) {
            fathersBirthdate.setValue(employee.getFamilyBackground().getFatherDOB().toLocalDate());
        }
        mothersName.setText(employee.getFamilyBackground().getMotherName());
        if (employee.getFamilyBackground().getMotherDOB() != null) {
            mothersBirthdate.setValue(employee.getFamilyBackground().getMotherDOB().toLocalDate());
        }
        spouseName.setText(employee.getFamilyBackground().getSpouseName());
        spouseAddress.setText(employee.getFamilyBackground().getSpouseAddress());
        if (employee.getFamilyBackground().getSpouseBirthDate() != null) {
            spouseBirthDate.setValue(employee.getFamilyBackground().getSpouseBirthDate().toLocalDate());
        }
        primarySchool.setText(employee.getEducation().getPrimarySchool());
        if (employee.getEducation().getPrimaryYearGraduated() != null) {
            primaryYearGraduated.setValue(employee.getEducation().getPrimaryYearGraduated().toLocalDate());
        }
        tertiarySchool.setText(employee.getEducation().getTertiarySchool());
        if (employee.getEducation().getTertiaryYearGraduated() != null) {
            tertiaryYearGraduated.setValue(employee.getEducation().getTertiaryYearGraduated().toLocalDate());
        }
        collegeSchool.setText(employee.getEducation().getCollegeSchool());
        if (employee.getEducation().getCollegeYearGraduated() != null) {
            collegeYearGraduated.setValue(employee.getEducation().getCollegeYearGraduated().toLocalDate());
        }
        vocationalSchool.setText(employee.getEducation().getVocationalSchool());
        if (employee.getEducation().getVocationalYearGraduated() != null) {
            vocationalYearGraduated.setValue(employee.getEducation().getVocationalYearGraduated().toLocalDate());
        }
        postGraduateSchool.setText(employee.getEducation().getPostGraduateSchool());
        if (employee.getEducation().getPostGraduateYearGraduated() != null) {
            postGraduateYearGraduated.setValue(employee.getEducation().getPostGraduateYearGraduated().toLocalDate());
        }
        certificateLicenseName.setText(employee.getEducation().getCertificateLicenseName());
        if (employee.getEducation().getDateIssued() != null) {
            dateIssued.setValue(employee.getEducation().getDateIssued().toLocalDate());
        }
        if (employee.getEducation().getValidUntil() != null) {
            validUntil.setValue(employee.getEducation().getValidUntil().toLocalDate());
        }

        if (employee.getEducation() != null) {
            if (employee.getEducation().getPrimarySchool() != null) {
                primarySchool.setText(employee.getEducation().getPrimarySchool());
            }
            if (employee.getEducation().getPrimaryYearGraduated() != null) {
                primaryYearGraduated.setValue(employee.getEducation().getPrimaryYearGraduated().toLocalDate());
            }
            if (employee.getEducation().getTertiarySchool() != null) {
                tertiarySchool.setText(employee.getEducation().getTertiarySchool());
            }
            if (employee.getEducation().getTertiaryYearGraduated() != null) {
                tertiaryYearGraduated.setValue(employee.getEducation().getTertiaryYearGraduated().toLocalDate());
            }
            if (employee.getEducation().getCollegeSchool() != null) {
                collegeSchool.setText(employee.getEducation().getCollegeSchool());
            }
            if (employee.getEducation().getCollegeYearGraduated() != null) {
                collegeYearGraduated.setValue(employee.getEducation().getCollegeYearGraduated().toLocalDate());
            }
            if (employee.getEducation().getVocationalSchool() != null) {
                vocationalSchool.setText(employee.getEducation().getVocationalSchool());
            }
            if (employee.getEducation().getVocationalYearGraduated() != null) {
                vocationalYearGraduated.setValue(employee.getEducation().getVocationalYearGraduated().toLocalDate());
            }
            if (employee.getEducation().getPostGraduateSchool() != null) {
                postGraduateSchool.setText(employee.getEducation().getPostGraduateSchool());
            }
            if (employee.getEducation().getPostGraduateYearGraduated() != null) {
                postGraduateYearGraduated.setValue(employee.getEducation().getPostGraduateYearGraduated().toLocalDate());
            }
            if (employee.getEducation().getCertificateLicenseName() != null) {
                certificateLicenseName.setText(employee.getEducation().getCertificateLicenseName());
            }
            if (employee.getEducation().getDateIssued() != null) {
                dateIssued.setValue(employee.getEducation().getDateIssued().toLocalDate());
            }
            if (employee.getEducation().getValidUntil() != null) {
                validUntil.setValue(employee.getEducation().getValidUntil().toLocalDate());
            }
        }
        if (employee.getWorkExperiences().size() > 0) {
            experienceCompany1.setText(employee.getWorkExperiences().get(0).getCompanyName());
            experiencePosition1.setText(employee.getWorkExperiences().get(0).getPositionHeld());
            experienceDuration1.setText(employee.getWorkExperiences().get(0).getDuration());
            experienceRemarks1.setText(employee.getWorkExperiences().get(0).getRemarks());
        }
        if (employee.getWorkExperiences().size() > 1) {
            experienceCompany2.setText(employee.getWorkExperiences().get(1).getCompanyName());
            experiencePosition2.setText(employee.getWorkExperiences().get(1).getPositionHeld());
            experienceDuration2.setText(employee.getWorkExperiences().get(1).getDuration());
            experienceRemarks2.setText(employee.getWorkExperiences().get(1).getRemarks());
        }
        if (employee.getWorkExperiences().size() > 2) {
            experienceCompany3.setText(employee.getWorkExperiences().get(2).getCompanyName());
            experiencePosition3.setText(employee.getWorkExperiences().get(2).getPositionHeld());
            experienceDuration3.setText(employee.getWorkExperiences().get(2).getDuration());
            experienceRemarks3.setText(employee.getWorkExperiences().get(2).getRemarks());
        }

        initializeChildren();
        initializeChildrenTable();
        setChildren(employee.getChildren());
    }

    private void initializeChildren() {
        childrenTableView.setItems(childrenList);
    }

    private void setChildren(ArrayList<Child> children) {
        childrenList.setAll(children);
    }

    private <T> boolean updateIfChanged(T oldValue, T newValue, Consumer<T> setter) {
        if (!Objects.equals(oldValue, newValue)) {
            setter.accept(newValue);
            return true;
        }
        return false;
    }
}