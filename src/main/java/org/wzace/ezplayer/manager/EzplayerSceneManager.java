package org.wzace.ezplayer.manager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.wzace.ezplayer.App;
import org.wzace.ezplayer.controller.HomePageController;
import org.wzace.ezplayer.enums.PageEnum;
import org.wzace.ezplayer.controller.SettingPageController;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @className: EzplayerSceneManager
 * @author: wangzhe
 * @date: 2024/12/12 16:52
 * @Version: 1.0
 * @description:
 */
public class EzplayerSceneManager {

    private static EzplayerSceneManager sceneManager;

    private final EzplayerI18nManager i18nManager;

    private final Map<PageEnum, Scene> sceneMap;

    private EzplayerSceneManager() {
        this.sceneMap = new ConcurrentHashMap<>();
        this.i18nManager = EzplayerI18nManager.getInstance();
    }

    public static EzplayerSceneManager getInstance() {
        if (sceneManager == null) {
            synchronized (EzplayerSceneManager.class) {
                if (sceneManager==null) {
                    sceneManager = new EzplayerSceneManager();
                }
            }
        }
        return sceneManager;
    }

    private FXMLLoader createFXMLLoader(PageEnum pageEnum) {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(App.class.getResource(pageEnum.fileName)), i18nManager.getResourceBundle());
        loader.setControllerFactory(t -> {
            if (t.equals(HomePageController.class)) {
                return HomePageController.getInstance();
            } else if (t.equals(SettingPageController.class)) {
                return SettingPageController.getInstance();
            }
            throw new RuntimeException("Unknown class, Please check controller factory");
        });
        return loader;
    }

    public Scene getScene(PageEnum pageEnum) throws IOException {
        Objects.requireNonNull(pageEnum);
        Scene scene;
        if ((scene = sceneMap.get(pageEnum)) == null) {
            FXMLLoader loader = createFXMLLoader(pageEnum);
            Parent parent = loader.load();
            Objects.requireNonNull(parent);
            scene = new Scene(parent);
            sceneMap.put(pageEnum, scene);
        }
        return scene;
    }

    public void setGlobalLanguage(Locale locale) {
        i18nManager.setLocale(locale);
        for (Scene scene : sceneMap.values()) {
            handleI18nNodes(scene.getRoot());
        }
    }

    private void handleI18nNodes(Parent parent) {
        for (Node node : parent.getChildrenUnmodifiable()) {
            Class<? extends Node> clazz = node.getClass();
            try {
                Method getTextMethod = clazz.getMethod("getText");
                if (getTextMethod.getReturnType() == String.class) {
                    String text = (String) getTextMethod.invoke(node);
                    if (text != null && text.length() > 1) {
                        String value = i18nManager.getResourceBundle().getString(node.getId());
                        Method setTextMethod = clazz.getMethod("setText", String.class);
                        setTextMethod.invoke(node, value);
                    }
                }
            } catch (Exception e) {

            }

            if (node instanceof Parent) {
                handleI18nNodes((Parent) node);
            }
        }
    }

}
