package com.ccnu.src;

import java.awt.event.MouseEvent;

interface DrawingState {
    void handleMousePress(MouseEvent e);
    void handleMouseDrag(MouseEvent e);
    void handleMouseRelease(MouseEvent e);
}