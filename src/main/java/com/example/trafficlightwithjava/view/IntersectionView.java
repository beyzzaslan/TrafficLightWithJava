package com.example.trafficlightwithjava.view;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.Group;

public class IntersectionView extends Pane {

    public IntersectionView() {
        // 1. Pane'in boyutunu belirle (sahne 700x700 piksel)
        this.setPrefSize(700, 700);

        // 2. Kavşağın merkezindeki açık gri kutu (kesim alanı)
        Rectangle center = new Rectangle(290, 290, 120, 120);
        center.setFill(Color.WHITE);
        center.setStrokeWidth(2);

        // Kuzey yolu
        Rectangle northRoad = new Rectangle(320, 0, 60, 290);
        northRoad.setFill(Color.DARKGRAY);

        // Güney yolu
        Rectangle southRoad = new Rectangle(320, 410, 60, 290);
        southRoad.setFill(Color.DARKGRAY);

        // Batı yolu
        Rectangle westRoad = new Rectangle(0, 320, 290, 60);
        westRoad.setFill(Color.DARKGRAY);

        // Doğu yolu
        Rectangle eastRoad = new Rectangle(410, 320, 290, 60);
        eastRoad.setFill(Color.DARKGRAY);

        // Şerit çizgisi - Dikey (Kuzey-Güney)
        Line verticalLine = new Line(350, 0, 350, 700);
        verticalLine.setStroke(Color.WHITE);
        verticalLine.setStrokeWidth(1.5);
        verticalLine.getStrokeDashArray().addAll(10.0, 10.0);

        // Şerit çizgisi - Yatay (Batı-Doğu)
        Line horizontalLine = new Line(0, 350, 700, 350);
        horizontalLine.setStroke(Color.WHITE);
        horizontalLine.setStrokeWidth(1.5);
        horizontalLine.getStrokeDashArray().addAll(10.0, 10.0);

        // Trafik ışıkları (her yön için pozisyonları ayarlamalısın)
        Group northLight = createTrafficLight(330, 270); // yukarı
        Group southLight = createTrafficLight(330, 420); // aşağı
        Group westLight = createTrafficLight(270, 330);  // sol
        Group eastLight = createTrafficLight(420, 330);  // sağ

        // Tüm bileşenleri sahneye ekle
        this.getChildren().addAll(
                northRoad, southRoad, westRoad, eastRoad,
                center,
                verticalLine, horizontalLine,
                northLight, southLight, westLight, eastLight
        );
    }

    private Group createTrafficLight(double x, double y) {
        Rectangle red = new Rectangle(x, y, 10, 10);
        red.setFill(Color.RED);

        Rectangle yellow = new Rectangle(x + 12, y, 10, 10);
        yellow.setFill(Color.YELLOW);

        Rectangle green = new Rectangle(x + 24, y, 10, 10);
        green.setFill(Color.GREEN);

        return new Group(red, yellow, green);
    }
}
