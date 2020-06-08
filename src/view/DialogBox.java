package view;

import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DialogBox {

    public static void display(String title, String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Game Over");
        window.setMinWidth(250);
        Label label = new Label();
        label.setText(message);

        Button button = new Button("OK");

        button.setOnAction(event -> window.close());

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(30, 30, 30, 30));
        layout.getStylesheets().add("view/style/main.css");
        layout.getChildren().addAll(label, button);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}
