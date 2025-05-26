package com.example.trafficlightwithjava.view;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class ArabaView extends Pane {

    public ArabaView(Color renk, double baslangicX, double baslangicY) {
        // Gövde
        Rectangle govde = new Rectangle(50, 20);
        govde.setFill(renk);
        govde.setArcWidth(8);
        govde.setArcHeight(8);
        govde.setLayoutX(0);
        govde.setLayoutY(15);

        // Kabin (üst bölüm)
        Rectangle kabin = new Rectangle(30, 12);
        kabin.setFill(Color.LIGHTGRAY);
        kabin.setLayoutX(10);
        kabin.setLayoutY(5);

        // Cam (ön cama mavi efekt)
        Polygon cam = new Polygon(
                12.0, 6.0,
                18.0, 6.0,
                25.0, 16.0,
                5.0, 16.0
        );
        cam.setFill(Color.LIGHTBLUE);

        // Farlar (ön)
        Circle farSol = new Circle(2);
        farSol.setFill(Color.WHITE);
        farSol.setLayoutX(2);
        farSol.setLayoutY(18);

        Circle farSag = new Circle(2);
        farSag.setFill(Color.WHITE);
        farSag.setLayoutX(2);
        farSag.setLayoutY(30);

        // Stop lambaları (arka)
        Circle stopSol = new Circle(2);
        stopSol.setFill(Color.RED);
        stopSol.setLayoutX(48);
        stopSol.setLayoutY(18);

        Circle stopSag = new Circle(2);
        stopSag.setFill(Color.RED);
        stopSag.setLayoutX(48);
        stopSag.setLayoutY(30);

        // Tekerlekler
        Circle tekerOn = new Circle(5);
        tekerOn.setFill(Color.BLACK);
        tekerOn.setLayoutX(10);
        tekerOn.setLayoutY(38);

        Circle tekerArka = new Circle(5);
        tekerArka.setFill(Color.BLACK);
        tekerArka.setLayoutX(40);
        tekerArka.setLayoutY(38);

        // Tüm parçaları ekle
        this.getChildren().addAll(govde, kabin, cam, farSol, farSag, stopSol, stopSag, tekerOn, tekerArka);
        this.setLayoutX(baslangicX);
        this.setLayoutY(baslangicY);
    }
}