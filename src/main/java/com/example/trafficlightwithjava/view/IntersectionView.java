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

        BackgroundImage grassBg = new BackgroundImage(
                new Image(getClass().getResource("/com/example/trafficlightwithjava/cim.png").toExternalForm(), WIDTH, HEIGHT, false, true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT
        );
        this.setBackground(new Background(grassBg));

        Group intersection = new Group();

        int centerX = WIDTH / 2;
        int centerY = HEIGHT / 2;

        Rectangle verticalRoad = new Rectangle(ROAD_WIDTH, HEIGHT);
        verticalRoad.setFill(Color.DARKGRAY);
        verticalRoad.setX(centerX - ROAD_WIDTH / 2);
        verticalRoad.setY(0);

        Rectangle horizontalRoad = new Rectangle(1800, ROAD_WIDTH);
        horizontalRoad.setFill(Color.DARKGRAY);
        horizontalRoad.setX(-400);
        horizontalRoad.setY(centerY - ROAD_WIDTH / 2);

        Line verticalLine = new Line(centerX, 0, centerX, HEIGHT);
        verticalLine.setStroke(Color.WHITE);
        verticalLine.setStrokeWidth(2);
        verticalLine.getStrokeDashArray().addAll(20.0, 20.0);

        Line horizontalLine = new Line(-400, centerY, 1400, centerY);
        horizontalLine.setStroke(Color.WHITE);
        horizontalLine.setStrokeWidth(2);
        horizontalLine.getStrokeDashArray().addAll(20.0, 20.0);

        lightNorth = new TrafficLight(centerX - 80, centerY - 200, false);
        lightSouth = new TrafficLight(centerX + 50, centerY + 100, false);
        lightEast  = new TrafficLight(centerX + 125, centerY - 105, true);
        lightWest  = new TrafficLight(centerX - 170, centerY + 25, true);

        intersection.getChildren().addAll(
                verticalRoad, horizontalRoad,
                verticalLine, horizontalLine,
                lightNorth, lightSouth, lightEast, lightWest
        );

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
