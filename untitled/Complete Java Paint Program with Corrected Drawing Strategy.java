import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Stack;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javax.imageio.ImageIO;

// Singleton Pattern for Application State Management
class ApplicationState {
    private static ApplicationState instance;
    private Color currentColor = Color.BLACK;
    private int currentStrokeWidth = 1;
    private String currentTool = "Pencil";
    private Image backgroundImage = null;

    private ApplicationState() {}

    public static synchronized ApplicationState getInstance() {
        if (instance == null) {
            instance = new ApplicationState();
        }
        return instance;
    }

    // Getters and Setters
    public Color getCurrentColor() { return currentColor; }
    public void setCurrentColor(Color color) { this.currentColor = color; }
    public int getCurrentStrokeWidth() { return currentStrokeWidth; }
    public void setCurrentStrokeWidth(int width) { this.currentStrokeWidth = width; }
    public String getCurrentTool() { return currentTool; }
    public void setCurrentTool(String tool) { this.currentTool = tool; }
    public Image getBackgroundImage() { return backgroundImage; }
    public void setBackgroundImage(Image image) { this.backgroundImage = image; }
}

// Observer Pattern for Drawing Updates
interface DrawingListener {
    void onDraw(Drawable drawable);
}

abstract class Drawable implements Serializable {
    protected Color color;
    protected int strokeWidth;

    public Drawable(Color color, int strokeWidth) {
        this.color = color;
        this.strokeWidth = strokeWidth;
    }

    public abstract void draw(Graphics g);

    public void accept(DrawingListener listener) {
        listener.onDraw(this);
    }
}

// Strategy Pattern for Different Drawing Tools
interface DrawingStrategy extends Serializable {
    Drawable createDrawable(Point start, Point end, Color color, int strokeWidth);
}

class LineDrawingStrategy implements DrawingStrategy {
    @Override
    public Drawable createDrawable(Point start, Point end, Color color, int strokeWidth) {
        return new Line(start, end, color, strokeWidth);
    }
}

class RectangleDrawingStrategy implements DrawingStrategy {
    @Override
    public Drawable createDrawable(Point start, Point end, Color color, int strokeWidth) {
        return new RectangleShape(start, end, color, strokeWidth);
    }
}

class EllipseDrawingStrategy implements DrawingStrategy {
    @Override
    public Drawable createDrawable(Point start, Point end, Color color, int strokeWidth) {
        return new EllipseShape(start, end, color, strokeWidth);
    }
}

class FreehandDrawingStrategy implements DrawingStrategy {
    @Override
    public Drawable createDrawable(Point start, Point end, Color color, int strokeWidth) {
        return new FreehandLine(start, end, color, strokeWidth);
    }
}

// Command Pattern for Undo/Redo Functionality
interface Command extends Serializable {
    void execute();
    void undo();
}

class DrawCommand implements Command {
    private Drawable drawable;
    private DrawingBoard board;

    public DrawCommand(Drawable drawable, DrawingBoard board) {
        this.drawable = drawable;
        this.board = board;
    }

    @Override
    public void execute() {
        board.addDrawable(drawable);
    }

    @Override
    public void undo() {
        board.removeDrawable(drawable);
    }
}

// Decorator Pattern for Adding New Features Without Changing Original Class Behavior
abstract class DrawableDecorator extends Drawable {
    protected Drawable decoratedDrawable;

    public DrawableDecorator(Drawable decoratedDrawable, Color color, int strokeWidth) {
        super(color, strokeWidth);
        this.decoratedDrawable = decoratedDrawable;
    }

    @Override
    public void draw(Graphics g) {
        decoratedDrawable.draw(g);
    }
}

class ThickDecorator extends DrawableDecorator {
    public ThickDecorator(Drawable decoratedDrawable, Color color, int strokeWidth) {
        super(decoratedDrawable, color, strokeWidth * 2);
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        Stroke oldStroke = g2d.getStroke();
        g2d.setStroke(new BasicStroke(strokeWidth));
        decoratedDrawable.draw(g2d);
        g2d.setStroke(oldStroke);
        g2d.dispose();
    }
}

// Adapter Pattern for Compatibility with Old Interfaces
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

// Proxy Pattern for Controlling Access to Real Object
class DrawingBoardProxy extends DrawingBoard {
    private DrawingBoard realBoard;

    public DrawingBoardProxy() {
        realBoard = new DrawingBoard();
    }

    @Override
    public void addDrawable(Drawable drawable) {
        realBoard.addDrawable(drawable);
    }

    @Override
    public void removeDrawable(Drawable drawable) {
        realBoard.removeDrawable(drawable);
    }

    @Override
    public void clear() {
        realBoard.clear();
    }

    @Override
    public void setBackground(Image image) {
        realBoard.setBackground(image);
    }

    @Override
    public void paintComponent(Graphics g) {
        realBoard.paintComponent(g);
    }
}

// Template Method Pattern for Defining Algorithm Skeleton
abstract class Shape extends Drawable {
    protected Point startPoint;
    protected Point endPoint;

