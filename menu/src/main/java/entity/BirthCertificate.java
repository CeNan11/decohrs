package entity;

public class BirthCertificate {
    private String documentNumber;
    private byte[] certificateFile;
    
    public boolean upload() {
        System.out.println("Uploading birth certificate: " + documentNumber);
        // Upload logic
        return true;
    }
    
    public byte[] download() {
        System.out.println("Downloading birth certificate: " + documentNumber);
        return certificateFile;
    }
    
    // Setters and getters
    public void setCertificateFile(byte[] certificateFile) {
        this.certificateFile = certificateFile;
    }
    
    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }
    
    public byte[] getCertificateFile() {
        return certificateFile;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }
}