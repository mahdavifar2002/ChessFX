package model;

import model.piece.Piece;

import java.awt.*;

public class Move {
    private Player player;
    private Point source;
    private Point target;
    private boolean killed;
    private Piece movedPiece;
    private Piece killedPiece;

    public Move(Player player, Point source, Point target, Piece movedPiece, Piece killedPiece) {
        this.player = player;
        this.source = source;
        this.target = target;
        this.movedPiece = movedPiece;
        this.killedPiece = killedPiece;
        killed = (killedPiece != null);
    }

    public Player getPlayer() {
        return player;
    }

    public Point getSource() {
        return source;
    }

    public Point getTarget() {
        return target;
    }

    public boolean hasKilled() {
        return killed;
    }

    public Piece getKilledPiece() {
        return killedPiece;
    }

    @Override
    public String toString() {
        if (killed)
            return movedPiece.getName() + " " + (source.x + 1) + "," + (source.y + 1)
                    + " to " + (target.x + 1) + "," + (target.y + 1)
                    + " destroyed " + killedPiece.getName();
        else
            return movedPiece.getName() + " " + (source.x + 1) + "," + (source.y + 1) +
                    " to " + (target.x + 1) + "," + (target.y + 1);
    }
}