    public Shape(Point startPoint, Point endPoint, Color color, int strokeWidth) {
        super(color, strokeWidth);
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    protected abstract void drawShape(Graphics g);

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(strokeWidth));
        drawShape(g2d);
        g2d.dispose();
    }
}

class Line extends Shape {
    public Line(Point startPoint, Point endPoint, Color color, int strokeWidth) {
        super(startPoint, endPoint, color, strokeWidth);
    }

    @Override
    protected void drawShape(Graphics g) {
        g.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
    }
}

class RectangleShape extends Shape {
    public RectangleShape(Point startPoint, Point endPoint, Color color, int strokeWidth) {
        super(startPoint, endPoint, color, strokeWidth);
    }

    @Override
    protected void drawShape(Graphics g) {
        int x = Math.min(startPoint.x, endPoint.x);
        int y = Math.min(startPoint.y, endPoint.y);
        int width = Math.abs(endPoint.x - startPoint.x);
        int height = Math.abs(endPoint.y - startPoint.y);
        g.drawRect(x, y, width, height);
    }
}

class EllipseShape extends Shape {
    public EllipseShape(Point startPoint, Point endPoint, Color color, int strokeWidth) {
        super(startPoint, endPoint, color, strokeWidth);
    }

    @Override
    protected void drawShape(Graphics g) {
        int x = Math.min(startPoint.x, endPoint.x);
        int y = Math.min(startPoint.y, endPoint.y);
        int width = Math.abs(endPoint.x - startPoint.x);
        int height = Math.abs(endPoint.y - startPoint.y);
        g.drawOval(x, y, width, height);
    }
}

class FreehandLine extends Drawable {
    private ArrayList<Point> points;

    public FreehandLine(Point start, Point end, Color color, int strokeWidth) {
        super(color, strokeWidth);
        points = new ArrayList<>();
        points.add(start);
        points.add(end);
    }

    public void addPoint(Point point) {
        points.add(point);
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(strokeWidth));

        for (int i = 1; i < points.size(); i++) {
            g2d.drawLine(points.get(i - 1).x, points.get(i - 1).y, points.get(i).x, points.get(i).y);
        }

        g2d.dispose();
    }
}

// Builder Pattern for Constructing Complex Objects Step by Step
class DrawingBoardBuilder {
    private DrawingBoard drawingBoard;

    public DrawingBoardBuilder() {
        drawingBoard = new DrawingBoard();
    }

    public DrawingBoardBuilder setColor(Color color) {
        ApplicationState.getInstance().setCurrentColor(color);
        return this;
    }

    public DrawingBoardBuilder setStrokeWidth(int strokeWidth) {
        ApplicationState.getInstance().setCurrentStrokeWidth(strokeWidth);
        return this;
    }

    public DrawingBoard build() {
        return drawingBoard;
    }
}

// State Pattern for Changing Behavior Based on Internal State
class DrawingState {
    protected DrawingBoard board;

    public DrawingState(DrawingBoard board) {
        this.board = board;
    }

    public abstract void handleMousePress(MouseEvent e);
    public abstract void handleMouseDrag(MouseEvent e);
    public abstract void handleMouseRelease(MouseEvent e);
}

class PencilState extends DrawingState {
    private Point lastPoint;

    public PencilState(DrawingBoard board) {
        super(board);
    }

    @Override
    public void handleMousePress(MouseEvent e) {
        lastPoint = e.getPoint();
        FreehandLine freehandLine = new FreehandLine(lastPoint, lastPoint,
                ApplicationState.getInstance().getCurrentColor(),
                ApplicationState.getInstance().getCurrentStrokeWidth());
        board.setCurrentFreehand(freehandLine);
        board.addDrawable(freehandLine);
    }

    @Override
    public void handleMouseDrag(MouseEvent e) {
        Point currentPoint = e.getPoint();
        board.getCurrentFreehand().addPoint(currentPoint);
        board.repaint();
    }

    @Override
    public void handleMouseRelease(MouseEvent e) {}
}

class ShapeState extends DrawingState {
    private Point startPoint;
    private Point endPoint;
    private DrawingStrategy strategy;

    public ShapeState(DrawingBoard board, DrawingStrategy strategy) {
        super(board);
        this.strategy = strategy;
    }

    @Override
    public void handleMousePress(MouseEvent e) {
        startPoint = e.getPoint();
    }

    @Override
    public void handleMouseDrag(MouseEvent e) {
        endPoint = e.getPoint();
        board.repaint();
    }

    @Override
    public void handleMouseRelease(MouseEvent e) {
        endPoint = e.getPoint();
        Drawable drawable = strategy.createDrawable(startPoint, endPoint,
                ApplicationState.getInstance().getCurrentColor(),
                ApplicationState.getInstance().getCurrentStrokeWidth());
        board.addDrawable(drawable);
        board.repaint();
    }
}

class FreehandState extends DrawingState {
    private Point lastPoint;
    private FreehandLine currentFreehand;

    public FreehandState(DrawingBoard board) {
        super(board);
    }

