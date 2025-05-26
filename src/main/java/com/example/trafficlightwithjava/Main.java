package com.example.trafficlightwithjava;

import com.example.trafficlightwithjava.controller.TrafficLightController;
import com.example.trafficlightwithjava.view.Arayuz;
import com.example.trafficlightwithjava.view.IntersectionView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        Arayuz arayuz = new Arayuz();
        IntersectionView intersectionView = arayuz.getIntersectionView();
        TrafficLightController controller = new TrafficLightController(intersectionView);

        // InputPanel'den başlatma bağlantısı
        arayuz.getInputPanel().setOnStartListener(() -> {
            controller.start(); // sayaçları başlat
        });

        Scene scene = new Scene(arayuz.getRoot(), 1200, 1000);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Kavşak Trafik Işıkları");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
