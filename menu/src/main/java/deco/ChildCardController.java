package deco;

import entity.Child;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ComboBox;
import java.sql.Date;

public class ChildCardController {
    @FXML private TextField childName;
    @FXML private DatePicker childDateOfBirth;
    @FXML private TextField childPlaceOfBirth;
    @FXML private ComboBox<String> childGender;

    private VBox parent;
    private Child child;

    public void initialize() {
        childGender.getItems().addAll("Male", "Female");
    }

    public void setParent(VBox parent) {
        this.parent = parent;
    }
    
    public Child getChild() {
        child = new Child();
        
        child.setName(childName.getText());
        child.setDateOfBirth(Date.valueOf(childDateOfBirth.getValue()));
        child.setPlaceOfBirth(childPlaceOfBirth.getText());
        child.setGender(childGender.getValue());
        
        return child;
    }
    
    public void setChild(Child child) {
        this.child = child;
    }
    
    public void removeChild() {
        parent.getChildren().remove(this.getRoot());
    }

    private VBox getRoot(){
        return (VBox) childName.getParent().getParent();
    }
}
