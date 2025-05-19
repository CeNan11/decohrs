package entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Employee {
    private String employeeNo;
    private String firstName;
    private String middleName;
    private String lastName;
    private String currentAddress;
    private String homeAddress;
    private String position;
    private String department;
    private Date dateHired;
    private Date dateRegularized;
    private String contactNumber;
    private String placeOfBirth;
    private Date dateOfBirth;
    private String gender;
    private String civilStatus;
    private String bloodType;
    private String SSSNumber;
    private String PHICNumber;
    private String TIN;
    private String HDMFNo;
    private EmployeeStatus status;
    
    // Relationships
    private EmergencyContact emergencyContact;
    private FamilyBackground familyBackground;
    private List<Child> children;
    private Education education;
    private BirthCertificate birthCertificate;
    
    public Employee() {
        this.children = new ArrayList<>();
        this.status = EmployeeStatus.ACTIVE;
    }
    
    public String viewProfile() {
        return "Employee #" + employeeNo + ": " + firstName + " " + lastName;
    }
    
    // Getters and setters
    public String getEmployeeNo() {
        return employeeNo;
    }
    
    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getMiddleName() {
        return middleName;
    }
    
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getCurrentAddress() {
        return currentAddress;
    }
    
    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }
    
    public String getHomeAddress() {
        return homeAddress;
    }
    
    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
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
    
    public Date getDateHired() {
        return dateHired;
    }
    
    public void setDateHired(Date dateHired) {
        this.dateHired = dateHired;
    }
    
    public Date getDateRegularized() {
        return dateRegularized;
    }
    
    public void setDateRegularized(Date dateRegularized) {
        this.dateRegularized = dateRegularized;
    }
    
    public String getContactNumber() {
        return contactNumber;
    }
    
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
    
    public String getPlaceOfBirth() {
        return placeOfBirth;
    }
    
    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }
    
    public Date getDateOfBirth() {
        return dateOfBirth;
    }
    
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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
    
    public String getBloodType() {
        return bloodType;
    }
    
    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }
    
    public String getSSSNumber() {
        return SSSNumber;
    }
    
    public void setSSSNumber(String SSSNumber) {
        this.SSSNumber = SSSNumber;
    }
    
    public String getPHICNumber() {
        return PHICNumber;
    }
    
    public void setPHICNumber(String PHICNumber) {
        this.PHICNumber = PHICNumber;
    }
    
    public String getTIN() {
        return TIN;
    }
    
    public void setTIN(String TIN) {
        this.TIN = TIN;
    }
    
    public String getHDMFNo() {
        return HDMFNo;
    }
    
    public void setHDMFNo(String HDMFNo) {
        this.HDMFNo = HDMFNo;
    }
    
    public EmployeeStatus getStatus() {
        return status;
    }
    
    public void setStatus(EmployeeStatus status) {
        this.status = status;
    }
    
    // Relationship getters and setters
    public EmergencyContact getEmergencyContact() {
        return emergencyContact;
    }
    
    public void setEmergencyContact(EmergencyContact emergencyContact) {
        this.emergencyContact = emergencyContact;
    }
    
    public FamilyBackground getFamilyBackground() {
        return familyBackground;
    }
    
    public void setFamilyBackground(FamilyBackground familyBackground) {
        this.familyBackground = familyBackground;
    }
    
    public List<Child> getChildren() {
        return children;
    }
    
    public void addChild(Child child) {
        this.children.add(child);
    }
    
    public Education getEducation() {
        return education;
    }
    
    public void setEducation(Education education) {
        this.education = education;
    }
    
    public BirthCertificate getBirthCertificate() {
        return birthCertificate;
    }
    
    public void setBirthCertificate(BirthCertificate birthCertificate) {
        this.birthCertificate = birthCertificate;
    }

    @Override
    public String toString() {
        return "Employee {" +
            "\n  Employee No: " + employeeNo +
            "\n  Name: " + firstName + " " + (middleName != null ? middleName + " " : "") + lastName +
            "\n  Position: " + position +
            "\n  Department: " + department +
            "\n  Status: " + (status != null ? status.name() : "N/A") +
            "\n  Contact Number: " + contactNumber +
            "\n  Date Hired: " + (dateHired != null ? dateHired.toString() : "N/A") +
            "\n  Date Regularized: " + (dateRegularized != null ? dateRegularized.toString() : "N/A") +
            "\n  Current Address: " + currentAddress +
            "\n  Home Address: " + homeAddress +
            "\n  Place of Birth: " + placeOfBirth +
            "\n  Date of Birth: " + (dateOfBirth != null ? dateOfBirth.toString() : "N/A") +
            "\n  Gender: " + gender +
            "\n  Civil Status: " + civilStatus +
            "\n  Blood Type: " + bloodType +
            "\n  SSS Number: " + SSSNumber +
            "\n  PHIC Number: " + PHICNumber +
            "\n  TIN: " + TIN +
            "\n  HDMF No: " + HDMFNo +
            "\n  Emergency Contact: " + (emergencyContact != null ? emergencyContact.toString() : "N/A") +
            "\n  Family Background: " + (familyBackground != null ? familyBackground.toString() : "N/A") +
            "\n  Children: " + (children != null ? children.size() + " child(ren)" : "0") +
            "\n  Education: " + (education != null ? education.toString() : "N/A") +
            "\n  Birth Certificate: " + (birthCertificate != null ? birthCertificate.toString() : "N/A") +
            "\n}";
    }
}