package com.example.mini_project2.controllers;

import com.example.mini_project2.models.Order;
import com.example.mini_project2.models.OrderStore;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class OrdersController {

    @FXML private TextField sourceField, destinationField, weightField, priceField;
    @FXML private ComboBox<String> deliveryTypeComboBox;
    @FXML private DatePicker orderDatePicker;
    @FXML private Slider estimatedTimeSlider;
    @FXML private Label estimatedTimeLabel, errorLabel;

    @FXML private TableView<Order> orderTable;
    @FXML private TableColumn<Order, Integer> idCol;
    @FXML private TableColumn<Order, Double> priceCol;
    @FXML private TableColumn<Order, String> sourceCol;
    @FXML private TableColumn<Order, String> destinationCol;
    @FXML private TableColumn<Order, Double> weightCol;
    @FXML private TableColumn<Order, String> deliveryTypeCol;
    @FXML private TableColumn<Order, LocalDate> orderDateCol;
    @FXML private TableColumn<Order, String> estimatedArrivalCol;

    private Order selectedOrder;

    @FXML
    public void initialize() {
        // delivery types
        deliveryTypeComboBox.getItems().addAll("Standard", "Express", "Same-Day");

        // slider label binding
        estimatedTimeSlider.valueProperty().addListener((obs, oldVal, newVal) ->
                estimatedTimeLabel.setText(newVal.intValue() + " days")
        );

        // table column bindings
        idCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()).asObject());
        sourceCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSource()));
        destinationCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDestination()));
        weightCol.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getWeight()).asObject());
        priceCol.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getPrice()).asObject());
        deliveryTypeCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDeliveryType()));
        orderDateCol.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getOrderDate()));
        estimatedArrivalCol.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getEstimatedArrival() + " days")
        );

        // table data
        orderTable.setItems(OrderStore.getOrders());

        // selection listener
        orderTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            selectedOrder = newSel;
            if (newSel != null) {
                sourceField.setText(newSel.getSource());
                destinationField.setText(newSel.getDestination());
                weightField.setText(String.valueOf(newSel.getWeight()));
                priceField.setText(String.valueOf(newSel.getPrice()));
                deliveryTypeComboBox.setValue(newSel.getDeliveryType());
                orderDatePicker.setValue(newSel.getOrderDate());
                estimatedTimeSlider.setValue(newSel.getEstimatedArrival());
                estimatedTimeLabel.setText(newSel.getEstimatedArrival() + " days");
                errorLabel.setText("");
            }
        });
    }

    @FXML
    private void addOrder() {
        try {
            String source = sourceField.getText();
            String destination = destinationField.getText();
            String deliveryType = deliveryTypeComboBox.getValue();
            LocalDate orderDate = orderDatePicker.getValue();

            if (source.isEmpty() || destination.isEmpty() || deliveryType == null || orderDate == null) {
                errorLabel.setText("Please fill all fields.");
                return;
            }

            double weight = Double.parseDouble(weightField.getText());
            double price = Double.parseDouble(priceField.getText());
            int estimatedArrival = (int) estimatedTimeSlider.getValue();

            // id = 0, store will set real id
            Order newOrder = new Order(0, source, destination, weight, price, deliveryType, orderDate, estimatedArrival);
            OrderStore.addOrder(newOrder);

            clearFields();
        } catch (NumberFormatException e) {
            errorLabel.setText("Invalid numeric input.");
        }
    }

    @FXML
    private void updateOrder() {
        if (selectedOrder == null) {
            errorLabel.setText("Select an order to update.");
            return;
        }
        try {
            selectedOrder.setSource(sourceField.getText());
            selectedOrder.setDestination(destinationField.getText());
            selectedOrder.setWeight(Double.parseDouble(weightField.getText()));
            selectedOrder.setPrice(Double.parseDouble(priceField.getText()));
            selectedOrder.setDeliveryType(deliveryTypeComboBox.getValue());
            selectedOrder.setOrderDate(orderDatePicker.getValue());
            selectedOrder.setEstimatedArrival((int) estimatedTimeSlider.getValue());

            OrderStore.updateOrder(selectedOrder);
            orderTable.refresh();
            clearFields();
        } catch (NumberFormatException e) {
            errorLabel.setText("Invalid numeric input.");
        }
    }

    @FXML
    private void deleteOrder() {
        if (selectedOrder != null) {
            OrderStore.deleteOrder(selectedOrder);
            orderTable.refresh();
            clearFields();
        } else {
            errorLabel.setText("Select an order to delete.");
        }
    }

    @FXML
    private void backToHome(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/mini_project2/home.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Home");
        stage.show();
    }

    private void clearFields() {
        sourceField.clear();
        destinationField.clear();
        weightField.clear();
        priceField.clear();
        deliveryTypeComboBox.setValue(null);
        orderDatePicker.setValue(null);
        estimatedTimeSlider.setValue(0);
        estimatedTimeLabel.setText("0 days");
        errorLabel.setText("");
        selectedOrder=null;
}
}
