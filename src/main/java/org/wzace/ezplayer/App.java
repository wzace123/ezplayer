package org.wzace.ezplayer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @description: Application startup class
 * @author: wangzhe
 * @date: 2024/12/1 9:26
 * @Version: 1.0
 */
public class App extends Application {

    private static final Logger log = LoggerFactory.getLogger(App.class);

    @Override
    public void start(Stage stage) throws IOException {
        log.info("app start");
        Scene scene = new Scene(FXMLLoader.load(App.class.getResource("HomePage.fxml")));
        stage.setScene(scene);
        stage.setAlwaysOnTop(true); // 确保它在其他窗口之上
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}