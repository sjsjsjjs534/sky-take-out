package com.ccnu.src;

import java.awt.*;

public class ApplicationState {
    private static ApplicationState instance;
    private Color currentColor;
    private int currentStrokeWidth;
    private DrawingStrategy currentStrategy;

    private ApplicationState() {
        this.currentColor = Color.BLACK;
        this.currentStrokeWidth = 1;
        this.currentStrategy = new PencilDrawingStrategy();
    }

    public static synchronized ApplicationState getInstance() {
        if (instance == null) {
            instance = new ApplicationState();
        }
        return instance;
    }

    public Color getCurrentColor() {
        return currentColor;
    }

    public void setCurrentColor(Color color) {
        this.currentColor = color;
    }

    public int getCurrentStrokeWidth() {
        return currentStrokeWidth;
    }

    public void setCurrentStrokeWidth(int strokeWidth) {
        this.currentStrokeWidth = strokeWidth;
    }

    public DrawingStrategy getCurrentStrategy() {
        return currentStrategy;
    }

    public void setCurrentStrategy(DrawingStrategy strategy) {
        this.currentStrategy = strategy;
    }
}