package entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AuditLog {
    private Date dateCreated;
    private String action;
    private String targetEmployee;
    private String performedBy;
    private String details;
    
    public AuditLog(String action, String targetEmployee, String performedBy, String details) {
        this.dateCreated = new Date(); // Current timestamp
        this.action = action;
        this.targetEmployee = targetEmployee;
        this.performedBy = performedBy;
        this.details = details;
    }
    
    public List<AuditLog> getAuditHistory() {
        return new ArrayList<>(); // Placeholder
    }
    
    // Setters and getters
    public void setAction(String action) {
        this.action = action;
    }
    
    public void setTargetEmployee(String targetEmployee) {
        this.targetEmployee = targetEmployee;
    }
    
    public void setPerformedBy(String performedBy) {
        this.performedBy = performedBy;
    }
    
    public void setDetails(String details) {
        this.details = details;
    }

    public Date getDateCreated() {
        return dateCreated;
    }
    
    public String getAction() {
        return action;
    }
    
    public String getTargetEmployee() {
        return targetEmployee;
    }
    
    public String getPerformedBy() {
        return performedBy;
    }
    
    public String getDetails() {
        return details;
    }
}