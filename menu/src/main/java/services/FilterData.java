package services;

import java.time.LocalDate;

public class FilterData {
    private String sortBy;
    private String orderBy;
    private String position;
    private String department;
    private String gender;
    private String civilStatus;
    private String educationAttainment;
    private LocalDate dateHired;
    private LocalDate dateRegularized;
    private LocalDate dateOfBirth;

    public FilterData() {}

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCivilStatus() {
        return civilStatus;
    }

    public void setCivilStatus(String civilStatus) {
        this.civilStatus = civilStatus;
    }

    public String getEducationAttainment() {
        return educationAttainment;
    }

    public void setEducationAttainment(String educationAttainment) {
        this.educationAttainment = educationAttainment;
    }

    public LocalDate getDateHired() {
        return dateHired;
    }

    public void setDateHired(LocalDate dateHired) {
        this.dateHired = dateHired;
    }

    public LocalDate getDateRegularized() {
        return dateRegularized;
    }

    public void setDateRegularized(LocalDate dateRegularized) {
        this.dateRegularized = dateRegularized;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "FilterData{" +
                "sortBy='" + sortBy + '\'' +
                ", orderBy='" + orderBy + '\'' +
                ", position='" + position + '\'' +
                ", department='" + department + '\'' +
                ", gender='" + gender + '\'' +
                ", civilStatus='" + civilStatus + '\'' +
                ", educationAttainment='" + educationAttainment + '\'' +
                ", dateHired=" + dateHired +
                ", dateRegularized=" + dateRegularized +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
}