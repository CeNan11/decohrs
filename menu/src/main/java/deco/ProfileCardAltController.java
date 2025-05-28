package deco;

import java.io.IOException;
import entity.Employee;
import entity.EmployeeStatus;
import entity.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.*;

public class ProfileCardAltController {
    // ===== FXML Components =====
    @FXML private Label status;
    @FXML private Label name;
    @FXML private Label position;
    @FXML private ImageView profilePhoto;

    // ===== Class Properties =====
    private Employee employee;
    private User user;

    // ===== Data Management Methods =====
    public void initData(EmployeeStatus status, String name, String positionId) {
        this.status.setText(status.toString());
        this.name.setText(name);
        this.position.setText(String.valueOf(positionId));
        
        // Set status color based on employee status
        if (status == EmployeeStatus.ACTIVE) {
            this.status.getStyleClass().add("status-active");
        } else {
            this.status.getStyleClass().add("status-inactive");
        }
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // ===== UI Update Methods =====
    public void setStatus(EmployeeStatus status) {
        this.status.setText(status.toString());
        // Update status color
        this.status.getStyleClass().clear();
        this.status.getStyleClass().add("font-size-m");
        this.status.getStyleClass().add("deco-font");
        this.status.getStyleClass().add("deco-font-bold");
        if (status == EmployeeStatus.ACTIVE) {
            this.status.getStyleClass().add("status-active");
        } else {
            this.status.getStyleClass().add("status-inactive");
        }
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    public void setPosition(String position) {
        this.position.setText(position);
    }

    // ===== Navigation Methods =====
    @FXML
    private void navigateToProfile() throws IOException {

        Object controller = App.setRoot("ProfileInactive");
        ((ProfileInactiveController) controller).setEmployee(employee);
        ((ProfileInactiveController) controller).setUser(user);
    }
} 