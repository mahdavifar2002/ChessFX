package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ChangePassBox {

    static String answer;

    public static String display() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Change Password");
        window.setMinWidth(250);
        Label label = new Label();
        label.setText("Please enter new password:");

        Button button = new Button("OK");

        TextField passInput = new PasswordField();
        passInput.setPromptText("password");

        button.setOnAction(event -> {
            answer = passInput.getText();
            window.close();
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(30, 30, 30, 30));
        layout.getStylesheets().add("view/style/main.css");
        layout.getChildren().addAll(label, passInput, button);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return answer;
    }
}
