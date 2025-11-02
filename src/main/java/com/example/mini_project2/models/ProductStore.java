package com.example.mini_project2.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductStore {

    private static final ObservableList<Product> products = FXCollections.observableArrayList();

    static {
        products.addAll(
                new Product(1, "Laptop", "HP", "Electronics", "High-performance laptop", 10, 1200.0, 50, "LaptopImage.png"),
                new Product(2, "Yoga Mat", "Adidas", "Fitness", "Non-slip exercise mat", 25, 35.0, 200, "YogaMatImage.png"),
                new Product(3, "Blender", "Philips", "Home Appliance", "Powerful kitchen blender", 8, 89.99, 35, "BlenderImage.png"),
                new Product(4, "Smartphone", "Samsung", "Electronics", "5G Android phone", 15, 999.0, 80, "SmartphoneImage.png"),
                new Product(5, "Basketball", "Spalding", "Sports", "Official size basketball", 20, 45.5, 120, "BasketballImage.png")
        );
    }

    public static ObservableList<Product> getProducts() { return products; }

    public static void addProduct(Product product) { products.add(product); }

    public static void updateProduct(Product product) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == product.getId()) {
                products.set(i, product);
                return;
            }
        }
    }

    public static void deleteProduct(Product product) { products.remove(product); }

    public static int getNextId() {
        return products.isEmpty() ? 1 : products.get(products.size() - 1).getId() + 1;
    }
}