package model.piece;

public class Knight extends Piece {
    public Knight(int x, int y, char color) {
        super(x, y, color);
    }

    @Override
    public String getName() {
        return "N" + color;
    }

    @Override
    public boolean canMoveTo(int xPrime, int yPrime, Piece[][] board) {
        if (Math.abs(x - xPrime) == 1 && Math.abs(y - yPrime) == 2)
            return true;
        if (Math.abs(x - xPrime) == 2 && Math.abs(y - yPrime) == 1)
            return true;
        return false;
    }
}
