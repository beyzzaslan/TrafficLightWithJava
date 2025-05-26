package com.example.trafficlightwithjava.view;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class TimerDisplay extends Label {

    public TimerDisplay() {
        this.setText("00");
        this.setFont(new Font("Arial", 18));
        this.setStyle("-fx-background-color: white; -fx-padding: 2px;");
    }

    public void setTime(int seconds, String color) {
        this.setText(String.format("%02d", seconds));

        switch (color.toLowerCase()) {
            case "green" -> this.setTextFill(Color.LIMEGREEN);
            case "red" -> this.setTextFill(Color.RED);
            case "yellow" -> this.setTextFill(Color.YELLOW);
        }
    }
}
