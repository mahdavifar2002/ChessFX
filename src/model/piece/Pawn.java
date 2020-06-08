package model.piece;

public class Pawn extends Piece {
    public Pawn(int x, int y, char color) {
        super(x, y, color);
    }

    @Override
    public String getName() {
        return "P" + color;
    }

    @Override
    public boolean canMoveTo(int xPrime, int yPrime, Piece[][] board) {
        if (color == 'w') {
            if (x == 1 && y == yPrime && xPrime == 3 && board[2][y] == null && board[3][y] == null)
                return true;

            if (Math.abs(yPrime - y) == 1 && xPrime - x == 1
                    && board[xPrime][yPrime] != null && board[xPrime][yPrime].color != color) {
                return true;
            }

            if (y == yPrime && xPrime - x == 1 && board[xPrime][yPrime] == null)
                return true;

            return false;
        } else {
            if (x == 6 && y == yPrime && xPrime == 4 && board[5][y] == null && board[4][y] == null)
                return true;

            if (Math.abs(yPrime - y) == 1 && xPrime - x == -1
                    && board[xPrime][yPrime] != null && board[xPrime][yPrime].color != color) {
                return true;
            }

            if (y == yPrime && xPrime - x == -1 && board[xPrime][yPrime] == null)
                return true;

            return false;
        }
    }
}