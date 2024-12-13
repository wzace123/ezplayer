package org.wzace.ezplayer.config;

import org.wzace.ezplayer.enums.LanguageEnum;

/**
 * @description: App Config
 * @author: wangzhe
 * @date: 2024/12/1 9:32
 * @Version: 1.0
 */
public class AppConfig {

    /**
     * 本地文件目录
     */
    private String localDirectory;

    /**
     * 透明度
     */
    private Double opacity;

    private Boolean alwaysOnTop;

    private LanguageEnum language;

    public AppConfig() {
    }

    public String getLocalDirectory() {
        return localDirectory;
    }

    public Double getOpacity() {
        return opacity;
    }

    public Boolean getAlwaysOnTop() {
        return alwaysOnTop;
    }

    public LanguageEnum getLanguage() {
        return language;
    }

    public void update(AppConfigObject appConfig) {
        this.localDirectory = appConfig.getLocalDirectory();
        this.opacity = appConfig.getOpacity();
        this.alwaysOnTop = appConfig.getAlwaysOnTop();
        this.language = appConfig.getLanguage();
    }

    public void clear() {
        this.localDirectory = null;
        this.opacity = null;
        this.alwaysOnTop = null;
        this.language = null;
    }
}