    @Override
    public void handleMousePress(MouseEvent e) {
        lastPoint = e.getPoint();
        currentFreehand = new FreehandLine(lastPoint, lastPoint,
                ApplicationState.getInstance().getCurrentColor(),
                ApplicationState.getInstance().getCurrentStrokeWidth());
        board.setCurrentFreehand(currentFreehand);
        board.addDrawable(currentFreehand);
    }

    @Override
    public void handleMouseDrag(MouseEvent e) {
        Point currentPoint = e.getPoint();
        board.getCurrentFreehand().addPoint(currentPoint);
        board.repaint();
    }

    @Override
    public void handleMouseRelease(MouseEvent e) {}
}

// Bridge Pattern for Separating Abstraction from Implementation
interface Tool extends Serializable {
    void useTool(DrawingBoard board, MouseEvent e);
}

class PenTool implements Tool {
    @Override
    public void useTool(DrawingBoard board, MouseEvent e) {
        board.setState(new PencilState(board));
    }
}

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

class FreehandTool implements Tool {
    @Override
    public void useTool(DrawingBoard board, MouseEvent e) {
        board.setState(new FreehandState(board));
    }
}

// Composite Pattern for Treating Individual Objects and Composites Uniformly
class DrawingGroup extends Drawable {
    private ArrayList<Drawable> children;

    public DrawingGroup(Color color, int strokeWidth) {
        super(color, strokeWidth);
        children = new ArrayList<>();
    }

    public void add(Drawable drawable) {
        children.add(drawable);
    }

    public void remove(Drawable drawable) {
        children.remove(drawable);
    }

    @Override
    public void draw(Graphics g) {
        for (Drawable child : children) {
            child.draw(g);
        }
    }
}

// Facade Pattern for Providing a Simplified Interface
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
                board.setTool(new PenTool());
                break;
            case "Line":
                board.setTool(new ShapeTool(new LineDrawingStrategy()));
                break;
            case "Rectangle":
                board.setTool(new ShapeTool(new RectangleDrawingStrategy()));
                break;
            case "Ellipse":
                board.setTool(new ShapeTool(new EllipseDrawingStrategy()));
                break;
            case "Freehand":
                board.setTool(new FreehandTool());
                break;
        }
    }

    public void undo() {
        if (!commandStack.isEmpty()) {
            Command command = commandStack.pop();
            command.undo();
            redoStack.push(command);
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            Command command = redoStack.pop();
            command.execute();
            commandStack.push(command);
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

class DrawingBoard extends JPanel implements Serializable {
    private ArrayList<Drawable> drawables;
    private DrawingState state;
    private Tool tool;
    private Image backgroundImage;
    private transient FreehandLine currentFreehand;
    private Stack<Command> commandStack;
    private Stack<Command> redoStack;

    public DrawingBoard() {
        drawables = new ArrayList<>();
        state = new PencilState(this);
        tool = new PenTool();
        commandStack = new Stack<>();
        redoStack = new Stack<>();
        setBackground(Color.WHITE);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                tool.useTool(DrawingBoard.this, e);
                state.handleMousePress(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                state.handleMouseRelease(e);
                if (currentFreehand != null) {
                    Command command = new DrawCommand(currentFreehand, DrawingBoard.this);
                    commandStack.push(command);
                    redoStack.clear();
                    currentFreehand = null;
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                state.handleMouseDrag(e);
            }
        });
    }

    public void setState(DrawingState newState) {
        this.state = newState;
    }

    public void setTool(Tool newTool) {
        this.tool = newTool;
    }

    public void addDrawable(Drawable drawable) {
        drawables.add(drawable);
        repaint();
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

    public void setCurrentFreehand(FreehandLine freehand) {
        this.currentFreehand = freehand;
    }

    public FreehandLine getCurrentFreehand() {
        return currentFreehand;
    }

    public ArrayList<Drawable> getDrawables() {
        return drawables;
    }

    public void setDrawables(ArrayList<Drawable> drawables) {
        this.drawables = drawables;
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
        if (state instanceof ShapeState && ((ShapeState) state).endPoint != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(ApplicationState.getInstance().getCurrentColor());
            g2d.setStroke(new BasicStroke(ApplicationState.getInstance().getCurrentStrokeWidth()));
            Point startPoint = ((ShapeState) state).startPoint;
            Point endPoint = ((ShapeState) state).endPoint;
            if (ApplicationState.getInstance().getCurrentTool().equals("Line")) {
                g2d.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
            } else if (ApplicationState.getInstance().getCurrentTool().equals("Rectangle")) {
                int x = Math.min(startPoint.x, endPoint.x);
                int y = Math.min(startPoint.y, endPoint.y);
                int width = Math.abs(endPoint.x - startPoint.x);
                int height = Math.abs(endPoint.y - startPoint.y);
                g2d.drawRect(x, y, width, height);
            } else if (ApplicationState.getInstance().getCurrentTool().equals("Ellipse")) {
                int x = Math.min(startPoint.x, endPoint.x);
                int y = Math.min(startPoint.y, endPoint.y);
                int width = Math.abs(endPoint.x - startPoint.x);
                int height = Math.abs(endPoint.y - startPoint.y);
                g2d.drawOval(x, y, width, height);
            }
            g2d.dispose();
        }
    }
}

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



