package net.xevy.chat;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import javax.xml.validation.Schema;

import org.bson.Document;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import net.xevy.*;
import net.xevy.MongoDB.Mongodb;

public class ChatGui implements Initializable {
    private ChatClient client;
    private static ArrayList<String> pepe = new ArrayList<String>();
    @FXML
    static ObservableList<String> observableList = FXCollections.observableArrayList();

    @FXML
    Button enviar;

    @FXML
    TextArea texto;

    @FXML
    ListView<String> vista = new ListView(observableList);

    @FXML
    private Label lblOut;

    public static void setobservableList(Document doc) {
        System.out.println("set al observable list Texto:" + doc.toJson());
        // pepe.add(textoString);
        observableList.add(new String(String.valueOf(doc.get("chat"))));
        for (String string : observableList) {

            System.out.println(string);
            System.out.println("hola wey");
        }
    }

    public static ObservableList<String> getobservableList() {
        return observableList;
    }

    private void onMessageReceived(String message) {

        // pepe.add(message);
        // vista.getItems().add(message);
        observableList.add(new String(message));
        vista.scrollTo(observableList.size());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        vista.setItems(observableList);

        // FXCollections.observableArrayList(pepe);
        // observableList.setAll(pepe);

        // pepe.add("hola");
        try {

            Mongodb.findConversacion();
            vista.scrollTo(observableList.size());
        } catch (Exception e) {

            System.out.println(e.getCause());
        }

        try {
            this.client = new ChatClient("xevy.net", 8081, this::onMessageReceived);
            client.startClient();
        } catch (IOException e) {
            e.printStackTrace();

            System.exit(1);
        }

        /*
         * Stage.EXIT_ON_CLOSE(e -> {
         * // Send a departure message to the server
         * String departureMessage = name + " has left the chat.";
         * client.sendMessage(departureMessage);
         * 
         * // Delay to ensure the message is sent before exiting
         * try {
         * Thread.sleep(1000); // Wait for 1 second to ensure message is sent
         * } catch (InterruptedException ie) {
         * Thread.currentThread().interrupt();
         * }
         */
    }

    @FXML
    private void enviarPress() {
        // esto es lo que se envia
        String message = "[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] " + PrimaryController.nombre_txt
                + ": "
                + texto.getText();
        client.sendMessage(message);
        // pepe.add(message);
        // observableList.add(new String(message));
        texto.setText("");
        Mongodb.añadirConversacion(PrimaryController.nombre_txt, message);

       
    }

    @FXML
    private void enviarPress1() {
        /// esto añade una linea como bugg
        texto.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                String message = "[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] "
                        + PrimaryController.nombre_txt
                        + ": "
                        + texto.getText().trim();
                client.sendMessage(message);
                // pepe.add(message);
                // observableList.add(new String(message));
                texto.setText("");
                Mongodb.añadirConversacion(PrimaryController.nombre_txt, message);

                HBox hbox = new HBox();
                hbox.setAlignment(Pos.CENTER_RIGHT);
        
                hbox.setPadding(new Insets(5, 5, 5, 10));
                Text text = new Text(message);
                TextFlow textFlow = new TextFlow(text);
                textFlow.setStyle("-fx-color: rgb (239,242,255 "+
                        "-fx-background-color: rgb(15,125,242)" +
                        "-fx-background-radius: 20px");
                        
                textFlow.setPadding(new Insets(5, 10, 5, 10));
                text.setFill(Color.color(0.934, 0.945, 0.996));

                hbox.getChildren().add(textFlow);

                client.sendMessage(message);
            }
            
        });   
    }

}
