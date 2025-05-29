package entity;

import java.sql.Date;

public class Dependent {
    private Integer dependentId;
    private Integer employeeId;
    private String fullName;
    private Date dateOfBirth;
    private String address;

    public Dependent() {}

    // Getters
    public Integer getDependentId() { return dependentId; }
    public Integer getEmployeeId() { return employeeId; }
    public String getFullName() { return fullName; }
    public Date getDateOfBirth() { return dateOfBirth; }
    public String getAddress() { return address; }

    // Setters
    public void setDependentId(Integer dependentId) { this.dependentId = dependentId; }
    public void setEmployeeId(Integer employeeId) { this.employeeId = employeeId; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setDateOfBirth(Date dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public void setAddress(String address) { this.address = address; }

    @Override
    public String toString() {
        return "Dependent{" +
                "dependentId=" + dependentId +
                ", employeeId=" + employeeId +
                ", fullName='" + fullName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", address='" + address + '\'' +
                '}';
    }
}