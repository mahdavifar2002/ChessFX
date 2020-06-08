package model.piece;

import java.awt.*;

public abstract class Piece {
    protected int x, y;
    protected char color;

    public char getColor() {
        return color;
    }

    public Piece(int x, int y, char color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public abstract String getName();

    public abstract boolean canMoveTo(int xPrime, int yPrime, Piece[][] board);

    public void moveTo(Point target) {
        this.x = target.x;
        this.y = target.y;
    }

    public static Piece makePiece(int x, int y, String name) {
        switch (name.charAt(0)) {
            case 'B':
                return new Bishop(x, y, name.charAt(1));
            case 'K':
                return new King(x, y, name.charAt(1));
            case 'N':
                return new Knight(x, y, name.charAt(1));
            case 'P':
                return new Pawn(x, y, name.charAt(1));
            case 'Q':
                return new Queen(x, y, name.charAt(1));
            case 'R':
                return new Rock(x, y, name.charAt(1));
            default:
                return null;
        }
    }

    public static Piece[] makePieceArray(int x, String[] names) {
        Piece[] pieceArray = new Piece[8];
        for (int y = 0; y < 8; y++)
            pieceArray[y] = makePiece(x, y, names[y]);
        return pieceArray;
    }

    public Point getPoint() {
        return new Point(x, y);
    }
}
