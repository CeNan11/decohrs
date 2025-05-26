CREATE DATABASE DECOHRS_DB;
USE DECOHRS_DB;

-- Common Lookup Tables
CREATE TABLE GenderTypes (
    gender_id TINYINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    gender_name VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE CivilStatusTypes (
    status_id TINYINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    status_name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE BloodTypes (
    blood_type_id TINYINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    blood_type VARCHAR(10) NOT NULL UNIQUE
);

CREATE TABLE EmploymentStatusTypes (
    status_id TINYINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    status_name VARCHAR(50) NOT NULL UNIQUE,
    description TEXT
);

-- Insert common values
INSERT INTO GenderTypes (gender_name) VALUES ('Male'), ('Female'), ('Other');
INSERT INTO CivilStatusTypes (status_name) VALUES ('Single'), ('Married'), ('Widowed'), ('Separated'), ('Divorced');
INSERT INTO BloodTypes (blood_type) VALUES ('A+'), ('A-'), ('B+'), ('B-'), ('AB+'), ('AB-'), ('O+'), ('O-');
INSERT INTO EmploymentStatusTypes (status_name, description) VALUES 
    ('Active', 'Currently employed and active'),
    ('Inactive', 'No longer employed');

-- Core Tables
CREATE TABLE Departments (
    department_id SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    department_code VARCHAR(10) NOT NULL UNIQUE,
    department_name VARCHAR(100) NOT NULL,
    description TEXT,
    parent_department_id SMALLINT UNSIGNED NULL,
    FOREIGN KEY (parent_department_id) REFERENCES Departments(department_id)
);

CREATE TABLE Positions (
    position_id SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    position_title VARCHAR(100) NOT NULL,
    department_id SMALLINT UNSIGNED NOT NULL,
    FOREIGN KEY (department_id) REFERENCES Departments(department_id)
);

-- Employee Core Information
CREATE TABLE Employees (
    employee_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    employee_code VARCHAR(20) NOT NULL UNIQUE,
    
    -- Personal Information
    last_name VARCHAR(100) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    middle_name VARCHAR(100),
    suffix VARCHAR(10),
    date_of_birth DATE NOT NULL,
    place_of_birth VARCHAR(255) NOT NULL,
    gender_id TINYINT UNSIGNED NOT NULL,
    civil_status_id TINYINT UNSIGNED NOT NULL,
    blood_type_id TINYINT UNSIGNED,
    nationality VARCHAR(100) DEFAULT 'Filipino',
    
    -- Contact Information
    email_address VARCHAR(191) UNIQUE,
    contact_number_primary VARCHAR(20) NOT NULL,
    contact_number_secondary VARCHAR(20),
    current_address TEXT NOT NULL,
    home_address TEXT,
    
    -- Employment Information
    department_id SMALLINT UNSIGNED NOT NULL,
    position_id SMALLINT UNSIGNED NOT NULL,
    hire_date DATE NOT NULL,
    regularization_date DATE,
    employment_status_id TINYINT UNSIGNED NOT NULL,
    
    -- Audit Fields
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP NULL,
    
    -- Foreign Keys
    FOREIGN KEY (gender_id) REFERENCES GenderTypes(gender_id),
    FOREIGN KEY (civil_status_id) REFERENCES CivilStatusTypes(status_id),
    FOREIGN KEY (blood_type_id) REFERENCES BloodTypes(blood_type_id),
    FOREIGN KEY (employment_status_id) REFERENCES EmploymentStatusTypes(status_id),
    FOREIGN KEY (department_id) REFERENCES Departments(department_id),
    FOREIGN KEY (position_id) REFERENCES Positions(position_id)
);

-- Government IDs (Separated to handle multiple IDs per employee)
CREATE TABLE EmployeeGovernmentIDs (
    government_id_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    employee_id INT UNSIGNED NOT NULL,
    id_type ENUM('SSS', 'PhilHealth', 'TIN', 'PagIBIG') NOT NULL,
    id_number VARCHAR(20) NOT NULL,
    FOREIGN KEY (employee_id) REFERENCES Employees(employee_id) ON DELETE CASCADE,
    FOREIGN KEY (verified_by) REFERENCES Users(user_id),
    UNIQUE KEY unique_government_id (id_type, id_number)
);

-- Family Information
CREATE TABLE EmployeeFamilyMembers (
    family_member_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    employee_id INT UNSIGNED NOT NULL,
    relationship_type ENUM('Father', 'Mother', 'Spouse', 'Child', 'Sibling') NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    date_of_birth DATE,
    sss_number VARCHAR(20),
    is_beneficiary BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (employee_id) REFERENCES Employees(employee_id) ON DELETE CASCADE
);

-- Emergency Contacts
CREATE TABLE EmergencyContacts (
    emergency_contact_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    employee_id INT UNSIGNED NOT NULL,
    contact_name VARCHAR(255) NOT NULL,
    relationship VARCHAR(50) NOT NULL,
    contact_number VARCHAR(20) NOT NULL,
    address TEXT NOT NULL,
    is_primary BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (employee_id) REFERENCES Employees(employee_id) ON DELETE CASCADE
);

-- Profile Pictures
CREATE TABLE ProfilePictures (
    profile_picture_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    employee_id INT UNSIGNED NOT NULL,
    picture_path VARCHAR(255) NOT NULL,
    is_current BOOLEAN DEFAULT TRUE,
    uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    uploaded_by INT UNSIGNED NULL,
    FOREIGN KEY (employee_id) REFERENCES Employees(employee_id) ON DELETE CASCADE,
    FOREIGN KEY (uploaded_by) REFERENCES Users(user_id)
);

-- Educational Background
CREATE TABLE EducationLevels (
    level_id TINYINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    level_name VARCHAR(50) NOT NULL UNIQUE,
    description TEXT
);

INSERT INTO EducationLevels (level_name, description) VALUES
    ('Primary', 'Elementary education'),
    ('Secondary', 'High school education'),
    ('Vocational', 'Technical and vocational education'),
    ('College', 'Bachelor''s degree'),
    ('Post-Graduate', 'Master''s and Doctorate degrees'),
    ('Certificate', 'Professional certifications'),
    ('License', 'Professional licenses');

CREATE TABLE EducationalBackground (
    education_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    employee_id INT UNSIGNED NOT NULL,
    education_level_id TINYINT UNSIGNED NOT NULL,
    institution_name VARCHAR(255) NOT NULL,
    course_degree_program VARCHAR(255),
    year_graduated YEAR,
    certificate_license_name VARCHAR(255),
    date_issued DATE,
    valid_until DATE,
    remarks TEXT,
    is_verified BOOLEAN DEFAULT FALSE,
    verified_at TIMESTAMP NULL,
    verified_by INT UNSIGNED NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (employee_id) REFERENCES Employees(employee_id) ON DELETE CASCADE,
    FOREIGN KEY (education_level_id) REFERENCES EducationLevels(level_id),
    FOREIGN KEY (verified_by) REFERENCES Users(user_id)
);

-- Work Experience
CREATE TABLE WorkExperience (
    work_experience_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    employee_id INT UNSIGNED NOT NULL,
    company_name VARCHAR(255) NOT NULL,
    position_held VARCHAR(255) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE,
    is_current_job BOOLEAN DEFAULT FALSE,
    responsibilities TEXT,
    achievements TEXT,
    is_verified BOOLEAN DEFAULT FALSE,
    verified_at TIMESTAMP NULL,
    verified_by INT UNSIGNED NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (employee_id) REFERENCES Employees(employee_id) ON DELETE CASCADE,
    FOREIGN KEY (verified_by) REFERENCES Users(user_id)
);

-- Dependents
CREATE TABLE Dependents (
    dependent_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    employee_id INT UNSIGNED NOT NULL,
    relationship_type ENUM('Spouse', 'Child', 'Parent', 'Sibling', 'Other') NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    date_of_birth DATE NOT NULL,
    place_of_birth VARCHAR(255),
    address TEXT,
    gender_id TINYINT UNSIGNED NOT NULL,
    is_beneficiary BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (employee_id) REFERENCES Employees(employee_id) ON DELETE CASCADE,
    FOREIGN KEY (gender_id) REFERENCES GenderTypes(gender_id)
);

-- USERS (Admin, Employee, EVP)
CREATE TABLE Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    employee_id INT NULL UNIQUE, 
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL, 
    email VARCHAR(191) UNIQUE, 
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (employee_id) REFERENCES Employees(employee_id) ON DELETE SET NULL
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
CREATE TABLE HeadcountRequests (
    request_id INT AUTO_INCREMENT PRIMARY KEY,
    requested_by_user_id INT NOT NULL,    -- The User ID of the EVP making the request
    department_id INT NOT NULL,           -- For which department
    position_id INT NULL,                 -- Optional: For which specific position (more specific)
    number_requested INT NOT NULL,
    justification TEXT,
    request_date DATE NOT NULL DEFAULT (CURDATE()),
    status ENUM('Pending', 'Approved', 'Rejected', 'Fulfilled') DEFAULT 'Pending',
    approved_by_user_id INT NULL,         -- User ID of who approved(Admin)
    decision_date DATE NULL,
    decision_remarks TEXT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (requested_by_user_id) REFERENCES Users(user_id), 
    FOREIGN KEY (department_id) REFERENCES Departments(department_id),
    FOREIGN KEY (position_id) REFERENCES Positions(position_id),
    FOREIGN KEY (approved_by_user_id) REFERENCES Users(user_id)
);
