package com.example.trafficlightwithjava.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class Arayuz {

    private StackPane root;

    public Arayuz() {
        // Root StackPane — her şeyi üst üste yerleştirir
        root = new StackPane();

        // === 1. Arka plan: Çim resmi ===
        BackgroundImage grassBg = new BackgroundImage(
                new Image(getClass().getResource("/com/example/trafficlightwithjava/cim.png").toExternalForm()),
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT
                //REPEAT sayesinde küçük bir çim görseli tüm sahneye yayılır.
        );
        root.setBackground(new Background(grassBg));

        // === 2. Ortadaki Kavşak ===
        IntersectionView intersectionView = new IntersectionView();

        // === 3. Sağ üst köşeye yerleşecek InputPanel ===
        InputPanel inputPanel = new InputPanel();
        inputPanel.setMaxWidth(300);

        BorderPane overlay = new BorderPane();
        overlay.setPickOnBounds(false);
        overlay.setTop(inputPanel);
        BorderPane.setAlignment(inputPanel, Pos.TOP_RIGHT);
        BorderPane.setMargin(inputPanel, new Insets(10));
//PickOnBounds(false) demek: görünmeyen yerlerden tıklamayı engelleme, yani alt katmanla etkileşimi sürdür.

        // === Her şeyi root StackPane'e ekle ===
        root.getChildren().addAll(intersectionView, overlay);
    }

    public Parent getRoot() {
        return root;
    }
}
