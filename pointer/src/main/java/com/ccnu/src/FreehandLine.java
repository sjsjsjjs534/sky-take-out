package com.ccnu.src;

import java.awt.*;
import java.util.ArrayList;

class FreehandLine implements Drawable {
    public ArrayList<Point> points;
    public Color color;
    public int strokeWidth;

    public FreehandLine(Point start, Point end, Color color, int strokeWidth) {
        this.points = new ArrayList<>();
        points.add(start);
        points.add(end);
        this.color = color;
        this.strokeWidth = strokeWidth;
    }

    public void addPoint(Point point) {
        points.add(point);
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(strokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));

        for (int i = 1; i < points.size(); i++) {
            g2d.drawLine(points.get(i - 1).x, points.get(i - 1).y, points.get(i).x, points.get(i).y);
        }

        g2d.dispose();
    }
}