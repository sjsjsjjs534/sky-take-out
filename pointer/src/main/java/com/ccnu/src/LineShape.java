package com.ccnu.src;

import java.awt.*;

class LineShape extends Shape {
    public LineShape(Point start, Point end, Color color, int strokeWidth) {
        super(start, end, color, strokeWidth);
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(strokeWidth));

        g2d.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
        g2d.dispose();
    }
}



