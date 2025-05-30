package tables;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.util.Date;

public class ChildTableItem {
    private StringProperty name;
    private StringProperty dateOfBirth;
    private StringProperty placeOfBirth;
    private StringProperty gender;

    public ChildTableItem(String name, Date dateOfBirth, String placeOfBirth, String gender) {
        this.name = new SimpleStringProperty(name);
        this.dateOfBirth = new SimpleStringProperty(dateOfBirth.toString());  // Corrected initialization
        this.placeOfBirth = new SimpleStringProperty(placeOfBirth);
        this.gender = new SimpleStringProperty(gender);
    }

    @SuppressWarnings("exports")
    public StringProperty nameProperty() { return name; }
    @SuppressWarnings("exports")
    public StringProperty dateOfBirthProperty() { return dateOfBirth; }
    @SuppressWarnings("exports")
    public StringProperty placeOfBirthProperty() { return placeOfBirth; }
    @SuppressWarnings("exports")
    public StringProperty genderProperty() { return gender; }

    public String getName() { return name.get(); }
    public String getDateOfBirth() { return dateOfBirth.get(); }
    public String getPlaceOfBirth() { return placeOfBirth.get(); }
    public String getGender() { return gender.get(); }

    public void setName(String name) { this.name.set(name); }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth.set(dateOfBirth); }
    public void setPlaceOfBirth(String placeOfBirth) { this.placeOfBirth.set(placeOfBirth); }
    public void setGender(String gender) { this.gender.set(gender); }

    @Override
    public String toString() {
        return "ChildTableItem{" +
                "name=" + name.get() +
                ", dateOfBirth=" + dateOfBirth.get() +
                ", placeOfBirth=" + placeOfBirth.get() +
                ", gender=" + gender.get() +
                '}';
    }
}
