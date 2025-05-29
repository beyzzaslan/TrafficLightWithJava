package com.example.trafficlightwithjava.view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

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
    private Runnable onStartSimulationListener; // EKLENDİ: sayaç başlatma için listener
    private Runnable onResetListener;
    private Runnable onStopListener;
    private Runnable onContinueListener;

    public InputPanel() {
        this.setPrefWidth(300);
        this.setPadding(new Insets(10));
        this.setSpacing(10);

// Başlık
        Label title = new Label("Araç Yoğunluğu Girişi");
        title.setPadding(new Insets(8));
        title.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 18));
        title.setTextFill(Color.web("#17c964")); // Canlı yeşil ton

// Manuel ve Rastgele butonları yatay HBox içine alınır
        manualButton = new Button("Manuel Giriş");
        randomButton = new Button("Rastgele Giriş");
        manualButton.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        randomButton.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        manualButton.setStyle("-fx-background-color: #e1ffe1; -fx-text-fill: #157115; -fx-background-radius: 8;");
        randomButton.setStyle("-fx-background-color: #e1ffe1; -fx-text-fill: #157115; -fx-background-radius: 8;");

        VBox girisBox = new VBox(8, manualButton, randomButton);
        girisBox.setAlignment(Pos.CENTER);

// Başlat, Resetle, Stop, Devam Et butonları yatay HBox içinde
        applyButton = new Button("Başlat");
        resetButton = new Button("Resetle");
        stopButton = new Button("Dur");
        continueButton = new Button("Devam ");

        Button[] actionBtns = { applyButton, resetButton, stopButton, continueButton };
        for (Button b : actionBtns) {
            b.setFont(Font.font("Arial", FontWeight.BOLD, 10)); // 13pt daha iyi gözükür
            b.setPrefWidth(110); // Hepsine 110 ver
            b.setStyle("-fx-background-color: #a5ffc9; -fx-text-fill: #006c3a; -fx-background-radius: 9;");
        }
        applyButton.setPrefWidth(120);
        resetButton.setPrefWidth(120);
        stopButton.setPrefWidth(120);
        continueButton.setPrefWidth(120);

        for (Button b : actionBtns) {
            b.setPrefWidth(120); // Hepsini 120 yap
        }


        controlButtonsBox = new HBox(8);
        controlButtonsBox.getChildren().addAll(applyButton, resetButton, stopButton, continueButton);
        controlButtonsBox.setAlignment(Pos.CENTER);
        controlButtonsBox.setPrefWidth(400); // İstediğin kadar genişlik ver (ör: 320)

        controlButtonsBox.setVisible(false);

// Üst panel, başlık ve giriş butonları üst üste (VBox)
        VBox topPanel = new VBox(8);
        topPanel.setPadding(new Insets(10, 0, 10, 0));
        topPanel.getChildren().addAll(title, girisBox);

// Ana layout
        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.setTop(topPanel);
        layout.setBottom(controlButtonsBox);
        BorderPane.setAlignment(controlButtonsBox, Pos.CENTER);
        BorderPane.setMargin(controlButtonsBox, new Insets(10, 0, 0, 0));

        this.getChildren().add(layout);

// Button Event Handlers (Değişmedi)
        manualButton.setOnAction(e -> openManuelDialog());
        randomButton.setOnAction(e -> generateRandomCounts());
        applyButton.setOnAction(e -> applyCounts());
        resetButton.setOnAction(e -> resetEvent());
        stopButton.setOnAction(e -> stopEvent());
        continueButton.setOnAction(e -> continueEvent());

    }

    private void openManuelDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Manuel Giriş");
        dialog.setHeaderText("Her Yön İçin Araç Sayısını Girin (1-100)"); // Sizin metniniz buydu.


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
                hideSimulationControls();
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
    }

    private void applyCounts() {
        System.out.println("Simülasyon başlatıldı.");
        if (onApplyListener != null) {
            onApplyListener.accept(getAllCounts());
        }
        if (onStartSimulationListener != null) {
            onStartSimulationListener.run(); // ❗ Sayaç burada başlar
        }
    }

    private void resetEvent() {
        northCount = 0;
        southCount = 0;
        eastCount = 0;
        westCount = 0;

        hideSimulationControls();
        if (onResetListener != null) { // Controller'a reset sinyalini gönder
            onResetListener.run();
        }
    }

    private void stopEvent() {
        System.out.println("Simülasyon durduruldu.");
        if (onStopListener != null) { // Controller'a durdurma sinyalini gönder
            onStopListener.run();
        }
    }

    private void continueEvent() {
        System.out.println("Simülasyon devam etti.");
        if (onContinueListener != null) { // Controller'a devam etme sinyalini gönder
            onContinueListener.run();
        }
    }

    private void showSimulationControls() {
        applyButton.setVisible(true);
        resetButton.setVisible(true);
        stopButton.setVisible(true);
        continueButton.setVisible(true);
        controlButtonsBox.setVisible(true);
    }
    public void hideSimulationControls() { // Bu metot da View'in kendi durumunu yönetiyor
        applyButton.setVisible(false);
        resetButton.setVisible(false);
        stopButton.setVisible(false);
        continueButton.setVisible(false);
        controlButtonsBox.setVisible(false);
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

    public void setOnStartSimulationListener(Runnable listener) {
        this.onStartSimulationListener = listener;
    }
    public void setOnResetListener(Runnable listener) {
        this.onResetListener = listener;
    }

    public void setOnStopListener(Runnable listener) {
        this.onStopListener = listener;
    }

    public void setOnContinueListener(Runnable listener) {
        this.onContinueListener = listener;
    }

    public Map<String, Integer> getAllCounts() {
        Map<String, Integer> counts = new HashMap<>();
        counts.put("KUZEY", northCount);
        counts.put("GUNEY", southCount);
        counts.put("DOGU", eastCount);
        counts.put("BATI", westCount);
        return counts;
    }

    public int getNorthCount() { return northCount; }
    public int getSouthCount() { return southCount; }
    public int getEastCount() { return eastCount; }
    public int getWestCount() { return westCount; }
}
