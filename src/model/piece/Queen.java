package model.piece;

public class Queen extends Piece {
    public Queen(int x, int y, char color) {
        super(x, y, color);
    }

    @Override
    public String getName() {
        return "Q" + color;
    }

    @Override
    public boolean canMoveTo(int xPrime, int yPrime, Piece[][] board) {
        if (new Rock(x, y, color).canMoveTo(xPrime, yPrime, board)) return true;
        if (new Bishop(x, y, color).canMoveTo(xPrime, yPrime, board)) return true;
        return false;
    }
}
