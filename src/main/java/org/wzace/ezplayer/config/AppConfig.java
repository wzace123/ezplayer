package org.wzace.ezplayer.config;

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

    public AppConfig() {
    }

    public String getLocalDirectory() {
        return localDirectory;
    }

    public Double getOpacity() {
        return opacity;
    }

    public void update(AppConfigObject appConfig) {
        this.localDirectory = appConfig.getLocalDirectory();
        this.opacity = appConfig.getOpacity();
    }

    public void clear() {
        this.localDirectory = null;
    }
}
