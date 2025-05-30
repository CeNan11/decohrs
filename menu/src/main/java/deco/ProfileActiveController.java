package deco;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import services.EntityService;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import entity.Child;
import entity.Department;
import entity.Employee;
import entity.Position;
import entity.User;

public class ProfileActiveController {
    
    // Basic Information
    @FXML private Label employeeCode;
    @FXML private Label fullName;
    @FXML private Label currentAddress;
    @FXML private Label homeAddress;
    @FXML private Label position;
    @FXML private Label department;
    @FXML private Label hireDate;
    @FXML private Label regularizationDate;
    @FXML private Label contactNumber;

    // Emergency Contact Information
    @FXML private Label emergencyContactName;
    @FXML private Label emergencyContactNumber;
    @FXML private Label emergencyContactRelationship;
    @FXML private Label emergencyContactAddress;

    // Personal Information
    @FXML private Label placeOfBirth;
    @FXML private Label dateOfBirth;
    @FXML private Label gender;
    @FXML private Label civilStatus;
    @FXML private Label bloodType;

    // Government Information
    @FXML private Label sssNumber;
    @FXML private Label philHealthNumber;
    @FXML private Label tinNumber;
    @FXML private Label pagIbigNumber;

    @FXML private Label fathersName;
    @FXML private Label fathersBirthdate;
    @FXML private Label mothersName;
    @FXML private Label mothersBirthdate;
    @FXML private Label numberOfSiblings;
    @FXML private Label spouseName;
    @FXML private Label spouseAddress;
    @FXML private Label spouseBirthDate;

    // Education
    @FXML private Label primarySchool;
    @FXML private Label primaryYearGraduated;
    @FXML private Label tertiarySchool;
    @FXML private Label tertiaryYearGraduated;
    @FXML private Label collegeSchool;
    @FXML private Label collegeYearGraduated;
    @FXML private Label vocationalSchool;
    @FXML private Label vocationalYearGraduated;
    @FXML private Label postGraduateSchool;
    @FXML private Label postGraduateYearGraduated;
    @FXML private Label certificateLicenseName;
    @FXML private Label dateIssued;
    @FXML private Label validUntil;

    // Work Experience
    @FXML private Label experienceDuration1;
    @FXML private Label experienceDuration2;
    @FXML private Label experienceDuration3;
    @FXML private Label experienceCompany1;
    @FXML private Label experienceCompany2;
    @FXML private Label experienceCompany3;
    @FXML private Label experiencePosition1;
    @FXML private Label experiencePosition2;
    @FXML private Label experiencePosition3;
    @FXML private Label experienceRemarks1;
    @FXML private Label experienceRemarks2;
    @FXML private Label experienceRemarks3;
    @FXML private VBox adminView;

    private Employee employee;
    private User user;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");

    private static String localHost = "jdbc:mysql://localhost:3306/DECOHRS_DB";
    private static String username = "root";
    private static String pass = "";

    @FXML
    private void initialize() {
        Platform.runLater(() -> {
            setEmployeeData();
            if (user.getRole() == User.roles.GUEST) {
                adminView.setVisible(false);
            } else {
                adminView.setVisible(true);
            }
        });
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
        System.out.println("Employee set: " + employee);
    }

    public void setUser(User user) {
        this.user = user;
    }

