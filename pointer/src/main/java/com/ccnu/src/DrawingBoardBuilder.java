package com.ccnu.src;

import com.ccnu.src.ApplicationState;
import com.ccnu.src.DrawingBoard;

import java.awt.*;

// Builder Pattern for Constructing Complex Objects Step by Step
class DrawingBoardBuilder {
    private DrawingBoard drawingBoard;

    public DrawingBoardBuilder() {
        drawingBoard = new DrawingBoard();
    }

    public DrawingBoardBuilder setColor(Color color) {
        ApplicationState.getInstance().setCurrentColor(color);
        return this;
    }

    public DrawingBoardBuilder setStrokeWidth(int strokeWidth) {
        ApplicationState.getInstance().setCurrentStrokeWidth(strokeWidth);
        return this;
    }

    public DrawingBoard build() {
        return drawingBoard;
    }
}