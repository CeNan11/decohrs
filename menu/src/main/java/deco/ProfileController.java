package deco;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;
import java.text.SimpleDateFormat;

import entity.Employee;
import entity.User;

public class ProfileController {
    
    @FXML private Label employeeCode;
    @FXML private Label fullName;
    @FXML private Label currentAddress;
    @FXML private Label homeAddress;
    @FXML private Label position;
    @FXML private Label department;
    @FXML private Label hireDate;
    @FXML private Label regularizationDate;
    @FXML private Label contactNumberPrimary;
    @FXML private Label contactNumberSecondary;
    @FXML private Label emailAddress;
    @FXML private Label placeOfBirth;
    @FXML private Label dateOfBirth;
    @FXML private Label gender;
    @FXML private Label civilStatus;
    @FXML private Label bloodType;
    @FXML private Label sssNumber;
    @FXML private Label philHealthNumber;
    @FXML private Label tinNumber;
    @FXML private Label pagIbigNumber;

    private Employee employee;
    private User user;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");

    public void setUser(User user) {
        this.user = user;
        System.out.println("User set: " + user.getUsername());
    }

    public void setEmployee(Employee employee) {
        setEmployeeData(employee);
    }
    
    public void initData(Employee employee) {
        setEmployeeData(employee);
    }

    private void setEmployeeData(Employee employee) {
        this.employee = employee;
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
        position.setText(employee.getPosition());
        department.setText(employee.getDepartment());
        
        // Format dates
        if (employee.getHireDate() != null) {
            hireDate.setText(dateFormat.format(employee.getHireDate()));
        }
        if (employee.getRegularizationDate() != null) {
            regularizationDate.setText(dateFormat.format(employee.getRegularizationDate()));
        }
        
        contactNumberPrimary.setText(employee.getContactNumberPrimary());
        contactNumberSecondary.setText(employee.getContactNumberSecondary());
        emailAddress.setText(employee.getEmailAddress());
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