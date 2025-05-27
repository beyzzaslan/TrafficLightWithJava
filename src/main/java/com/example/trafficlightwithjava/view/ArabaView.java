package com.example.trafficlightwithjava.view;

import com.example.trafficlightwithjava.model.Konum;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class ArabaView extends Pane {
    final Rectangle govde;
    final Rectangle kabin;
    final Polygon cam;
    final Circle farSol, farSag, stopSol, stopSag, tekerOn, tekerArka;

    public static final double ARABA_GENISLIGI = 50.0;
    public static final double ARABA_YUKSEKLIGI = 43.0;

    int arabaId;
    Color arabaRengi;

    public ArabaView(int arabaId, Color renk, Konum baslangicKonum, double arabaGenisligi, double arabaYuksekligi) {
        this.arabaId = arabaId;
        this.arabaRengi = renk;

        // Gövde
        govde = new Rectangle(50, 20);
        govde.setFill(renk);
        govde.setArcWidth(8);
        govde.setArcHeight(8);
        govde.setLayoutX(0);
        govde.setLayoutY(15);

        // Kabin (üst bölüm)
        kabin = new Rectangle(30, 12);
        kabin.setFill(Color.LIGHTGRAY);
        kabin.setLayoutX(10);
        kabin.setLayoutY(5);

        // Cam
        cam = new Polygon(
                12.0, 6.0,
                18.0, 6.0,
                25.0, 16.0,
                5.0, 16.0
        );
        cam.setFill(Color.LIGHTBLUE);

        // Farlar (ön)
        farSol = new Circle(2, Color.WHITE);
        farSol.setLayoutX(2);
        farSol.setLayoutY(18);

        farSag = new Circle(2, Color.WHITE);
        farSag.setLayoutX(2);
        farSag.setLayoutY(30);

        // Stop lambaları (arka)
        stopSol = new Circle(2, Color.RED);
        stopSol.setLayoutX(48);
        stopSol.setLayoutY(18);

        stopSag = new Circle(2, Color.RED);
        stopSag.setLayoutX(48);
        stopSag.setLayoutY(30);

        // Tekerlekler
        tekerOn = new Circle(5, Color.BLACK);
        tekerOn.setLayoutX(10);
        tekerOn.setLayoutY(38);

        tekerArka = new Circle(5, Color.BLACK);
        tekerArka.setLayoutX(40);
        tekerArka.setLayoutY(38);

        // Bileşenleri ekle
        this.getChildren().addAll(govde, kabin, cam, farSol, farSag, stopSol, stopSag, tekerOn, tekerArka);

        // Başlangıç pozisyonu
        updatePosition(baslangicKonum);
    }

    // LayoutX/Y yerine sadece TranslateX/Y kullanılıyor
    public void updatePosition(Konum yeniKonum) {
        this.setTranslateX(yeniKonum.getX());
        this.setTranslateY(yeniKonum.getY());
    }

    public int getArabaId() {
        return arabaId;
    }

    public Color getArabaRengi() {
        return arabaRengi;
    }

    public static double getArabaGenisligi() {
        return ARABA_GENISLIGI;
    }

    public static double getArabaYuksekligi() {
        return ARABA_YUKSEKLIGI;
    }
}
