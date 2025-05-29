package entity;

import java.sql.Date;

public class FamilyBackground {
    private String fatherName;
    private Date fatherDOB;
    private String motherName;
    private Date motherDOB;
    private Integer numberOfSiblings;
    private String spouseName;
    private String spouseAddress;
    private Date spouseBirthDate;

    public FamilyBackground() {}

    // Getters
    public String getFatherName() { return fatherName; }
    public Date getFatherDOB() { return fatherDOB; }
    public String getMotherName() { return motherName; }
    public Date getMotherDOB() { return motherDOB; }
    public Integer getNumberOfSiblings() { return numberOfSiblings; }
    public String getSpouseName() { return spouseName; }
    public String getSpouseAddress() { return spouseAddress; }
    public Date getSpouseBirthDate() { return spouseBirthDate; }

    // Setters
    public void setFatherName(String fatherName) { this.fatherName = fatherName; }
    public void setFatherDOB(Date fatherDOB) { this.fatherDOB = fatherDOB; }
    public void setMotherName(String motherName) { this.motherName = motherName; }
    public void setMotherDOB(Date motherDOB) { this.motherDOB = motherDOB; }
    public void setNumberOfSiblings(Integer numberOfSiblings) { this.numberOfSiblings = numberOfSiblings; }
    public void setSpouseName(String spouseName) { this.spouseName = spouseName; }
    public void setSpouseAddress(String spouseBirthPlace) { this.spouseAddress = spouseBirthPlace; }
    public void setSpouseBirthDate(Date spouseBirthDate) { this.spouseBirthDate = spouseBirthDate; }

    @Override
    public String toString() {
        return "FamilyBackground{" +
                "fatherName='" + fatherName + '\'' +
                ", motherName='" + motherName + '\'' +
                ", numberOfSiblings=" + numberOfSiblings +
                ", spouseName='" + spouseName + '\'' +
                ", spouseAddress='" + spouseAddress + '\'' +
                ", spouseBirthDate=" + spouseBirthDate +
                '}';
    }
}