package model.piece;

public class Bishop extends Piece {
    public Bishop(int x, int y, char color) {
        super(x, y, color);
    }

    @Override
    public String getName() {
        return "B" + color;
    }

    @Override
    public boolean canMoveTo(int xPrime, int yPrime, Piece[][] board) {
        if (Math.abs(x - xPrime) != Math.abs(y - yPrime))
            return false;
        int xStep = (x < xPrime) ? 1 : -1;
        int yStep = (y < yPrime) ? 1 : -1;
        for (int i = 1; i < Math.abs(x - xPrime); i++) {
            if (board[x + i*xStep][y + i*yStep] != null)
                return false;
        }
        return true;
    }
}
