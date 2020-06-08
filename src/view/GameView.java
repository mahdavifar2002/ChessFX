package view;

import controller.Manager;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public abstract class GameView {

    protected static Manager manager = new Manager();
    protected static Stage stage;

    public static void setStage(Stage stage) {
        GameView.stage = stage;
        stage.setScene(new Scene(new Pane()));

        try {
            manager.addUser("alexander", "alexander");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setScene(Pane pane) {
        stage.getScene().setRoot(pane);
    }

    public static void setScene(Scene scene) {
        stage.setScene(scene);
    }

}
