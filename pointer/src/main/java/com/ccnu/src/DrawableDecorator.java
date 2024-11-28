package com.ccnu.src;

import java.awt.*;

abstract class DrawableDecorator implements Drawable {
    protected Drawable drawable;

    public DrawableDecorator(Drawable drawable) {
        this.drawable = drawable;
    }

    @Override
    public void draw(Graphics g) {
        drawable.draw(g);
    }
}