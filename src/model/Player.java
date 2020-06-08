package model;

import model.User;

public class Player {
    private User user;
    private char color;
    boolean moved = false;

    long undoNumber;
    boolean undoThisTurn;

    public Player(User user, char color, long undoNumber) {
        this.user = user;
        this.color = color;
        this.undoNumber = undoNumber;

    }


    public User getUser() {
        return user;
    }

    public char getColor() {
        return color;
    }

    public String getColorString() {
        return (color == 'w') ? "white" : "black";
    }

    public boolean hasMoved() {
        return moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    public String getName() {
        return user.getUsername();
    }

    public long getUndoNumber() {
        return undoNumber;
    }

    public boolean usedUndoThisTurn() {
        return undoThisTurn;
    }

    public void resetUndoThisTurn() {
        undoThisTurn = false;
    }

    public void addUndo() {
        moved = false;
        undoThisTurn = true;
        undoNumber--;
    }
}
