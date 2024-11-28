package com.ccnu.src;

import java.awt.event.MouseEvent;

/**
 * @BelongsProject: pointer
 * @Author: 张宇若
 * @CreateTime: 2024-11-28  20:12
 * @Description: TODO
 * @Version: 1.0
 */
class ShapeTool implements Tool {
    private DrawingStrategy strategy;

    public ShapeTool(DrawingStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public void useTool(DrawingBoard board, MouseEvent e) {
        board.setState(new ShapeState(board, strategy));
    }
}
