package entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmployeeFilter {
    private String position;
    private String department;
    private Date dateHired;
    private Date dateRegularized;
    private Date dateOfBirth;
    private String gender;
    private String civilStatus;
    private String bloodType;
    private String educationalAttainmentLevel;
    
    public List<Employee> applyFilters(List<Employee> employees) {
        List<Employee> filteredEmployees = new ArrayList<>(employees);
        
        // Apply position filter
        if (position != null && !position.isEmpty()) {
            filteredEmployees.removeIf(e -> !e.getPositionId().equals(position));
        }
        
        // Apply department filter
        if (department != null && !department.isEmpty()) {
            filteredEmployees.removeIf(e -> !e.getDepartmentId().equals(department));
        }
        
        // Apply date hired filter
        if (dateHired != null) {
            filteredEmployees.removeIf(e -> !e.getHireDate().equals(dateHired));
        }
        
        // Apply date regularized filter
        if (dateRegularized != null) {
            filteredEmployees.removeIf(e -> !e.getRegularizationDate().equals(dateRegularized));
        }
        
        // Apply date of birth filter
        if (dateOfBirth != null) {
            filteredEmployees.removeIf(e -> !e.getDateOfBirth().equals(dateOfBirth));
        }
        
        // Apply gender filter
        if (gender != null && !gender.isEmpty()) {
            filteredEmployees.removeIf(e -> !e.getGender().equals(gender));
        }
        
        // Apply civil status filter
        if (civilStatus != null && !civilStatus.isEmpty()) {
            filteredEmployees.removeIf(e -> !e.getCivilStatus().equals(civilStatus));
        }
        
        // Apply blood type filter
        if (bloodType != null && !bloodType.isEmpty()) {
            filteredEmployees.removeIf(e -> !e.getBloodType().equals(bloodType));
        }
        
        return filteredEmployees;
    }
    
    public void clearFilters() {
        this.position = null;
        this.department = null;
        this.dateHired = null;
        this.dateRegularized = null;
        this.dateOfBirth = null;
        this.gender = null;
        this.civilStatus = null;
        this.bloodType = null;
        this.educationalAttainmentLevel = null;
    }
    
    // Setters and getters
    public void setPosition(String position) {
        this.position = position;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setDateHired(Date dateHired) {
        this.dateHired = dateHired;
    }

    public void setDateRegularized(Date dateRegularized) {
        this.dateRegularized = dateRegularized;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setCivilStatus(String civilStatus) {
        this.civilStatus = civilStatus;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public void setEducationalAttainmentLevel(String educationalAttainmentLevel) {
        this.educationalAttainmentLevel = educationalAttainmentLevel;
    }

    public String getPosition() {
        return position;
    }

    public String getDepartment() {
        return department;
    }

    public Date getDateHired() {
        return dateHired;
    }

    public Date getDateRegularized() {
        return dateRegularized;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public String getCivilStatus() {
        return civilStatus;
    }

    public String getBloodType() {
        return bloodType;
    }

    public String getEducationalAttainmentLevel() {
        return educationalAttainmentLevel;
    }

    @Override
    public String toString() {
        return "EmployeeFilter {" +
            "\n  Position: " + (position != null ? position : "N/A") +
            "\n  Department: " + (department != null ? department : "N/A") +
            "\n  Date Hired: " + (dateHired != null ? dateHired.toString() : "N/A") +
            "\n  Date Regularized: " + (dateRegularized != null ? dateRegularized.toString() : "N/A") +
            "\n  Date of Birth: " + (dateOfBirth != null ? dateOfBirth.toString() : "N/A") +
            "\n  Gender: " + (gender != null ? gender : "N/A") +
            "\n  Civil Status: " + (civilStatus != null ? civilStatus : "N/A") +
            "\n  Blood Type: " + (bloodType != null ? bloodType : "N/A") +
            "\n  Educational Attainment Level: " + (educationalAttainmentLevel != null ? educationalAttainmentLevel : "N/A") +
            "\n}";
    }
}