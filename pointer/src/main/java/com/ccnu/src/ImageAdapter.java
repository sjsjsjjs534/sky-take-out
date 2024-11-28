package com.ccnu.src;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @BelongsProject: pointer
 * @Author: 张宇若
 * @CreateTime: 2024-11-28  20:11
 * @Description: TODO
 * @Version: 1.0
 */ // Adapter Pattern for Compatibility with Old Interfaces
class ImageAdapter {
    private Image image;

    public ImageAdapter(Image image) {
        this.image = image;
    }

    public BufferedImage toBufferedImage() {
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        return bufferedImage;
    }
}