    private void setEmployeeData() {
        employeeCode.setText(employee.getEmployeeCode());
        
        // Format full name with suffix if present
        String fullNameText = employee.getFirstName();
        if (employee.getMiddleName() != null && !employee.getMiddleName().isEmpty()) {
            fullNameText += " " + employee.getMiddleName();
        }
        fullNameText += " " + employee.getLastName();
        if (employee.getSuffix() != null && !employee.getSuffix().isEmpty()) {
            fullNameText += ", " + employee.getSuffix();
        }
        fullName.setText(fullNameText);
        
        currentAddress.setText(employee.getCurrentAddress());
        homeAddress.setText(employee.getHomeAddress()); 
        
        Position position = null;
        Department department = null;
        
        try {
            Connection connection = DriverManager.getConnection(localHost, username, pass);
            EntityService entityService = new EntityService(connection);
            position = entityService.getPositionById(Integer.parseInt(employee.getPositionId().toString()));
            department = entityService.getDepartmentById(Integer.parseInt(employee.getDepartmentId().toString()));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.position.setText(position.getPositionTitle());
        this.department.setText(department.getDepartmentName());
            
        // Format dates
        if (employee.getHireDate() != null) {
            hireDate.setText(dateFormat.format(employee.getHireDate()));
        }
        if (employee.getRegularizationDate() != null) {
            regularizationDate.setText(dateFormat.format(employee.getRegularizationDate()));
        }
        
        contactNumber.setText(employee.getContactNumberPrimary());
        placeOfBirth.setText(employee.getPlaceOfBirth());

        if (employee.getEmergencyContact() != null) {
            emergencyContactName.setText(employee.getEmergencyContact().getName());
        }
        if (employee.getEmergencyContact().getContactNumber() != null) {
            emergencyContactNumber.setText(employee.getEmergencyContact().getContactNumber());
        }
        if (employee.getEmergencyContact().getRelationship() != null) {
            emergencyContactRelationship.setText(employee.getEmergencyContact().getRelationship());
        }
        if (employee.getEmergencyContact().getAddress() != null) {
            emergencyContactAddress.setText(employee.getEmergencyContact().getAddress());
        }
        
        
        if (employee.getDateOfBirth() != null) {
            dateOfBirth.setText(dateFormat.format(employee.getDateOfBirth()));
        }
        
        if (employee.getGender() != null) {
            gender.setText(employee.getGender());
        }
        if (employee.getCivilStatus() != null) {
            civilStatus.setText(employee.getCivilStatus());
        }
        if (employee.getBloodType() != null) {
            bloodType.setText(employee.getBloodType());
        }
        if (employee.getSSSNumber() != null) {
            sssNumber.setText(employee.getSSSNumber());
        }
        if (employee.getPHICNumber() != null) {
            philHealthNumber.setText(employee.getPHICNumber());
        }
        if (employee.getTIN() != null) {
            tinNumber.setText(employee.getTIN());
        }
        if (employee.getHDMFNo() != null) {
            pagIbigNumber.setText(employee.getHDMFNo());
        }

        if (employee.getFamilyBackground().getFatherName() != null) {
            fathersName.setText(employee.getFamilyBackground().getFatherName());
        }
        if (employee.getFamilyBackground().getFatherDOB() != null) {
            fathersBirthdate.setText(dateFormat.format(employee.getFamilyBackground().getFatherDOB()));
        }
        if (employee.getFamilyBackground().getMotherName() != null) {
            mothersName.setText(employee.getFamilyBackground().getMotherName());
        }
        if (employee.getFamilyBackground().getMotherDOB() != null) {
            mothersBirthdate.setText(dateFormat.format(employee.getFamilyBackground().getMotherDOB()));
        }
        if (employee.getFamilyBackground().getNumberOfSiblings() != null) {
            numberOfSiblings.setText(employee.getFamilyBackground().getNumberOfSiblings().toString());
        }
        if (employee.getFamilyBackground().getSpouseName() != null) {
            spouseName.setText(employee.getFamilyBackground().getSpouseName());
        }
        if (employee.getFamilyBackground().getSpouseAddress() != null) {
            spouseAddress.setText(employee.getFamilyBackground().getSpouseAddress());
        }
        if (employee.getFamilyBackground().getSpouseBirthDate() != null) {
            spouseBirthDate.setText(dateFormat.format(employee.getFamilyBackground().getSpouseBirthDate()));
        }
        if (employee.getEducation() != null) {
            if (employee.getEducation().getPrimarySchool() != null) {
                primarySchool.setText(employee.getEducation().getPrimarySchool());
            }
            if (employee.getEducation().getPrimaryYearGraduated() != null) {
                primaryYearGraduated.setText(dateFormat.format(employee.getEducation().getPrimaryYearGraduated()));
            }
            if (employee.getEducation().getTertiarySchool() != null) {
                tertiarySchool.setText(employee.getEducation().getTertiarySchool());
            }
            if (employee.getEducation().getTertiaryYearGraduated() != null) {
                tertiaryYearGraduated.setText(dateFormat.format(employee.getEducation().getTertiaryYearGraduated()));
            }
            if (employee.getEducation().getCollegeSchool() != null) {
                collegeSchool.setText(employee.getEducation().getCollegeSchool());
            }
            if (employee.getEducation().getCollegeYearGraduated() != null) {
                collegeYearGraduated.setText(dateFormat.format(employee.getEducation().getCollegeYearGraduated()));
            }
            if (employee.getEducation().getVocationalSchool() != null) {
                vocationalSchool.setText(employee.getEducation().getVocationalSchool());
            }
            if (employee.getEducation().getVocationalYearGraduated() != null) {
                vocationalYearGraduated.setText(dateFormat.format(employee.getEducation().getVocationalYearGraduated()));
            }
            if (employee.getEducation().getPostGraduateSchool() != null) {
                postGraduateSchool.setText(employee.getEducation().getPostGraduateSchool());
            }
            if (employee.getEducation().getPostGraduateYearGraduated() != null) {
                postGraduateYearGraduated.setText(dateFormat.format(employee.getEducation().getPostGraduateYearGraduated()));
            }
            if (employee.getEducation().getCertificateLicenseName() != null) {
                certificateLicenseName.setText(employee.getEducation().getCertificateLicenseName());
            }
            if (employee.getEducation().getDateIssued() != null) {
                dateIssued.setText(dateFormat.format(employee.getEducation().getDateIssued()));
            }
            if (employee.getEducation().getValidUntil() != null) {
                validUntil.setText(dateFormat.format(employee.getEducation().getValidUntil()));
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

    private ObservableList<Child> childrenList = FXCollections.observableArrayList();
    @FXML private TableView<Child> childrenTableView;
    @FXML private TableColumn<Child, String> nameColumn;
    @FXML private TableColumn<Child, String> dateOfBirthColumn;
    @FXML private TableColumn<Child, String> placeOfBirthColumn;
    @FXML private TableColumn<Child, String> genderColumn;

    private void initializeChildren() {
        childrenTableView.setItems(childrenList);
    }

    private void initializeChildrenTable() {
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        dateOfBirthColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDateOfBirth().toString()));
        placeOfBirthColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPlaceOfBirth()));
        genderColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGender()));
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setChildren(ArrayList<Child> children) {
        childrenList.setAll(children);
    }

    @FXML
    private void navigateToEdit() throws IOException {
        Object controller = App.setRoot("EditEmployee");
        ((EditEmployeeController) controller).setUser(user);
        ((EditEmployeeController) controller).setEmployee(employee);
    }

    @FXML
    private void navigateToActive() throws IOException {
        Object controller = App.setRoot("Active");
        ((ActiveController) controller).setUser(user);
    }
}