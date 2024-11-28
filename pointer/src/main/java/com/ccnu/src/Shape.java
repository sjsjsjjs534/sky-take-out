package com.ccnu.src;

import java.awt.*;

abstract class Shape implements Drawable {
    protected Point startPoint;
    protected Point endPoint;
    protected Color color;
    protected int strokeWidth;

    public Shape(Point start, Point end, Color color, int strokeWidth) {
        this.startPoint = start;
        this.endPoint = end;
        this.color = color;
        this.strokeWidth = strokeWidth;
    }

    public void setEndPoint(Point end) {
        this.endPoint = end;
    }

    // Other common methods can be added here
}