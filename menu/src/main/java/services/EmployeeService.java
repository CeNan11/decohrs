package services;

import entity.Employee;
import entity.EmployeeStatus;
import entity.Department;
import entity.Position;
import entity.EmergencyContact;
import entity.FamilyBackground;
import entity.Education;
import entity.Child;
import entity.Dependent;
import entity.WorkExperience;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeService {
    private Connection connection;
    private static String localHost = "jdbc:mysql://localhost:3306/DECOHRS_DB";
    private static String username = "root";
    private static String pass = "";
    
    public EmployeeService(Connection connection) {
        this.connection = connection;
    }

    // Insert a new employee with all related data
    public int insertEmployee(Employee employee) throws SQLException {
        String sql = "INSERT INTO Employees (" +
            "company_employee_id, last_name, first_name, middle_name, suffix, " +
            "contact_number_primary, email_address, current_address, home_address, " +
            "date_of_birth, place_of_birth, gender, civil_status, blood_type, " +
            "number_of_siblings, hire_date, regularization_date, employment_status, " +
            "sss_number, philhealth_number, tin_number, pagibig_number, " +
            "father_full_name, father_DOB, father_sss_number, " +
            "mother_full_name, mother_DOB, " +
            "emergency_contact_name, emergency_contact_relationship, " +
            "emergency_contact_address, emergency_contact_number, " +
            "current_department_id, current_position_id) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int paramIndex = 1;
            pstmt.setString(paramIndex++, employee.getEmployeeCode());
            pstmt.setString(paramIndex++, employee.getLastName());
            pstmt.setString(paramIndex++, employee.getFirstName());
            pstmt.setString(paramIndex++, employee.getMiddleName());
            pstmt.setString(paramIndex++, employee.getSuffix());
            pstmt.setString(paramIndex++, employee.getContactNumberPrimary());
            pstmt.setString(paramIndex++, employee.getEmailAddress());
            pstmt.setString(paramIndex++, employee.getCurrentAddress());
            pstmt.setString(paramIndex++, employee.getHomeAddress());
            pstmt.setDate(paramIndex++, employee.getDateOfBirth());
            pstmt.setString(paramIndex++, employee.getPlaceOfBirth());
            pstmt.setString(paramIndex++, employee.getGender());
            pstmt.setString(paramIndex++, employee.getCivilStatus());
            pstmt.setString(paramIndex++, employee.getBloodType());
            pstmt.setInt(paramIndex++, 0); // number_of_siblings - default to 0
            pstmt.setDate(paramIndex++, employee.getHireDate());
            pstmt.setDate(paramIndex++, employee.getRegularizationDate());
            pstmt.setString(paramIndex++, employee.getStatus().toString());
            pstmt.setString(paramIndex++, employee.getSSSNumber());
            pstmt.setString(paramIndex++, employee.getPHICNumber());
            pstmt.setString(paramIndex++, employee.getTIN());
            pstmt.setString(paramIndex++, employee.getHDMFNo());
            
            // Family background
            if (employee.getFamilyBackground() != null) {
                pstmt.setString(paramIndex++, employee.getFamilyBackground().getFatherName());
                pstmt.setDate(paramIndex++, employee.getFamilyBackground().getFatherDOB());
                pstmt.setString(paramIndex++, employee.getFamilyBackground().getMotherName());
                pstmt.setDate(paramIndex++, employee.getFamilyBackground().getMotherDOB());
            } else {
                pstmt.setString(paramIndex++, null);
                pstmt.setDate(paramIndex++, null);
                pstmt.setString(paramIndex++, null);
                pstmt.setString(paramIndex++, null);
                pstmt.setDate(paramIndex++, null);
            }

            // Emergency contact
            if (employee.getEmergencyContact() != null) {
                pstmt.setString(paramIndex++, employee.getEmergencyContact().getName());
                pstmt.setString(paramIndex++, employee.getEmergencyContact().getRelationship());
                pstmt.setString(paramIndex++, employee.getEmergencyContact().getAddress());
                pstmt.setString(paramIndex++, employee.getEmergencyContact().getContactNumber());
            } else {
                pstmt.setString(paramIndex++, null);
                pstmt.setString(paramIndex++, null);
                pstmt.setString(paramIndex++, null);
                pstmt.setString(paramIndex++, null);
            }

            pstmt.setInt(paramIndex++, employee.getDepartmentId());
            pstmt.setInt(paramIndex++, employee.getPositionId());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating employee failed, no rows affected.");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int employeeId = generatedKeys.getInt(1);
                    
                    // Insert related data if available
                    // if (employee.getEducation() != null) {
                    //     insertEducation(employeeId, employee.getEducation());
                    // }
                    
                    // if (employee.getChildren() != null && !employee.getChildren().isEmpty()) {
                    //     for (Child child : employee.getChildren()) {
                    //         insertDependent(employeeId, child);
                    //     }
                    // }
                    
                    return employeeId;
                } else {
                    throw new SQLException("Creating employee failed, no ID obtained.");
                }
            }
        }
    }

    // Insert education record
    // private void insertEducation(int employeeId, Education education) throws SQLException {
    //     String sql = "INSERT INTO EducationalBackground (" +
    //         "employee_id, education_level, institution_name, course_degree_program, " +
    //         "year_graduated, certificate_license_name, date_issued, valid_until, remarks) " +
    //         "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    //     try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
    //         pstmt.setInt(1, employeeId);
    //         pstmt.setString(2, education.getEducationLevel());
    //         pstmt.setString(3, education.getInstitutionName());
    //         pstmt.setString(4, education.getCourseDegreeProgram());
    //         pstmt.setString(5, education.getYearGraduated());
    //         pstmt.setString(6, education.getCertificateLicenseName());
    //         pstmt.setDate(7, education.getDateIssued());
    //         pstmt.setDate(8, education.getValidUntil());
    //         pstmt.setString(9, education.getRemarks());

    //         pstmt.executeUpdate();
    //     }
    // }

    // Insert dependent record
    // private void insertDependent(int employeeId, Child child) throws SQLException {
    //     String sql = "INSERT INTO Dependents (" +
    //         "employee_id, relationship_type, full_name, date_of_birth, " +
    //         "place_of_birth, address, gender) " +
    //         "VALUES (?, ?, ?, ?, ?, ?, ?)";

    //     try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
    //         pstmt.setInt(1, employeeId);
    //         pstmt.setString(2, "Child");
    //         pstmt.setString(3, child.getFullName());
    //         pstmt.setDate(4, child.getDateOfBirth());
    //         pstmt.setString(5, child.getPlaceOfBirth());
    //         pstmt.setString(6, child.getAddress());
    //         pstmt.setString(7, child.getGender());

    //         pstmt.executeUpdate();
    //     }
    // }

    // Insert work experience
    public void insertWorkExperience(int employeeId, WorkExperience experience) throws SQLException {
        String sql = "INSERT INTO WorkExperience (" +
            "employee_id, company_name, position_held, start_date, end_date, " +
            "duration_text, responsibilities_remarks) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, employeeId);
            pstmt.setString(2, experience.getCompanyName());
            pstmt.setString(3, experience.getPositionHeld());
            // TODO: Work Expericnce SQL

            pstmt.executeUpdate();
        }
    }

    // Get employee by ID
    public Employee getEmployeeById(int employeeId) throws SQLException {
        String sql = "SELECT * FROM Employees WHERE employee_id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, employeeId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEmployee(rs);
                }
            }
        }
        return null;
    }

    // Get all employees
    public List<Employee> getAllEmployees() throws SQLException {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM Employees";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                employees.add(mapResultSetToEmployee(rs));
            }
        }
        return employees;
    }

    // Map ResultSet to Employee object
    private Employee mapResultSetToEmployee(ResultSet rs) throws SQLException {
        Employee employee = new Employee();
        employee.setEmployeeId(rs.getInt("employee_id"));
        employee.setEmployeeCode(rs.getString("company_employee_id"));
        employee.setLastName(rs.getString("last_name"));
        employee.setFirstName(rs.getString("first_name"));
        employee.setMiddleName(rs.getString("middle_name"));
        employee.setSuffix(rs.getString("suffix"));
        employee.setContactNumberPrimary(rs.getString("contact_number_primary"));
        employee.setEmailAddress(rs.getString("email_address"));
        employee.setCurrentAddress(rs.getString("current_address"));
        employee.setHomeAddress(rs.getString("home_address"));
        employee.setDateOfBirth(rs.getDate("date_of_birth"));
        employee.setPlaceOfBirth(rs.getString("place_of_birth"));
        employee.setGender(rs.getString("gender"));
        employee.setCivilStatus(rs.getString("civil_status"));
        employee.setBloodType(rs.getString("blood_type"));
        employee.setHireDate(rs.getDate("hire_date"));
        employee.setRegularizationDate(rs.getDate("regularization_date"));
        employee.setStatus(EmployeeStatus.valueOf(rs.getString("employment_status")));
        employee.setSSSNumber(rs.getString("sss_number"));
        employee.setPHICNumber(rs.getString("philhealth_number"));
        employee.setTIN(rs.getString("tin_number"));
        employee.setHDMFNo(rs.getString("pagibig_number"));
        employee.setDepartmentId(rs.getInt("current_department_id"));
        employee.setPositionId(rs.getInt("current_position_id"));
        
        // Load related data
        // loadEmployeeRelatedData(employee);
        
        return employee;
    }

    // Load related data for an employee
    // private void loadEmployeeRelatedData(Employee employee) throws SQLException {
    //     // Load education
    //     loadEducation(employee);
        
    //     // Load dependents
    //     loadDependents(employee);
        
    //     // Load work experience
    //     loadWorkExperience(employee);
    // }

    // Load education records
    // private void loadEducation(Employee employee) throws SQLException {
    //     String sql = "SELECT * FROM EducationalBackground WHERE employee_id = ?";
        
    //     try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
    //         pstmt.setInt(1, employee.getEmployeeId());
            
    //         try (ResultSet rs = pstmt.executeQuery()) {
    //             if (rs.next()) {
    //                 Education education = new Education();
    //                 education.setEducationLevel(rs.getString("education_level"));
    //                 education.setInstitutionName(rs.getString("institution_name"));
    //                 education.setCourseDegreeProgram(rs.getString("course_degree_program"));
    //                 education.setYearGraduated(rs.getString("year_graduated"));
    //                 education.setCertificateLicenseName(rs.getString("certificate_license_name"));
    //                 education.setDateIssued(rs.getDate("date_issued"));
    //                 education.setValidUntil(rs.getDate("valid_until"));
    //                 education.setRemarks(rs.getString("remarks"));
    //                 employee.setEducation(education);
    //             }
    //         }
    //     }
    // }

    // Load dependents
    // private void loadDependents(Employee employee) throws SQLException {
    //     String sql = "SELECT * FROM Dependents WHERE employee_id = ? AND relationship_type = 'Child'";
    //     List<Child> children = new ArrayList<>();
        
    //     try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
    //         pstmt.setInt(1, employee.getEmployeeId());
            
    //         try (ResultSet rs = pstmt.executeQuery()) {
    //             while (rs.next()) {
    //                 Child child = new Child();
    //                 child.setFullName(rs.getString("full_name"));
    //                 child.setDateOfBirth(rs.getDate("date_of_birth"));
    //                 child.setPlaceOfBirth(rs.getString("place_of_birth"));
    //                 child.setAddress(rs.getString("address"));
    //                 child.setGender(rs.getString("gender"));
    //                 children.add(child);
    //             }
    //         }
    //     }
    //     employee.setChildren(children);
    // }

    // Load work experience
    private void loadWorkExperience(Employee employee) throws SQLException {
        String sql = "SELECT * FROM WorkExperience WHERE employee_id = ?";
        List<WorkExperience> experiences = new ArrayList<>();
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, employee.getEmployeeId());
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    WorkExperience experience = new WorkExperience();
                    experience.setCompanyName(rs.getString("company_name"));
                    experience.setPositionHeld(rs.getString("position_held"));
                    // TODO: Work experience in SQL load

                    experiences.add(experience);
                }
            }
        }
        // Note: You'll need to add a setter for work experiences in the Employee class
    }

    // Update employee
    public boolean updateEmployee(Employee employee) throws SQLException {
        String sql = "UPDATE Employees SET " +
            "company_employee_id = ?, last_name = ?, first_name = ?, middle_name = ?, " +
            "suffix = ?, contact_number_primary = ?, email_address = ?, " +
            "current_address = ?, home_address = ?, date_of_birth = ?, " +
            "place_of_birth = ?, gender = ?, civil_status = ?, blood_type = ?, " +
            "hire_date = ?, regularization_date = ?, employment_status = ?, " +
            "sss_number = ?, philhealth_number = ?, tin_number = ?, pagibig_number = ?, " +
            "current_department_id = ?, current_position_id = ? " +
            "WHERE employee_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            int paramIndex = 1;
            pstmt.setString(paramIndex++, employee.getEmployeeCode());
            pstmt.setString(paramIndex++, employee.getLastName());
            pstmt.setString(paramIndex++, employee.getFirstName());
            pstmt.setString(paramIndex++, employee.getMiddleName());
            pstmt.setString(paramIndex++, employee.getSuffix());
            pstmt.setString(paramIndex++, employee.getContactNumberPrimary());
            pstmt.setString(paramIndex++, employee.getEmailAddress());
            pstmt.setString(paramIndex++, employee.getCurrentAddress());
            pstmt.setString(paramIndex++, employee.getHomeAddress());
            pstmt.setDate(paramIndex++, employee.getDateOfBirth());
            pstmt.setString(paramIndex++, employee.getPlaceOfBirth());
            pstmt.setString(paramIndex++, employee.getGender());
            pstmt.setString(paramIndex++, employee.getCivilStatus());
            pstmt.setString(paramIndex++, employee.getBloodType());
            pstmt.setDate(paramIndex++, employee.getHireDate());
            pstmt.setDate(paramIndex++, employee.getRegularizationDate());
            pstmt.setString(paramIndex++, employee.getStatus().toString());
            pstmt.setString(paramIndex++, employee.getSSSNumber());
            pstmt.setString(paramIndex++, employee.getPHICNumber());
            pstmt.setString(paramIndex++, employee.getTIN());
            pstmt.setString(paramIndex++, employee.getHDMFNo());
            pstmt.setInt(paramIndex++, employee.getDepartmentId());
            pstmt.setInt(paramIndex++, employee.getPositionId());
            pstmt.setInt(paramIndex++, employee.getEmployeeId());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    // Delete employee
    public boolean deleteEmployee(int employeeId) throws SQLException {
        String sql = "DELETE FROM Employees WHERE employee_id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, employeeId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    // Search employees by name
    public List<Employee> searchEmployeesByName(String searchTerm) throws SQLException {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM Employees WHERE " +
            "first_name LIKE ? OR last_name LIKE ? OR middle_name LIKE ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            String searchPattern = "%" + searchTerm + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);
            pstmt.setString(3, searchPattern);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    employees.add(mapResultSetToEmployee(rs));
                }
            }
        }
        return employees;
    }

    // Get employees by department
    public List<Employee> getEmployeesByDepartment(int departmentId) throws SQLException {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM Employees WHERE current_department_id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, departmentId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    employees.add(mapResultSetToEmployee(rs));
                }
            }
        }
        return employees;
    }

    // Get employees by position
    public List<Employee> getEmployeesByPosition(int positionId) throws SQLException {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM Employees WHERE current_position_id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, positionId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    employees.add(mapResultSetToEmployee(rs));
                }
            }
        }
        return employees;
    }
} 