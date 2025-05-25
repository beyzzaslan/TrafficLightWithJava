package com.example.trafficlightwithjava.view;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class TrafficLight extends Group {
    private final Circle red;
    private final Circle yellow;
    private final Circle green;

    public TrafficLight(double x, double y, boolean rotate90) {
        double width = 30;
        double height = 80;
        double radius = 10;

        Rectangle body = new Rectangle(width, height);
        body.setFill(Color.BLACK);
        body.setArcWidth(10);
        body.setArcHeight(10);

        red = new Circle(radius, Color.DARKRED);
        yellow = new Circle(radius, Color.DARKGOLDENROD);
        green = new Circle(radius, Color.DARKGREEN);

        // Paddingli şekilde yerleştir
        double paddingTop = 15;
        double spaceBetween = 25;

        red.setCenterX(width / 2);
        red.setCenterY(paddingTop);

        yellow.setCenterX(width / 2);
        yellow.setCenterY(paddingTop + spaceBetween);

        green.setCenterX(width / 2);
        green.setCenterY(paddingTop + 2 * spaceBetween);

        this.getChildren().addAll(body, red, yellow, green);
        this.setLayoutX(x);
        this.setLayoutY(y);

        if (rotate90) {
            this.setRotate(90);
        }
    }

    public void setState(String color) {
        // önce hepsini sönük yap
        red.setFill(Color.DARKRED);
        yellow.setFill(Color.DARKGOLDENROD);
        green.setFill(Color.DARKGREEN);

        // sonra sadece biri aktif parlak olsun
        switch (color.toLowerCase()) {
            case "red" -> red.setFill(Color.RED);
            case "yellow" -> yellow.setFill(Color.YELLOW);
            case "green" -> green.setFill(Color.LIMEGREEN);
        }
    }
}
