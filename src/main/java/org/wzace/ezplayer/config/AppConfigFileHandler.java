package org.wzace.ezplayer.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wzace.ezplayer.cache.LocalAudioFileScanner;
import org.wzace.ezplayer.cache.LocalCache;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AppConfigFileHandler {

    private static final Logger log = LoggerFactory.getLogger(AppConfigFileHandler.class);

    private static final AppConfigFileHandler INSTANCE = new AppConfigFileHandler();

    private final String fileName;
    private final String dirPath;
    private final Gson gson;

    private AppConfigFileHandler() {
        this.fileName = "AppConfig.json";
        this.dirPath = System.getProperty("user.home") + "\\.ezplayer";
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public static AppConfigFileHandler getInstance() {
        return INSTANCE;
    }

    public void start() throws IOException {
        // 创建 Path 对象
        Path path = Paths.get(dirPath);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
            log.info("目录已创建:{}", dirPath);
        }

        File configFile =  new File(dirPath, fileName);
        if (!configFile.exists()) {
            configFile.createNewFile();
            writeFile(new AppConfigObject());
        } else {
            AppConfigObject appConfig = readFile();
            if (appConfig != null && appConfig.getLocalDirectory() != null) {
                LocalCache.getInstance().init(appConfig, LocalAudioFileScanner.doScan(appConfig.getLocalDirectory()));
            }
        }
    }

    public void writeFile(AppConfigObject appConfigObject) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(dirPath, fileName), StandardCharsets.UTF_8, false))) {
            writer.write(gson.toJson(appConfigObject));
        } catch (IOException e) {
            log.error("写入文件异常!", e);
        }
    }

    public AppConfigObject readFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(dirPath, fileName), StandardCharsets.UTF_8))) {
            return gson.fromJson(reader, AppConfigObject.class);
        } catch (IOException e) {
            log.error("读取文件异常!", e);
        }
        return null;
    }
}
