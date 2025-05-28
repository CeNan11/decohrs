package entity;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Employee {
    // Primary Key
    private Integer employeeId;
    private String employeeCode;

    // Personal Information
    private String lastName;
    private String firstName;
    private String middleName;
    private String suffix;
    private Date dateOfBirth;
    private String placeOfBirth;
    private String nationality;

    // Contact Information
    private String emailAddress;
    private String contactNumberPrimary;
    private String currentAddress;
    private String homeAddress;

    // Employment Information
    private Integer departmentId;
    private Integer positionId;
    private Date hireDate;
    private Date regularizationDate;

    // Audit Fields
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Display Information (from related tables)
    private String gender;
    private String civilStatus;
    private String bloodType;
    private EmployeeStatus status;

    // Government IDs
    private String sssNumber;
    private String philHealthNumber;
    private String tinNumber;
    private String pagIbigNumber;

    // Related Entities
    Dependent dependent;
    private EmergencyContact emergencyContact;
    private FamilyBackground familyBackground;
    private Education education;
    private BirthCertificate birthCertificate;
    private List<Child> children;

    // Work Experience
    private ArrayList<WorkExperience> workExperiences;

    public Employee() {
        this.children = new ArrayList<>();
        this.nationality = "Filipino";
        this.status = EmployeeStatus.ACTIVE;
    }

    // Getters
    public Integer getEmployeeId() { return employeeId; }
    public String getEmployeeCode() { return employeeCode; }
    public String getLastName() { return lastName; }
    public String getFirstName() { return firstName; }
    public String getMiddleName() { return middleName; }
    public String getSuffix() { return suffix; }
    public Date getDateOfBirth() { return dateOfBirth; }
    public String getPlaceOfBirth() { return placeOfBirth; }
    public String getNationality() { return nationality; }
    public String getEmailAddress() { return emailAddress; }
    public String getContactNumberPrimary() { return contactNumberPrimary; }
    public String getCurrentAddress() { return currentAddress; }
    public String getHomeAddress() { return homeAddress; }
    public Integer getDepartmentId() { return departmentId; }
    public Integer getPositionId() { return positionId; }
    public Date getHireDate() { return hireDate; }
    public Date getRegularizationDate() { return regularizationDate; }
    public Timestamp getCreatedAt() { return createdAt; }
    public Timestamp getUpdatedAt() { return updatedAt; }
    public String getGender() { return gender; }
    public String getCivilStatus() { return civilStatus; }
    public String getBloodType() { return bloodType; }
    public EmployeeStatus getStatus() { return status; }
    public String getSSSNumber() { return sssNumber; }
    public String getPHICNumber() { return philHealthNumber; }
    public String getTIN() { return tinNumber; }
    public String getHDMFNo() { return pagIbigNumber; }
    public EmergencyContact getEmergencyContact() { return emergencyContact; }
    public FamilyBackground getFamilyBackground() { return familyBackground; }
    public Education getEducation() { return education; }
    public BirthCertificate getBirthCertificate() { return birthCertificate; }
    public List<Child> getChildren() { return children; }
    public ArrayList<WorkExperience> getWorkExperiences() { return workExperiences; }
    // Setters
    public void setEmployeeId(Integer employeeId) { this.employeeId = employeeId; }
    public void setEmployeeCode(String employeeCode) { this.employeeCode = employeeCode; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }
    public void setSuffix(String suffix) { this.suffix = suffix; }
    public void setDateOfBirth(Date dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public void setPlaceOfBirth(String placeOfBirth) { this.placeOfBirth = placeOfBirth; }
    public void setNationality(String nationality) { this.nationality = nationality; }
    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }
    public void setContactNumberPrimary(String contactNumberPrimary) { this.contactNumberPrimary = contactNumberPrimary; }
    public void setCurrentAddress(String currentAddress) { this.currentAddress = currentAddress; }
    public void setHomeAddress(String homeAddress) { this.homeAddress = homeAddress; }
    public void setDepartmentId(Integer departmentId) { this.departmentId = departmentId; }
    public void setPositionId(Integer positionId) { this.positionId = positionId; }
    public void setHireDate(Date hireDate) { this.hireDate = hireDate; }
    public void setRegularizationDate(Date regularizationDate) { this.regularizationDate = regularizationDate; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }
    public void setGender(String gender) { this.gender = gender; }
    public void setCivilStatus(String civilStatus) { this.civilStatus = civilStatus; }
    public void setBloodType(String bloodType) { this.bloodType = bloodType; }
    public void setStatus(EmployeeStatus status) { this.status = status; }
    public void setSSSNumber(String sssNumber) { this.sssNumber = sssNumber; }
    public void setPHICNumber(String philHealthNumber) { this.philHealthNumber = philHealthNumber; }
    public void setTIN(String tinNumber) { this.tinNumber = tinNumber; }
    public void setHDMFNo(String pagIbigNumber) { this.pagIbigNumber = pagIbigNumber; }
    public void setEmergencyContact(EmergencyContact emergencyContact) { this.emergencyContact = emergencyContact; }
    public void setFamilyBackground(FamilyBackground familyBackground) { this.familyBackground = familyBackground; }
    public void setEducation(Education education) { this.education = education; }
    public void setBirthCertificate(BirthCertificate birthCertificate) { this.birthCertificate = birthCertificate; }
    public void setChildren(List<Child> children) { this.children = children; }
    public void addChild(Child child) { this.children.add(child); }
    public void setWorkExperiences(ArrayList<WorkExperience> workExperiences) { this.workExperiences = workExperiences; }
    public void addWorkExperience(WorkExperience workExperience) { this.workExperiences.add(workExperience); }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", employeeCode='" + employeeCode + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", currentAddress='" + currentAddress + '\'' +
                ", homeAddress='" + homeAddress + '\'' +
                ", contactNumberPrimary='" + contactNumberPrimary + '\'' +
                ", placeOfBirth='" + placeOfBirth + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", gender='" + gender + '\'' +
                ", civilStatus='" + civilStatus + '\'' +
                ", bloodType='" + bloodType + '\'' +
                ", departmentId=" + departmentId +
                ", positionId=" + positionId +
                ", hireDate=" + hireDate +
                ", regularizationDate=" + regularizationDate +
                ", status=" + status +
                ", sssNumber='" + sssNumber + '\'' +
                ", philHealthNumber='" + philHealthNumber + '\'' +
                ", tinNumber='" + tinNumber + '\'' +
                ", pagIbigNumber='" + pagIbigNumber + '\'' +
                ", emergencyContact=" + emergencyContact +
                ", familyBackground=" + familyBackground +
                ", education=" + education +
                ", birthCertificate=" + birthCertificate +
                ", children=" + children +
                '}';
    }
}