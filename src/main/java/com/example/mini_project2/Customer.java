//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.mini_project2;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class Customer {
    private final SimpleStringProperty name;
    private final SimpleIntegerProperty age;
    private final SimpleStringProperty address;
    private final SimpleStringProperty status;
    private final SimpleObjectProperty<LocalDate> dateOfBirth;
    private final SimpleStringProperty phoneNumber;

    public Customer(String name, Integer age, String address, String status, LocalDate dateOfBirth, String phoneNumber) {
        this.name = new SimpleStringProperty(name);
        this.age = new SimpleIntegerProperty(age);
        this.address = new SimpleStringProperty(address);
        this.status = new SimpleStringProperty(status);
        this.dateOfBirth = new SimpleObjectProperty<>(dateOfBirth);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
    }

    public String getName() {
        return this.name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return this.name;
    }

    public String getStatus() {
        return this.status.get();
    }

    public int getAge() {
        return this.age.get();
    }

    public void setAge(int age) {
        this.age.set(age);
    }

    public IntegerProperty ageProperty() {
        return this.age;
    }

    public String getAddress() {
        return this.address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public StringProperty addressProperty() {
        return this.address;
    }

    public void setStatus(String customerStatus) {
        this.status.set(customerStatus);
    }

    public StringProperty statusProperty() {
        return this.status;
    }

    public LocalDate getDateOfBirth() {
        return this.dateOfBirth.get();
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth.set(dateOfBirth);
    }

    public ObjectProperty<LocalDate> dateOfBirthProperty() {
        return this.dateOfBirth;
    }

    public String getPhoneNumber() {
        return this.phoneNumber.get();
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public StringProperty phoneNumberProperty() {
        return this.phoneNumber;
    }
}
