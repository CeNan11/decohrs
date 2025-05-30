package entity;

import java.util.ArrayList;
import java.util.Date;
import java.sql.Timestamp;
public class AuditLog {
    private int id;
    private Date dateCreated;
    private String action;
    private int targetEmployee;
    private String performedBy;

    public AuditLog() {}
    
    public AuditLog(String action, int targetEmployee, String performedBy) {
        this.dateCreated = new Timestamp(System.currentTimeMillis()); // Current timestamp
        this.action = action;
        this.targetEmployee = targetEmployee;
        this.performedBy = performedBy;
    }
    
    public ArrayList<AuditLog> getAuditHistory() {
        return new ArrayList<>(); // Placeholder
    }
    
    // Setters
    public void setId(int id) { this.id = id; } 
    public void setAction(String action) { this.action = action; }
    public void setTargetEmployee(int targetEmployee) { this.targetEmployee = targetEmployee; }
    public void setPerformedBy(String performedBy) { this.performedBy = performedBy; }
    public void setDateCreated(Date dateCreated) { this.dateCreated = dateCreated; }
    // Getters
    public int getId() { return id; }
    public Date getDateCreated() { return dateCreated; }
    public String getAction() { return action; }
    public int getTargetEmployee() { return targetEmployee; }
    public String getPerformedBy() { return performedBy; }

    @Override
    public String toString() {
        return "AuditLog {" +
            "\n  ID: " + id +
            "\n  Date Created: " + (dateCreated != null ? dateCreated.toString() : "N/A") +
            "\n  Action: " + action +
            "\n  Target Employee: " + targetEmployee +      
            "\n  Performed By: " + performedBy +
            "\n}";
    }
}