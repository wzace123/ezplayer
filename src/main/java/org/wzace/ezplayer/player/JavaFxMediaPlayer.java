package org.wzace.ezplayer.player;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description: JavaFx MediaPlayer wrapper
 * @author: wangzhe
 * @date: 2024/12/1 9:28
 * @Version: 1.0
 */
public class JavaFxMediaPlayer {

    private static final Logger log = LoggerFactory.getLogger(JavaFxMediaPlayer.class);
    private static final JavaFxMediaPlayer instance = new JavaFxMediaPlayer(); // 单例
    private static MediaPlayer player;

    private JavaFxMediaPlayer() {}

    public static JavaFxMediaPlayer getInstance() {
        return instance;
    }

    public void play(Media media) {
        stop();
        player = new MediaPlayer(media);
        player.setOnError(() -> {
            log.error("MediaPlayer error! message:{}", player.getError().getLocalizedMessage());
        });
        player.play();
    }

    public void stop() {
        if (player != null) {
            player.stop();
            player = null; // Reset the player
        }
    }

}
