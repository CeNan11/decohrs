package tables;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * JavaFX-friendly representation of an audit log entry for display in UI tables.
 * Uses StringProperty for JavaFX data binding.
 */
public class AuditLogTableItem {
    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty timestamp = new SimpleStringProperty();
    private final StringProperty user = new SimpleStringProperty();
    private final StringProperty action = new SimpleStringProperty();
    private final StringProperty details = new SimpleStringProperty();

    public AuditLogTableItem(String id, String timestamp, String user, String action, String details) {
        setId(id);
        setTimestamp(timestamp);
        setUser(user);
        setAction(action);
        setDetails(details);
    }

    // Property getters for JavaFX binding
    @SuppressWarnings("exports")
    public StringProperty idProperty() { return id; }
    @SuppressWarnings("exports")
    public StringProperty timestampProperty() { return timestamp; }
    @SuppressWarnings("exports")
    public StringProperty userProperty() { return user; }
    @SuppressWarnings("exports")
    public StringProperty actionProperty() { return action; }
    @SuppressWarnings("exports")
    public StringProperty detailsProperty() { return details; }

    // Standard getters and setters
    public String getId() { return id.get(); }
    public void setId(String id) { this.id.set(id); }

    public String getTimestamp() { return timestamp.get(); }
    public void setTimestamp(String timestamp) { this.timestamp.set(timestamp); }

    public String getUser() { return user.get(); }
    public void setUser(String user) { this.user.set(user); }

    public String getAction() { return action.get(); }
    public void setAction(String action) { this.action.set(action); }

    public String getDetails() { return details.get(); }
    public void setDetails(String details) { this.details.set(details); }
}