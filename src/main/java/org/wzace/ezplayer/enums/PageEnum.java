package org.wzace.ezplayer.enums;

/**
 * @description: Page Enum
 * @author: wangzhe
 * @date: 2024/12/1 9:31
 * @Version: 1.0
 */
public enum PageEnum {

    HomePage("HomePage.fxml"),
    SettingPage("SettingPage.fxml");

    public final String fileName;

    PageEnum(String fileName) {
        this.fileName = fileName;
    }
}
