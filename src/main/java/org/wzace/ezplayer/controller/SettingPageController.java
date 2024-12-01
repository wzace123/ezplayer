package org.wzace.ezplayer.controller;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wzace.ezplayer.cache.LocalAudioFileScanner;
import org.wzace.ezplayer.cache.LocalCache;
import org.wzace.ezplayer.config.AppConfig;
import org.wzace.ezplayer.config.AppConfigFileHandler;
import org.wzace.ezplayer.config.AppConfigObject;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * @description: Setting Page Controller
 * @author: wangzhe
 * @date: 2024/12/1 9:30
 * @Version: 1.0
 */
public class SettingPageController {

    private static final Logger log = LoggerFactory.getLogger(SettingPageController.class);

    @FXML
    private Text directoryPathText;

    @FXML
    private Slider opacitySetting;

    @FXML
    public void initialize() throws IOException {

        AppConfig appConfig = LocalCache.getInstance().getAppConfig();

        opacitySetting.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                Stage stage = (Stage) opacitySetting.getScene().getWindow();
                if (!Objects.isNull(stage)) {
                    stage.setOpacity(newValue.doubleValue());
                }
            }
        });

        Platform.runLater(() -> {
            if (appConfig.getLocalDirectory() != null) {
                directoryPathText.setText(appConfig.getLocalDirectory());
            }
            if (appConfig.getOpacity() != null) {
                opacitySetting.setValue(appConfig.getOpacity());
            }
        });

    }

    @FXML
    private void settingExitButtonOnAction(ActionEvent event) throws IOException {
        PageUtil.open(event, PageEnum.HomePage.fileName);
    }

    @FXML
    protected void handleDirectoryChoose() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("选择一个目录");
        // 设置初始目录
        String localDirectory = LocalCache.getInstance().getAppConfig().getLocalDirectory();
        directoryChooser.setInitialDirectory(new File(Objects.isNull(localDirectory) ? "C:/" : localDirectory));
        // 显示对话框并获取选中的目录
        File selectedDirectory = directoryChooser.showDialog((Stage) directoryPathText.getScene().getWindow());
        if (selectedDirectory != null) {
            // 更新文本内容以显示选择的目录路径
            directoryPathText.setText(selectedDirectory.getAbsolutePath());
        } else {
            directoryPathText.setText("没有选择任何目录");
        }
    }



    @FXML
    private void settingSaveButtonOnAction(ActionEvent event) throws IOException {
        AppConfigObject appConfig = new AppConfigObject();

        boolean isDirectory = new File(directoryPathText.getText()).isDirectory();
        if (isDirectory) {
            appConfig.setLocalDirectory(directoryPathText.getText());
        }
        appConfig.setOpacity(opacitySetting.getValue());
        AppConfigFileHandler.getInstance().writeFile(appConfig);

        if (isDirectory) {
            CompletableFuture.supplyAsync(() -> {
                return LocalAudioFileScanner.doScan(directoryPathText.getText());
            }).thenAccept(list -> {
                LocalCache.getInstance().init(appConfig, list);
            }).whenComplete ((result, e) -> {
                if (e != null) {
                    log.error("配置保存异常！", e);
                }
            });
        }

        PageUtil.open(event, PageEnum.HomePage.fileName);
    }
}
