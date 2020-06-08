import javafx.application.Application;
import javafx.stage.Stage;
import view.ChessView;
import view.GameView;
import view.LoginView;
import view.SoundPlayer;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("ChessFX");
        primaryStage.setMaximized(true);

        new SoundPlayer("/music/background.mp3", true).start();
        GameView.setStage(primaryStage);
        new LoginView().setPane();

        primaryStage.show();
    }
}
