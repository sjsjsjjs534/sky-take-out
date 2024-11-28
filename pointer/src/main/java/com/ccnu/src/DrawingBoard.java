package com.ccnu.src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Stack;
import javax.imageio.ImageIO;

class DrawingBoard extends JPanel implements DrawingListener {
    private ArrayList<Drawable> drawables;
    private Stack<Command> commandStack;
    private Stack<Command> redoStack;
    private DrawingState state;
    private Drawable currentShape;
    private FreehandLine currentFreehand;
    private Image backgroundImage;

    public DrawingBoard() {
        drawables = new ArrayList<>();
        commandStack = new Stack<>();
        redoStack = new Stack<>();
        setBackground(Color.WHITE);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                state.handleMousePress(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                state.handleMouseRelease(e);
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                state.handleMouseDrag(e);
            }
        });
        setState(new PencilState(this));
    }

    public void setState(DrawingState state) {
        this.state = state;
    }

    public void addDrawable(Drawable drawable) {
        Command command = new DrawCommand(drawable, this);
        command.execute();
        commandStack.push(command);
        redoStack.clear();
    }

    public void removeDrawable(Drawable drawable) {
        drawables.remove(drawable);
        repaint();
    }

    public void clear() {
        drawables.clear();
        commandStack.clear();
        redoStack.clear();
        repaint();
    }

    public void setBackground(Image image) {
        backgroundImage = image;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
        for (Drawable drawable : drawables) {
            drawable.draw(g);
        }
        if (currentShape != null) {
            currentShape.draw(g);
        }
        if (currentFreehand != null) {
            currentFreehand.draw(g);
        }
    }

    public void onDraw(Drawable drawable) {
        currentShape = drawable;
        repaint();
    }

    public void setCurrentFreehand(FreehandLine freehand) {
        this.currentFreehand = freehand;
    }

    public FreehandLine getCurrentFreehand() {
        return currentFreehand;
    }

    public Stack<Command> getCommandStack() {
        return commandStack;
    }

    public Stack<Command> getRedoStack() {
        return redoStack;
    }

    public ArrayList<Drawable> getDrawables() {
        return drawables;
    }

    public void setDrawables(ArrayList<Drawable> drawables) {
        this.drawables = drawables;
    }
}