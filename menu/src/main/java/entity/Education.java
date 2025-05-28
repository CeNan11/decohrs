package entity;

import java.sql.Date;

public class Education {
    private Integer educationId;
    private Integer employeeId;
    private String educationLevel;
    private String institutionName;
    private String courseDegreeProgram;
    private String yearGraduated;
    private String certificateLicenseName;
    private Date dateIssued;
    private Date validUntil;
    private String remarks;

    // Constructors
    public Education() {}

    // Getters
    public Integer getEducationId() { return educationId; }
    public Integer getEmployeeId() { return employeeId; }
    public String getEducationLevel() { return educationLevel; }
    public String getInstitutionName() { return institutionName; }
    public String getCourseDegreeProgram() { return courseDegreeProgram; }
    public String getYearGraduated() { return yearGraduated; }
    public String getCertificateLicenseName() { return certificateLicenseName; }
    public Date getDateIssued() { return dateIssued; }
    public Date getValidUntil() { return validUntil; }
    public String getRemarks() { return remarks; }

    // Setters
    public void setEducationId(Integer educationId) { this.educationId = educationId; }
    public void setEmployeeId(Integer employeeId) { this.employeeId = employeeId; }
    public void setEducationLevel(String educationLevel) { this.educationLevel = educationLevel; }
    public void setInstitutionName(String institutionName) { this.institutionName = institutionName; }
    public void setCourseDegreeProgram(String courseDegreeProgram) { this.courseDegreeProgram = courseDegreeProgram; }
    public void setYearGraduated(String yearGraduated) { this.yearGraduated = yearGraduated; }
    public void setCertificateLicenseName(String certificateLicenseName) { this.certificateLicenseName = certificateLicenseName; }
    public void setDateIssued(Date dateIssued) { this.dateIssued = dateIssued; }
    public void setValidUntil(Date validUntil) { this.validUntil = validUntil; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    // Other methods
    @Override 
    public String toString() {
        return String.format("Education { " +
            "educationId=%d, " +
            "employeeId=%d, " + 
            "educationLevel='%s', " +
            "institutionName='%s', " +
            "courseDegreeProgram='%s', " +
            "yearGraduated='%s', " +
            "certificateLicenseName='%s', " +
            "dateIssued=%s, " +
            "validUntil=%s, " +
            "remarks='%s' }", 
            educationId, employeeId, educationLevel, institutionName, 
            courseDegreeProgram, yearGraduated, certificateLicenseName,
            dateIssued, validUntil, remarks);
    }
}