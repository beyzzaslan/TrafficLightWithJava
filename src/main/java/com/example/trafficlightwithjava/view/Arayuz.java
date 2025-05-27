package com.example.trafficlightwithjava.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class Arayuz {

    private final StackPane root;
    private final IntersectionView intersectionView;
    private final InputPanel inputPanel;

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
        intersectionView = new IntersectionView();

        // Sağ üst köşe için InputPanel
        inputPanel = new InputPanel();
        inputPanel.setMaxWidth(300);
        BorderPane overlay = new BorderPane();
        overlay.setPickOnBounds(false);
        overlay.setTop(inputPanel);
        BorderPane.setAlignment(inputPanel, Pos.TOP_RIGHT);
        BorderPane.setMargin(inputPanel, new Insets(10));

        root.getChildren().addAll(intersectionView, overlay);
    }

    public Parent getRoot() {
        return root;
    }

    public IntersectionView getIntersectionView() {
        return intersectionView;
    }

    public InputPanel getInputPanel() {
        return inputPanel;
    }
}
