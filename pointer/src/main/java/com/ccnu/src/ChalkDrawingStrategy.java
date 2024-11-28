package com.ccnu.src;

import java.awt.*;

class ChalkDrawingStrategy implements DrawingStrategy {
    @Override
    public Drawable createDrawable(Point start, Point end, Color color, int strokeWidth) {
        return new ChalkLine(start, end, color, strokeWidth);
    }
}

