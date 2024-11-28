package com.ccnu.src;

import java.awt.*;

class BrushLine extends FreehandLine {
    public BrushLine(Point start, Point end, Color color, int strokeWidth) {
        super(start, end, color, strokeWidth);
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(strokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL, 10f, new float[]{strokeWidth/2}, 0));

        for (int i = 1; i < points.size(); i++) {
            g2d.drawLine(points.get(i - 1).x, points.get(i - 1).y, points.get(i).x, points.get(i).y);
        }

        g2d.dispose();
    }
}