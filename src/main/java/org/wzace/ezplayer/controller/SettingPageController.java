package org.wzace.ezplayer.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wzace.ezplayer.App;
import org.wzace.ezplayer.cache.LocalAudioFileScanner;
import org.wzace.ezplayer.cache.LocalCache;
import org.wzace.ezplayer.config.AppConfig;
import org.wzace.ezplayer.config.AppConfigFileHandler;
import org.wzace.ezplayer.config.AppConfigObject;
import org.wzace.ezplayer.enums.LanguageEnum;
import org.wzace.ezplayer.enums.PageEnum;
import org.wzace.ezplayer.manager.EzplayerI18nManager;
import org.wzace.ezplayer.manager.EzplayerSceneManager;

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
    private Label directoryPathLabel;

    @FXML
    private Slider opacitySetting;

    @FXML
    private VBox maskVbox;

    @FXML
    private CheckBox alwaysOnTop;

    @FXML
    private ChoiceBox<LanguageEnum> selectedLanguage;

    private static final SettingPageController instance = new SettingPageController();

    private SettingPageController() {}

    public static SettingPageController getInstance() {
        return instance;
    }

    @FXML
    public void initialize() {
        opacitySetting.valueProperty().addListener((observableValue, oldValue, newValue) -> App.getStage().setOpacity(newValue.doubleValue()));

        selectedLanguage.getItems().addAll(LanguageEnum.SIMPLIFIED_CHINESE, LanguageEnum.US);
        selectedLanguage.setConverter(new StringConverter<>() {
            @Override
            public String toString(LanguageEnum languageEnum) {
                if (languageEnum == null) return null;
                try {
                    return EzplayerI18nManager.getInstance().getResourceBundle().getString(languageEnum.name());
                } catch (Exception e) {
                    return languageEnum.name();
                }
            }

            @Override
            public LanguageEnum fromString(String string) {
                if (string == null) return null;
                return LanguageEnum.getEnum(string);
            }
        });

        Platform.runLater(() -> {
            AppConfig appConfig = LocalCache.getInstance().getAppConfig();
            if (appConfig.getLocalDirectory() != null) {
                directoryPathLabel.setText(appConfig.getLocalDirectory());
            }
            opacitySetting.setValue(appConfig.getOpacity());
            alwaysOnTop.setSelected(appConfig.getAlwaysOnTop());
            selectedLanguage.setValue(appConfig.getLanguage());
        });

    }

    @FXML
    private void settingExitButtonOnAction() throws IOException {
        AppConfig appConfig = LocalCache.getInstance().getAppConfig();
        if (!Objects.equals(directoryPathLabel.getText(), appConfig.getLocalDirectory())) {
            directoryPathLabel.setText(appConfig.getLocalDirectory());
        }

        Stage stage = App.getStage();
        if (Double.compare(stage.getOpacity(), appConfig.getOpacity()) != 0) {
            stage.setOpacity(appConfig.getOpacity());
            opacitySetting.setValue(appConfig.getOpacity());
        }

        if (Boolean.compare(alwaysOnTop.isSelected(), appConfig.getAlwaysOnTop()) != 0) {
            alwaysOnTop.setSelected(appConfig.getAlwaysOnTop());
        }

        if (appConfig.getLanguage() != selectedLanguage.getValue()) {
            selectedLanguage.setValue(appConfig.getLanguage());
        }

        PageUtil.open(PageEnum.HomePage);
    }

    @FXML
    protected void handleDirectoryChoose() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        // 设置初始目录
        String localDirectory = LocalCache.getInstance().getAppConfig().getLocalDirectory();
        directoryChooser.setInitialDirectory(new File(Objects.isNull(localDirectory) ? "C:/" : localDirectory));
        // 显示对话框并获取选中的目录
        File selectedDirectory = directoryChooser.showDialog(directoryPathLabel.getScene().getWindow());
        if (selectedDirectory != null) {
            // 更新文本内容以显示选择的目录路径
            directoryPathLabel.setText(selectedDirectory.getAbsolutePath());
        }
    }

    @FXML
    private void settingsSaveButtonOnAction() {
        maskVbox.setVisible(true);

        AppConfigObject appConfig = new AppConfigObject();
        boolean isDirectory = new File(directoryPathLabel.getText()).isDirectory();
        if (isDirectory) {
            appConfig.setLocalDirectory(directoryPathLabel.getText());
        }
        appConfig.setOpacity(opacitySetting.getValue());
        appConfig.setAlwaysOnTop(alwaysOnTop.isSelected());
        appConfig.setLanguage(selectedLanguage.getValue());

        if (LocalCache.getInstance().getAppConfig().getLanguage() != appConfig.getLanguage()) {
            EzplayerSceneManager.getInstance().setGlobalLanguage(appConfig.getLanguage().getLocale());
            selectedLanguage.getItems().clear();
            selectedLanguage.getItems().addAll(LanguageEnum.SIMPLIFIED_CHINESE, LanguageEnum.US);
            selectedLanguage.setValue(appConfig.getLanguage());
        }

        Stage stage = App.getStage();
        stage.setOpacity(appConfig.getOpacity());
        stage.setAlwaysOnTop(appConfig.getAlwaysOnTop());

        String directoryPath = directoryPathLabel.getText();
        CompletableFuture.runAsync(() -> {
            AppConfigFileHandler.getInstance().writeFile(appConfig);
            LocalCache.getInstance().init(appConfig, Objects.isNull(directoryPath) ? null : LocalAudioFileScanner.doScan(directoryPath));
        }).whenComplete ((result, e) -> {
            if (e != null) {
                log.error("save settings exception!", e);
            } else {
                Platform.runLater(() -> {
                    maskVbox.setVisible(false);
                    try {
                        PageUtil.open(PageEnum.HomePage);
                        HomePageController.getInstance().searchButtonOnAction();
                    } catch (IOException ex) {
                        log.error("go to homepage exception!", ex);
                    }
                });
            }
        });
    }
}
