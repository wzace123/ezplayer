package org.wzace.ezplayer.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wzace.ezplayer.App;
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
    private VBox maskVbox;

    @FXML
    private CheckBox alwaysOnTop;

    private static final SettingPageController instance = new SettingPageController();

    private SettingPageController() {}

    public static SettingPageController getInstance() {
        return instance;
    }

    @FXML
    public void initialize() {
        opacitySetting.valueProperty().addListener((observableValue, oldValue, newValue) -> App.getStage().setOpacity(newValue.doubleValue()));

        Platform.runLater(() -> {
            AppConfig appConfig = LocalCache.getInstance().getAppConfig();
            if (appConfig.getLocalDirectory() != null) {
                directoryPathText.setText(appConfig.getLocalDirectory());
            }
            opacitySetting.setValue(appConfig.getOpacity());
            alwaysOnTop.setSelected(appConfig.getAlwaysOnTop());
        });

    }

    @FXML
    private void settingExitButtonOnAction() throws IOException {
        AppConfig appConfig = LocalCache.getInstance().getAppConfig();
        if (!Objects.equals(directoryPathText.getText(), appConfig.getLocalDirectory())) {
            directoryPathText.setText(appConfig.getLocalDirectory());
        }

        Stage stage = App.getStage();
        if (Double.compare(stage.getOpacity(), appConfig.getOpacity()) != 0) {
            stage.setOpacity(appConfig.getOpacity());
            opacitySetting.setValue(appConfig.getOpacity());
        }

        if (Boolean.compare(alwaysOnTop.isSelected(), appConfig.getAlwaysOnTop()) != 0) {
            alwaysOnTop.setSelected(appConfig.getAlwaysOnTop());
        }

        PageUtil.open(PageEnum.HomePage);
    }

    @FXML
    protected void handleDirectoryChoose() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("选择一个目录");
        // 设置初始目录
        String localDirectory = LocalCache.getInstance().getAppConfig().getLocalDirectory();
        directoryChooser.setInitialDirectory(new File(Objects.isNull(localDirectory) ? "C:/" : localDirectory));
        // 显示对话框并获取选中的目录
        File selectedDirectory = directoryChooser.showDialog(directoryPathText.getScene().getWindow());
        if (selectedDirectory != null) {
            // 更新文本内容以显示选择的目录路径
            directoryPathText.setText(selectedDirectory.getAbsolutePath());
        }
    }

    @FXML
    private void settingSaveButtonOnAction() {
        maskVbox.setVisible(true);

        AppConfigObject appConfig = new AppConfigObject();
        boolean isDirectory = new File(directoryPathText.getText()).isDirectory();
        if (isDirectory) {
            appConfig.setLocalDirectory(directoryPathText.getText());
        }
        appConfig.setOpacity(opacitySetting.getValue());
        appConfig.setAlwaysOnTop(alwaysOnTop.isSelected());

        Stage stage = App.getStage();
        stage.setOpacity(appConfig.getOpacity());
        stage.setAlwaysOnTop(appConfig.getAlwaysOnTop());

        String directoryPath = directoryPathText.getText();
        CompletableFuture.runAsync(() -> {
            AppConfigFileHandler.getInstance().writeFile(appConfig);
            LocalCache.getInstance().init(appConfig, Objects.isNull(directoryPath) ? null : LocalAudioFileScanner.doScan(directoryPath));
        }).whenComplete ((result, e) -> {
            if (e != null) {
                log.error("配置保存异常！", e);
            } else {
                Platform.runLater(() -> {
                    maskVbox.setVisible(false);
                    try {
                        PageUtil.open(PageEnum.HomePage);
                        HomePageController.getInstance().selectButtonOnAction();
                    } catch (IOException ex) {
                        log.error("跳转首页异常！", ex);
                    }
                });
            }
        });
    }
}
