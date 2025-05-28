package entity;

public class EmergencyContact {
    private String name;
    private String relationship;
    private String address;
    private String contactNumber;

    public EmergencyContact() {}

    public EmergencyContact(String name, String relationship, String address, String contactNumber) {
        this.name = name;
        this.relationship = relationship;
        this.address = address;
        this.contactNumber = contactNumber;
    }

    // Getters
    public String getName() { return name; }
    public String getRelationship() { return relationship; }
    public String getAddress() { return address; }
    public String getContactNumber() { return contactNumber; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setRelationship(String relationship) { this.relationship = relationship; }
    public void setAddress(String address) { this.address = address; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    @Override
    public String toString() {
        return "EmergencyContact{" +
                "name='" + name + '\'' +
                ", relationship='" + relationship + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                '}';
    }
}