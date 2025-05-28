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
        
        gender.setText(employee.getGender());
        civilStatus.setText(employee.getCivilStatus());
        bloodType.setText(employee.getBloodType());
        sssNumber.setText(employee.getSSSNumber());
        philHealthNumber.setText(employee.getPHICNumber());
        tinNumber.setText(employee.getTIN());
        pagIbigNumber.setText(employee.getHDMFNo());
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