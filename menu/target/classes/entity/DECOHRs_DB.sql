CREATE DATABASE DECOHRS_DB;
USE DECOHRS_DB;

-- Employee Profile
CREATE TABLE Departments (
    department_id INT AUTO_INCREMENT PRIMARY KEY,
    department_name VARCHAR(100) NOT NULL UNIQUE
);
CREATE TABLE Positions (
    position_id INT AUTO_INCREMENT PRIMARY KEY,
    position_title VARCHAR(100) NOT NULL UNIQUE
);
CREATE TABLE Employees (
    employee_id INT AUTO_INCREMENT PRIMARY KEY,
    company_employee_id VARCHAR(20) UNIQUE,
    
    last_name VARCHAR(100) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    middle_name VARCHAR(100) NULL,
    suffix VARCHAR(20),
    
    contact_number_primary VARCHAR(20),
    current_address TEXT,
    home_address TEXT,
    date_of_birth DATE,
    place_of_birth VARCHAR(255),
    gender ENUM('Male', 'Female', 'Other'),
    civil_status VARCHAR(50),
    blood_type VARCHAR(10),
    number_of_siblings INT,
    hire_date DATE,
    regularization_date DATE,
    employment_status ENUM('Active', 'Inactive') DEFAULT 'Active',
    profile_picture_path VARCHAR(255),
    sss_number VARCHAR(20) UNIQUE,
    philhealth_number VARCHAR(20) UNIQUE,
    tin_number VARCHAR(20) UNIQUE,
    pagibig_number VARCHAR(20) UNIQUE,
    father_full_name VARCHAR(255),
    father_DOB DATE,
    mother_full_name VARCHAR(255),
    mother_DOB DATE,
    emergency_contact_name VARCHAR(255),
    emergency_contact_relationship VARCHAR(50),
    emergency_contact_address TEXT,
    emergency_contact_number VARCHAR(20),
    current_department_id INT,
    current_position_id INT,
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    FOREIGN KEY (current_department_id) REFERENCES Departments(department_id),
    FOREIGN KEY (current_position_id) REFERENCES Positions(position_id)
);
CREATE TABLE Dependents (
    dependent_id INT AUTO_INCREMENT PRIMARY KEY,
    employee_id INT NOT NULL,
    relationship_type ENUM('Spouse', 'Partner', 'Child') NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    date_of_birth DATE,
    place_of_birth VARCHAR(255),
    address TEXT,
    gender ENUM('Male', 'Female', 'Other'),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (employee_id) REFERENCES Employees(employee_id) ON DELETE CASCADE
);
CREATE TABLE EducationalBackground (
    education_id INT AUTO_INCREMENT PRIMARY KEY,
    employee_id INT NOT NULL,
    primary_school VARCHAR(255) NULL,
    primary_year_graduated Date,
    tertiary_school VARCHAR(255) NULL,
    tertiary_year_graduated Date,
    college_school VARCHAR(255) NULL,
    college_year_graduated Date,
    vocational_school VARCHAR(255) NULL,
    vocational_year_graduated Date,
    certificate_license_name VARCHAR(255) NULL,
    date_issued DATE NULL,
    valid_until DATE NULL,
    remarks TEXT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (employee_id) REFERENCES Employees(employee_id) ON DELETE CASCADE
);
CREATE TABLE WorkExperience (
    work_experience_id INT AUTO_INCREMENT PRIMARY KEY,
    employee_id INT NOT NULL,
    company_name VARCHAR(255) NOT NULL,
    position_held VARCHAR(255) NOT NULL,
    start_date DATE,
    end_date DATE,
    duration VARCHAR(100),
    remarks TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (employee_id) REFERENCES Employees(employee_id) ON DELETE CASCADE
);


-- USERS (Admin, Employee, EVP)
CREATE TABLE Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL, 
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE Roles (
    role_id INT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL UNIQUE --  'Admin', 'EVP', 'Employee', 'Manager'
);
INSERT INTO Roles (role_name) VALUES ('Admin'), ('EVP'), ('Employee');
CREATE TABLE UserRoles (
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (user_id, role_id), -- Ensures a user doesn't have the same role twice
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES Roles(role_id) ON DELETE CASCADE
);

-- Headcount Request
-- CREATE TABLE HeadcountRequests (
--     request_id INT AUTO_INCREMENT PRIMARY KEY,
--     requested_by_user_id INT NOT NULL,    -- The User ID of the EVP making the request
--     department_id INT NOT NULL,           -- For which department
--     position_id INT NULL,                 -- Optional: For which specific position (more specific)
--     number_requested INT NOT NULL,
--     justification TEXT,
--     request_date DATE NOT NULL DEFAULT (CURDATE()),
--     status ENUM('Pending', 'Approved', 'Rejected', 'Fulfilled') DEFAULT 'Pending',
--     approved_by_user_id INT NULL,         -- User ID of who approved(Admin)
--     decision_date DATE NULL,
--     decision_remarks TEXT NULL,
--     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

--     FOREIGN KEY (requested_by_user_id) REFERENCES Users(user_id), 
--     FOREIGN KEY (department_id) REFERENCES Departments(department_id),
--     FOREIGN KEY (position_id) REFERENCES Positions(position_id),
--     FOREIGN KEY (approved_by_user_id) REFERENCES Users(user_id)
-- );
