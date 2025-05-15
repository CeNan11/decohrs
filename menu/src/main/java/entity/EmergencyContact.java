package entity;

public class EmergencyContact {
    private String nameOfContact;
    private String relationship;
    private String address;
    private String contactNumber;
    
    // Getters and setters
    public String getNameOfContact() {
        return nameOfContact;
    }
    
    public void setNameOfContact(String nameOfContact) {
        this.nameOfContact = nameOfContact;
    }
    
    public String getRelationship() {
        return relationship;
    }
    
    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getContactNumber() {
        return contactNumber;
    }
    
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}