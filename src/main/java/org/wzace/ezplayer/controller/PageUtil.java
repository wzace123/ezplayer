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

/**
 * @description: Page Util
 * @author: wangzhe
 * @date: 2024/12/1 9:31
 * @Version: 1.0
 */
public class PageUtil {

    public static void open(ActionEvent event, String page) throws IOException {
        Objects.requireNonNull(event);
        Node node = (Node) event.getSource();
        Objects.requireNonNull(node);
        Scene scene = node.getScene();
        Objects.requireNonNull(scene);
        Stage stage = (Stage) scene.getWindow();
        Objects.requireNonNull(stage);
        Parent root = FXMLLoader.load(Objects.requireNonNull(App.class.getResource(page)));
        stage.setScene(new Scene(root));
        stage.show();
    }
}
