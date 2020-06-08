package model.piece;

public class King extends Piece {
    public King(int x, int y, char color) {
        super(x, y, color);
    }

    @Override
    public String getName() {
        return "K" + color;
    }

    @Override
    public boolean canMoveTo(int xPrime, int yPrime, Piece[][] board) {
        return Math.abs(x - xPrime) <= 1 && Math.abs(y - yPrime) <= 1;
    }
}
