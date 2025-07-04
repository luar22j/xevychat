package net.xevy.MongoDB;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import net.xevy.chat.ChatGui;

public class Mongodb {

    public static void añadirConversacion(String user, String Lastmensage) {
        try (MongoClient mongoClient = new MongoClient("xevy.net", 27017)) {

            try {

                MongoDatabase database = mongoClient.getDatabase("Chatsingpt");

                MongoCollection<Document> mongodoc = database.getCollection("Chats_privados");
                List<Document> mongostring = new ArrayList<>();
                mongostring.add(new Document("user", user).append("chat", Lastmensage).append("loqueosapetezca",
                        "you get hacked,buy bitcoins"));

                mongodoc.insertMany(mongostring);

            } catch (Exception e) {
                e.getCause();
            }

        }
    }

    public static void findConversacion() {
        try (MongoClient mongoClient = new MongoClient("xevy.net", 27017)) {

            try {

                MongoDatabase database = mongoClient.getDatabase("Chatsingpt");
                MongoCollection<Document> pepecol = database.getCollection("Chats_privados");
                // List<Document> documents = pepecol.find().into(new ArrayList<>());
                FindIterable<Document> pepdoc = pepecol.find();

                if (pepdoc == null) {
                    System.out.println("No results found.");
                } else {
                    // funcionaba hace nada auhora no lo se, creo que esto ban xF
                    for (Document doc : pepdoc) {
                        System.out.println("Obteniendo valor");

                        System.out.println(doc.get("chat"));
                        // String newChat = doc.get("chat").toString();
                        System.out.println(doc.get("user"));
                        System.out.println(doc.get("loqueosapetezca"));
                        ChatGui.setobservableList(doc);
                    }
                }
            } catch (Exception e) {
                e.getCause();
            }
        }
    }
}