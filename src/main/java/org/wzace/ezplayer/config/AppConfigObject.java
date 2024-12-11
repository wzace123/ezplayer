package org.wzace.ezplayer.config;

import java.io.Serializable;

/**
 * @description: App Config Object
 * @author: wangzhe
 * @date: 2024/12/1 9:32
 * @Version: 1.0
 */
public class AppConfigObject implements Serializable {

    /**
     * 本地文件目录
     */
    private String localDirectory;

    private Double opacity;

    private Boolean alwaysOnTop;

    public AppConfigObject() {
        this.opacity = 1d;
        this.alwaysOnTop = true;
    }

    public String getLocalDirectory() {
        return localDirectory;
    }

    public void setLocalDirectory(String localDirectory) {
        this.localDirectory = localDirectory;
    }

    public Double getOpacity() {
        return opacity;
    }

    public void setOpacity(Double opacity) {
        this.opacity = opacity;
    }

    public Boolean getAlwaysOnTop() {
        return alwaysOnTop;
    }

    public void setAlwaysOnTop(Boolean alwaysOnTop) {
        this.alwaysOnTop = alwaysOnTop;
    }
}
