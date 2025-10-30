package com.example.mini_project2.models;

import javafx.beans.property.*;
import java.time.LocalDate;

public class Employee {

    private final StringProperty fname;
    private final StringProperty lname;
    private final StringProperty email;
    private final ObjectProperty<LocalDate> hireDate;
    private final StringProperty role;
    private final StringProperty department;
    private final IntegerProperty salary; // int now

    public Employee(
            String fname,
            String lname,
            String email,
            LocalDate hireDate,
            String role,
            String department,
            int salary
    ) {
        this.fname = new SimpleStringProperty(fname);
        this.lname = new SimpleStringProperty(lname);
        this.email = new SimpleStringProperty(email);
        this.hireDate = new SimpleObjectProperty<>(hireDate);
        this.role = new SimpleStringProperty(role);
        this.department = new SimpleStringProperty(department);
        this.salary = new SimpleIntegerProperty(salary); // int property
    }

    // First name
    public String getFname() {
        return fname.get();
    }
    public void setFname(String value) {
        fname.set(value);
    }
    public StringProperty fnameProperty() {
        return fname;
    }

    // Last name
    public String getLname() {
        return lname.get();
    }
    public void setLname(String value) {
        lname.set(value);
    }
    public StringProperty lnameProperty() {
        return lname;
    }

    // Email
    public String getEmail() {
        return email.get();
    }
    public void setEmail(String value) {
        email.set(value);
    }
    public StringProperty emailProperty() {
        return email;
    }

    // Hire date
    public LocalDate getHireDate() {
        return hireDate.get();
    }
    public void setHireDate(LocalDate value) {
        hireDate.set(value);
    }
    public ObjectProperty<LocalDate> hireDateProperty() {
        return hireDate;
    }

    // Role
    public String getRole() {
        return role.get();
    }
    public void setRole(String value) {
        role.set(value);
    }
    public StringProperty roleProperty() {
        return role;
    }

    // Department
    public String getDepartment() {
        return department.get();
    }
    public void setDepartment(String value) {
        department.set(value);
    }
    public StringProperty departmentProperty() {
        return department;
    }

    // Salary (int)
    public int getSalary() {
        return salary.get();
    }
    public void setSalary(int value) {
        salary.set(value);
    }
    public IntegerProperty salaryProperty() {
        return salary;
    }
}
