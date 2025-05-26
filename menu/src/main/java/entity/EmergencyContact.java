package entity;

public class EmergencyContact {
    private String nameOfContact;
    private String relationship;
    private String address;
    private String contactNumber;
    
    // Setters and getters
    public void setNameOfContact(String nameOfContact) {
        this.nameOfContact = nameOfContact;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getNameOfContact() {
        return nameOfContact;
    }

    public String getRelationship() {
        return relationship;
    }

    public String getAddress() {
        return address;
    }

    public String getContactNumber() {
        return contactNumber;
    }
}