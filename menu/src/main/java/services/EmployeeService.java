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
    
    public EmployeeService(Connection connection) {
        this.connection = connection;
    }

    // Insert a new employee with all related data
    public int insertEmployee(Employee employee) throws SQLException {
        String sql = "INSERT INTO Employees (" +
            "company_employee_id, last_name, first_name, middle_name, suffix, " + // 1-5
            "contact_number_primary, current_address, home_address, " + // 6-8
            "date_of_birth, place_of_birth, gender, civil_status, blood_type, " + // 9-13
            "number_of_siblings, hire_date, regularization_date, employment_status, " + // 14 - 17
            "sss_number, philhealth_number, tin_number, pagibig_number, " + // 18-21
            "father_full_name, father_DOB, " + // 22-23
            "mother_full_name, mother_DOB, " + // 24-25
            "emergency_contact_name, emergency_contact_relationship, " + // 26-27
            "emergency_contact_address, emergency_contact_number, " + // 28-29
            "current_department_id, current_position_id) " + // 30-31
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, employee.getEmployeeCode() != null ? employee.getEmployeeCode() : "");
            pstmt.setString(2, employee.getLastName() != null ? employee.getLastName() : "");
            pstmt.setString(3, employee.getFirstName() != null ? employee.getFirstName() : "");
            pstmt.setString(4, employee.getMiddleName() != null ? employee.getMiddleName() : "");
            pstmt.setString(5, employee.getSuffix() != null ? employee.getSuffix() : "");
            pstmt.setString(6, employee.getContactNumberPrimary() != null ? employee.getContactNumberPrimary() : "");
            pstmt.setString(7, employee.getCurrentAddress() != null ? employee.getCurrentAddress() : "");
            pstmt.setString(8, employee.getHomeAddress() != null ? employee.getHomeAddress() : "");
            pstmt.setDate(9, employee.getDateOfBirth() != null ? employee.getDateOfBirth() : null);
            pstmt.setString(10, employee.getPlaceOfBirth() != null ? employee.getPlaceOfBirth() : "");
            pstmt.setString(11, employee.getGender() != null ? employee.getGender() : "");
            pstmt.setString(12, employee.getCivilStatus() != null ? employee.getCivilStatus() : "");
            pstmt.setString(13, employee.getBloodType() != null ? employee.getBloodType() : "");
            pstmt.setInt(14, 0); // number_of_siblings - default to 0
            pstmt.setDate(15, employee.getHireDate() != null ? employee.getHireDate() : null);
            pstmt.setDate(16, employee.getRegularizationDate() != null ? employee.getRegularizationDate() : null);
            pstmt.setString(17, employee.getStatus().toString());
            pstmt.setString(18, employee.getSSSNumber() != null ? employee.getSSSNumber() : "");
            pstmt.setString(19, employee.getPHICNumber() != null ? employee.getPHICNumber() : "");
            pstmt.setString(20, employee.getTIN() != null ? employee.getTIN() : "");
            pstmt.setString(21, employee.getHDMFNo() != null ? employee.getHDMFNo() : "");
            // Family background
            pstmt.setString(22, employee.getFamilyBackground().getFatherName() != null ? employee.getFamilyBackground().getFatherName() : "");
            pstmt.setDate(23, employee.getFamilyBackground().getFatherDOB() != null ? employee.getFamilyBackground().getFatherDOB() : null);
            pstmt.setString(24, employee.getFamilyBackground().getMotherName() != null ? employee.getFamilyBackground().getMotherName() : "");
            pstmt.setDate(25, employee.getFamilyBackground().getMotherDOB() != null ? employee.getFamilyBackground().getMotherDOB() : null);

            // Emergency contact
            pstmt.setString(26, employee.getEmergencyContact().getName() != null ? employee.getEmergencyContact().getName() : "");
            pstmt.setString(27, employee.getEmergencyContact().getRelationship() != null ? employee.getEmergencyContact().getRelationship() : "");
            pstmt.setString(28, employee.getEmergencyContact().getAddress() != null ? employee.getEmergencyContact().getAddress() : "");
            pstmt.setString(29, employee.getEmergencyContact().getContactNumber() != null ? employee.getEmergencyContact().getContactNumber() : "");

            // Department and Position
            pstmt.setInt(30, employee.getDepartmentId() != null ? employee.getDepartmentId() : 0);
            pstmt.setInt(31, employee.getPositionId() != null ? employee.getPositionId() : 0);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating employee failed, no rows affected.");
            }
            
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int employeeId = generatedKeys.getInt(1); // Get the first generated key
                    return employeeId; // Return the generated employeeId
                } else {
                    throw new SQLException("Creating employee failed, no ID obtained.");
                }
            }
        }
    }

    public void insertChildren(int employeeId, ArrayList<Child> children) throws SQLException {
        String sql = "INSERT INTO Children (" +
            "employee_id, name, date_of_birth, place_of_birth, gender) " +
            "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (Child child : children) {
                pstmt.setInt(1, employeeId);
                pstmt.setString(2, child.getName());
                pstmt.setDate(3, child.getDateOfBirth());
                pstmt.setString(4, child.getPlaceOfBirth());
                pstmt.setString(5, child.getGender());

                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Child getChildById(int childId) throws SQLException {
        String sql = "SELECT * FROM Children WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, childId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Child child = new Child();
                    child.setChildId(rs.getInt("child_id"));
                    child.setEmployeeId(rs.getInt("employee_id"));
                    child.setName(rs.getString("name"));
                    child.setDateOfBirth(rs.getDate("date_of_birth"));
                    child.setPlaceOfBirth(rs.getString("place_of_birth"));
                    child.setGender(rs.getString("gender"));

                    return child;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



    public void insertEducation(int employeeId, Education education) throws SQLException {
        String sql = "INSERT INTO EducationalBackground (" +
            "employee_id, primary_school, primary_year_graduated, tertiary_school, tertiary_year_graduated, " +
            "college_school, college_year_graduated, vocational_school, vocational_year_graduated, " +
            "certificate_license_name, date_issued, valid_until) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            int paramIndex = 1;
            pstmt.setInt(paramIndex++, employeeId);
            pstmt.setString(paramIndex++, education.getPrimarySchool() != null ? education.getPrimarySchool() : "");
            pstmt.setDate(paramIndex++, education.getPrimaryYearGraduated() != null ? education.getPrimaryYearGraduated() : null);
            pstmt.setString(paramIndex++, education.getTertiarySchool() != null ? education.getTertiarySchool() : "");
            pstmt.setDate(paramIndex++, education.getTertiaryYearGraduated() != null ? education.getTertiaryYearGraduated() : null);
            pstmt.setString(paramIndex++, education.getCollegeSchool() != null ? education.getCollegeSchool() : "");
            pstmt.setDate(paramIndex++, education.getCollegeYearGraduated() != null ? education.getCollegeYearGraduated() : null);
            pstmt.setString(paramIndex++, education.getVocationalSchool() != null ? education.getVocationalSchool() : "");
            pstmt.setDate(paramIndex++, education.getVocationalYearGraduated() != null ? education.getVocationalYearGraduated() : null);
            pstmt.setString(paramIndex++, education.getCertificateLicenseName() != null ? education.getCertificateLicenseName() : "");
            pstmt.setDate(paramIndex++, education.getDateIssued() != null ? education.getDateIssued() : null);
            pstmt.setDate(paramIndex++, education.getValidUntil() != null ? education.getValidUntil() : null);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Insert work experience
    public void insertWorkExperience(int employeeId, ArrayList<WorkExperience> experiences) throws SQLException {
        String sql = "INSERT INTO WorkExperience (" +
            "employee_id, company_name, position_held," +
            "duration, remarks) " +
            "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (WorkExperience experience : experiences) {
                pstmt.setInt(1, employeeId);
                pstmt.setString(2, experience.getCompanyName());
                pstmt.setString(3, experience.getPositionHeld());
                pstmt.setString(4, experience.getDuration());
                pstmt.setString(5, experience.getRemarks());

                pstmt.executeUpdate();
            }

        }
    }

    // Get employee by ID
    public Employee getEmployeeById(int employeeId) throws SQLException {
        String sql = "{Call GetEmployeeDetailsByID(?)}";
        
        try (CallableStatement pstmt = connection.prepareCall(sql)) {
            pstmt.setInt(1, employeeId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Employee employee = mapResultSetToEmployee(rs);
                    return employee;
                }
            }
        }
        return null;
    }

    public ArrayList<Employee> getEmployees() throws SQLException {
        ArrayList<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM Employees";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                employees.add(mapResultSetToEmployee(rs));
            }
        }
        return employees;
    }

    public Employee getEmployeeByCode(String employeeCode) throws SQLException {
        String sql = "SELECT * FROM Employees WHERE company_employee_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, employeeCode);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToEmployee(rs);
            }
        }
        return null;
    }

    // Get all employees
    public ArrayList<Employee> getAllEmployees() throws SQLException {
        ArrayList<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM Employees";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                employees.add(mapResultSetToEmployee(rs));
            }
        }
        return employees;
    }

    public int getEmployeeCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS employee_count FROM Employees"; // Important: Alias the count
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {  // Move the cursor to the first (and only) row
                return rs.getInt("employee_count"); // Use the alias to retrieve the count
            } else {
                return 0; // Handle the case where the table is empty (though it shouldn't happen)
            }
        }
    }
    
    public int getEmployeeCountByStatus(String status) throws SQLException {
        String sql = "SELECT COUNT(*) as employee_count FROM Employees WHERE employment_status = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, status);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("employee_count");
            } else {
                return 0;
            }
        } 
    }

    // Map ResultSet to Employee object
    private Employee mapResultSetToEmployee(ResultSet rs) throws SQLException {
        Employee employee = new Employee();

        // Employee ID
        employee.setEmployeeId(rs.getInt("employee_id"));

        // Employee code
        employee.setEmployeeCode(rs.getString("company_employee_id"));

        // Name
        employee.setLastName(rs.getString("last_name"));
        employee.setFirstName(rs.getString("first_name"));
        employee.setMiddleName(rs.getString("middle_name"));
        employee.setSuffix(rs.getString("suffix"));

        // Contact number and address
        employee.setContactNumberPrimary(rs.getString("contact_number_primary"));
        employee.setCurrentAddress(rs.getString("current_address"));
        employee.setHomeAddress(rs.getString("home_address"));
        employee.setDateOfBirth(rs.getDate("date_of_birth"));
        employee.setPlaceOfBirth(rs.getString("place_of_birth"));
        employee.setGender(rs.getString("gender"));

        // Civil status and blood type
        employee.setCivilStatus(rs.getString("civil_status"));
        employee.setBloodType(rs.getString("blood_type"));

        // Hire date and regularization date
        employee.setHireDate(rs.getDate("hire_date"));
        employee.setRegularizationDate(rs.getDate("regularization_date"));
        if (rs.getString("employment_status").equals("Active")) {
            employee.setStatus(EmployeeStatus.ACTIVE);
        } else {
            employee.setStatus(EmployeeStatus.INACTIVE);
        }

        // SSS, PHIC, TIN, and Pagibig
        employee.setSSSNumber(rs.getString("sss_number"));
        employee.setPHICNumber(rs.getString("philhealth_number"));
        employee.setTIN(rs.getString("tin_number"));
        employee.setHDMFNo(rs.getString("pagibig_number"));

        // Department and Position
        employee.setDepartmentId(rs.getInt("current_department_id"));
        employee.setPositionId(rs.getInt("current_position_id"));

        // Family background
        FamilyBackground family = new FamilyBackground();
        family.setFatherName(rs.getString("father_full_name"));
        family.setFatherDOB(rs.getDate("father_DOB"));
        family.setMotherName(rs.getString("mother_full_name"));
        family.setMotherDOB(rs.getDate("mother_DOB"));
        int siblings = 0;
        try { siblings = rs.getInt("number_of_siblings"); } catch (Exception ignore) {}
        Dependent dependent = getEmployeeDependents(employee.getEmployeeId());
        family.setNumberOfSiblings(siblings);
        family.setSpouseName(dependent.getFullName());
        family.setSpouseBirthDate(dependent.getDateOfBirth());
        family.setSpouseAddress(dependent.getAddress());
        
        employee.setFamilyBackground(family);

        
        // Emergency contact
        EmergencyContact emergency = new EmergencyContact();
        emergency.setName(rs.getString("emergency_contact_name"));
        emergency.setRelationship(rs.getString("emergency_contact_relationship"));
        emergency.setAddress(rs.getString("emergency_contact_address"));
        emergency.setContactNumber(rs.getString("emergency_contact_number"));
        employee.setEmergencyContact(emergency);

        employee.setEducation(getEmployeeEducation(employee.getEmployeeId()));

        for (WorkExperience experience : getEmployeeWorkExperience(employee.getEmployeeId())) {
            if (experience != null) {
                employee.addWorkExperience(experience);
            }
        }

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
            "company_employee_id = ?, last_name = ?, first_name = ?, middle_name = ?, suffix = ?, " +
            "contact_number_primary = ?, current_address = ?, home_address = ?, " +
            "date_of_birth = ?, place_of_birth = ?, gender = ?, civil_status = ?, blood_type = ?, " +
            "number_of_siblings = ?, hire_date = ?, regularization_date = ?, employment_status = ?, " +
            "sss_number = ?, philhealth_number = ?, tin_number = ?, pagibig_number = ?, " +
            "father_full_name = ?, father_DOB = ?, mother_full_name = ?, mother_DOB = ?, " +
            "emergency_contact_name = ?, emergency_contact_relationship = ?, emergency_contact_address = ?, emergency_contact_number = ?, " +
            "current_department_id = ?, current_position_id = ? " +
            "WHERE employee_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            int paramIndex = 1;
            pstmt.setString(paramIndex++, employee.getEmployeeCode() != null ? employee.getEmployeeCode() : "");
            pstmt.setString(paramIndex++, employee.getLastName() != null ? employee.getLastName() : "");
            pstmt.setString(paramIndex++, employee.getFirstName() != null ? employee.getFirstName() : "");
            pstmt.setString(paramIndex++, employee.getMiddleName() != null ? employee.getMiddleName() : "");
            pstmt.setString(paramIndex++, employee.getSuffix() != null ? employee.getSuffix() : "");
            pstmt.setString(paramIndex++, employee.getContactNumberPrimary() != null ? employee.getContactNumberPrimary() : "");
            pstmt.setString(paramIndex++, employee.getCurrentAddress() != null ? employee.getCurrentAddress() : "");
            pstmt.setString(paramIndex++, employee.getHomeAddress() != null ? employee.getHomeAddress() : "");
            pstmt.setDate(paramIndex++, employee.getDateOfBirth() != null ? employee.getDateOfBirth() : null);
            pstmt.setString(paramIndex++, employee.getPlaceOfBirth() != null ? employee.getPlaceOfBirth() : "");
            pstmt.setString(paramIndex++, employee.getGender() != null ? employee.getGender() : "");
            pstmt.setString(paramIndex++, employee.getCivilStatus() != null ? employee.getCivilStatus() : "");
            pstmt.setString(paramIndex++, employee.getBloodType() != null ? employee.getBloodType() : "");
            pstmt.setInt(paramIndex++, (employee.getFamilyBackground() != null && employee.getFamilyBackground().getNumberOfSiblings() != null) ? employee.getFamilyBackground().getNumberOfSiblings() : 0);
            pstmt.setDate(paramIndex++, employee.getHireDate() != null ? employee.getHireDate() : null);
            pstmt.setDate(paramIndex++, employee.getRegularizationDate() != null ? employee.getRegularizationDate() : null);
            pstmt.setString(paramIndex++, employee.getStatus() != null ? employee.getStatus().toString() : "ACTIVE");
            pstmt.setString(paramIndex++, employee.getSSSNumber() != null ? employee.getSSSNumber() : "");
            pstmt.setString(paramIndex++, employee.getPHICNumber() != null ? employee.getPHICNumber() : "");
            pstmt.setString(paramIndex++, employee.getTIN() != null ? employee.getTIN() : "");
            pstmt.setString(paramIndex++, employee.getHDMFNo() != null ? employee.getHDMFNo() : "");
            // Family background
            pstmt.setString(paramIndex++, (employee.getFamilyBackground() != null && employee.getFamilyBackground().getFatherName() != null) ? employee.getFamilyBackground().getFatherName() : "");
            pstmt.setDate(paramIndex++, (employee.getFamilyBackground() != null && employee.getFamilyBackground().getFatherDOB() != null) ? employee.getFamilyBackground().getFatherDOB() : null);
            pstmt.setString(paramIndex++, (employee.getFamilyBackground() != null && employee.getFamilyBackground().getMotherName() != null) ? employee.getFamilyBackground().getMotherName() : "");
            pstmt.setDate(paramIndex++, (employee.getFamilyBackground() != null && employee.getFamilyBackground().getMotherDOB() != null) ? employee.getFamilyBackground().getMotherDOB() : null);
            // Emergency contact
            pstmt.setString(paramIndex++, (employee.getEmergencyContact() != null && employee.getEmergencyContact().getName() != null) ? employee.getEmergencyContact().getName() : "");
            pstmt.setString(paramIndex++, (employee.getEmergencyContact() != null && employee.getEmergencyContact().getRelationship() != null) ? employee.getEmergencyContact().getRelationship() : "");
            pstmt.setString(paramIndex++, (employee.getEmergencyContact() != null && employee.getEmergencyContact().getAddress() != null) ? employee.getEmergencyContact().getAddress() : "");
            pstmt.setString(paramIndex++, (employee.getEmergencyContact() != null && employee.getEmergencyContact().getContactNumber() != null) ? employee.getEmergencyContact().getContactNumber() : "");
            // Department and Position
            pstmt.setInt(paramIndex++, employee.getDepartmentId() != null ? employee.getDepartmentId() : 0);
            pstmt.setInt(paramIndex++, employee.getPositionId() != null ? employee.getPositionId() : 0);
            pstmt.setInt(paramIndex++, employee.getEmployeeId() != null ? employee.getEmployeeId() : 0);

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
    public ArrayList<Employee> searchEmployeesByName(String searchTerm) throws SQLException {
        ArrayList<Employee> employees = new ArrayList<>();
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
    public ArrayList<Employee> getEmployeesByDepartment(int departmentId) throws SQLException {
        ArrayList<Employee> employees = new ArrayList<>();
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
    public ArrayList<Employee> getEmployeesByPosition(int positionId) throws SQLException {
        ArrayList<Employee> employees = new ArrayList<>();
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

    public ArrayList<Employee> getEmployeesByStatus(String status) throws SQLException {
        ArrayList<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM Employees WHERE employment_status = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, status);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    employees.add(mapResultSetToEmployee(rs));
                }
            }
        }
        return employees;
    }

    public Education getEmployeeEducation(int employeeId) throws SQLException {
        Education education = new Education();
        String sql = "SELECT * FROM EducationalBackground WHERE employee_id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, employeeId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {

                    education.setEducationId(rs.getInt("education_id"));
                    education.setEmployeeId(rs.getInt("employee_id"));
                    education.setPrimarySchool(rs.getString("primary_school"));
                    education.setPrimaryYearGraduated(rs.getDate("primary_year_graduated"));
                    education.setTertiarySchool(rs.getString("tertiary_school"));
                    education.setTertiaryYearGraduated(rs.getDate("tertiary_year_graduated"));
                    education.setCollegeSchool(rs.getString("college_school"));
                    education.setCollegeYearGraduated(rs.getDate("college_year_graduated"));
                    education.setVocationalSchool(rs.getString("vocational_school"));
                    education.setVocationalYearGraduated(rs.getDate("vocational_year_graduated"));
                    education.setCertificateLicenseName(rs.getString("certificate_license_name"));
                    education.setDateIssued(rs.getDate("date_issued"));
                    education.setValidUntil(rs.getDate("valid_until"));
                }
            }
        }
        return education;
    }

    public ArrayList<WorkExperience> getEmployeeWorkExperience(int employeeId) throws SQLException {
        ArrayList<WorkExperience> workExperiences = new ArrayList<>();
        String sql = "SELECT * FROM WorkExperience WHERE employee_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, employeeId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    WorkExperience experience = new WorkExperience();
                    experience.setWorkExperienceId(rs.getInt("work_experience_id"));
                    experience.setEmployeeId(rs.getInt("employee_id"));
                    experience.setCompanyName(rs.getString("company_name"));
                    experience.setPositionHeld(rs.getString("position_held"));
                    experience.setDuration(rs.getString("duration"));
                    experience.setRemarks(rs.getString("remarks"));

                    workExperiences.add(experience);
                }
            }
        }
        return workExperiences;
    }

    public Dependent getEmployeeDependents(int employeeId) throws SQLException {
        Dependent dependent = new Dependent();
        String sql = "SELECT * FROM Dependents WHERE employee_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, employeeId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    dependent.setDependentId(rs.getInt("dependent_id"));
                    dependent.setEmployeeId(rs.getInt("employee_id"));
                    dependent.setFullName(rs.getString("full_name"));
                    dependent.setDateOfBirth(rs.getDate("date_of_birth"));
                    dependent.setAddress(rs.getString("address"));
                }
            }
        }
        return dependent;
    }

    public Child getEmployeeChildren(int employeeId) throws SQLException {
        Child child = new Child();
        String sql = "SELECT * FROM Children WHERE employee_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, employeeId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    child.setChildId(rs.getInt("child_id"));
                    child.setEmployeeId(rs.getInt("employee_id"));
                    child.setName(rs.getString("name"));
                    child.setDateOfBirth(rs.getDate("date_of_birth"));
                    child.setPlaceOfBirth(rs.getString("place_of_birth"));
                    child.setGender(rs.getString("gender"));
                }
            }
        }
        return child;
    }

    public void insertChild(Child child) throws SQLException {
        String sql = "INSERT INTO Children (employee_id, name, date_of_birth, place_of_birth, gender)" + 
        " VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, child.getEmployeeId());
            pstmt.setString(2, child.getName());
            pstmt.setDate(3, child.getDateOfBirth() != null ? child.getDateOfBirth() : null);
            pstmt.setString(4, child.getPlaceOfBirth());
            pstmt.setString(5, child.getGender());

            pstmt.executeUpdate();
        }
    }
} 
