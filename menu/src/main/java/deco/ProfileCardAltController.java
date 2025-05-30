package deco;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import entity.Department;
import entity.Employee;
import entity.EmployeeStatus;
import entity.Position;
import entity.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.*;
import services.EntityService;

public class ProfileCardAltController {
    @FXML private Label status, name, position, department;
    @FXML private ImageView profilePhoto;
    private Employee employee;
    private User user;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/DECOHRS_DB";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public void initData(EmployeeStatus status, String name, String position, String department) {
        this.status.setText(status.toString());
        this.name.setText(name);
        Position positionString = null;
        Department departmentString = null;
        
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            EntityService entityService = new EntityService(connection);
            positionString = entityService.getPositionById(Integer.parseInt(position));
            departmentString = entityService.getDepartmentById(Integer.parseInt(department));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.position.setText(positionString.getPositionTitle());
        this.department.setText(departmentString.getDepartmentName());
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setStatus(EmployeeStatus status) {this.status.setText(status.toString());}
    public void setName(String name) {this.name.setText(name);}
    public void setPosition(String position) {this.position.setText(position);}
    public void setDepartment(String department) {this.department.setText(department);}

    // ===== Navigation Methods =====
    @FXML
    private void navigateToProfile() throws IOException {

        Object controller = App.setRoot("ProfileInactive");
        ((ProfileInactiveController) controller).setEmployee(employee);
        ((ProfileInactiveController) controller).setUser(user);
    }
} 