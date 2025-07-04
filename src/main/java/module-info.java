module net.xevy {
    requires javafx.controls;
    requires javafx.fxml;
    requires mongo.java.driver;
    requires java.desktop;
    requires javafx.base;
    requires javafx.graphics;
    requires org.xmldb.api;
    requires exist.core;
    requires com.google.common;

    opens net.xevy to javafx.fxml, Existdb.Login;
    opens net.xevy.chat to javafx.fxml, net.xevy.MongoDB.Mongodb;
    opens net.xevy.MongoDB to javafx.fxml;

    exports net.xevy;
    exports net.xevy.chat;
    exports net.xevy.MongoDB;
    exports net.xevy.Existdb;
}