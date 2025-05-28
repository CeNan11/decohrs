package entity;

import java.sql.Date;

public class Dependent {
    private Integer dependentId;
    private Integer employeeId;
    private String relationshipType;
    private String fullName;
    private Date dateOfBirth;
    private String placeOfBirth;
    private String address;
    private String gender;

    public Dependent() {}

    // Getters
    public Integer getDependentId() { return dependentId; }
    public Integer getEmployeeId() { return employeeId; }
    public String getRelationshipType() { return relationshipType; }
    public String getFullName() { return fullName; }
    public Date getDateOfBirth() { return dateOfBirth; }
    public String getPlaceOfBirth() { return placeOfBirth; }
    public String getAddress() { return address; }
    public String getGender() { return gender; }

    // Setters
    public void setDependentId(Integer dependentId) { this.dependentId = dependentId; }
    public void setEmployeeId(Integer employeeId) { this.employeeId = employeeId; }
    public void setRelationshipType(String relationshipType) { this.relationshipType = relationshipType; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setDateOfBirth(Date dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public void setPlaceOfBirth(String placeOfBirth) { this.placeOfBirth = placeOfBirth; }
    public void setAddress(String address) { this.address = address; }
    public void setGender(String gender) { this.gender = gender; }

    @Override
    public String toString() {
        return "Dependent{" +
                "fullName='" + fullName + '\'' +
                ", relationshipType='" + relationshipType + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
}