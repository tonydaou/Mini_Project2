package com.example.mini_project2.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public class EmployeeStore {

    private final ObservableList<Employee> employees = FXCollections.observableArrayList();

    public EmployeeStore() {
        this.employees.addAll(
                new Employee(
                        "Maya",
                        "Haddad",
                        "maya@company.com",
                        LocalDate.of(2024, 3, 10),
                        "Manager",
                        "Finance",
                        3200
                ),
                new Employee(
                        "Joe",
                        "Nassar",
                        "joe@company.com",
                        LocalDate.of(2023, 11, 2),
                        "Staff",
                        "IT",
                        1800
                ),
                new Employee(
                        "Louis",
                        "Fares",
                        "louis@company.com",
                        LocalDate.of(2022, 7, 1),
                        "Admin",
                        "HR",
                        2500
                )
        );
    }

    public ObservableList<Employee> getEmployeesList() {
        return employees;
    }

    public void addEmployee(Employee employee) {
        if (employee != null) {
            this.employees.add(employee);
        }
    }

    public void deleteEmployee(Employee employee) {
        if (employee != null) {
            this.employees.remove(employee);
        }
    }

    public void updateEmployee(
            Employee employee,
            String fname,
            String lname,
            String email,
            LocalDate hireDate,
            String role,
            String department,
            int salary
    ) {
        if (employee != null) {
            employee.setFname(fname);
            employee.setLname(lname);
            employee.setEmail(email);
            employee.setHireDate(hireDate);
            employee.setRole(role);
            employee.setDepartment(department);
            employee.setSalary(salary);
        }
    }
}
