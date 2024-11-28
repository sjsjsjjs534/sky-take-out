package com.ccnu.src;

import java.awt.*;

class BrushDrawingStrategy implements DrawingStrategy {
    @Override
    public Drawable createDrawable(Point start, Point end, Color color, int strokeWidth) {
        return new BrushLine(start, end, color, strokeWidth);
    }
}

