package entity;

import java.util.Date;

public class FamilyBackground {
    private String fathersName;
    private Date fathersBirthdate;
    private String fathersSSS;
    private String mothersName;
    private Date mothersBirthdate;
    private String mothersSSS;
    private int numberOfSiblings;
    private String spouseName;
    private String spouseBirthPlace;
    private Date spouseBirthDate;
    
    // Getters and setters
    public String getFathersName() {
        return fathersName;
    }
    
    public void setFathersName(String fathersName) {
        this.fathersName = fathersName;
    }
    
    public Date getFathersBirthdate() {
        return fathersBirthdate;
    }
    
    public void setFathersBirthdate(Date fathersBirthdate) {
        this.fathersBirthdate = fathersBirthdate;
    }
    
    public String getFathersSSS() {
        return fathersSSS;
    }
    
    public void setFathersSSS(String fathersSSS) {
        this.fathersSSS = fathersSSS;
    }
    
    public String getMothersName() {
        return mothersName;
    }
    
    public void setMothersName(String mothersName) {
        this.mothersName = mothersName;
    }
    
    public Date getMothersBirthdate() {
        return mothersBirthdate;
    }
    
    public void setMothersBirthdate(Date mothersBirthdate) {
        this.mothersBirthdate = mothersBirthdate;
    }
    
    public String getMothersSSS() {
        return mothersSSS;
    }
    
    public void setMothersSSS(String mothersSSS) {
        this.mothersSSS = mothersSSS;
    }
    
    public int getNumberOfSiblings() {
        return numberOfSiblings;
    }
    
    public void setNumberOfSiblings(int numberOfSiblings) {
        this.numberOfSiblings = numberOfSiblings;
    }
    
    public String getSpouseName() {
        return spouseName;
    }
    
    public void setSpouseName(String spouseName) {
        this.spouseName = spouseName;
    }
    
    public String getSpouseBirthPlace() {
        return spouseBirthPlace;
    }
    
    public void setSpouseBirthPlace(String spouseBirthPlace) {
        this.spouseBirthPlace = spouseBirthPlace;
    }
    
    public Date getSpouseBirthDate() {
        return spouseBirthDate;
    }
    
    public void setSpouseBirthDate(Date spouseBirthDate) {
        this.spouseBirthDate = spouseBirthDate;
    }
}