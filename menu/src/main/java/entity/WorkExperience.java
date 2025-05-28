package entity;

import java.sql.Date;

public class WorkExperience {
    private Integer workExperienceId;
    private Integer employeeId;
    private String companyName;
    private String positionHeld;
    private String duration;
    private String remarks;

    public WorkExperience() {}

    // Getters
    public Integer getWorkExperienceId() { return workExperienceId; }
    public Integer getEmployeeId() { return employeeId; }
    public String getCompanyName() { return companyName; }
    public String getPositionHeld() { return positionHeld; }
    public String getDuration() { return duration; }
    public String getRemarks() { return remarks; }

    // Setters
    public void setWorkExperienceId(Integer workExperienceId) { this.workExperienceId = workExperienceId; }
    public void setEmployeeId(Integer employeeId) { this.employeeId = employeeId; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public void setPositionHeld(String positionHeld) { this.positionHeld = positionHeld; }
    public void setDuration(String duration) { this.duration = duration; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public String toString() {
        return "WorkExperience {" +
            "\n  Work Experience ID: " + workExperienceId +
            "\n  Employee ID: " + employeeId + 
            "\n  Company Name: " + companyName +
            "\n  Position Held: " + positionHeld +
            "\n  Duration: " + duration +
            "\n  Remarks: " + remarks +
            "\n}";
    }
}