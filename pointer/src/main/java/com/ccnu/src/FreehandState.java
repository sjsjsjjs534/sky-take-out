package com.ccnu.src;

import java.awt.*;
import java.awt.event.MouseEvent;

class FreehandState implements DrawingState {
    private DrawingBoard board;
    private FreehandLine freehand;

    public FreehandState(DrawingBoard board) {
        this.board = board;
    }

    @Override
    public void handleMousePress(MouseEvent e) {
        Color color = ApplicationState.getInstance().getCurrentColor();
        int strokeWidth = ApplicationState.getInstance().getCurrentStrokeWidth();
        freehand = new FreehandLine(e.getPoint(), e.getPoint(), color, strokeWidth);
        board.onDraw(freehand);
    }

    @Override
    public void handleMouseDrag(MouseEvent e) {
        if (freehand != null) {
            freehand.addPoint(e.getPoint());
            board.repaint();
        }
    }

    @Override
    public void handleMouseRelease(MouseEvent e) {
        if (freehand != null) {
            freehand.addPoint(e.getPoint());
            board.addDrawable(freehand);
            freehand = null; // Reset the freehand after release
        }
    }
}