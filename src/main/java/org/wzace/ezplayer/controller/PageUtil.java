package org.wzace.ezplayer.controller;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.wzace.ezplayer.App;
import org.wzace.ezplayer.enums.PageEnum;
import org.wzace.ezplayer.manager.EzplayerSceneManager;

import java.io.IOException;
import java.util.Objects;

/**
 * @description: Page Util
 * @author: wangzhe
 * @date: 2024/12/1 9:31
 * @Version: 1.0
 */
public class PageUtil {

    public static void open(PageEnum pageEnum) throws IOException {
        Objects.requireNonNull(pageEnum);
        Stage stage = App.getStage();
        double width =  stage.getWidth();
        double height = stage.getHeight();
        Objects.requireNonNull(stage);
        Scene scene = EzplayerSceneManager.getInstance().getScene(pageEnum);
        stage.setScene(scene);
        stage.setWidth(width);
        stage.setHeight(height);
        stage.show();
    }
}
