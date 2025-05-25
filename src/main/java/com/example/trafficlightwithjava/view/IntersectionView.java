package com.example.trafficlightwithjava.view;

import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.Group;

public class IntersectionView extends StackPane {

    private final int WIDTH = 1000;
    private final int HEIGHT = 1000;
    private final int ROAD_WIDTH = 100;

    public IntersectionView() {
        this.setPrefSize(WIDTH, HEIGHT);

        // Çim arka planı
        BackgroundImage grassBg = new BackgroundImage(
                new Image(getClass().getResource("/com/example/trafficlightwithjava/cim.png").toExternalForm(), WIDTH, HEIGHT, false, true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT
        );
        this.setBackground(new Background(grassBg));

        Group intersection = new Group();

        // Ortalamak için hesaplama
        int centerX = WIDTH / 2;
        int centerY = HEIGHT / 2;

        // Dikey yol
        Rectangle verticalRoad = new Rectangle(ROAD_WIDTH, HEIGHT);
        verticalRoad.setFill(Color.DARKGRAY);
        verticalRoad.setX(centerX - ROAD_WIDTH / 2);
        verticalRoad.setY(0);

        // Yatay yol
        Rectangle horizontalRoad = new Rectangle(1800, ROAD_WIDTH);
        horizontalRoad.setFill(Color.DARKGRAY);
        horizontalRoad.setX(-400); // Ortalamak için
        horizontalRoad.setY(centerY - ROAD_WIDTH / 2);


        // Dikey çizgi (kesikli)
        Line verticalLine = new Line(centerX, 0, centerX, HEIGHT);
        verticalLine.setStroke(Color.WHITE);
        verticalLine.setStrokeWidth(2);
        verticalLine.getStrokeDashArray().addAll(20.0, 20.0);

        // Yatay çizgi (kesikli)
        Line horizontalLine = new Line(-400, centerY, 1400, centerY);
        horizontalLine.setStroke(Color.WHITE);
        horizontalLine.setStrokeWidth(2);
        horizontalLine.getStrokeDashArray().addAll(20.0, 20.0);

        // Hepsini ekle
        intersection.getChildren().addAll(verticalRoad, horizontalRoad, verticalLine, horizontalLine);
        this.getChildren().add(intersection);
    }
}
