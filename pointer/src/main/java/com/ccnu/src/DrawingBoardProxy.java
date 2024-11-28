package com.ccnu.src;

import com.ccnu.src.Drawable;
import com.ccnu.src.DrawingBoard;

import java.awt.*;

// Proxy Pattern for Controlling Access to Real Object
class DrawingBoardProxy extends DrawingBoard {
    private DrawingBoard realBoard;

    public DrawingBoardProxy() {
        realBoard = new DrawingBoard();
    }

    @Override
    public void addDrawable(Drawable drawable) {
        realBoard.addDrawable(drawable);
    }

    @Override
    public void removeDrawable(Drawable drawable) {
        realBoard.removeDrawable(drawable);
    }

    @Override
    public void clear() {
        realBoard.clear();
    }

    @Override
    public void setBackground(Image image) {
        realBoard.setBackground(image);
    }

    @Override
    public void paintComponent(Graphics g) {
        realBoard.paintComponent(g);
    }
}