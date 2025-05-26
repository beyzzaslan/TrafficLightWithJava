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

        // Araç oluşturma
        inputPanel.setOnApplyListener(countMap -> {
            intersectionView.getArabaKatmani().getChildren().clear();
            int spacing = 45;

            int north = countMap.get("NORTH");
            int south = countMap.get("SOUTH");
            int east = countMap.get("EAST");
            int west = countMap.get("WEST");

            // NORTH (aşağı gidiyor)
            for (int i = 0; i < north; i++) {
                ArabaView araba = new ArabaView(getRandomColor(), 510, 100 + i * spacing);
                intersectionView.getArabaKatmani().getChildren().add(araba);
            }

            // SOUTH (yukarı gidiyor)
            for (int i = 0; i < south; i++) {
                ArabaView araba = new ArabaView(getRandomColor(), 480, 600 + i * spacing);
                intersectionView.getArabaKatmani().getChildren().add(araba);
            }

            // WEST (sağa gidiyor)
            for (int i = 0; i < west; i++) {
                ArabaView araba = new ArabaView(getRandomColor(), 100 + i * spacing, 510);
                intersectionView.getArabaKatmani().getChildren().add(araba);
            }

            // EAST (sola gidiyor)
            for (int i = 0; i < east; i++) {
                ArabaView araba = new ArabaView(getRandomColor(), 850 - i * spacing, 470);
                intersectionView.getArabaKatmani().getChildren().add(araba);
            }
        });

        root.getChildren().addAll(intersectionView, overlay);
    }

    public Parent getRoot() {
        return root;
    }

    private Color getRandomColor() {
        Color[] renkler = {
                Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW,
                Color.ORANGE, Color.PINK, Color.CYAN, Color.LIGHTGREEN
        };
        return renkler[random.nextInt(renkler.length)];
    }
}