package org.wzace.ezplayer.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.wzace.ezplayer.App;

import java.io.IOException;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * @description: Page Util
 * @author: wangzhe
 * @date: 2024/12/1 9:31
 * @Version: 1.0
 */
public class PageUtil {

    public static void open(PageEnum pageEnum) throws IOException {
        open(pageEnum, null);
    }

    public static <T> void open(PageEnum pageEnum, Consumer<T> consumer) throws IOException {
        Stage stage = App.getStage();
        Objects.requireNonNull(stage);
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(App.class.getResource(pageEnum.fileName)));
        Parent root = loader.load();
        if (consumer != null) {
            consumer.accept(loader.getController());
        }
        stage.setScene(new Scene(root));
        stage.show();
    }
}
