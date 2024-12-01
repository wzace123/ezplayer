module org.wzace.ezplayer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires com.google.gson;
    requires ch.qos.logback.classic;
    requires ch.qos.logback.core;
    requires org.slf4j;
    requires java.naming;


    exports org.wzace.ezplayer;
    exports org.wzace.ezplayer.config;
    opens org.wzace.ezplayer.config to com.google.gson;
    exports org.wzace.ezplayer.cache;
    exports org.wzace.ezplayer.controller;
    exports org.wzace.ezplayer.player;
    opens org.wzace.ezplayer.controller to javafx.fxml;
}