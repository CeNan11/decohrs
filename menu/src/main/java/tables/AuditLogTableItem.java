package tables;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * JavaFX-friendly representation of an audit log entry for display in UI tables.
 * Uses StringProperty for JavaFX data binding.
 */
public class AuditLogTableItem {
    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty created_at = new SimpleStringProperty();
    private final StringProperty performed_by = new SimpleStringProperty();
    private final StringProperty action = new SimpleStringProperty();
    private final StringProperty target_employee = new SimpleStringProperty();

    public AuditLogTableItem(String id, String created_at, String performed_by, String action, String target_employee) {
        setId(id);
        setCreated_at(created_at);
        setPerformed_by(performed_by);
        setAction(action);
        setTarget_employee(target_employee);
    }

    // Property getters for JavaFX binding
    @SuppressWarnings("exports")
    public StringProperty idProperty() { return id; }
    @SuppressWarnings("exports")
    public StringProperty created_atProperty() { return created_at; }
    @SuppressWarnings("exports")
    public StringProperty performed_byProperty() { return performed_by; }
    @SuppressWarnings("exports")
    public StringProperty actionProperty() { return action; }
    @SuppressWarnings("exports")
    public StringProperty target_employeeProperty() { return target_employee; }

    // Standard getters and setters
    public String getId() { return id.get(); }
    public void setId(String id) { this.id.set(id); }

    public String getCreated_at() { return created_at.get(); }
    public void setCreated_at(String created_at) { this.created_at.set(created_at); }

    public String getPerformed_by() { return performed_by.get(); }
    public void setPerformed_by(String performed_by) { this.performed_by.set(performed_by); }

    public String getAction() { return action.get(); }
    public void setAction(String action) { this.action.set(action); }

    public String getTarget_employee() { return target_employee.get(); }
    public void setTarget_employee(String target_employee) { this.target_employee.set(target_employee); }
}