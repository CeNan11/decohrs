package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

import entity.Department;
import entity.Employee;
import entity.Position;

public class FilterData {
    private String sortBy;
    private String orderBy;
    private String position;
    private String department;
    private String gender;
    private String civilStatus;
    private String educationAttainment;
    private LocalDate dateHired;
    private LocalDate dateRegularized;
    private LocalDate dateOfBirth;

    public FilterData() {}

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCivilStatus() {
        return civilStatus;
    }

    public void setCivilStatus(String civilStatus) {
        this.civilStatus = civilStatus;
    }

    public String getEducationAttainment() {
        return educationAttainment;
    }

    public void setEducationAttainment(String educationAttainment) {
        this.educationAttainment = educationAttainment;
    }

    public LocalDate getDateHired() {
        return dateHired;
    }

    public void setDateHired(LocalDate dateHired) {
        this.dateHired = dateHired;
    }

    public LocalDate getDateRegularized() {
        return dateRegularized;
    }

    public void setDateRegularized(LocalDate dateRegularized) {
        this.dateRegularized = dateRegularized;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "FilterData{" +
                "sortBy='" + sortBy + '\'' +
                ", orderBy='" + orderBy + '\'' +
                ", position='" + position + '\'' +
                ", department='" + department + '\'' +
                ", gender='" + gender + '\'' +
                ", civilStatus='" + civilStatus + '\'' +
                ", educationAttainment='" + educationAttainment + '\'' +
                ", dateHired=" + dateHired +
                ", dateRegularized=" + dateRegularized +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }

    public static List<Employee> filterEmployees(List<Employee> employees, FilterData filter, List<Position> positions, List<Department> departments) {        
        List<Employee> filtered = employees.stream()
            .filter(e -> filter.getPosition() == null || filter.getPosition().isEmpty() ||
                filter.getPosition().equalsIgnoreCase(getPositionTitleById(e.getPositionId(), positions)))
            .filter(e -> filter.getDepartment() == null || filter.getDepartment().isEmpty() ||
                filter.getDepartment().equalsIgnoreCase(getDepartmentNameById(e.getDepartmentId(), departments)))
            .filter(e -> filter.getGender() == null || filter.getGender().isEmpty() || e.getGender().equalsIgnoreCase(filter.getGender()))
            .filter(e -> filter.getCivilStatus() == null || filter.getCivilStatus().isEmpty() || e.getCivilStatus().equalsIgnoreCase(filter.getCivilStatus()))
            .filter(e -> filter.getEducationAttainment() == null || filter.getEducationAttainment().isEmpty() || 
                (e.getEducation() != null && filter.getEducationAttainment().equalsIgnoreCase(e.getEducation().getHighestAttainment())))
            .filter(e -> filter.getDateHired() == null || 
                (e.getHireDate() != null && e.getHireDate().toLocalDate().equals(filter.getDateHired())))
            .filter(e -> filter.getDateRegularized() == null || 
                (e.getRegularizationDate() != null && e.getRegularizationDate().toLocalDate().equals(filter.getDateRegularized())))
            .filter(e -> filter.getDateOfBirth() == null || 
                (e.getDateOfBirth() != null && e.getDateOfBirth().toLocalDate().equals(filter.getDateOfBirth())))
            .collect(Collectors.toList());

        // Sorting
        if (filter.getSortBy() != null && !filter.getSortBy().isEmpty()) {
            String sortBy = filter.getSortBy();
            boolean ascending = filter.getOrderBy() == null || filter.getOrderBy().equalsIgnoreCase("Ascending");
            filtered.sort((a, b) -> {
                int cmp = 0;
                switch (sortBy) {
                    case "Name":
                        cmp = (a.getFirstName() + a.getLastName()).compareToIgnoreCase(b.getFirstName() + b.getLastName());
                        break;
                    case "Age":
                        if (a.getDateOfBirth() != null && b.getDateOfBirth() != null) {
                            cmp = a.getDateOfBirth().compareTo(b.getDateOfBirth()); // older = less
                        }
                        break;
                    case "Department":
                        cmp = String.valueOf(a.getDepartmentId()).compareTo(String.valueOf(b.getDepartmentId()));
                        break;
                    case "Position":
                        cmp = String.valueOf(a.getPositionId()).compareTo(String.valueOf(b.getPositionId()));
                        break;
                    case "Gender":
                        cmp = a.getGender().compareToIgnoreCase(b.getGender());
                        break;
                    case "Civil Status":
                        cmp = a.getCivilStatus().compareToIgnoreCase(b.getCivilStatus());
                        break;
                    case "Education Attainment":
                        String eduA = a.getEducation() != null ? a.getEducation().getHighestAttainment() : "";
                        String eduB = b.getEducation() != null ? b.getEducation().getHighestAttainment() : "";
                        cmp = eduA.compareToIgnoreCase(eduB);
                        break;
                    case "Date Hired":
                        if (a.getHireDate() != null && b.getHireDate() != null) {
                            cmp = a.getHireDate().compareTo(b.getHireDate());
                        }
                        break;
                    case "Date Regularized":
                        if (a.getRegularizationDate() != null && b.getRegularizationDate() != null) {
                            cmp = a.getRegularizationDate().compareTo(b.getRegularizationDate());
                        }
                        break;
                    case "Date of Birth":
                        if (a.getDateOfBirth() != null && b.getDateOfBirth() != null) {
                            cmp = a.getDateOfBirth().compareTo(b.getDateOfBirth());
                        }
                        break;
                }
                return ascending ? cmp : -cmp;
            });
        }
        return filtered;
    }

    public static String getPositionTitleById(Integer id, List<Position> positions) {
        if (id == null) return "";
        return positions.stream()
            .filter(p -> p.getPositionId().equals(id))
            .map(Position::getPositionTitle)
            .findFirst()
            .orElse("");
    }

    public static String getDepartmentNameById(Integer id, List<Department> departments) {
        if (id == null) return "";
        return departments.stream()
            .filter(d -> d.getDepartmentId().equals(id))
            .map(Department::getDepartmentName)
            .findFirst()
            .orElse("");
    }
}