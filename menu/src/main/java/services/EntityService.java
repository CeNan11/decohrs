package services;

import entity.AuditLog;
import entity.Department;
import entity.Employee;
import entity.Position;

import java.sql.*;
import java.util.ArrayList;

public class EntityService {
    private Connection connection;

    public EntityService(Connection connection) {
        this.connection = connection;
    }
    
    public void insertDepartment(Department department) throws SQLException {
        String sql = "INSERT INTO Departments (department_name) VALUES (?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, department.getDepartmentName());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertPosition(Position position) throws SQLException {
        String sql = "INSERT INTO Positions (position_title) VALUES (?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, position.getPositionTitle());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Department> getDepartments() throws SQLException {
        ArrayList<Department> departments = new ArrayList<>();
        String sql = "SELECT * FROM Departments";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Department department = new Department();
                department.setDepartmentId(rs.getInt("department_id"));
                department.setDepartmentName(rs.getString("department_name"));
                departments.add(department);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return departments;
    }

    public ArrayList<Position> getPositions() throws SQLException {
        ArrayList<Position> positions = new ArrayList<>();
        String sql = "SELECT * FROM Positions";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Position position = new Position();
                position.setPositionId(rs.getInt("position_id"));
                position.setPositionTitle(rs.getString("position_title"));
                positions.add(position);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return positions;
    }
    
    public Department getDepartmentById(int departmentId) throws SQLException {
        String sql = "SELECT * FROM Departments WHERE department_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, departmentId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Department department = new Department();
                department.setDepartmentId(rs.getInt("department_id"));
                department.setDepartmentName(rs.getString("department_name"));
                return department;
            }
        }
        return null;
    }

    public Position getPositionById(int positionId) throws SQLException {
        String sql = "SELECT * FROM Positions WHERE position_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, positionId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Position position = new Position();
                position.setPositionId(rs.getInt("position_id"));
                position.setPositionTitle(rs.getString("position_title"));
                return position;
            }
        }
        return null;
    }

    public Department getDepartmentByName(String departmentName) throws SQLException {
        String sql = "SELECT * FROM Departments WHERE department_name = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, departmentName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Department department = new Department();
                department.setDepartmentId(rs.getInt("department_id"));
                department.setDepartmentName(rs.getString("department_name"));
                return department;
            }
        }
        return null;
    }

    public Position getPositionByName(String positionName) throws SQLException {
        String sql = "SELECT * FROM Positions WHERE position_title = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, positionName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Position position = new Position();
                position.setPositionId(rs.getInt("position_id"));
                position.setPositionTitle(rs.getString("position_title"));
                return position;
            }
        }
        return null;
    }

    public int insertAuditLog(AuditLog auditLog) throws SQLException {
        String sql = "INSERT INTO AuditLogs (performed_by, action, target_employee) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, auditLog.getPerformedBy());
            pstmt.setString(2, auditLog.getAction());
            pstmt.setInt(3, auditLog.getTargetEmployee());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public ArrayList<AuditLog> getAuditLogs() throws SQLException {
        ArrayList<AuditLog> auditLogs = new ArrayList<>();
        String sql = "SELECT * FROM AuditLogs";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                AuditLog auditLog = new AuditLog();
                auditLog.setId(rs.getInt("audit_log_id"));
                auditLog.setPerformedBy(rs.getString("performed_by"));
                auditLog.setAction(rs.getString("action"));
                auditLog.setTargetEmployee(rs.getInt("target_employee"));
                auditLog.setDateCreated(rs.getDate("created_at"));
                auditLogs.add(auditLog);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return auditLogs;
    }
}