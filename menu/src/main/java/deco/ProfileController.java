package deco;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import java.io.IOException;


import entity.Employee;
import entity.User;

public class ProfileController {
    
    @FXML private Label employeeNo;
    @FXML private Label fullName;
    @FXML private Label currentAddress;
    @FXML private Label homeAddress;
    @FXML private Label position;
    @FXML private Label department;
    @FXML private Label dateHired;
    @FXML private Label dateRegularized;
    @FXML private Label contactNumber;
    @FXML private Label placeOfBirth;
    @FXML private Label dateOfBirth;
    @FXML private Label gender;
    @FXML private Label civilStatus;
    @FXML private Label bloodType;
    @FXML private Label SSSNumber;
    @FXML private Label PHICNumber;
    @FXML private Label TIN;
    @FXML private Label HDMFNo;

    private Employee employee;
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public void setEmployee(Employee employee) {
        setEmployeeData(employee);
    }
    
    public void initData(Employee employee) {
        setEmployeeData(employee);
    }

    private void setEmployeeData(Employee employee) {
        employeeNo.setText(employee.getEmployeeNo());
        fullName.setText(employee.getFirstName() + " " + employee.getMiddleName() + " " + employee.getLastName());
        currentAddress.setText(employee.getCurrentAddress());
        homeAddress.setText(employee.getHomeAddress()); 
        position.setText(employee.getPosition());
        department.setText(employee.getDepartment());
        dateHired.setText(employee.getDateHired().toString());
        dateRegularized.setText(employee.getDateRegularized().toString());
        contactNumber.setText(employee.getContactNumber());
        placeOfBirth.setText(employee.getPlaceOfBirth());
        dateOfBirth.setText(employee.getDateOfBirth().toString());
        gender.setText(employee.getGender());
        civilStatus.setText(employee.getCivilStatus());
        bloodType.setText(employee.getBloodType());
        SSSNumber.setText(employee.getSSSNumber());
        PHICNumber.setText(employee.getPHICNumber());
        TIN.setText(employee.getTIN());
        HDMFNo.setText(employee.getHDMFNo());
    }

    @FXML
    private void navigateToActive() throws IOException {
        Object controller = App.setRoot("Active");
        ((ActiveController) controller).setUser(user);
    }
}