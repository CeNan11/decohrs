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

import entity.Employee;
import entity.EmployeeStatus;
import entity.FamilyBackground;
import entity.Position;
import entity.User;
import entity.WorkExperience;
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
        System.out.println(employee.getEmployeeId());
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

        Object controller = App.setRoot("ProfileActive");
        ((ProfileActiveController) controller).setEmployee(employee);
        ((ProfileActiveController) controller).setUser(user);
    }
    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void handleSave() {
        if (checkIfChangedandSaveEmployee() || checkIfChangedandSaveEmergencyContact() || checkIfChangedandSaveFamilyBackground() || newEmployee.getStatus() != employee.getStatus()) {
            System.out.println("Employee has changed");
            updateEmployee();
        }
        if (checkIfChangedandSaveEducation()) {
            System.out.println("Education has changed");
        }
        if (checkIfChangedandSaveWorkAllExperience()) {
            System.out.println("Work Experience has changed");
        }
    }

    @FXML private Button transferButton;

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

    private boolean checkIfChangedandSaveEmployee() {
        Employee oldEmployee = this.employee;
        Employee employee = this.employee;
        boolean isChanged = false;

        isChanged |= updateIfChanged(oldEmployee.getEmployeeCode(), getTextOrNull(employeeCode), employee::setEmployeeCode);
        isChanged |= updateIfChanged(oldEmployee.getFirstName(), getTextOrNull(firstName), employee::setFirstName);
        isChanged |= updateIfChanged(oldEmployee.getMiddleName(), getTextOrNull(middleName), employee::setMiddleName);
        isChanged |= updateIfChanged(oldEmployee.getLastName(), getTextOrNull(lastName), employee::setLastName);
        isChanged |= updateIfChanged(oldEmployee.getSuffix(), getTextOrNull(suffix), employee::setSuffix);
        isChanged |= updateIfChanged(oldEmployee.getCurrentAddress(), getTextOrNull(currentAddress), employee::setCurrentAddress);
        isChanged |= updateIfChanged(oldEmployee.getHomeAddress(), getTextOrNull(homeAddress), employee::setHomeAddress);
        isChanged |= updateIfChanged(oldEmployee.getContactNumberPrimary(), getTextOrNull(contactNumber), employee::setContactNumberPrimary);
        isChanged |= updateIfChanged(oldEmployee.getPlaceOfBirth(), getTextOrNull(placeOfBirth), employee::setPlaceOfBirth);
        isChanged |= updateIfChanged(oldEmployee.getDateOfBirth(), getDateOrNull(dateOfBirth), employee::setDateOfBirth);
        isChanged |= updateIfChanged(oldEmployee.getGender(), gender.getValue(), employee::setGender);
        isChanged |= updateIfChanged(oldEmployee.getCivilStatus(), civilStatus.getValue(), employee::setCivilStatus);
        isChanged |= updateIfChanged(oldEmployee.getBloodType(), bloodType.getValue(), employee::setBloodType);
        isChanged |= updateIfChanged(oldEmployee.getDepartmentId(), departments.stream().filter(d -> d.getDepartmentName().equals(department.getValue())).findFirst().orElse(null).getDepartmentId(), employee::setDepartmentId);
        isChanged |= updateIfChanged(oldEmployee.getPositionId(), positions.stream().filter(p -> p.getPositionTitle().equals(position.getValue())).findFirst().orElse(null).getPositionId(), employee::setPositionId);
        isChanged |= updateIfChanged(oldEmployee.getHireDate(), getDateOrNull(hireDate), employee::setHireDate);
        isChanged |= updateIfChanged(oldEmployee.getRegularizationDate(), getDateOrNull(regularizationDate), employee::setRegularizationDate);
        isChanged |= updateIfChanged(oldEmployee.getSSSNumber(), getTextOrNull(sssNumber), employee::setSSSNumber);
        isChanged |= updateIfChanged(oldEmployee.getPHICNumber(), getTextOrNull(philHealthNumber), employee::setPHICNumber);
        isChanged |= updateIfChanged(oldEmployee.getTIN(), getTextOrNull(tinNumber), employee::setTIN);
        isChanged |= updateIfChanged(oldEmployee.getHDMFNo(), getTextOrNull(pagIbigNumber), employee::setHDMFNo);

        if (isChanged) {
            newEmployee = new Employee(employee);
            System.out.println(newEmployee);
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
        EmergencyContact emergencyContact = new EmergencyContact();
        boolean isChanged = false;

        isChanged |= updateIfChanged(oldEmergencyContact.getName(), getTextOrNull(emergencyContactName), emergencyContact::setName);
        isChanged |= updateIfChanged(oldEmergencyContact.getRelationship(), getTextOrNull(emergencyContactRelationship), emergencyContact::setRelationship);
        isChanged |= updateIfChanged(oldEmergencyContact.getAddress(), getTextOrNull(emergencyContactAddress), emergencyContact::setAddress);
        isChanged |= updateIfChanged(oldEmergencyContact.getContactNumber(), getTextOrNull(emergencyContactNumber), emergencyContact::setContactNumber);

        if (isChanged) {
            newEmployee.setEmergencyContact(emergencyContact);
        }
        return isChanged;
    }

    private boolean checkIfChangedandSaveFamilyBackground() {
        FamilyBackground oldFB = employee.getFamilyBackground();
        FamilyBackground newFB = new FamilyBackground();
        boolean isChanged = false;

        isChanged |= updateIfChanged(oldFB.getFatherName(), getTextOrNull(fathersName), newFB::setFatherName);
        isChanged |= updateIfChanged(oldFB.getFatherDOB(), getDateOrNull(fathersBirthdate), newFB::setFatherDOB);
        isChanged |= updateIfChanged(oldFB.getMotherName(), getTextOrNull(mothersName), newFB::setMotherName);
        isChanged |= updateIfChanged(oldFB.getMotherDOB(), getDateOrNull(mothersBirthdate), newFB::setMotherDOB);
        isChanged |= updateIfChanged(oldFB.getSpouseName(), getTextOrNull(spouseName), newFB::setSpouseName);
        isChanged |= updateIfChanged(oldFB.getSpouseAddress(), getTextOrNull(spouseAddress), newFB::setSpouseAddress);
        isChanged |= updateIfChanged(oldFB.getSpouseBirthDate(), getDateOrNull(spouseBirthDate), newFB::setSpouseBirthDate);

        if (isChanged) {
            newEmployee.setFamilyBackground(newFB);
        }
        return isChanged;
    }

    private boolean checkIfChangedandSaveEducation() {
        Education oldEducation = employee.getEducation();
        Education education = new Education();
        boolean isChanged = false;

        isChanged |= updateIfChanged(oldEducation.getPrimarySchool(), getTextOrNull(primarySchool), education::setPrimarySchool);
        isChanged |= updateIfChanged(oldEducation.getPrimaryYearGraduated(), getDateOrNull(primaryYearGraduated), education::setPrimaryYearGraduated);
        isChanged |= updateIfChanged(oldEducation.getTertiarySchool(), getTextOrNull(tertiarySchool), education::setTertiarySchool);
        isChanged |= updateIfChanged(oldEducation.getTertiaryYearGraduated(), getDateOrNull(tertiaryYearGraduated), education::setTertiaryYearGraduated);
        isChanged |= updateIfChanged(oldEducation.getCollegeSchool(), getTextOrNull(collegeSchool), education::setCollegeSchool);
        isChanged |= updateIfChanged(oldEducation.getCollegeYearGraduated(), getDateOrNull(collegeYearGraduated), education::setCollegeYearGraduated);
        isChanged |= updateIfChanged(oldEducation.getVocationalSchool(), getTextOrNull(vocationalSchool), education::setVocationalSchool);
        isChanged |= updateIfChanged(oldEducation.getVocationalYearGraduated(), getDateOrNull(vocationalYearGraduated), education::setVocationalYearGraduated);
        isChanged |= updateIfChanged(oldEducation.getPostGraduateSchool(), getTextOrNull(postGraduateSchool), education::setPostGraduateSchool);
        isChanged |= updateIfChanged(oldEducation.getPostGraduateYearGraduated(), getDateOrNull(postGraduateYearGraduated), education::setPostGraduateYearGraduated);
        isChanged |= updateIfChanged(oldEducation.getCertificateLicenseName(), getTextOrNull(certificateLicenseName), education::setCertificateLicenseName);
        isChanged |= updateIfChanged(oldEducation.getDateIssued(), getDateOrNull(dateIssued), education::setDateIssued);
        isChanged |= updateIfChanged(oldEducation.getValidUntil(), getDateOrNull(validUntil), education::setValidUntil);

        if (isChanged) {
            newEmployee.setEducation(education);
        }
        return isChanged;
    }

    private boolean checkIfChangedandSaveWorkAllExperience() throws NullPointerException {
        boolean isChanged = false;
        
        if (employee.getWorkExperiences().size() == 0) {
            return false;
        }

        WorkExperience work1 = new WorkExperience();
        WorkExperience oldWorkExperience = employee.getWorkExperiences().get(0);
        String oldWorkCompany1 = oldWorkExperience.getCompanyName();
        String newWorkCompany1 = experienceCompany1.getText().trim() != null ? experienceCompany1.getText().trim() : null;
        if (oldWorkCompany1 != null && newWorkCompany1 != null) {
            if (!newWorkCompany1.equals(oldWorkCompany1)) {
                work1.setCompanyName(experienceCompany1.getText().trim());
                isChanged = true;
            }
        }
        String oldWorkPosition1 = oldWorkExperience.getPositionHeld();
        String newWorkPosition1 = experiencePosition1.getText().trim() != null ? experiencePosition1.getText().trim() : null;
        if (oldWorkPosition1 != null && newWorkPosition1 != null) {
            if (!newWorkPosition1.equals(oldWorkPosition1)) {
                work1.setPositionHeld(experiencePosition1.getText().trim());
                isChanged = true;
            }
        }
        String oldWorkDuration1 = oldWorkExperience.getDuration();
        String newWorkDuration1 = experienceDuration1.getText().trim() != null ? experienceDuration1.getText().trim() : null;
        if (oldWorkDuration1 != null && newWorkDuration1 != null) {
            if (!newWorkDuration1.equals(oldWorkDuration1)) {
                work1.setDuration(experienceDuration1.getText().trim());
                isChanged = true;
            }
        }
        String oldWorkRemarks1 = oldWorkExperience.getRemarks();
        String newWorkRemarks1 = experienceRemarks1.getText().trim() != null ? experienceRemarks1.getText().trim() : null;
        if (oldWorkRemarks1 != null && newWorkRemarks1 != null) {
            if (!newWorkRemarks1.equals(oldWorkRemarks1)) {
                work1.setRemarks(experienceRemarks1.getText().trim());
                isChanged = true;
            }
        }

        if (employee.getWorkExperiences().size() == 1) {
            if (isChanged) {
                newEmployee.setWorkExperiences(new ArrayList<>(Arrays.asList(work1)));
            }
            return isChanged;
        }

        WorkExperience work2 = new WorkExperience();
        oldWorkExperience = employee.getWorkExperiences().get(1);
        String oldWorkCompany2 = oldWorkExperience.getCompanyName();
        String newWorkCompany2 = experienceCompany2.getText().trim() != null ? experienceCompany2.getText().trim() : null;
        if (oldWorkCompany2 != null && newWorkCompany2 != null) {
            if (!newWorkCompany2.equals(oldWorkCompany2)) {
                work2.setCompanyName(experienceCompany2.getText().trim());
                isChanged = true;
            }
        }
        String oldWorkPosition2 = oldWorkExperience.getPositionHeld();
        String newWorkPosition2 = experiencePosition2.getText().trim() != null ? experiencePosition2.getText().trim() : null;
        if (oldWorkPosition2 != null && newWorkPosition2 != null) {
            if (!newWorkPosition2.equals(oldWorkPosition2)) {
                work2.setPositionHeld(experiencePosition2.getText().trim());
                isChanged = true;
            }
        }
        String oldWorkDuration2 = oldWorkExperience.getDuration();
        String newWorkDuration2 = experienceDuration2.getText().trim() != null ? experienceDuration2.getText().trim() : null;
        if (oldWorkDuration2 != null && newWorkDuration2 != null) {
            if (!newWorkDuration2.equals(oldWorkDuration2)) {
                work2.setDuration(experienceDuration2.getText().trim());
                isChanged = true;
            }
        }
        String oldWorkRemarks2 = oldWorkExperience.getRemarks();
        String newWorkRemarks2 = experienceRemarks2.getText().trim() != null ? experienceRemarks2.getText().trim() : null;
        if (oldWorkRemarks2 != null && newWorkRemarks2 != null) {
            if (!newWorkRemarks2.equals(oldWorkRemarks2)) {
                work2.setRemarks(experienceRemarks2.getText().trim());
                isChanged = true;
            }
        }

        if (employee.getWorkExperiences().size() == 2) {
            if (isChanged) {
                newEmployee.setWorkExperiences(new ArrayList<>(Arrays.asList(work1, work2)));
            }
            return isChanged;
        }

        WorkExperience work3 = new WorkExperience();    
        oldWorkExperience = employee.getWorkExperiences().get(2);
        String oldWorkCompany3 = oldWorkExperience.getCompanyName();
        String newWorkCompany3 = experienceCompany3.getText().trim() != null ? experienceCompany3.getText().trim() : null;
        if (oldWorkCompany3 != null && newWorkCompany3 != null) {
            if (!newWorkCompany3.equals(oldWorkCompany3)) {
                work3.setCompanyName(experienceCompany3.getText().trim());
                isChanged = true;
            }
        }
        String oldWorkPosition3 = oldWorkExperience.getPositionHeld();
        String newWorkPosition3 = experiencePosition3.getText().trim() != null ? experiencePosition3.getText().trim() : null;
        if (oldWorkPosition3 != null && newWorkPosition3 != null) {
            if (!newWorkPosition3.equals(oldWorkPosition3)) {
                work3.setPositionHeld(experiencePosition3.getText().trim());
                isChanged = true;
            }
        }
        String oldWorkDuration3 = oldWorkExperience.getDuration();
        String newWorkDuration3 = experienceDuration3.getText().trim() != null ? experienceDuration3.getText().trim() : null;
        if (oldWorkDuration3 != null && newWorkDuration3 != null) {
            if (!newWorkDuration3.equals(oldWorkDuration3)) {
                work3.setDuration(experienceDuration3.getText().trim());
                isChanged = true;
            }
        }
        String oldWorkRemarks3 = oldWorkExperience.getRemarks();
        String newWorkRemarks3 = experienceRemarks3.getText().trim() != null ? experienceRemarks3.getText().trim() : null;
        if (oldWorkRemarks3 != null && newWorkRemarks3 != null) {
            if (!newWorkRemarks3.equals(oldWorkRemarks3)) {
                work3.setRemarks(experienceRemarks3.getText().trim());
                isChanged = true;
            }
        }

        if (isChanged) {
            newEmployee.setWorkExperiences(new ArrayList<>(Arrays.asList(work1, work2, work3)));
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