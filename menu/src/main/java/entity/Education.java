package entity;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

public class Education {
    private Integer educationId;
    private Integer employeeId;
    private String primarySchool;
    private Date primaryYearGraduated;
    private String tertiarySchool;
    private Date tertiaryYearGraduated;
    private String collegeSchool;
    private Date collegeYearGraduated;
    private String vocationalSchool;
    private Date vocationalYearGraduated;
    private String postGraduateSchool;
    private Date postGraduateYearGraduated;
    private String certificateLicenseName;
    private Date dateIssued;
    private Date validUntil;

    // Constructors
    public Education() {}

    // Getters
    public Integer getEducationId() { return educationId; }
    public Integer getEmployeeId() { return employeeId; }
    public String getPrimarySchool() { return primarySchool; }
    public Date getPrimaryYearGraduated() { return primaryYearGraduated; }
    public String getTertiarySchool() { return tertiarySchool; }
    public Date getTertiaryYearGraduated() { return tertiaryYearGraduated; }
    public String getCollegeSchool() { return collegeSchool; }
    public Date getCollegeYearGraduated() { return collegeYearGraduated; }
    public String getVocationalSchool() { return vocationalSchool; }
    public Date getVocationalYearGraduated() { return vocationalYearGraduated; }
    public String getPostGraduateSchool() { return postGraduateSchool; }
    public Date getPostGraduateYearGraduated() { return postGraduateYearGraduated; }
    public String getCertificateLicenseName() { return certificateLicenseName; }
    public Date getDateIssued() { return dateIssued; }
    public Date getValidUntil() { return validUntil; }
    public String getHighestAttainment() {
        if (postGraduateYearGraduated != null) {
            return "Post Graduate";
        }
        if (collegeYearGraduated != null) {
            return "College";
        }
        if (vocationalYearGraduated != null) {
            return "Vocational";
        }
        return "Primary";
    }

    public static List<String> getHighestAttainmentList() {
        return Arrays.asList("Primary", "Vocational", "College", "Post Graduate");
    }

    // Setters
    public void setEducationId(Integer educationId) { this.educationId = educationId; }
    public void setEmployeeId(Integer employeeId) { this.employeeId = employeeId; }
    public void setPrimarySchool(String primarySchool) { this.primarySchool = primarySchool; }
    public void setPrimaryYearGraduated(Date primaryYearGraduated) { this.primaryYearGraduated = primaryYearGraduated; }
    public void setTertiarySchool(String tertiarySchool) { this.tertiarySchool = tertiarySchool; }
    public void setTertiaryYearGraduated(Date tertiaryYearGraduated) { this.tertiaryYearGraduated = tertiaryYearGraduated; }
    public void setCollegeSchool(String collegeSchool) { this.collegeSchool = collegeSchool; }
    public void setCollegeYearGraduated(Date collegeYearGraduated) { this.collegeYearGraduated = collegeYearGraduated; }
    public void setVocationalSchool(String vocationalSchool) { this.vocationalSchool = vocationalSchool; }
    public void setVocationalYearGraduated(Date vocationalYearGraduated) { this.vocationalYearGraduated = vocationalYearGraduated; }
    public void setPostGraduateSchool(String postGraduateSchool) { this.postGraduateSchool = postGraduateSchool; }
    public void setPostGraduateYearGraduated(Date postGraduateYearGraduated) { this.postGraduateYearGraduated = postGraduateYearGraduated; }
    public void setCertificateLicenseName(String certificateLicenseName) { this.certificateLicenseName = certificateLicenseName; }
    public void setDateIssued(Date dateIssued) { this.dateIssued = dateIssued; }
    public void setValidUntil(Date validUntil) { this.validUntil = validUntil; }

    // Other methods
    @Override 
    public String toString() {
        return String.format("Education { " +
            "educationId: %s, " +
            "employeeId: %s, " +
            "primarySchool: %s, " +
            "primaryYearGraduated: %s, " +
            "tertiarySchool: %s, " +
            "tertiaryYearGraduated: %s, " +
            "collegeSchool: %s, " +
            "collegeYearGraduated: %s, " +
            "vocationalSchool: %s, " +
            "vocationalYearGraduated: %s, " +
            "postGraduateSchool: %s, " +
            "postGraduateYearGraduated: %s, " +
            "certificateLicenseName: %s, " +
            "dateIssued: %s, " +
            "validUntil: %s }", 
            educationId, employeeId, primarySchool, primaryYearGraduated, tertiarySchool, tertiaryYearGraduated, 
            collegeSchool, collegeYearGraduated, vocationalSchool, vocationalYearGraduated, 
            postGraduateSchool, postGraduateYearGraduated, certificateLicenseName, 
            dateIssued, validUntil);
    }
}