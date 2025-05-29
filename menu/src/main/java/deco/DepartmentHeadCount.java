package deco;

class DepartmentHeadcountData {
    private String departmentName;
    private int count;

    public DepartmentHeadcountData(String departmentName, int count) {
        this.departmentName = departmentName;
        this.count = count;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "Department: " + departmentName + ", Headcount: " + count;
    }
}
