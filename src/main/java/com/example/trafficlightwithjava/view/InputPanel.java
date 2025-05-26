package com.example.trafficlightwithjava.view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;

import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.Random;
import java.util.function.Consumer;

public class InputPanel extends VBox {
    Button manualButton;
    Button randomButton;
    Button applyButton;
    Button resetButton;
    Button stopButton;
    Button continueButton;

    int northCount, southCount, eastCount, westCount;

    private HBox controlButtonsBox;
    private Consumer<Map<String, Integer>> onApplyListener;
    private Runnable onStartListener; // EKLENDİ: sayaç başlatma için listener

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

        VBox topPanel = new VBox(10);
        topPanel.getChildren().addAll(title, manualButton, randomButton);

        controlButtonsBox = new HBox(10);
        controlButtonsBox.getChildren().addAll(applyButton, resetButton, stopButton, continueButton);
        controlButtonsBox.setVisible(false);

        manualButton.setOnAction(e -> openManuelDialog());
        randomButton.setOnAction(e -> generateRandomCounts());
        applyButton.setOnAction(e -> applyCounts()); // sayaç burada başlatılacak
        resetButton.setOnAction(e -> resetEvent());
        stopButton.setOnAction(e -> stopEvent());
        continueButton.setOnAction(e -> continueEvent());

        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(10));
        layout.setStyle("-fx-border-color: blue; -fx-border-width: 2px; -fx-border-style: dotted;");
        BorderPane.setAlignment(controlButtonsBox, Pos.TOP_CENTER);
        layout.setTop(topPanel);
        layout.setBottom(controlButtonsBox);
        BorderPane.setMargin(controlButtonsBox, new Insets(5, 0, 0, 0));

        this.getChildren().add(layout);
    }

    private void openManuelDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Manuel Giriş");

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        TextField northField = new TextField();
        TextField southField = new TextField();
        TextField eastField = new TextField();
        TextField westField = new TextField();
        grid.addRow(0, new Label("North"), northField);
        grid.addRow(1, new Label("South"), southField);
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

                if (northCount < 1 || northCount > 100 ||
                        southCount < 1 || southCount > 100 ||
                        eastCount < 1 || eastCount > 100 ||
                        westCount < 1 || westCount > 100) {
                    showError("Tüm sayılar 1 ile 100 arasında ve 100 dahil olmalıdır.");
                    return;
                }

                showSimulationControls();
                Alert info = new Alert(Alert.AlertType.INFORMATION);
                info.setHeaderText("Veriler Alındı");
                info.setContentText("North: " + northCount + "\nSouth: " + southCount +
                        "\nEast: " + eastCount + "\nWest: " + westCount);
                info.showAndWait();

            } catch (NumberFormatException ex) {
                showError("Geçerli sayılar giriniz");
            }
        }
    }

    private void generateRandomCounts() {
        Random rand = new Random();
        northCount = rand.nextInt(100) + 1;
        southCount = rand.nextInt(100) + 1;
        eastCount = rand.nextInt(100) + 1;
        westCount = rand.nextInt(100) + 1;

        showSimulationControls();

        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setHeaderText("Rastgele Değerler Atandı");
        info.setContentText("North: " + northCount + "\nSouth: " + southCount +
                "\nEast: " + eastCount + "\nWest: " + westCount);
        info.showAndWait();

        if (onApplyListener != null) {
            onApplyListener.accept(getAllCounts());
        }
    }

    private void applyCounts() {
        System.out.println("Simülasyon başlatıldı.");
        if (onApplyListener != null) {
            onApplyListener.accept(getAllCounts());
        }
        if (onStartListener != null) {
            onStartListener.run(); // ❗ Sayaç burada başlar
        }
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
        controlButtonsBox.setVisible(false);
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
        controlButtonsBox.setVisible(true);
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Hata");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public void setOnApplyListener(Consumer<Map<String, Integer>> listener) {
        this.onApplyListener = listener;
    }

    public void setOnStartListener(Runnable listener) {
        this.onStartListener = listener;
    }

    public Map<String, Integer> getAllCounts() {
        Map<String, Integer> counts = new HashMap<>();
        counts.put("NORTH", northCount);
        counts.put("SOUTH", southCount);
        counts.put("EAST", eastCount);
        counts.put("WEST", westCount);
        return counts;
    }

    public int getNorthCount() { return northCount; }
    public int getSouthCount() { return southCount; }
    public int getEastCount() { return eastCount; }
    public int getWestCount() { return westCount; }
}
