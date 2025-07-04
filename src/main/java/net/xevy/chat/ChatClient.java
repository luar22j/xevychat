package net.xevy.chat;

import java.io.*;
import java.net.*;
import java.util.function.Consumer;

import javafx.application.Platform;

public class ChatClient {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Consumer<String> onMessageReceived;

    public ChatClient(String serverAddress, int serverPort, Consumer<String> onMessageReceived) throws IOException {
        this.socket = new Socket(serverAddress, serverPort);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.onMessageReceived = onMessageReceived;
    }

    public void sendMessage(String msg) {
        out.println(msg);
    }

    public void startClient() {

        new Thread(() -> {
            try {
                String line;
                while ((line = in.readLine()) != null) {
                    String pepe = line;
                    Platform.runLater(() -> onMessageReceived.accept(pepe));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start(); // Iniciar el hilo

    }
}