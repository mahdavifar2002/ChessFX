package model;

import model.piece.*;

import java.awt.*;
import java.util.ArrayList;

public class Game {
    private Player[] players;
    private long limit;
    private Piece[][] board;
    int turn;
    private ArrayList<Piece> killedPieces;
    private ArrayList<Move> moves;

    public Game(User firstPlayer, User secondPlayer, long limit, long undo) {
        players = new Player[2];
        this.players[0] = new Player(firstPlayer, 'w', undo);
        this.players[1] = new Player(secondPlayer, 'b', undo);

        board = new Piece[8][8];
        killedPieces = new ArrayList<>();
        moves = new ArrayList<>();
        this.limit = limit;
        turn = 0;

        board[7] = Piece.makePieceArray(7, "Rb|Nb|Bb|Kb|Qb|Bb|Nb|Rb".split("\\|"));
        board[6] = Piece.makePieceArray(6, "Pb|Pb|Pb|Pb|Pb|Pb|Pb|Pb".split("\\|"));
        board[1] = Piece.makePieceArray(1, "Pw|Pw|Pw|Pw|Pw|Pw|Pw|Pw".split("\\|"));
        board[0] = Piece.makePieceArray(0, "Rw|Nw|Bw|Kw|Qw|Bw|Nw|Rw".split("\\|"));
    }

    public Player getTurnPlayer() {
        return players[turn];
    }

    public Player getRestPlayer() {
        return players[1 - turn];
    }

    public long getLimit() {
        return limit;
    }

    public Piece getPiece(int x, int y) {
        return board[x][y];
    }

    public Piece[][] getBoard() {
        return board;
    }

    public Piece getPiece(Point point) {
        return board[point.x][point.y];
    }

    public boolean canMove(Point source, Point target) {
        if (getPiece(target) != null)
            if (getPiece(source).getColor() == getPiece(target).getColor())
                return false;
        return getPiece(source).canMoveTo(target.x, target.y, board);
    }

    private void swapPiece(Point source, Point target) {
        if (getPiece(source) != null)
            getPiece(source).moveTo(target);
        if (getPiece(target) != null)
            getPiece(target).moveTo(source);
        Piece temp = board[source.x][source.y];
        board[source.x][source.y] = board[target.x][target.y];
        board[target.x][target.y] = temp;
    }

    public void makeMove(Point source, Point target) {
        moves.add(new Move(players[turn], source, target, getPiece(source), getPiece(target)));

        if (getPiece(target) != null) {
            killedPieces.add(getPiece(target));
            board[target.x][target.y] = null;
        }

        swapPiece(source, target);
        players[turn].setMoved(true);
    }

    public boolean kingIsKilled() {
        if (killedPieces.isEmpty())
            return false;
        return killedPieces.get(killedPieces.size() - 1).getName().charAt(0) == 'K';
    }

    public void switchTurn() {
        players[turn].setMoved(false);
        players[turn].resetUndoThisTurn();
        turn = 1 - turn;
    }

    public ArrayList<Move> getMoves() {
        return moves;
    }

    public ArrayList<Piece> getKilledPieces() {
        return killedPieces;
    }

    public void undo() {
        getRestPlayer().addUndo();
        Move move = moves.remove(moves.size() - 1);
        if (move.hasKilled()) {
            board[move.getSource().x][move.getSource().y] = move.getKilledPiece();
            killedPieces.remove(killedPieces.size() - 1);
        }
        swapPiece(move.getSource(), move.getTarget());
        switchTurn();
    }
}