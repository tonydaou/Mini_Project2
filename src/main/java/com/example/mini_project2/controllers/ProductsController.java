package com.example.mini_project2.controllers;

import com.example.mini_project2.models.Product;
import com.example.mini_project2.models.ProductStore;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class ProductsController {

    @FXML private TextField nameField, brandField, descriptionField, quantityField, stockField;
    @FXML private ComboBox<String> categoryComboBox;
    @FXML private Label priceLabel, errorLabel;
    @FXML private Slider priceSlider;
    @FXML private ImageView productImageView;
    @FXML private TableView<Product> productTable;
    @FXML private TableColumn<Product, String> nameCol, brandCol, categoryCol, descriptionCol;
    @FXML private TableColumn<Product, Integer> quantityCol, stockCol;
    @FXML private TableColumn<Product, Double> priceCol;

    private final ObservableList<Product> productList = ProductStore.getProducts();
    private String imagePath = "noImage.png";

    @FXML
    public void initialize() {
        categoryComboBox.getItems().addAll("Electronics", "Home Appliance", "Fitness", "Sports", "Books", "Clothing");

        nameCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getName()));
        brandCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getBrand()));
        categoryCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getCategory()));
        descriptionCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getDescription()));
        quantityCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleIntegerProperty(cell.getValue().getQuantity()).asObject());
        stockCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleIntegerProperty(cell.getValue().getStock()).asObject());
        priceCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleDoubleProperty(cell.getValue().getPrice()).asObject());

        productTable.setItems(productList);
        productTable.getSelectionModel().selectedItemProperty().addListener((obs, old, selected) -> showProductDetails(selected));

        priceSlider.valueProperty().addListener((obs, old, val) -> priceLabel.setText(String.format("%.2f", val.doubleValue())));
    }

    private void showProductDetails(Product product) {
        if (product != null) {
            nameField.setText(product.getName());
            brandField.setText(product.getBrand());
            categoryComboBox.setValue(product.getCategory());
            descriptionField.setText(product.getDescription());
            quantityField.setText(String.valueOf(product.getQuantity()));
            stockField.setText(String.valueOf(product.getStock()));
            priceSlider.setValue(product.getPrice());
            priceLabel.setText(String.format("%.2f", product.getPrice()));
            loadImage(product.getImagePath());
        }
    }

    @FXML
    void chooseImage(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select Product Image");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File file = chooser.showOpenDialog(null);
        if (file != null) {
            imagePath = file.getName();
            loadImage(imagePath);
        }
    }

    private void loadImage(String imgPath) {
        try {
            Image image = new Image(getClass().getResourceAsStream("/com/example/mini_project2/images/" + imgPath));
            productImageView.setImage(image);
        } catch (Exception e) {
            productImageView.setImage(new Image(getClass().getResourceAsStream("/com/example/mini_project2/images/noImage.png")));
        }
    }

    @FXML
    void addProduct(ActionEvent event) {
        if (!validateInputs()) return;

        Product newProduct = new Product(
                ProductStore.getNextId(),
                nameField.getText(),
                brandField.getText(),
                categoryComboBox.getValue(),
                descriptionField.getText(),
                Integer.parseInt(quantityField.getText()),
                priceSlider.getValue(),
                Integer.parseInt(stockField.getText()),
                imagePath
        );
        ProductStore.addProduct(newProduct);
        clearFields();
        productTable.refresh();
    }

    @FXML
    void updateProduct(ActionEvent event) {
        Product selected = productTable.getSelectionModel().getSelectedItem();
        if (selected == null || !validateInputs()) return;

        selected.setName(nameField.getText());
        selected.setBrand(brandField.getText());
        selected.setCategory(categoryComboBox.getValue());
        selected.setDescription(descriptionField.getText());
        selected.setQuantity(Integer.parseInt(quantityField.getText()));
        selected.setStock(Integer.parseInt(stockField.getText()));
        selected.setPrice(priceSlider.getValue());
        selected.setImagePath(imagePath);

        productTable.refresh();
        clearFields();
    }

    @FXML
    void deleteProduct(ActionEvent event) {
        Product selected = productTable.getSelectionModel().getSelectedItem();
        if (selected != null) ProductStore.deleteProduct(selected);
        clearFields();
    }

    @FXML
    void backToHome(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/mini_project2/home.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Home");
        stage.show();
    }


    private void clearFields() {
        nameField.clear();
        brandField.clear();
        categoryComboBox.setValue(null);
        descriptionField.clear();
        quantityField.clear();
        stockField.clear();
        priceSlider.setValue(0);
        priceLabel.setText("0.00");
        imagePath = "noImage.png";
        loadImage(imagePath);
        errorLabel.setText("");
    }

    private boolean validateInputs() {
        if (nameField.getText().isEmpty() || brandField.getText().isEmpty() ||
                categoryComboBox.getValue() == null || descriptionField.getText().isEmpty() ||
                quantityField.getText().isEmpty() || stockField.getText().isEmpty()) {
            errorLabel.setText("⚠ Please fill all fields!");
            return false;
        }

        try {
            int quantity = Integer.parseInt(quantityField.getText());
            int stock = Integer.parseInt(stockField.getText());
            if (quantity < 0 || stock < 0) {
                errorLabel.setText("⚠ Quantity/Stock cannot be negative!");
                return false;
            }
        } catch (NumberFormatException e) {
            errorLabel.setText("⚠ Quantity and Stock must be numbers!");
            return false;
        }
        return true;
    }
}