package com.ccnu.src;

import java.awt.event.MouseEvent;

class FreehandTool implements Tool {
    @Override
    public void useTool(DrawingBoard board, MouseEvent e) {
        board.setState(new FreehandState(board));
    }
}