package org.wzace.ezplayer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wzace.ezplayer.cache.LocalCache;
import org.wzace.ezplayer.config.AppConfig;
import org.wzace.ezplayer.config.AppConfigFileHandler;
import org.wzace.ezplayer.controller.PageEnum;

import java.io.IOException;

/**
 * @description: Application startup class
 * @author: wangzhe
 * @date: 2024/12/1 9:26
 * @Version: 1.0
 */
public class App extends Application {

    private static final Logger log = LoggerFactory.getLogger(App.class);

    private static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        log.info("app start");
        AppConfigFileHandler.getInstance().start();
        AppConfig appConfig = LocalCache.getInstance().getAppConfig();
        if (appConfig.getOpacity() != null) {
            stage.setOpacity(appConfig.getOpacity());
        }

        Scene scene = new Scene(FXMLLoader.load(App.class.getResource(PageEnum.HomePage.fileName)));
        stage.setScene(scene);
        stage.setAlwaysOnTop(true); // 确保它在其他窗口之上
        stage.show();
    }

    public static Stage getStage() {
        return stage;
    }

    public static void main(String[] args) {
        launch();
    }

}