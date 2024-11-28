package com.ccnu.src;

import java.awt.*;

class FreehandDrawingStrategy implements DrawingStrategy {
    @Override
    public Drawable createDrawable(Point start, Point end, Color color, int strokeWidth) {
        return new FreehandLine(start, end, color, strokeWidth);
    }
}