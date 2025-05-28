package deco;

import java.io.IOException;
import entity.Employee;
import entity.EmployeeStatus;
import entity.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.*;

public class ProfileCardController {
    @FXML private Label status, name, position;
    @FXML private ImageView profilePhoto;
    private Employee employee;
    private User user;

    public void initData(EmployeeStatus status, String name, String position) {
        this.status.setText(status.toString());
        this.name.setText(name);
        this.position.setText(position);
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

    @FXML
    private void navigateToProfile() throws IOException {
        if (employee == null) {
            // Handle error case - maybe show an alert
            System.err.println("Error: No employee data available");
            return;
        }

        Object controller = App.setRoot("Profile");
        ((ProfileController) controller).setEmployee(employee);
        ((ProfileController) controller).setUser(user);
    }
}