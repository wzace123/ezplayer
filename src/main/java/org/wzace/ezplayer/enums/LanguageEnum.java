package org.wzace.ezplayer.enums;

import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

public enum LanguageEnum {

    SIMPLIFIED_CHINESE(Locale.SIMPLIFIED_CHINESE),
    US(Locale.US);

    private final Locale locale;

    LanguageEnum(Locale locale) {
        this.locale = locale;
    }

    public Locale getLocale() {
        return locale;
    }

    public static LanguageEnum getEnum(String enumName) {
        return Arrays.stream(LanguageEnum.values()).filter(t -> Objects.equals(enumName, t.name())).findFirst().orElseThrow();
    }
}
