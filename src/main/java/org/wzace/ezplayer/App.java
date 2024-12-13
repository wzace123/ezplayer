package org.wzace.ezplayer;

import javafx.application.Application;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wzace.ezplayer.cache.LocalCache;
import org.wzace.ezplayer.config.AppConfig;
import org.wzace.ezplayer.config.AppConfigFileHandler;
import org.wzace.ezplayer.enums.PageEnum;
import org.wzace.ezplayer.manager.EzplayerI18nManager;
import org.wzace.ezplayer.manager.EzplayerSceneManager;

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
        App.stage = stage;
        log.info("app start");
        AppConfigFileHandler.getInstance().start();
        AppConfig appConfig = LocalCache.getInstance().getAppConfig();
        EzplayerI18nManager.getInstance().setLocale(appConfig.getLanguage().getLocale());

        stage.setOpacity(appConfig.getOpacity());
        stage.setScene(EzplayerSceneManager.getInstance().getScene(PageEnum.HomePage));
        stage.setAlwaysOnTop(appConfig.getAlwaysOnTop());
        stage.show();
    }

    public static Stage getStage() {
        return stage;
    }

    public static void main(String[] args) {
        launch();
    }

}