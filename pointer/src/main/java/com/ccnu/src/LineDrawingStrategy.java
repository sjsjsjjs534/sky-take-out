package com.ccnu.src;

import java.awt.*;

class LineDrawingStrategy implements DrawingStrategy {
    @Override
    public Drawable createDrawable(Point start, Point end, Color color, int strokeWidth) {
        return new LineShape(start, end, color, strokeWidth);
    }
}