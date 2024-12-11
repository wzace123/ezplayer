package org.wzace.ezplayer.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.wzace.ezplayer.App;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: Page Util
 * @author: wangzhe
 * @date: 2024/12/1 9:31
 * @Version: 1.0
 */
public class PageUtil {

    private static final Map<PageEnum, Scene> SCENE_MAP = new ConcurrentHashMap<>();

    public static void open(PageEnum pageEnum) throws IOException {
        Objects.requireNonNull(pageEnum);
        Stage stage = App.getStage();
        Objects.requireNonNull(stage);
        stage.setScene(getScene(pageEnum));
        stage.show();
    }

    public static FXMLLoader getFXMLLoader(PageEnum pageEnum) {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(App.class.getResource(pageEnum.fileName)));
        loader.setControllerFactory(t -> {
            if (t.equals(HomePageController.class)) {
                return HomePageController.getInstance();
            } else if (t.equals(SettingPageController.class)) {
                return SettingPageController.getInstance();
            }
            throw new RuntimeException("Unknown class, Please check controller factory");
        });
        return loader;
    }

    public static Scene getScene(PageEnum pageEnum) throws IOException {
        Objects.requireNonNull(pageEnum);
        Scene scene;
        if ((scene = SCENE_MAP.get(pageEnum)) == null) {
            FXMLLoader loader = getFXMLLoader(pageEnum);
            Parent parent = loader.load();
            Objects.requireNonNull(parent);
            scene = new Scene(parent);
            SCENE_MAP.put(pageEnum, scene);
        }
        return scene;
    }
}
