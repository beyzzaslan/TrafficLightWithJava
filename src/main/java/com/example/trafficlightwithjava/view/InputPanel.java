package com.example.trafficlightwithjava.view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Optional;
import java.util.Random;

public class InputPanel extends VBox {
    //Burada butonları tanımlıyorum
    Button manualButton;
    Button randomButton;
    Button applyButton;
    Button resetButton;
    Button stopButton;
    Button continueButton;

    //her yönden gelen araç sayısını değişken olarak yazdım
    int northCount, southCount, eastCount, westCount;

    private HBox controlButtonsBox;

    //şimdi ekran başlayınca olacak olaylar için constructor yazıyoruz

    public InputPanel() {

        //this dicez yani bu input panelin genişliği anlamında
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

        // Üst kısım: Başlık ve giriş butonları
        VBox topPanel = new VBox(10);
        topPanel.getChildren().addAll(title, manualButton, randomButton);
        // Kontrol butonlarını yatay yerleştirmek için HBox

        controlButtonsBox = new HBox(10);
        controlButtonsBox.getChildren().addAll(applyButton, resetButton, stopButton, continueButton);
        controlButtonsBox.setVisible(false);


        //butona basıncaki handler eventler içinse
        manualButton.setOnAction(e -> openManuelDialog());
        randomButton.setOnAction(e -> generateRandomCounts());

        applyButton.setOnAction(e -> applyCounts());
        resetButton.setOnAction(e -> resetEvent());
        stopButton.setOnAction(e -> stopEvent());
        continueButton.setOnAction(e -> continueEvent());


        // Ortak layout: BorderPane ile düzenle
        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setStyle("-fx-border-color: blue; -fx-border-width: 2px; -fx-border-style: dotted;");
        BorderPane.setAlignment(controlButtonsBox, javafx.geometry.Pos.TOP_CENTER);
        layout.setTop(topPanel);
        layout.setBottom(controlButtonsBox);
        BorderPane.setMargin(controlButtonsBox, new Insets(5, 0, 0, 0));

        //buton ve başlıkları ekledik inputpanele
        this.getChildren().add(layout);
    }

    private void openManuelDialog() {
        //burda popup yapıyoruz
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Manuel Giriş");

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        //grid içindeki textFieldleri oluşturuyoruz
        TextField northField = new TextField();
        TextField southField = new TextField();
        TextField eastField = new TextField();
        TextField westField = new TextField();
        //oluşturduğumuz textFieldleri grid içine ekliyoruz
        grid.addRow(0, new Label("North"), northField);
        grid.addRow(1, new Label("South"), southField);
        grid.addRow(2, new Label("East:"), eastField);
        grid.addRow(3, new Label("West:"), westField);
        //dialog içine butonları VE gridi ekliyoruz
        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK)
        //acaba burda OK a basınca mı animasyonlar oynamaya başlayacak
        {
            try {
                northCount = Integer.parseInt(northField.getText());
                southCount = Integer.parseInt(southField.getText());
                eastCount = Integer.parseInt(eastField.getText());
                westCount = Integer.parseInt(westField.getText());

                // Aralık kontrolü
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
    }

    private void applyCounts() {
        System.out.println("Simülasyon başlatıldı.");
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

    //butonları görünür hale getiriyor
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

    // Getter metodlar (Controller bu değerleri isterse kullanabilir yani  dışarıdan erişilebilir olmasını sağlıyoruz.)
    public int getNorthCount() {
        return northCount;
    }

    public int getSouthCount() {
        return southCount;
    }

    public int getEastCount() {
        return eastCount;
    }

    public int getWestCount() {
        return westCount;
    }

}
