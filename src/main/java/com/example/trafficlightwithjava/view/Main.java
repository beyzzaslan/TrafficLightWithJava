package com.example.trafficlightwithjava.view;

import com.example.trafficlightwithjava.view.Arayuz;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        Arayuz arayuz = new Arayuz(); // arayüzümüzü başlat
        Scene scene = new Scene(arayuz.getRoot(), 800, 800);

        stage.setScene(scene);
        stage.setTitle("Akıllı Trafik Işığı Simülasyonu");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args); // JavaFX uygulamasını başlatır
    }
}
