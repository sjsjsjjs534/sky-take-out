package com.ccnu.src;

import com.ccnu.src.PaintingFacade;

import javax.swing.*;
import java.awt.*;

public class PaintProgram {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Paint Program");
        PaintingFacade facade = new PaintingFacade();

        JButton pencilButton = new JButton("Pencil");
        pencilButton.addActionListener(e -> facade.setTool("Pencil"));

        JButton lineButton = new JButton("Line");
        lineButton.addActionListener(e -> facade.setTool("Line"));

        JButton rectangleButton = new JButton("Rectangle");
        rectangleButton.addActionListener(e -> facade.setTool("Rectangle"));

        JButton ellipseButton = new JButton("Ellipse");
        ellipseButton.addActionListener(e -> facade.setTool("Ellipse"));

        JButton freehandButton = new JButton("Freehand");
        freehandButton.addActionListener(e -> facade.setTool("Freehand"));

        JButton undoButton = new JButton("Undo");
        undoButton.addActionListener(e -> facade.undo());

        JButton redoButton = new JButton("Redo");
        redoButton.addActionListener(e -> facade.redo());

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> facade.saveToFile("drawing.dat"));

        JButton loadButton = new JButton("Load");
        loadButton.addActionListener(e -> facade.loadFromFile("drawing.dat"));

        JButton exportJPEGButton = new JButton("Export JPEG");
        exportJPEGButton.addActionListener(e -> facade.exportToImage("drawing.jpg", "jpg"));

        JButton exportBMPButton = new JButton("Export BMP");
        exportBMPButton.addActionListener(e -> facade.exportToImage("drawing.bmp", "bmp"));

        JToolBar toolbar = new JToolBar();
        toolbar.add(pencilButton);
        toolbar.add(lineButton);
        toolbar.add(rectangleButton);
        toolbar.add(ellipseButton);
        toolbar.add(freehandButton);
        toolbar.addSeparator();
        toolbar.add(undoButton);
        toolbar.add(redoButton);
        toolbar.add(saveButton);
        toolbar.add(loadButton);
        toolbar.addSeparator();
        toolbar.add(exportJPEGButton);
        toolbar.add(exportBMPButton);

        frame.setLayout(new BorderLayout());
        frame.add(toolbar, BorderLayout.NORTH);
        frame.add(facade.getPanel(), BorderLayout.CENTER);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}