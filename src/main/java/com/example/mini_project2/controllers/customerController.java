package com.example.mini_project2.controllers;

import com.example.mini_project2.Customer;
import com.example.mini_project2.models.CustomerStore;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class CustomerController {


    @FXML
    private Button addBtn;
    @FXML
    private TableColumn<Customer, String> addressCol;
    @FXML
    private TableColumn<Customer, String> statusCol;
    @FXML
    private RadioButton c1;
    @FXML
    private RadioButton c2;
    @FXML
    private RadioButton c3;
    @FXML
    private ToggleGroup statusGroup;
    @FXML
    private TextField addressFld;
    @FXML
    private TableColumn<Customer, Integer> ageCol;
    @FXML
    private TextField ageFld;
    @FXML
    private Button deleteBtn;
    @FXML
    private TableColumn<Customer, String> nameCol;
    @FXML
    private TextField nameFld;
    @FXML
    private TableView<Customer> personsTable;
    @FXML
    private Button updateBtn;
    @FXML
    private Label errorMsg;
    private final CustomerStore Customerstore = new CustomerStore();

    @FXML
    public void initialize() {
        this.nameCol.setCellValueFactory(new PropertyValueFactory("name"));
        this.ageCol.setCellValueFactory(new PropertyValueFactory("age"));
        this.addressCol.setCellValueFactory(new PropertyValueFactory("address"));
        this.statusCol.setCellValueFactory(new PropertyValueFactory("status"));
        this.statusGroup = new ToggleGroup();
        this.c1.setToggleGroup(this.statusGroup);
        this.c2.setToggleGroup(this.statusGroup);
        this.c3.setToggleGroup(this.statusGroup);
        ObservableList<Customer> customers = this.Customerstore.getCustomersList();
        this.personsTable.setItems(customers);
        this.personsTable.getSelectionModel().selectedItemProperty().addListener((evt) -> {
            Customer selectedcustomer = (Customer)this.personsTable.getSelectionModel().getSelectedItem();
            if (selectedcustomer != null) {
                this.nameFld.setText(selectedcustomer.getName());
                this.ageFld.setText(Integer.toString(selectedcustomer.getAge()));
                this.addressFld.setText(selectedcustomer.getAddress());
                this.setStatusRadioButton(selectedcustomer.getStatus());
            }

        });
    }

    private String getSelectedStatus() {
        RadioButton selectedRadioButton = (RadioButton)this.statusGroup.getSelectedToggle();
        return selectedRadioButton != null ? selectedRadioButton.getText() : "";
    }

    private void setStatusRadioButton(String status) {
        if (status.equals("single")) {
            this.c1.setSelected(true);
        } else if (status.equals("married")) {
            this.c2.setSelected(true);
        } else if (status.equals("divorced")) {
            this.c3.setSelected(true);
        }

    }

    @FXML
    void addPerson(ActionEvent event) {
        String error = "";
        boolean isValid = true;
        String name = this.nameFld.getText();
        if (name.isEmpty()) {
            error = error + "Error: Name is required\n";
            isValid = false;
        }

        Integer age = null;
        if (this.ageFld.getText().isEmpty()) {
            error = error + "Error: Age is required\n";
            isValid = false;
        } else {
            try {
                age = Integer.parseInt(this.ageFld.getText());
            } catch (NumberFormatException var8) {
                error = error + "Error: Invalid age value!\n";
                isValid = false;
            }
        }

        String address = this.addressFld.getText();
        if (address.isEmpty()) {
            error = error + "Error: Address is required\n";
            isValid = false;
        }

        String status = this.getSelectedStatus();
        if (status.isEmpty()) {
            error = error + "Status is required.\n";
            isValid = false;
        }

        if (isValid) {
            this.Customerstore.addCustomer(new Customer(name, age, address, status));
            this.nameFld.setText("");
            this.ageFld.setText("");
            this.addressFld.setText("");
            this.errorMsg.setText("");
        } else {
            this.errorMsg.setText(error);
        }

    }

    @FXML
    void deletePerson(ActionEvent event) {
        Customer selectedcustomer = (Customer)this.personsTable.getSelectionModel().getSelectedItem();
        if (selectedcustomer != null) {
            this.Customerstore.deleteCustomer(selectedcustomer);
        }

    }

    @FXML
    void updatePerson(ActionEvent event) {
        Customer selectedcustomer = (Customer)this.personsTable.getSelectionModel().getSelectedItem();
        if (selectedcustomer != null) {
            String error = "";
            boolean isValid = true;
            String name = this.nameFld.getText();
            if (name.isEmpty()) {
                error = error + "Error: Name is required!\n";
                isValid = false;
            }

            Integer age = null;
            if (this.ageFld.getText().isEmpty()) {
                error = error + "Error: Age is required!\n";
                isValid = false;
            } else {
                try {
                    age = Integer.parseInt(this.ageFld.getText());
                } catch (NumberFormatException var9) {
                    error = error + "Error: Invalid age value!\n";
                    isValid = false;
                }
            }

            String address = this.addressFld.getText();
            if (address.isEmpty()) {
                error = error + "Error: Address is required!\n";
                isValid = false;
            }

            String status = this.getSelectedStatus();
            if (status.isEmpty()) {
                error = error + "Error: Status is required!\n";
                isValid = false;
            }

            if (isValid) {
                this.Customerstore.updateCustomer(selectedcustomer, name, age, address, status);
                this.personsTable.refresh();
                this.errorMsg.setText("");
            } else {
                this.errorMsg.setText(error);
            }
        }

    }
}

