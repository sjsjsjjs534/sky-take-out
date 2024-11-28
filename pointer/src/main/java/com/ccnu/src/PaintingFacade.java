package com.ccnu.src;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Stack;

class PaintingFacade {
    private DrawingBoard board;
    private Stack<Command> commandStack;
    private Stack<Command> redoStack;

    public PaintingFacade() {
        board = new DrawingBoard();
        commandStack = new Stack<>();
        redoStack = new Stack<>();
    }

    public void setColor(Color color) {
        ApplicationState.getInstance().setCurrentColor(color);
    }

    public void setStrokeWidth(int strokeWidth) {
        ApplicationState.getInstance().setCurrentStrokeWidth(strokeWidth);
    }

    public void setTool(String toolName) {
        switch (toolName) {
            case "Pencil":
                board.setState(new PencilState(board));
                ApplicationState.getInstance().setCurrentStrategy(new PencilDrawingStrategy());
                break;
            case "Line":
                board.setState(new ShapeState(board, new LineDrawingStrategy()));
                break;
            case "Rectangle":
                board.setState(new ShapeState(board, new RectangleDrawingStrategy()));
                break;
            case "Ellipse":
                board.setState(new ShapeState(board, new EllipseDrawingStrategy()));
                break;
            case "Freehand":
                board.setState(new FreehandState(board));
                ApplicationState.getInstance().setCurrentStrategy(new PencilDrawingStrategy());
                break;
            case "Chalk":
                ApplicationState.getInstance().setCurrentStrategy(new ChalkDrawingStrategy());
                break;
            case "Brush":
                ApplicationState.getInstance().setCurrentStrategy(new BrushDrawingStrategy());
                break;
        }
    }

    public void undo() {
        if (!board.getCommandStack().isEmpty()) {
            Command command = board.getCommandStack().pop();
            command.undo();
            board.getRedoStack().push(command);
        }
    }

    public void redo() {
        if (!board.getRedoStack().isEmpty()) {
            // Clear the drawing board before redrawing
            board.clear();

            // Re-execute all commands in commandStack
            for (Command cmd : commandStack) {
                cmd.execute();
            }

            // Execute the next command from redoStack and push it back to commandStack
            Command command = board.getRedoStack().pop();
            command.execute();
            board.getCommandStack().push(command);

            // Repaint the board to reflect changes
            board.repaint();
        }
    }

    public void saveToFile(String filename) {
        try (FileOutputStream fileOut = new FileOutputStream(filename);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(board.getDrawables());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void loadFromFile(String filename) {
        try (FileInputStream fileIn = new FileInputStream(filename);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            board.setDrawables((ArrayList<Drawable>) in.readObject());
            board.repaint();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public void exportToImage(String filename, String format) {
        try {
            BufferedImage image = new BufferedImage(board.getWidth(), board.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = image.createGraphics();
            board.printAll(g2d);
            g2d.dispose();
            ImageIO.write(image, format, new File(filename));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public JPanel getPanel() {
        return board;
    }
}