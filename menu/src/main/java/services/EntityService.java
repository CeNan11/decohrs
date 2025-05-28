package services;

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
    
}