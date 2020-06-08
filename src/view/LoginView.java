package view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class LoginView extends GameView {
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

        //Name label
        Label nameLabel = new Label("Username:");
        GridPane.setConstraints(nameLabel, 0, 2);

        //Name input
        TextField nameInput = new TextField();
        GridPane.setConstraints(nameInput, 1, 2, 2, 1);
        nameInput.setPromptText("username");
        //nameInput.setText("test");

        //Password label
        Label passLabel = new Label("Password:");
        GridPane.setConstraints(passLabel, 0, 3);

        //Password input
        TextField passInput = new PasswordField();
        GridPane.setConstraints(passInput, 1, 3, 2, 1);
        passInput.setPromptText("password");
        //passInput.setText("test");

        //Buttons
        Button signupButton = new Button("Sign Up");
        GridPane.setConstraints(signupButton, 1, 4);

        Button signinButton = new Button("Sign In");
        GridPane.setConstraints(signinButton, 2, 4);

        Button changePassButton = new Button("Change Password");
        GridPane.setConstraints(changePassButton, 1, 5);

        Button deleteAccountButton = new Button("Delete Account");
        GridPane.setConstraints(deleteAccountButton, 2, 5);

        //dialog label
        GridPane.setConstraints(dialogLabel, 1, 7, 2, 1);

        //OnAction methods
        signupButton.setOnAction(event -> {
            try {
                manager.addUser(nameInput.getText(), passInput.getText());
                updateDialog("Signed up successfully", "green");
            } catch (Exception e) {
                updateDialog(e.getMessage(), "red");
            }
        });
        changePassButton.setOnAction(event -> {
            String newPass = ChangePassBox.display();
            try {
                manager.changePassword(nameInput.getText(), passInput.getText(), newPass);
                passInput.setText(newPass);
                updateDialog("Password changed successfully", "green");
            } catch (Exception e) {
                updateDialog(e.getMessage(), "red");
            }
        });
        deleteAccountButton.setOnAction(event -> {
            try {
                manager.removeUser(nameInput.getText(), passInput.getText());
                updateDialog("User deleted successfully", "green");
            } catch (Exception e) {
                updateDialog(e.getMessage(), "red");
            }
        });
        signinButton.setOnAction(event -> {
            try {
                manager.loginUser(nameInput.getText(), passInput.getText());
                updateDialog("Signed in successfully", "green");
                new MainMenuView().setPane();
            } catch (Exception e) {
                updateDialog(e.getMessage(), "red");
            }
        });

        //Add objects to grid
        grid.getChildren().addAll(heading, nameLabel, nameInput, passLabel, passInput);
        grid.getChildren().addAll(signupButton, signinButton, changePassButton, deleteAccountButton);
        grid.getChildren().add(dialogLabel);

        setScene(grid);
    }

    private void updateDialog(String message, String color) {
        dialogLabel.setText(message);
        dialogLabel.setStyle("-fx-text-fill: " + color + "; -fx-font-weight: bold");
    }
}
