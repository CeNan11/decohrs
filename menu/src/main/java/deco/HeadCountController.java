package deco; // Or your preferred package

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class HeadCountController {

    public List<DepartmentHeadcountData> getHeadcountByDepartment(Connection conn) throws SQLException {
        if (conn == null || conn.isClosed()) {
            throw new IllegalArgumentException("Database connection is null or closed.");
        }

        List<DepartmentHeadcountData> headcounts = new ArrayList<>();
        String sql = "SELECT d.department_name, COUNT(e.employee_id) AS employee_count " +
                "FROM Employees e " +
                "JOIN Departments d ON e.current_department_id = d.department_id " +
                "WHERE e.employment_status = 'Active' " +
                "GROUP BY d.department_id, d.department_name " +
                "ORDER BY d.department_name";

        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String departmentName = rs.getString("department_name");
                int employeeCount = rs.getInt("employee_count");
                headcounts.add(new DepartmentHeadcountData(departmentName, employeeCount));
            }
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* log or ignore */ }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) { /* log or ignore */ }
            }
        }
        return headcounts;
    }
}