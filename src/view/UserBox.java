package view;

import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class UserBox extends GameView {

    static String answer;

    public static String display() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Change Password");
        window.setMinWidth(250);
        Label label = new Label();
        label.setText("Choose another users:");

        Button button = new Button("OK");


        ListView<String> userList = new ListView<>(manager.getOfflineUsers());
        userList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        button.setOnAction(event -> {
            answer = userList.getSelectionModel().getSelectedItem();
            window.close();
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(30, 30, 30, 30));
        layout.getStylesheets().add("view/style/main.css");
        layout.getChildren().addAll(label, userList, button);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return answer;
    }
}
