package view;

import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class MainMenuView extends GameView {

    GridPane grid = new GridPane();
    Label dialogLabel = new Label();

    public void setPane() {
        grid.setPadding(new Insets(40, 40, 40, 40));
        grid.setVgap(20);
        grid.setHgap(20);
        grid.getStylesheets().add("view/style/main.css");

        //Heading
        Label heading = new Label("Welcome to ChessFX ™");
        GridPane.setConstraints(heading, 0, 0, 4, 1);
        heading.setId("h1");

        //Limit label
        Label limitLabel = new Label("Turn Limit:");
        GridPane.setConstraints(limitLabel, 0, 2);


        grid.setMaxSize(100.0, 100.0);
        grid.setMinSize(100.0, 100.0);

        //Name input
        TextField limitInput = new TextField();
        GridPane.setConstraints(limitInput, 1, 2);
        limitInput.setPromptText("leave blank for no limit");

        //Undo label
        Label undoLabel = new Label("Undo Limit:");
        GridPane.setConstraints(undoLabel, 0, 3);

        //Password input
        TextField undoInput = new TextField();
        GridPane.setConstraints(undoInput, 1, 3);
        undoInput.setPromptText("0");
        undoInput.setPromptText("leave blank for no limit");

        //Buttons
        Button newGameButton = new Button("New Game");
        GridPane.setConstraints(newGameButton, 1, 4);
        Button scoreboardButton = new Button("Scoreboard");
        GridPane.setConstraints(scoreboardButton, 1, 5);

        Button logoutButton = new Button("Logout");
        GridPane.setConstraints(logoutButton, 1, 6);

        //dialog label
        GridPane.setConstraints(dialogLabel, 1, 7, 2, 1);

        //OnAction methods
        scoreboardButton.setOnAction(event -> ScoreboardBox.display());
        logoutButton.setOnAction(event -> new LoginView().setPane());

        newGameButton.setOnAction(event -> {
            try {
                int limit = Integer.parseInt(0 + limitInput.getText());
                int undo = Integer.parseInt(0 + undoInput.getText());
                if (undo == 0)
                    undo = Integer.MAX_VALUE;
                if (manager.getOfflineUsers().isEmpty())
                    throw new Exception("Wait for other users to sign up");
                String anotherUsername = UserBox.display();
                manager.startNewGame(anotherUsername, limit, undo);
                new ChessView().setPane();
            } catch (NumberFormatException e) {
                updateDialog("Invalid input", "red");
            } catch (Exception e) {
                updateDialog(e.getMessage(), "red");
            }
        });

        //Add objects to grid
        grid.getChildren().addAll(heading, limitLabel, limitInput, undoLabel, undoInput);
        grid.getChildren().addAll(newGameButton, scoreboardButton, logoutButton, dialogLabel);

        GameView.setScene(grid);
    }

    private void updateDialog(String message, String color) {
        dialogLabel.setText(message);
        dialogLabel.setStyle("-fx-text-fill: " + color + "; -fx-font-weight: bold");
    }
}
