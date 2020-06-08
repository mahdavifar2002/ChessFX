package view;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.net.URISyntaxException;

public class SoundPlayer extends Thread {

    String path;
    boolean repeat;

    public SoundPlayer(String path, boolean repeat) {
        this.path = path;
        this.repeat = repeat;
    }

    @Override
    public void run() {
        try {
            AudioClip sound = new AudioClip(ChessView.class.getResource(path).toURI().toString());
            if (repeat) {
                sound.setVolume(0.3);
                sound.setCycleCount(AudioClip.INDEFINITE);
            }
            sound.play();
        } catch (
                URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
