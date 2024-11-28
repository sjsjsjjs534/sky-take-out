package com.ccnu.src;

import com.ccnu.src.ApplicationState;
import com.ccnu.src.DrawingBoard;
import com.ccnu.src.DrawingState;
import com.ccnu.src.FreehandLine;

import java.awt.*;
import java.awt.event.MouseEvent;

class PencilState implements DrawingState {
    private DrawingBoard board;

    public PencilState(DrawingBoard board) {
        this.board = board;
    }

    @Override
    public void handleMousePress(MouseEvent e) {
        Color color = ApplicationState.getInstance().getCurrentColor();
        int strokeWidth = ApplicationState.getInstance().getCurrentStrokeWidth();
        FreehandLine line = new FreehandLine(e.getPoint(), e.getPoint(), color, strokeWidth);
        board.setCurrentFreehand(line);
    }

    @Override
    public void handleMouseDrag(MouseEvent e) {
        FreehandLine line = board.getCurrentFreehand();
        if (line != null) {
            line.addPoint(e.getPoint());
            board.repaint();
        }
    }

    @Override
    public void handleMouseRelease(MouseEvent e) {
        FreehandLine line = board.getCurrentFreehand();
        if (line != null) {
            line.addPoint(e.getPoint());
            board.addDrawable(line);
            board.setCurrentFreehand(null);
        }
    }
}