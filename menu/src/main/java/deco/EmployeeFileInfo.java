package deco; // Or your main package

import java.util.Date;

public class EmployeeFileInfo { // Simple name
    private int fileId;
    private int employeeId;
    private String filePath;
    private String originalFilename;
    private String mimeType;
    private Long fileSizeBytes;
    private String fileType;
    private Date uploadedAt;

    // Constructor for creating an object to save (fileId and uploadedAt are auto by DB)
    public EmployeeFileInfo(int employeeId, String filePath, String originalFilename, String mimeType, Long fileSizeBytes, String fileType) {
        this.employeeId = employeeId;
        this.filePath = filePath;
        this.originalFilename = originalFilename;
        this.mimeType = mimeType;
        this.fileSizeBytes = fileSizeBytes;
        this.fileType = fileType;
    }

    // Constructor for creating an object when reading from DB
    public EmployeeFileInfo(int fileId, int employeeId, String filePath, String originalFilename, String mimeType, Long fileSizeBytes, String fileType, Date uploadedAt) {
        this.fileId = fileId;
        this.employeeId = employeeId;
        this.filePath = filePath;
        this.originalFilename = originalFilename;
        this.mimeType = mimeType;
        this.fileSizeBytes = fileSizeBytes;
        this.fileType = fileType;
        this.uploadedAt = uploadedAt;
    }

    // Getters (add setters if you need to modify after creation)
    public int getFileId() { return fileId; }
    public int getEmployeeId() { return employeeId; }
    public String getFilePath() { return filePath; }
    public String getOriginalFilename() { return originalFilename; }
    public String getMimeType() { return mimeType; }
    public Long getFileSizeBytes() { return fileSizeBytes; }
    public String getFileType() { return fileType; }
    public Date getUploadedAt() { return uploadedAt; }

    @Override
    public String toString() {
        return "EmployeeFile{" +
                "fileId=" + fileId +
                ", employeeId=" + employeeId +
                ", fileType='" + fileType + '\'' +
                ", originalFilename='" + originalFilename + '\'' +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}