package com.example.trafficlightwithjava.view;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class Arayuz {

    private BorderPane root;

    public Arayuz() {
        root = new BorderPane();

        // Kavşaklarla görüntüsü (ortaya yerleştir)
        IntersectionView intersectionView = new IntersectionView();
        // Ortalamak için StackPane içine alıyoruz
        StackPane centerPane = new StackPane(intersectionView);
        centerPane.setAlignment(Pos.CENTER);
        root.setCenter(intersectionView);
    }

    public Parent getRoot() {
        return root;
    }
}
