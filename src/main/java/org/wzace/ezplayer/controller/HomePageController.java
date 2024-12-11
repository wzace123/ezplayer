package org.wzace.ezplayer.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.util.Callback;
import org.wzace.ezplayer.cache.LocalAudioFile;
import org.wzace.ezplayer.cache.LocalCache;
import org.wzace.ezplayer.player.JavaFxMediaPlayer;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @description: Home Page Controller
 * @author: wangzhe
 * @date: 2024/12/1 9:30
 * @Version: 1.0
 */
public class HomePageController {

    private static final JavaFxMediaPlayer mediaPlayer = JavaFxMediaPlayer.getInstance();

    private static HomePageController instance = new HomePageController();

    @FXML
    private TextField selectText;

    @FXML
    private ListView<LocalAudioFile> selectVoiceList;

    private HomePageController() {}

    public static HomePageController getInstance() {
        return instance;
    }

    @FXML
    public void initialize() {

        selectVoiceList.setCellFactory(new Callback<ListView<LocalAudioFile>, ListCell<LocalAudioFile>>() {
            @Override
            public ListCell<LocalAudioFile> call(ListView<LocalAudioFile> param) {
                return new ListCell<LocalAudioFile>() {
                    @Override
                    protected void updateItem(LocalAudioFile item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            setText(item.getFileName());
                        }
                    }
                };
            }
        });

        Platform.runLater(() -> {
            selectButtonOnAction();
        });
    }

    @FXML
    public void selectButtonOnAction() {
        String queryCondition = selectText.getText();

        List<LocalAudioFile> matchingFiles = LocalCache.getInstance().getLocalFileList().stream()
                .filter(file -> Pattern.matches(".*" +queryCondition+ ".*", file.getFileName()))
                .toList();

        ObservableList<LocalAudioFile> list = FXCollections.observableArrayList(matchingFiles);
        selectVoiceList.setItems(list);
    }

    @FXML
    private void selectVoiceListOnMouseClicked(MouseEvent event) {
        LocalAudioFile localAudioFile = selectVoiceList.getSelectionModel().getSelectedItem();
        if (localAudioFile != null) {
            mediaPlayer.play(new Media(new File(localAudioFile.getAbsolutePath()).toURI().toString()));
        }
    }

    @FXML
    private void stoptButtonOnAction() {
        mediaPlayer.stop();
    }

    @FXML
    private void settingButtonOnAction(ActionEvent event) throws IOException {
        PageUtil.open(PageEnum.SettingPage);
    }
}
