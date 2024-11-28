package com.ccnu.src;


import java.awt.event.MouseEvent;
import java.io.Serializable;

interface Tool extends Serializable {
    void useTool(DrawingBoard board, MouseEvent e);
}