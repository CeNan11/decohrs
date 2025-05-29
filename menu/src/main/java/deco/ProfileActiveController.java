package deco;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;
import java.text.SimpleDateFormat;

import entity.Employee;
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
    

    private Employee employee;
    private User user;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");

    @FXML
    private void initialize() {
        Platform.runLater(() -> {
            setEmployeeData();
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
        position.setText(employee.getPositionId().toString());
        department.setText(employee.getDepartmentId().toString());
            
        // Format dates
        if (employee.getHireDate() != null) {
            hireDate.setText(dateFormat.format(employee.getHireDate()));
        }
        if (employee.getRegularizationDate() != null) {
            regularizationDate.setText(dateFormat.format(employee.getRegularizationDate()));
        }
        
        contactNumber.setText(employee.getContactNumberPrimary());
        placeOfBirth.setText(employee.getPlaceOfBirth());
        
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

        if (employee.getEducation().getPrimarySchool() != null) {
            primarySchool.setText(employee.getEducation().getPrimarySchool());
        }
        if (employee.getEducation().getPrimaryYearGraduated() != null) {
            primaryYearGraduated.setText(dateFormat.format(employee.getEducation().getPrimaryYearGraduated()));
        }
        if (employee.getEducation().getTertiarySchool() != null) {
            tertiarySchool.setText(employee.getEducation().getTertiarySchool());
        }
        tertiaryYearGraduated.setText(dateFormat.format(employee.getEducation().getTertiaryYearGraduated() != null ? employee.getEducation().getTertiaryYearGraduated() : ""));
        if (employee.getEducation().getCollegeSchool() != null) {
            collegeSchool.setText(employee.getEducation().getCollegeSchool());
        }
        collegeYearGraduated.setText(dateFormat.format(employee.getEducation().getCollegeYearGraduated() != null ? employee.getEducation().getCollegeYearGraduated() : ""));
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

    public Employee getEmployee() {
        return employee;
    }

    @FXML
    private void navigateToActive() throws IOException {
        Object controller = App.setRoot("Active");
        ((ActiveController) controller).setUser(user);
    }
}