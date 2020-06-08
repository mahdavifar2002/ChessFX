package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;
import model.piece.Piece;

import java.awt.*;
import java.util.*;

public class Manager {

    private ArrayList<User> users = new ArrayList<>();
    private User userLoggedIn;
    private Game game;
    private Point select;
    private boolean gameOver;
    private String result;

    public boolean isInvalidFormat(String string) {
        return !string.matches("\\w+");
    }

    public User getUserByName(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username))
                return user;
        }
        return null;
    }

    public ObservableList<String> getOfflineUsers() {
        ObservableList<String> usernames = FXCollections.observableArrayList();
        for (User user : users) {
            if (!user.equals(userLoggedIn))
                usernames.add(user.getUsername());
        }
        return usernames;
    }

    public void addUser(String username, String password) throws Exception {
        if (isInvalidFormat(username) || isInvalidFormat(password))
            throw new Exception("Invalid Format");
        if (getUserByName(username) != null)
            throw new Exception("Username is already taken");
        users.add(new User(username, password));
    }

    public void changePassword(String username, String oldValue, String newValue) throws Exception {
        checkUserValidation(username, oldValue);
        if (isInvalidFormat(newValue))
            throw new Exception("Invalid Format");
        getUserByName(username).setPassword(newValue);

    }

    public void removeUser(String username, String password) throws Exception {
        checkUserValidation(username, password);
        removeUser(getUserByName(username));
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public void loginUser(String username, String password) throws Exception {
        checkUserValidation(username, password);
        login(getUserByName(username));
    }

    private void checkUserValidation(String username, String password) throws Exception {
        if (isInvalidFormat(username) || isInvalidFormat(password))
            throw new Exception("Invalid Format");
        User user = getUserByName(username);
        if (user == null)
            throw new Exception("Username not found");
        if (!user.getPassword().equals(password))
            throw new Exception("Incorrect password");
    }

    public void login(User user) {
        this.userLoggedIn = user;
    }


    public void printUsers() {
        Collections.sort(users);
        for (User user : users) {
            System.out.println(user.getUsername());
        }
    }

    public void printScores() {
        ArrayList<ScoreItem> scores = new ArrayList<>();
        for (User user : users) {
            scores.add(new ScoreItem(user));
        }
        scores.sort(Collections.reverseOrder());
        for (ScoreItem score : scores) {
            System.out.println(score.toString());
        }
    }

    public ObservableList<ScoreItem> getScores() {
        ObservableList<ScoreItem> scores = FXCollections.observableArrayList();
        for (User user : users) {
            scores.add(new ScoreItem(user));
        }
        scores.sort(Collections.reverseOrder());
        return scores;
    }

    public User getUserLoggedIn() {
        return userLoggedIn;
    }

    public void startNewGame(String anotherUsername, long limit, long undo) throws Exception {
        if (getUserByName(anotherUsername) == null)
            throw new Exception("Choose a competitor");
        game = new Game(userLoggedIn, getUserByName(anotherUsername), limit, undo);
        select = null;
        gameOver = false;
    }

    public void showBoard() {
        for (int i = 7; i >= 0; i--) {
            for (int j = 0; j <= 7; j++) {
                if (game.getPiece(i, j) == null)
                    System.out.print("  |");
                else
                    System.out.print(game.getPiece(i, j).getName() + "|");
            }
            System.out.println();
        }
    }

    public Piece[][] getBoard() {
        return game.getBoard();
    }

    public ArrayList<Point> getAvailableMoves(Point source) {
        ArrayList<Point> result = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Point target = new Point(i, j);
                if (game.canMove(source, target))
                    result.add(target);
            }
        }
        return result;
    }

    public void select(Point point) throws Exception {
        if (invalidCoordination(point)) {
            throw new Exception("Wrong coordination");
        }
        Piece piece = game.getPiece(point);
        if (piece != null && piece.getColor() != game.getTurnPlayer().getColor()) {
            throw new Exception("You can only select one of your pieces");
        }
        if (piece == null) {
            throw new Exception("no piece on this spot");
        }
        select = new Point(point);
        System.out.println("selected");
    }

    public void deselect() {
        if (select == null) {
            System.out.println("no piece is selected");
            return;
        }
        select = null;
        System.out.println("deselected");
    }

    public boolean hasMovedAlready() {
        return game.getTurnPlayer().hasMoved();
    }

    public void move(Point target) throws Exception {
        if (invalidCoordination(target)) {
            throw new Exception("Wrong coordination");
        }
        if (hasMovedAlready()) {
            throw new Exception("You have already moved");
        }
        if (select == null) {
            throw new Exception("Do not have any selected piece");
        }
        if (!game.canMove(select, target)) {
            throw new Exception("Cannot move to the spot");
        }
        if (game.getPiece(target) != null) {
            System.out.println("rival piece destroyed");
        } else System.out.println("moved");

        game.makeMove(select, target);
    }

    private boolean invalidCoordination(Point p) {
        return p.x > 7 || p.x < 0 || p.y > 7 || p.y < 0;
    }

    public boolean waitForMove() {
        return !game.getTurnPlayer().hasMoved();
    }

    public void switchTurn() throws Exception {
        if (waitForMove()) {
            throw new Exception("you must move then proceed to next turn");
        }
        if (game.kingIsKilled()) {
            finishGame(3, 0);
        } else if (game.getMoves().size() == game.getLimit()) {
            finishGame(1, 1);
        } else {
            game.switchTurn();
            select = null;
        }
    }

    public void showTurn() {
        System.out.print("it is player " + game.getTurnPlayer().getName() + " turn with color ");
        System.out.println(game.getTurnPlayer().getColorString());
    }

    public void showMoves(boolean all) {
        ArrayList<Move> moves = game.getMoves();
        for (Move move : moves) {
            if (all || move.getPlayer() == game.getTurnPlayer())
                System.out.println(move.toString());
        }
    }

    public ArrayList<Move> getMoves() {
        return game.getMoves();
    }

    public Move getLastMove() {
        return game.getMoves().get(game.getMoves().size() - 1);
    }

    public void showKilled(boolean all) {
        ArrayList<Piece> killedPieces = game.getKilledPieces();
        for (Piece piece : killedPieces) {
            if (all || piece.getColor() == game.getTurnPlayer().getColor())
                System.out.println(piece.getName() + " killed in spot "
                        + (piece.getPoint().x + 1) + "," + (piece.getPoint().y + 1));
        }
    }

    public void showUndoNumber() {
        System.out.println("you have " + game.getTurnPlayer().getUndoNumber() + " undo moves");
    }

    public Move undo() throws Exception {
        if (game.getRestPlayer().getUndoNumber() == 0) {
            throw new Exception(game.getRestPlayer().getColorString().toUpperCase()
                    + " cannot undo anymore");
        }
        if (game.getMoves().isEmpty()) {
            throw new Exception("You must move before undo");
        }
//        if (game.getTurnPlayer().usedUndoThisTurn()) {
//            System.out.println("You have used your undo for this turn");
//            return;
//        }
        Move move = getLastMove();

        game.undo();

        System.out.println("undo completed");

        return move;
    }

    public void finishGame(int turnPlayerScore, int restPlayerScore) {
        Player turnPlayer = game.getTurnPlayer();
        Player restPlayer = game.getRestPlayer();
        if (turnPlayerScore > restPlayerScore) {
            result = ("Player " + turnPlayer.getName() + " with color " + turnPlayer.getColorString() + " won!");
            turnPlayer.getUser().addWins();
            restPlayer.getUser().addLooses();
        }
        if (turnPlayerScore < restPlayerScore) {
            result = ("Player " + restPlayer.getName() + " with color " + restPlayer.getColorString() + " won!");
            turnPlayer.getUser().addLooses();
            restPlayer.getUser().addWins();
        }
        if (turnPlayerScore == restPlayerScore) {
            result = ("Draw!");
            turnPlayer.getUser().addDraws();
            restPlayer.getUser().addDraws();
        }

        game.getTurnPlayer().getUser().addScore(turnPlayerScore);
        game.getRestPlayer().getUser().addScore(restPlayerScore);
        gameOver = true;
        //game = null;
    }

    public boolean gameIsOver() {
        return gameOver;
    }

    public String getResult() {
        return result;
    }

    public void resign() {
        finishGame(-1, 2);
    }
}
