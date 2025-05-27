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

    private TrafficLight lightNorth, lightSouth, lightEast, lightWest;
    private Group arabaKatmani;

    public IntersectionView() {
        this.setPrefSize(WIDTH, HEIGHT);

        // Arka plan çim
        BackgroundImage grassBg = new BackgroundImage(
                new Image(getClass().getResource("/com/example/trafficlightwithjava/cimm.png").toExternalForm(), WIDTH, HEIGHT, false, true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT
        );
        this.setBackground(new Background(grassBg));

        Group intersection = new Group();

        int centerX = WIDTH / 2;
        int centerY = HEIGHT / 2;

        // Koyu gri yol rengi
        Color koyuGri = Color.rgb(30, 30, 30);

        // Dikey yol
        Rectangle verticalRoad = new Rectangle(ROAD_WIDTH, HEIGHT);
        verticalRoad.setFill(koyuGri);
        verticalRoad.setX(centerX - ROAD_WIDTH / 2);
        verticalRoad.setY(0);

        // Yatay yol
        Rectangle horizontalRoad = new Rectangle(1800, ROAD_WIDTH);
        horizontalRoad.setFill(koyuGri);
        horizontalRoad.setX(-400);
        horizontalRoad.setY(centerY - ROAD_WIDTH / 2);

        // Yolların ortasındaki çizgiler
        Line verticalLine = new Line(centerX, 65, centerX, HEIGHT);
        verticalLine.setStroke(Color.WHITE);
        verticalLine.setStrokeWidth(1);
        verticalLine.getStrokeDashArray().addAll(20.0, 20.0);

        Line horizontalLine = new Line(-410, centerY, 1400, centerY);
        horizontalLine.setStroke(Color.WHITE);
        horizontalLine.setStrokeWidth(1);
        horizontalLine.getStrokeDashArray().addAll(20.0, 20.0);

        // Trafik ışıkları
        lightNorth = new TrafficLight(centerX - 80, centerY - 200, false);
        lightNorth.setRotate(180); // 180 derece döndür
        lightSouth = new TrafficLight(centerX + 50, centerY + 100, false);
        lightEast = new TrafficLight(centerX + 125, centerY - 120, true);
        lightEast.setRotate(270); // 180 derece döndür
        lightWest = new TrafficLight(centerX - 170, centerY + 10, true);

        // Kavşak grubu
        intersection.getChildren().addAll(
                verticalRoad, horizontalRoad,
                verticalLine, horizontalLine,
                lightNorth, lightSouth, lightEast, lightWest
        );

        // Araç katmanı
        arabaKatmani = new Group();
        this.getChildren().addAll(intersection, arabaKatmani);
    }

    public Group getArabaKatmani() {
        return arabaKatmani;
    }

    public TrafficLight getLightNorth() {
        return lightNorth;
    }

    public TrafficLight getLightSouth() {
        return lightSouth;
    }

    public TrafficLight getLightEast() {
        return lightEast;
    }

    public TrafficLight getLightWest() {
        return lightWest;
    }
}
