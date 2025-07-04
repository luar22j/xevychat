package net.xevy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import net.xevy.Existdb.Existdb;

import java.io.IOException;

import net.xevy.Existdb.*;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @SuppressWarnings("exports")
    @Override
    public void start(Stage stage) throws IOException, InterruptedException {
        scene = new Scene(loadFXML("signUp"), 620, 480);
        stage.setTitle("XevyChat: Registrar-se");
        stage.setMaxHeight(480);
        stage.setMaxWidth(620);
        stage.resizableProperty().setValue(Boolean.FALSE);
        stage.setScene(scene);
        Image icon = new Image(getClass().getResourceAsStream("icon/icon.png"));
        stage.getIcons().add(icon);
        stage.show();
        try {
            System.out.println("cargando");

        } catch (Exception e) {
            System.out.println(e.getCause());
        }
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}