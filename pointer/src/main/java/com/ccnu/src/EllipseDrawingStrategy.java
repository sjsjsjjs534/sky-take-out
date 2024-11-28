package com.ccnu.src;

import java.awt.*;

class EllipseDrawingStrategy implements DrawingStrategy {
    @Override
    public Drawable createDrawable(Point start, Point end, Color color, int strokeWidth) {
        return new EllipseShape(start, end, color, strokeWidth);
    }
}