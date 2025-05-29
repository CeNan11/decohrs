package entity;

import java.sql.Date;

public class Child {
    private Integer childId;
    private Integer employeeId;
    private String name;
    private Date dateOfBirth;
    private String placeOfBirth;
    private String gender;
    
    // Setters
    public void setChildId(Integer childId) { this.childId = childId; }
    public void setEmployeeId(Integer employeeId) { this.employeeId = employeeId; }
    public void setName(String name) { this.name = name; }
    public void setDateOfBirth(Date dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public void setPlaceOfBirth(String placeOfBirth) { this.placeOfBirth = placeOfBirth; }
    public void setGender(String gender) { this.gender = gender; }

    // Getters
    public Integer getChildId() { return childId; }
    public Integer getEmployeeId() { return employeeId; }
    public String getName() { return name; }
    public Date getDateOfBirth() { return dateOfBirth; }
    public String getPlaceOfBirth() { return placeOfBirth; }
    public String getGender() { return gender; }

    @Override
    public String toString() {
        return "Child {" +
            "\n  Child ID: " + childId +
            "\n  Employee ID: " + employeeId +
            "\n  Name: " + name +
            "\n  Date of Birth: " + (dateOfBirth != null ? dateOfBirth.toString() : "N/A") +
            "\n  Place of Birth: " + placeOfBirth +
            "\n  Gender: " + gender +
            "\n}";
    }
}