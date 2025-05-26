package com.example.trafficlightwithjava.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import java.util.Map;
import java.util.Random;

public class Arayuz {

    private StackPane root;
    private final Random random = new Random();

    public Arayuz() {
        root = new StackPane();

        // Arka plan çim
        BackgroundImage grassBg = new BackgroundImage(
                new Image(getClass().getResource("/com/example/trafficlightwithjava/cim.png").toExternalForm()),
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT
        );
        root.setBackground(new Background(grassBg));

        // Kavşak görünümü
        IntersectionView intersectionView = new IntersectionView();

        // Sağ üst köşe için InputPanel
        InputPanel inputPanel = new InputPanel();
        inputPanel.setMaxWidth(300);
        BorderPane overlay = new BorderPane();
        overlay.setPickOnBounds(false);
        overlay.setTop(inputPanel);
        BorderPane.setAlignment(inputPanel, Pos.TOP_RIGHT);
        BorderPane.setMargin(inputPanel, new Insets(10));

        // === applyButton'dan gelen değerlerle test arabası oluştur ===
        inputPanel.setOnApplyListener(countMap -> {
            intersectionView.getArabaKatmani().getChildren().clear();

            // Test arabası - ekran ortasına yakın sabit pozisyon
            ArabaView testAraba = new ArabaView(Color.RED, 485, 300);
            intersectionView.getArabaKatmani().getChildren().add(testAraba);

            System.out.println("Test arabası eklendi");
        });

        root.getChildren().addAll(intersectionView, overlay);
    }

    public Parent getRoot() {
        return root;
    }

    private Color getRandomColor() {
        Color[] renkler = {
                Color.RED, Color.BLUE, Color.MEDIUMPURPLE, Color.DARKCYAN,
                Color.DEEPPINK, Color.GREENYELLOW, Color.BROWN, Color.LIGHTSKYBLUE
        };
        return renkler[random.nextInt(renkler.length)];
    }
}





