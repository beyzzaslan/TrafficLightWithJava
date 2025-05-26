package com.example.trafficlightwithjava.controller;

import com.example.trafficlightwithjava.view.IntersectionView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class TrafficLightController {

    private final IntersectionView view;
    private Timeline timeline;

    private final int greenDuration = 10;
    private final int yellowDuration = 3;
    private int remaining = greenDuration;
    private String currentGreen = "north";

    public TrafficLightController(IntersectionView view) {
        this.view = view;
    }

    public void start() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> update()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void update() {
        if (remaining > 0) {
            updateLights(currentGreen, remaining);
            remaining--;
        } else {
            switchDirection();
        }
    }

    private void switchDirection() {
        switch (currentGreen) {
            case "north" -> currentGreen = "east";
            case "east" -> currentGreen = "south";
            case "south" -> currentGreen = "west";
            case "west" -> currentGreen = "north";
        }
        remaining = greenDuration;
    }

    private void updateLights(String greenDirection, int secondsLeft) {
        view.getLightNorth().updateState(greenDirection.equals("north") ? "green" : "red", secondsLeft);
        view.getLightSouth().updateState(greenDirection.equals("south") ? "green" : "red", secondsLeft);
        view.getLightEast().updateState(greenDirection.equals("east") ? "green" : "red", secondsLeft);
        view.getLightWest().updateState(greenDirection.equals("west") ? "green" : "red", secondsLeft);
    }
}
