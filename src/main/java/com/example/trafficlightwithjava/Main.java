package com.example.trafficlightwithjava;

import com.example.trafficlightwithjava.controller.TrafficLightController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        TrafficLightController controller = new TrafficLightController();

        Scene scene = new Scene(controller.getRoot(), 900, 800); // Daha küçük boyut

        primaryStage.setTitle("Trafik Işığı Simülasyonu");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.DECORATED); // Üst çerçeve ZORLA görünür
        primaryStage.centerOnScreen(); // Ortala
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}














