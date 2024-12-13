package org.wzace.ezplayer.manager;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * @className: EzplayerI18nManager
 * @author: wangzhe
 * @date: 2024/12/12 16:06
 * @Version: 1.0
 * @description:
 */
public class EzplayerI18nManager {

    private static EzplayerI18nManager i18nManager;

    private ResourceBundle resourceBundle;

    private EzplayerI18nManager() {

    }

    public static EzplayerI18nManager getInstance() {
        if (i18nManager == null) {
            synchronized (EzplayerI18nManager.class) {
                if (i18nManager==null) {
                    i18nManager = new EzplayerI18nManager();
                }
            }
        }
        return i18nManager;
    }

    public void setLocale(Locale locale) {
        Objects.requireNonNull(locale, "setLocale can not be null");
        this.resourceBundle = ResourceBundle.getBundle("org.wzace.ezplayer.i18n.MessagesBundle", locale);
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

}
