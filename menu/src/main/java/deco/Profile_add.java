package deco;

import entity.Employee;
import entity.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class Profile_add {
    @FXML private VBox addProfileCard;
    @FXML private Button addButton;
    
    private User user;

    @FXML
    private void initialize() {
        // Disable add button for guest users
        if (addButton != null) {
            addButton.setDisable(user != null && user.getRole() == User.roles.GUEST);
        }
    }

    public void setUser(User user) {
        this.user = user;
        // Update button state when user is set
        if (addButton != null) {
            addButton.setDisable(user != null && user.getRole() == User.roles.GUEST);
        }
    }

    @FXML 
    private void addNewEmployee() {
        if (user != null && user.getRole() != User.roles.GUEST) {
            Employee emp = new Employee();
            // TODO: Open a dialog or navigate to a form to add new employee details
            System.out.println("Adding new employee as user: " + user.getUsername());
        }
    }
}