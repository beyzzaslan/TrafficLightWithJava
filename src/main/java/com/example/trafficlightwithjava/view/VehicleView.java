package com.example.trafficlightwithjava.view;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import com.example.trafficlightwithjava.view.Direction;

public class VehicleView extends Pane {

    /*
    * Önce araçları oluştur(rectangle ile)
    * Her araç North, South, East, West yönlerinden birinden girer. Başlangıç pozisyonu bu yöne göre ayarlanır.(konuma göre ayarlancak)
    */
    //Önce yönleri tanımladım
    public Direction direction;

    private Rectangle vehicle;
    private boolean gectiMi = false; // Geçti mi?

    public VehicleView(Direction direction) {
        this.direction = direction;
        // Aracın boyutu ve rengi
        vehicle = new Rectangle(20,40);
        vehicle.setFill(Color.RED);
        //Yöne göre başlangıç konumu
        switch (direction) {
            case NORTH -> {
                vehicle.setLayoutX(340); // yol ortası (320 + (60-20)/2)
                vehicle.setLayoutY(-40); // ekran dışında başlayacak (giriş için)
            }
            case SOUTH -> {
                vehicle.setLayoutX(340);
                vehicle.setLayoutY(700); // ekran dışından giriyor
            }
            case EAST -> {
                vehicle.setWidth(40);
                vehicle.setHeight(20);
                vehicle.setLayoutX(700); // ekran dışından giriyor
                vehicle.setLayoutY(340);
            }
            case WEST -> {
                vehicle.setWidth(40);
                vehicle.setHeight(20);
                vehicle.setLayoutX(-40); // sol dıştan giriyor
                vehicle.setLayoutY(340);
            }
        }

        this.getChildren().add(vehicle);
    }
    public Direction getDirection(){
        return direction;
    }
    public boolean gectiMi(){
        return gectiMi;
    }
    public void setgecti (boolean gecti) {
        this.gectiMi = gecti;
    }
    public Rectangle getVehicleShape() {
        return vehicle;
    }

}
