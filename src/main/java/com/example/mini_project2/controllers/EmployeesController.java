package com.example.mini_project2.controllers;

import com.example.mini_project2.models.Employee;
import com.example.mini_project2.models.EmployeeStore;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class EmployeesController {

    @FXML
    private TextField employeeFnameFld;

    @FXML
    private TextField employeeLnameFld;

    @FXML
    private TextField employeeEmailFld;

    @FXML
    private DatePicker employeeHireDatePicker;

    @FXML
    private ComboBox<String> employeeRoleCmb;

    @FXML
    private ComboBox<String> employeeDepartmentCmb;

    @FXML
    private Slider employeeSalarySlider;

    @FXML
    private Label employeeSalaryValueLbl;

    @FXML
    private Label employeeErrorLbl;

    @FXML
    private TableView<Employee> employeeTable;

    @FXML
    private TableColumn<Employee, String> employeeFnameCol;

    @FXML
    private TableColumn<Employee, String> employeeLnameCol;

    @FXML
    private TableColumn<Employee, String> employeeEmailCol;

    @FXML
    private TableColumn<Employee, LocalDate> employeeHireDateCol;

    @FXML
    private TableColumn<Employee, String> employeeRoleCol;

    @FXML
    private TableColumn<Employee, String> employeeDepartmentCol;

    @FXML
    private TableColumn<Employee, Number> employeeSalaryCol;

    private final EmployeeStore employeeStore = new EmployeeStore();

    @FXML
    public void initialize() {
        // fill dropdowns when page loads
        employeeRoleCmb.getItems().setAll(
                "Manager",
                "Staff",
                "Admin",
                "Intern",
                "HR",
                "IT Support"
        );

        employeeDepartmentCmb.getItems().setAll(
                "Finance",
                "IT",
                "HR",
                "Sales",
                "Marketing",
                "Logistics"
        );

        // bind table columns to Employee properties
        employeeFnameCol.setCellValueFactory(new PropertyValueFactory<>("fname"));
        employeeLnameCol.setCellValueFactory(new PropertyValueFactory<>("lname"));
        employeeEmailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        employeeHireDateCol.setCellValueFactory(new PropertyValueFactory<>("hireDate"));
        employeeRoleCol.setCellValueFactory(new PropertyValueFactory<>("role"));
        employeeDepartmentCol.setCellValueFactory(new PropertyValueFactory<>("department"));
        employeeSalaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));

        // attach data store to table
        ObservableList<Employee> allEmployees = employeeStore.getEmployeesList();
        employeeTable.setItems(allEmployees);

        // when selecting a row, show data in the form
        employeeTable.getSelectionModel().selectedItemProperty().addListener(evt -> {
            Employee selected = employeeTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                employeeFnameFld.setText(selected.getFname());
                employeeLnameFld.setText(selected.getLname());
                employeeEmailFld.setText(selected.getEmail());
                employeeHireDatePicker.setValue(selected.getHireDate());
                employeeRoleCmb.setValue(selected.getRole());
                employeeDepartmentCmb.setValue(selected.getDepartment());
                employeeSalarySlider.setValue(selected.getSalary()); // int -> slider double ok
                employeeSalaryValueLbl.setText(Integer.toString(selected.getSalary()));
            }
        });

        // sync salary label with slider
        employeeSalarySlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            int salaryInt = newVal.intValue();
            employeeSalaryValueLbl.setText(Integer.toString(salaryInt));
        });
    }

    @FXML
    void addEmployee(ActionEvent event) {
        // simple validation style:
        // if ANY required field is missing -> show one generic error message
        if (isAnyRequiredFieldMissing()) {
            employeeErrorLbl.setText("Please fill in all required fields.");
            return;
        }

        String fname = employeeFnameFld.getText();
        String lname = employeeLnameFld.getText();
        String email = employeeEmailFld.getText();
        LocalDate hireDate = employeeHireDatePicker.getValue();
        String role = employeeRoleCmb.getValue();
        String department = employeeDepartmentCmb.getValue();
        int salary = (int) employeeSalarySlider.getValue();

        Employee e = new Employee(
                fname,
                lname,
                email,
                hireDate,
                role,
                department,
                salary
        );

        employeeStore.addEmployee(e);

        // clear form
        employeeFnameFld.setText("");
        employeeLnameFld.setText("");
        employeeEmailFld.setText("");
        employeeHireDatePicker.setValue(null);
        employeeRoleCmb.setValue(null);
        employeeDepartmentCmb.setValue(null);
        employeeSalarySlider.setValue(0);
        employeeSalaryValueLbl.setText("0");

        employeeErrorLbl.setText("");
    }

    @FXML
    void updateEmployee(ActionEvent event) {
        Employee selected = employeeTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            // no row selected, do nothing (you could also show a message if you want)
            return;
        }

        // same validation rule: if anything is blank -> stop and show generic message
        if (isAnyRequiredFieldMissing()) {
            employeeErrorLbl.setText("Please fill in all required fields.");
            return;
        }

        String fname = employeeFnameFld.getText();
        String lname = employeeLnameFld.getText();
        String email = employeeEmailFld.getText();
        LocalDate hireDate = employeeHireDatePicker.getValue();
        String role = employeeRoleCmb.getValue();
        String department = employeeDepartmentCmb.getValue();
        int salary = (int) employeeSalarySlider.getValue();

        employeeStore.updateEmployee(
                selected,
                fname,
                lname,
                email,
                hireDate,
                role,
                department,
                salary
        );

        employeeSalaryValueLbl.setText(Integer.toString(salary));
        employeeErrorLbl.setText("");
        employeeTable.refresh();
    }

    @FXML
    void deleteEmployee(ActionEvent event) {
        Employee selected = employeeTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            employeeStore.deleteEmployee(selected);
        }
    }

    @FXML
    void backToHome(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/mini_project2/home.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Home");
        stage.show();
    }

    private boolean isAnyRequiredFieldMissing() {
        if (employeeFnameFld.getText() == null || employeeFnameFld.getText().isEmpty())
            return true;

        if (employeeLnameFld.getText() == null || employeeLnameFld.getText().isEmpty())
            return true;

        if (employeeEmailFld.getText() == null || employeeEmailFld.getText().isEmpty())
            return true;

        if (employeeHireDatePicker.getValue() == null)
            return true;

        if (employeeRoleCmb.getValue() == null || employeeRoleCmb.getValue().isEmpty())
            return true;

        if (employeeDepartmentCmb.getValue() == null || employeeDepartmentCmb.getValue().isEmpty())
            return true;

        // salary can be zero, that's allowed, so we don't block for that

        return false;
    }
}
