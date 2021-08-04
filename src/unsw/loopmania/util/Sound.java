package unsw.loopmania.util;

import java.nio.file.Paths;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.util.ArrayList;

public class Sound {
    public static ArrayList<Sound> soundList = new ArrayList<>();

    MediaPlayer player;
    double volume;

    static final double EFFECT_VOLUME = 0.15;

    public Sound(String name, double volume) {
        this.volume = volume;
        String s = "src/music/" + name;
        Media song = new Media(Paths.get(s).toUri().toString());
        player = new MediaPlayer(song);
        player.setVolume(volume);
        player.setCycleCount(MediaPlayer.INDEFINITE);
        soundList.add(this);
    }

    public void play() {
        player.play();
    }

    public void switchSong(String name) {
        player.stop();
        boolean isMuted = (player.getVolume() == 0);
        String s = "src/music/" + name;
        Media song = new Media(Paths.get(s).toUri().toString());
        player = new MediaPlayer(song);
        if (isMuted) {
            player.setVolume(0);
        } else {
            player.setVolume(volume);
        }
        player.setCycleCount(MediaPlayer.INDEFINITE);
        player.play();
    }

    public void toggleSound() {
        Sound.playSound("mouse_click.mp3");
        if (player.getVolume() != 0) {
            player.setVolume(0);
        } else {
            player.setVolume(volume);
        }
    }

    // static methods for playing sound effect
    static public void playSound(String name) {
        MediaPlayer bgmPlayer;
        String s = "src/music/" + name;
        Media song = new Media(Paths.get(s).toUri().toString());
        bgmPlayer = new MediaPlayer(song);
        bgmPlayer.setVolume(EFFECT_VOLUME);
        bgmPlayer.setCycleCount(1);
        bgmPlayer.play();
    }

    static public void playSound(String name, double volume) {
        MediaPlayer bgmPlayer;
        String s = "src/music/" + name;
        Media song = new Media(Paths.get(s).toUri().toString());
        bgmPlayer = new MediaPlayer(song);
        bgmPlayer.setVolume(volume);
        bgmPlayer.setCycleCount(1);
        bgmPlayer.play();
    }
}
