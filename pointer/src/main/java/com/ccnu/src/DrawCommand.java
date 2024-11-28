package com.ccnu.src;

class DrawCommand implements Command {
    private Drawable drawable;
    private DrawingBoard board;

    public DrawCommand(Drawable drawable, DrawingBoard board) {
        this.drawable = drawable;
        this.board = board;
    }

    @Override
    public void execute() {
        board.getDrawables().add(drawable);
        board.repaint();
    }

    @Override
    public void undo() {
        board.getDrawables().remove(drawable);
        board.repaint();
    }
}