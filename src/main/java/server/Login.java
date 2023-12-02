package server;

import com.mongodb.client.*;

import javafx.scene.control.TextField;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Sorts.descending;

public class Login {
    private static String userID;

    public static boolean loginAccount(String username, String password) {
        String uri = "mongodb+srv://aditijain:cse110project@cluster0.yu0exzy.mongodb.net/?retryWrites=true&w=majority";
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase PantryPalDB = mongoClient.getDatabase("PantryPal");
        MongoCollection<Document> accountsCollection = PantryPalDB.getCollection("accounts");

        // find one document with Filters.eq()
        Document user = accountsCollection.find(and(eq("username", username), eq("password", password))).first();

        if (user != null) {
            System.out.println(user.toJson());
            System.out.println("Success");
            userID = user.getObjectId("_id").toString();
            System.out.println("User ID: " + userID);
            return true;
        }
        return false;
    }

    public static String getID() {
        return userID;
    }
}