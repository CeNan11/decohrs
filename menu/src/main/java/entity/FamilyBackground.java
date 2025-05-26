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

    // Setters and getters
    public void setFathersName(String fathersName) {
        this.fathersName = fathersName;
    }

    public void setFathersBirthdate(Date fathersBirthdate) {
        this.fathersBirthdate = fathersBirthdate;
    }

    public void setFathersSSS(String fathersSSS) {
        this.fathersSSS = fathersSSS;
    }

    public void setMothersName(String mothersName) {
        this.mothersName = mothersName;
    }

    public void setMothersBirthdate(Date mothersBirthdate) {
        this.mothersBirthdate = mothersBirthdate;
    }

    public void setMothersSSS(String mothersSSS) {
        this.mothersSSS = mothersSSS;
    }

    public void setNumberOfSiblings(int numberOfSiblings) {
        this.numberOfSiblings = numberOfSiblings;
    }

    public void setSpouseName(String spouseName) {
        this.spouseName = spouseName;
    }

    public void setSpouseBirthPlace(String spouseBirthPlace) {
        this.spouseBirthPlace = spouseBirthPlace;
    }

    public void setSpouseBirthDate(Date spouseBirthDate) {
        this.spouseBirthDate = spouseBirthDate;
    }

    public String getFathersName() {
        return fathersName;
    }

    public Date getFathersBirthdate() {
        return fathersBirthdate;
    }

    public String getFathersSSS() {
        return fathersSSS;
    }

    public String getMothersName() {
        return mothersName;
    }

    public Date getMothersBirthdate() {
        return mothersBirthdate;
    }

    public String getMothersSSS() {
        return mothersSSS;
    }

    public int getNumberOfSiblings() {
        return numberOfSiblings;
    }

    public String getSpouseName() {
        return spouseName;
    }

    public String getSpouseBirthPlace() {
        return spouseBirthPlace;
    }

    public Date getSpouseBirthDate() {
        return spouseBirthDate;
    }
}