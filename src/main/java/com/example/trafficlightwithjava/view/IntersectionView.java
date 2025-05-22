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
        // x=290, y=290'dan başlayarak 120x120 boyutunda olacak şekilde ortalanmış
        Rectangle center = new Rectangle(290, 290, 120, 120);
        center.setFill(Color.WHITE);
        center.setStrokeWidth(2);

        //  (kuzey) yolu –
        // x=320, y=0'dan başlayarak 60 piksel genişlikte ve 290 piksel yükseklikte bir dikdörtgen
        Rectangle northRoad = new Rectangle(320, 0, 60, 290);
        northRoad.setFill(Color.DARKGRAY);

        // (güney)
        Rectangle southRoad = new Rectangle(320, 410, 60, 290);
        southRoad.setFill(Color.DARKGRAY);

        //  (batı) yolu
        Rectangle westRoad = new Rectangle(0, 320, 290, 60);
        westRoad.setFill(Color.DARKGRAY);

        // (doğu) yolu –
        Rectangle eastRoad = new Rectangle(410, 320, 290, 60);
        eastRoad.setFill(Color.DARKGRAY);

        // şerit çizgisi (kuzey-güney )
        // Ortada x=350 boyunca baştan sona kadar bir çizgi
        Line verticalLine = new Line(350, 0, 350, 700);
        verticalLine.setStroke(Color.WHITE); // çizgi rengi beyaz
        verticalLine.setStrokeWidth(1.5);     // kalınlık
        verticalLine.getStrokeDashArray().addAll(10.0, 10.0); // kesikli çizgi

        //  Yatay şerit çizgisi batı-doğu
        Line horizontalLine = new Line(0, 350, 700, 350);
        horizontalLine.setStroke(Color.WHITE);
        horizontalLine.setStrokeWidth(1.5);
        horizontalLine.getStrokeDashArray().addAll(10.0, 10.0);

        //trafik ışıkları
        Group northLight = createTrafficLight(200, 250);
        Group southLight = createTrafficLight(200, 250);
        Group westLight = createTrafficLight(200, 250);
        Group eastlight = createTrafficLight(200, 250);


        // Tüm bileşenleri sahneye ekle
        // Önce yollar, sonra kavşak, en üste çizgiler ekleniyor
        this.getChildren().addAll(
                northRoad, southRoad, westRoad, eastRoad, // Yollar
                center,                                    // Ortadaki kavşak
                verticalLine, horizontalLine,// Şerit çizgileri
                northLight, southLight, westLight, eastlight
        );

    }

    private Group createTrafficLight(double x,double y) {
        Rectangle red = new Rectangle(x, y, 10, 10);
        red.setFill(Color.RED);//kırmızı ışık

        Rectangle yellow = new Rectangle(x + 12, y, 10, 10);
        yellow.setFill(Color.YELLOW);

        Rectangle green = new Rectangle(x + 24, y, 10, 10);
        yellow.setFill(Color.GREEN);
        return new Group(red, green, yellow);

    }

}
