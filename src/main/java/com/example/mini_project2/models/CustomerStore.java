package com.example.mini_project2.models;

import com.example.mini_project2.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CustomerStore {

    private final ObservableList<Customer> customers = FXCollections.observableArrayList();

    public CustomerStore() {
        this.customers.addAll(new Customer[]{new Customer("Louis", 21, "usek", "single")});
    }

    public ObservableList<Customer> getCustomersList() {
        return this.customers;
    }

    public void addCustomer(Customer Customer) {
        if (Customer != null) {
            this.customers.add(Customer);
        }

    }

    public void deleteCustomer(Customer Customer) {
        if (Customer != null) {
            this.customers.remove(Customer);
        }

    }

    public void updateCustomer(Customer Customer, String name, Integer age, String address, String status) {
        if (Customer != null) {
            Customer.setName(name);
            Customer.setAge(age);
            Customer.setAddress(address);
            Customer.setStatus(status);
        }

    }
}
