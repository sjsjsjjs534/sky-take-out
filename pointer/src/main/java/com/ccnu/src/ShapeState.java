package com.ccnu.src;

import java.awt.*;
import java.awt.event.MouseEvent;

class ShapeState implements DrawingState {
    private DrawingBoard board;
    private DrawingStrategy strategy;
    private Point startPoint;
    private Drawable shape;

    public ShapeState(DrawingBoard board, DrawingStrategy strategy) {
        this.board = board;
        this.strategy = strategy;
    }

    @Override
    public void handleMousePress(MouseEvent e) {
        startPoint = e.getPoint();
        Color color = ApplicationState.getInstance().getCurrentColor();
        int strokeWidth = ApplicationState.getInstance().getCurrentStrokeWidth();
        shape = strategy.createDrawable(startPoint, startPoint, color, strokeWidth);
        board.onDraw(shape);
    }

    @Override
    public void handleMouseDrag(MouseEvent e) {
        if (shape != null) {
            Point endPoint = e.getPoint();
            ((Shape) shape).setEndPoint(endPoint);
            board.repaint();
        }
    }

    @Override
    public void handleMouseRelease(MouseEvent e) {
        if (shape != null) {
            Point endPoint = e.getPoint();
            ((Shape) shape).setEndPoint(endPoint);
            board.addDrawable(shape);
            shape = null; // Reset the shape after release
        }
    }
}