package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import model.Move;
import model.piece.Piece;

import java.awt.*;
import java.util.ArrayList;

public class ChessView extends GameView {

    double squareSize = 50;
    Pane[][] chessCells;
    Label turnLabel = new Label("It's WHITE turn");
    Button undoButton = new Button("Undo");
    Button resignButton = new Button("Resign");
    GridPane boardPane = new GridPane();
    GridPane grid = new GridPane();

    public void setPane() {

        chessCells = getChessCells();
        setPhotos();

        boardPane.getStylesheets().add("view/style/chessboard.css");
        boardPane.setMinSize(10 * squareSize, 10 * squareSize);
        boardPane.setMaxSize(10 * squareSize, 10 * squareSize);
        boardPane.setPrefSize(10 * squareSize, 10 * squareSize);
        for (Pane[] row : chessCells) {
            boardPane.getChildren().addAll(row);
        }


        grid.setPadding(new Insets(40, 40, 40, 40));
        grid.setVgap(20);
        grid.setHgap(20);
        grid.getStylesheets().add("view/style/main.css");

        GridPane.setConstraints(boardPane, 0, 0, 10, 10);
        GridPane.setConstraints(turnLabel, 15, 4);
        GridPane.setConstraints(undoButton, 15, 5);
        GridPane.setConstraints(resignButton, 15, 6);

        turnLabel.setAlignment(Pos.CENTER);
        turnLabel.setStyle("-fx-font-size: 30px");

        resignButton.setOnAction(event -> {
            manager.resign();
            DialogBox.display("Game Over", manager.getResult());
            new MainMenuView().setPane();
        });

        undoButton.setOnAction(event -> {
            try {
                Move move = manager.undo();
                playMoveSound(move);
                switchTurnLabel();
                setPhotos();
            } catch (Exception e) {
                DialogBox.display("Undo Failed", e.getMessage());
            }
        });

        grid.setMinWidth(squareSize * 10 + 100);
        grid.setMinWidth(squareSize * 100 + 100);
        grid.getChildren().addAll(boardPane, turnLabel, undoButton, resignButton);
        GameView.setScene(grid);
    }

    private void setPhotos() {
        Piece[][] boardPieces = manager.getBoard();
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                Pane cell = chessCells[i][j];
                cell.getChildren().clear();

                if (boardPieces[8 - j][8 - i] != null) {
                    ImageView image = new ImageView("img/piece/" + boardPieces[8 - j][8 - i].getName() + ".png");
                    image.setFitHeight(squareSize);
                    image.setFitWidth(squareSize);
                    cell.getChildren().add(image);
                }
            }
        }
    }

    private Pane[][] getChessCells() {
        Pane[][] cells = new Pane[10][10];

        //Initialize
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                cells[i][j] = new Pane();
                cells[i][j].setStyle("-fx-alignment: center;");
                cells[i][j].setMinSize(squareSize, squareSize);
                cells[i][j].setMaxSize(squareSize, squareSize);
            }
        }

        //Set borders
        for (int i = 1; i < 9; i++) {
            cells[i][0].getChildren().add(new LabelCenter(Character.toString((char) ('A' + i - 1))));
            cells[i][9].getChildren().add(new LabelCenter(Character.toString((char) ('A' + i - 1))));
            cells[0][i].getChildren().add(new LabelCenter(Integer.toString(i)));
            cells[9][i].getChildren().add(new LabelCenter(Integer.toString(i)));
        }

        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                cells[i][j].getStylesheets().add("view/style/main.css");
                cells[i][j].setId((i + j) % 2 == 0 ? "white-rect" : "black-rect");
                int finalI = i;
                int finalJ = j;
                cells[i][j].setOnMouseClicked(event -> handleMouseClicked(finalI, finalJ));
            }
        }

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (cells[i][j] == null)
                    cells[i][j] = new Pane();
                GridPane.setConstraints(cells[i][j], i, j);
            }
        }

        return cells;
    }

    private void handleMouseClicked(int finalI, int finalJ) {
        try {
            if (manager.hasMovedAlready())
                return;
            manager.select(new ConvertPoint(finalI, finalJ));
            resetAvailableMoves();
            setAvailableMoves(finalI, finalJ);
        } catch (Exception e) {
            try {
                manager.move(new ConvertPoint(finalI, finalJ));
                this.switchTurn();
                if (manager.gameIsOver()) {
                    DialogBox.display("Game Over", manager.getResult());
                    new MainMenuView().setPane();
                }

            } catch (Exception e2) {
                System.out.println(e2.getMessage());
            }
            System.out.println(e.getMessage());
        }
    }

    private void playMoveSound(Move move) {
        String path;
        if (move.hasKilled())
            path = "/music/move-killed.mp3";
        else
            path = "/music/move.mp3";
        new SoundPlayer(path, false).start();
    }

    private void switchTurn() throws Exception {
        manager.switchTurn();
        playMoveSound(manager.getLastMove());
        setPhotos();
        switchTurnLabel();
    }

    private void switchTurnLabel() {
        turnLabel.setText(turnLabel.getText().contains("BLACK") ? "It's WHITE turn" : "It's BLACK turn");
    }

    private void setAvailableMoves(int i, int j) {
        ArrayList<Point> targets = manager.getAvailableMoves(new ConvertPoint(i, j));
        for (Point target : targets) {
            Circle circle = new SmallCircle();
            chessCells[8 - target.y][8 - target.x].getChildren().add(circle);
        }
    }

    private void resetAvailableMoves() {
        for (Pane[] row : chessCells) {
            for (Pane cell : row) {
                for (Node child : cell.getChildren()) {
                    if (child instanceof Circle) {
                        cell.getChildren().remove(child);
                        break;
                    }
                }
            }
        }
    }

    private class LabelCenter extends Label {
        public LabelCenter(String text) {
            super(text);
            setAlignment(Pos.CENTER);
            setLayoutX(squareSize / 2 - 10);
            setLayoutY(squareSize / 2 - 10);
            setAlignment(Pos.CENTER);
        }
    }

    private class SmallCircle extends Circle {
        public SmallCircle() {
            super(squareSize / 5);
            setStyle("-fx-opacity: 0.33");
            setCenterX(squareSize / 2);
            setCenterY(squareSize / 2);
        }
    }

    private static class ConvertPoint extends Point {
        public ConvertPoint(int x, int y) {
            super(8 - y, 8 - x);
        }

        public ConvertPoint(Point p) {
            super(8 - p.y, 8 - p.x);
        }
    }
}