package org.wzace.ezplayer.cache;

import org.wzace.ezplayer.config.AppConfig;
import org.wzace.ezplayer.config.AppConfigObject;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @description: Local Cache
 * @author: wangzhe
 * @date: 2024/12/1 9:34
 * @Version: 1.0
 */
public class LocalCache {

    private static final LocalCache LOCAL_CACHE = new LocalCache();

    private static final AppConfig APP_CONFIG = new AppConfig();

    private static final Map<String, LocalAudioFile> LOCAL_FILE_MAP = new ConcurrentHashMap<>();

    private static final List<LocalAudioFile> LOCAL_FILE_LIST = new CopyOnWriteArrayList<>();

    private LocalCache() {}

    public static LocalCache getInstance() {
        return LOCAL_CACHE;
    }

    public void clear() {
        LOCAL_FILE_MAP.clear();
        LOCAL_FILE_LIST.clear();
        APP_CONFIG.clear();
    }

    public void init(AppConfigObject appConfig, List<LocalAudioFile> fileList) {
        clear();

        APP_CONFIG.update(appConfig);

        for (LocalAudioFile file : fileList) {
            LOCAL_FILE_MAP.put(file.getFileName(), file);
            LOCAL_FILE_LIST.add(file);
        }
    }

    public List<LocalAudioFile> getLocalFileList() {
        return LOCAL_FILE_LIST;
    }

    public AppConfig getAppConfig() {
        return APP_CONFIG;
    }
}
