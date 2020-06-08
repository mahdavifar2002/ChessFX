package model.piece;

public class Rock extends Piece {
    public Rock(int x, int y, char color) {
        super(x, y, color);
    }

    @Override
    public String getName() {
        return "R" + color;
    }

    @Override
    public boolean canMoveTo(int xPrime, int yPrime, Piece[][] board) {
        if (x == xPrime) {
            for (int i = Math.min(y, yPrime) + 1; i < Math.max(y, yPrime); i++) {
                if (board[x][i] != null)
                    return false;
            }
            return true;
        }
        if (y == yPrime) {
            for (int i = Math.min(x, xPrime) + 1; i < Math.max(x, xPrime); i++) {
                if (board[i][y] != null)
                    return false;
            }
            return true;
        }
        return false;
    }
}
