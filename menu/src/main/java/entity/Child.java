package entity;

import java.util.Date;

public class Child {
    private String name;
    private Date dateOfBirth;
    private String placeOfBirth;
    private String gender;
    
    // Setters and getters
    public void setName(String name) {
        this.name = name;
    }
    
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }
    
    public Date getDateOfBirth() {
        return dateOfBirth;
    }
    
    public String getPlaceOfBirth() {
        return placeOfBirth;
    }
    
    public String getGender() {
        return gender;
    }
}