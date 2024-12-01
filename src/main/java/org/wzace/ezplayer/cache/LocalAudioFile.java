package org.wzace.ezplayer.cache;

import java.io.Serializable;

public class LocalAudioFile implements Serializable {

    private String fileName;

    private String fileExtension;

    private String absolutePath;

    public LocalAudioFile() {
    }

    public LocalAudioFile(String fileName, String fileExtension, String absolutePath) {
        this.fileName = fileName;
        this.fileExtension = fileExtension;
        this.absolutePath = absolutePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }
}
