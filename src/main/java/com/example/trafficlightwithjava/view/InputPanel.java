package com.example.trafficlightwithjava.view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.Random;

public class InputPanel extends VBox {

    private TextField northField;
    private TextField southField;
    private TextField eastField;
    private TextField westField;

    private Button randomButton;
    private Button applyButton;

    public InputPanel() {
        // Arayüzün dış boşluğu
        this.setPrefWidth(300); // veya daha geniş bir değer (örneğin 250)
        this.setPadding(new Insets(10));
        this.setSpacing(10);


        // Başlık
        Label title = new Label("Araç Yoğunluğu Girişi");

        // TextField ve Label seti
        northField = new TextField();
        southField = new TextField();
        eastField = new TextField();
        westField = new TextField();

        northField.setPromptText("North");
        southField.setPromptText("South");
        eastField.setPromptText("East");
        westField.setPromptText("West");

        // Butonlar
        randomButton = new Button("Rastgele Üret");
        applyButton = new Button("Uygula");

        // Grid yerleşimi
        GridPane grid = new GridPane();
        grid.setVgap(5);
        grid.setHgap(10);
        grid.add(new Label("North:"), 0, 0);
        grid.add(northField, 1, 0);
        grid.add(new Label("South:"), 0, 1);
        grid.add(southField, 1, 1);
        grid.add(new Label("East:"), 0, 2);
        grid.add(eastField, 1, 2);
        grid.add(new Label("West:"), 0, 3);
        grid.add(westField, 1, 3);

        // Butonların işlevi
        randomButton.setOnAction(e -> generateRandom());
        applyButton.setOnAction(e -> applyCounts());

        // Panele ekle
        this.getChildren().addAll(title, grid, randomButton, applyButton);
    }

    // Rastgele sayı üret (1-10 arasında örnek)
    private void generateRandom() {
        Random rand = new Random();
        northField.setText(String.valueOf(rand.nextInt(10) + 1));
        southField.setText(String.valueOf(rand.nextInt(10) + 1));
        eastField.setText(String.valueOf(rand.nextInt(10) + 1));
        westField.setText(String.valueOf(rand.nextInt(10) + 1));
    }

    // Manuel girilen değerleri okutuyoruz
    private void applyCounts() {
        int north = Integer.parseInt(northField.getText());
        int south = Integer.parseInt(southField.getText());
        int east = Integer.parseInt(eastField.getText());
        int west = Integer.parseInt(westField.getText());

        // Burada bu değerleri Controller'a iletebilirsin
        System.out.println("Manuel Giriş:");
        System.out.println("North: " + north);
        System.out.println("South: " + south);
        System.out.println("East: " + east);
        System.out.println("West: " + west);
    }

    // Getter metodlar (Controller bu değerleri isterse kullanabilir)
    public int getNorthCount() { return Integer.parseInt(northField.getText()); }
    public int getSouthCount() { return Integer.parseInt(southField.getText()); }
    public int getEastCount() { return Integer.parseInt(eastField.getText()); }
    public int getWestCount() { return Integer.parseInt(westField.getText()); }

}
