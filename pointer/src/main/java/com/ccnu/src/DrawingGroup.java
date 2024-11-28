package com.ccnu.src;

import java.awt.*;
import java.util.ArrayList;

class DrawingGroup implements Drawable {
    private ArrayList<Drawable> drawables;
    private Color color;
    private int strokeWidth;

    public DrawingGroup(Color color, int strokeWidth) {
        this.drawables = new ArrayList<>();
        this.color = color;
        this.strokeWidth = strokeWidth;
    }

    public void addDrawable(Drawable drawable) {
        drawables.add(drawable);
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(strokeWidth));

        for (Drawable drawable : drawables) {
            drawable.draw(g2d);
        }

        g2d.dispose();
    }
}