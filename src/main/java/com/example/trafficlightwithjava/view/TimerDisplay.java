package com.example.trafficlightwithjava.view;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class TimerDisplay extends Label {

    public TimerDisplay(double x,double y) {
        this.setText("00");
        this.setFont(new Font("Arial", 18));
        this.setStyle("-fx-background-color: white; -fx-padding: 2px;");

        this.setLayoutX(x);
        this.setLayoutY(y);
    }

    public void updateTime(int seconds)
    {
        this.setText(String.format("%02d", Math.max(0,seconds)));
    }
    public void setTextColor(Color color){
        this.setTextFill(color);
    }
}
