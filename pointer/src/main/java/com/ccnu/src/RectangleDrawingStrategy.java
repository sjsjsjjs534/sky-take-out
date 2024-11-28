package com.ccnu.src;

import java.awt.*;

class RectangleDrawingStrategy implements DrawingStrategy {
    @Override
    public Drawable createDrawable(Point start, Point end, Color color, int strokeWidth) {
        return new RectangleShape(start, end, color, strokeWidth);
    }
}