package com.ccnu.src;

import java.awt.*;

class ThickDecorator extends DrawableDecorator {
    private int thickness;

    public ThickDecorator(Drawable drawable, int thickness) {
        super(drawable);
        this.thickness = thickness;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        Stroke originalStroke = g2d.getStroke();
        g2d.setStroke(new BasicStroke(thickness));
        super.draw(g2d);
        g2d.setStroke(originalStroke);
        g2d.dispose();
    }
}