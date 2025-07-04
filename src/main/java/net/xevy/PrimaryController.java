package net.xevy;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.xmldb.api.base.XMLDBException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import net.xevy.Existdb.Existdb;
import net.xevy.Existdb.Login;
import net.xevy.Security.Password;

public class PrimaryController implements Initializable {
    public static String nombre_txt = "";

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }

    @FXML
    private TextField nombre, correo;

    @FXML
    private PasswordField contrasena;

    @FXML
    Button primaryButton;

    @FXML
    Button primaryLoginButton;

    @FXML
    Hyperlink enlaceLogin;

    @FXML
    Label labelpass;

    @FXML
    private void registrarme() throws IOException {
        System.out.println("Nombre: " + nombre.getText());
        System.out.println("Correo: " + correo.getText());
        System.out.println("Contraseña: " + contrasena.getText().toString());
        nombre_txt = nombre.getText();
        String email = correo.getText();
        String passwd = contrasena.getText();
        String error = "";
        Existdb.loginExistenteExist(error, passwd);
        if (Validadores.validador(email)) {

        } else {
            error = "L'email és invàlid\n";
        }
        if (Validadores.validadordepassword(passwd)) {

        } else {
            error += "La contrasenya no es correcta";
        }
        labelpass.setText(error);
        if (!nombre_txt.isEmpty() && !email.isEmpty() && !passwd.isEmpty() && Validadores.validador(email)
                && Validadores.validadordepassword(passwd)) {
            Password passwdsec = new Password(passwd);
            Existdb.conneccionExistdb(nombre_txt, passwdsec.getPasswd(), email);
            try {

                Stage primaryStage = new Stage();
                BorderPane root2 = FXMLLoader.load(getClass().getResource("chat/Chat.fxml"));

                // Crear la escena
                Scene scene2 = new Scene(root2, 1000, 700);

                // Configurar la ventana principal
                primaryStage.setTitle("XevyChat");
                Image icon = new Image(getClass().getResourceAsStream("icon/icon.png"));
                primaryStage.getIcons().add(icon);
                primaryStage.setScene(scene2);
                primaryStage.show();
            } catch (Exception e) {

                String error2 = e.getLocalizedMessage() + e.getMessage() + e.getCause();
                System.out.println(e.getCause());
                Alert dialog = new Alert(AlertType.ERROR, error2, ButtonType.OK);
                dialog.show();

            }
            Stage registredStage = (Stage) primaryButton.getScene().getWindow();
            if (registredStage != null)
                registredStage.close();
            if (!nombre_txt.isEmpty() && !passwd.isEmpty()) {
                // App.setRoot("secondary");
            }
        }

    }

    @FXML
    private void login() throws IOException, XMLDBException {
        System.out.println("Nombre: " + nombre.getText());
        System.out.println("Contraseña: " + contrasena.getText().toString());
        nombre_txt = nombre.getText();
        String passwd = contrasena.getText();
        String errorLogin = "";
        Existdb.loginExistenteExist(nombre_txt, passwd);
        if (Validadores.validador(nombre_txt)) {

        } else {
            errorLogin = "L'email és invàlid\n";
        }
        if (Validadores.validadordepassword(passwd)) {

        } else {
            errorLogin += "La contrasenya no es correcta";
        }
        labelpass.setText(errorLogin);
        try {

            Stage primaryStage = new Stage();
            BorderPane root2 = FXMLLoader.load(getClass().getResource("signIn.fxml"));

            // Crear la escena
            Scene scene2 = new Scene(root2, 620, 480);

            // Configurar la ventana principal
            primaryStage.setTitle("XevyChat: Iniciar sessió");
            primaryStage.resizableProperty().setValue(Boolean.FALSE);
            Image icon = new Image(getClass().getResourceAsStream("icon/icon.png"));
            primaryStage.getIcons().add(icon);
            primaryStage.setScene(scene2);
            primaryStage.show();
        } catch (Exception e) {

            String error = e.getLocalizedMessage() + e.getMessage() + e.getCause();
            System.out.println(e.getCause());
            Alert dialog = new Alert(AlertType.ERROR, error, ButtonType.OK);
            dialog.show();

        }
        Stage registredStage = (Stage) enlaceLogin.getScene().getWindow();
        if (registredStage != null) {
            registredStage.close();
        }

        /*
        boolean loginExistente = Login.loginExistente(nombre_txt, passwd);

        if (loginExistente == true) {
            Stage primaryStage = new Stage();
            BorderPane root2 = FXMLLoader.load(getClass().getResource("chat/Chat.fxml"));

            // Crear la escena
            Scene scene2 = new Scene(root2, 1000, 700);

            // Configurar la ventana principal
            primaryStage.setTitle("XevyChat");
            Image icon = new Image(getClass().getResourceAsStream("icon/icon.png"));
            primaryStage.getIcons().add(icon);
            primaryStage.setScene(scene2);
            primaryStage.show();
        }
        */
    }
}
