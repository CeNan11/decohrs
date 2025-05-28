package entity;

public class Department {
    private Integer departmentId;
    private String departmentName;

    public Department() {}

    public Department(Integer departmentId, String departmentName) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }

    // Getters
    public Integer getDepartmentId() { return departmentId; }
    public String getDepartmentName() { return departmentName; }

    // Setters 
    public void setDepartmentId(Integer departmentId) { this.departmentId = departmentId; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }

    @Override
    public String toString() {
        return departmentName;
    }
}