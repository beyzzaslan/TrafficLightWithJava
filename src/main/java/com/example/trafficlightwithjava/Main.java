package com.example.trafficlightwithjava;

import com.example.trafficlightwithjava.controller.TrafficLightController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Controller'ı oluştur
        TrafficLightController controller = new TrafficLightController();

        // Controller'dan View'in ana kökünü al
        Scene scene = new Scene(controller.getRoot(), 1000, 1000); // Sahne boyutlarını IntersectionView boyutlarıyla eşleştirin

        primaryStage.setTitle("Trafik Işığı Simülasyonu");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false); // Pencere boyutunun değişmesini engelleyebiliriz
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}