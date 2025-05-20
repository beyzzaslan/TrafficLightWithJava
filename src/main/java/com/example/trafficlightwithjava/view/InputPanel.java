package com.example.trafficlightwithjava.view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Pair;

import java.util.Optional;
import java.util.Random;

public class InputPanel extends VBox {

    private Button manualButton;
    private Button randomButton;
    private Button applyButton;
    private Button resetButton;
    private Button stopButton;
    private Button continueButton;

    private int northCount, southCount, eastCount, westCount;

    public InputPanel() {
        this.setPrefWidth(300);
        this.setPadding(new Insets(10));
        this.setSpacing(10);

        Label title = new Label("Araç Yoğunluğu Girişi");

        manualButton = new Button("Manuel Giriş");
        randomButton = new Button("Rastgele Giriş");

        applyButton = new Button("Başlat");
        resetButton = new Button("Resetle");
        stopButton = new Button("Stop");
        continueButton = new Button("Devam Et");

        applyButton.setVisible(false);
        resetButton.setVisible(false);
        stopButton.setVisible(false);
        continueButton.setVisible(false);

        manualButton.setOnAction(e -> openManualDialog());
        randomButton.setOnAction(e -> generateRandomCounts());

        applyButton.setOnAction(e -> applyCounts());
        resetButton.setOnAction(e -> resetEvent());
        stopButton.setOnAction(e -> stopEvent());
        continueButton.setOnAction(e -> continueEvent());

        this.getChildren().addAll(title, manualButton, randomButton, applyButton, resetButton, stopButton, continueButton);
    }

    private void openManualDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Manuel Giriş");

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);

        TextField northField = new TextField();
        TextField southField = new TextField();
        TextField eastField = new TextField();
        TextField westField = new TextField();

        grid.addRow(0, new Label("North:"), northField);
        grid.addRow(1, new Label("South:"), southField);
        grid.addRow(2, new Label("East:"), eastField);
        grid.addRow(3, new Label("West:"), westField);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                northCount = Integer.parseInt(northField.getText());
                southCount = Integer.parseInt(southField.getText());
                eastCount = Integer.parseInt(eastField.getText());
                westCount = Integer.parseInt(westField.getText());

                showSimulationControls();

                Alert info = new Alert(Alert.AlertType.INFORMATION);
                info.setHeaderText("Veriler Alındı");
                info.setContentText("North: " + northCount + "\nSouth: " + southCount +
                        "\nEast: " + eastCount + "\nWest: " + westCount);
                info.showAndWait();

            } catch (NumberFormatException ex) {
                showError("Geçerli sayılar giriniz!");
            }
        }
    }

    private void generateRandomCounts() {
        Random rand = new Random();
        northCount = rand.nextInt(10) + 1;
        southCount = rand.nextInt(10) + 1;
        eastCount = rand.nextInt(10) + 1;
        westCount = rand.nextInt(10) + 1;

        showSimulationControls();

        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setHeaderText("Rastgele Değerler Atandı");
        info.setContentText("North: " + northCount + "\nSouth: " + southCount +
                "\nEast: " + eastCount + "\nWest: " + westCount);
        info.showAndWait();
    }

    private void applyCounts() {
        System.out.println("Simülasyon Başlatıldı:");
        System.out.println("North: " + northCount);
        System.out.println("South: " + southCount);
        System.out.println("East: " + eastCount);
        System.out.println("West: " + westCount);

        // Controller’a veri iletimi yapılabilir burada
    }

    private void resetEvent() {
        northCount = 0;
        southCount = 0;
        eastCount = 0;
        westCount = 0;

        applyButton.setVisible(false);
        resetButton.setVisible(false);
        stopButton.setVisible(false);
        continueButton.setVisible(false);
    }

    private void stopEvent() {
        System.out.println("Simülasyon durduruldu.");
    }

    private void continueEvent() {
        System.out.println("Simülasyon devam etti.");
    }

    private void showSimulationControls() {
        applyButton.setVisible(true);
        resetButton.setVisible(true);
        stopButton.setVisible(true);
        continueButton.setVisible(true);
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Hata");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    // Getter metodlar (Controller bu değerleri isterse kullanabilir)
    public int getNorthCount() { return northCount; }
    public int getSouthCount() { return southCount; }
    public int getEastCount() { return eastCount; }
    public int getWestCount() { return westCount; }
}
