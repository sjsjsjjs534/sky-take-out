package com.ccnu.src;

import java.awt.*;

interface DrawingStrategy {
    Drawable createDrawable(Point start, Point end, Color color, int strokeWidth);
}