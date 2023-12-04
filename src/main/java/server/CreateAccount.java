package server;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import static java.util.Arrays.asList;

import java.util.prefs.Preferences;

public class CreateAccount {

    public static void createAccount(String username, String password) {
        String uri = "mongodb+srv://aditijain:cse110project@cluster0.yu0exzy.mongodb.net/?retryWrites=true&w=majority";
        try (MongoClient mongoClient = MongoClients.create(uri)) {

            MongoDatabase PantryPalDB = mongoClient.getDatabase("PantryPal");
            MongoCollection<Document> accountsCollection = PantryPalDB.getCollection("accounts");

            Document user = new Document("_id", new ObjectId())
                    .append("username", username).append("password", password);
            accountsCollection.insertOne(user);

            // boolean autoLogin = false;

            if (username != null && password != null) {
                storeCredentials(username, password);
            }

            System.out.println("Account created for: " + username);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void storeCredentials(String username, String password) {
        // Use Preferences API to securely store credentials
        Preferences prefs = Preferences.userNodeForPackage(Login.class);
        prefs.put("username", username);
        prefs.put("password", password);
    }

    public static boolean attemptAutoLogin() {
        // Attempt automatic login using stored credentials
        Preferences prefs = Preferences.userNodeForPackage(Login.class);
        String storedUsername = prefs.get("username", null);
        String storedPassword = prefs.get("password", null);

        boolean autoLogin = storedUsername != null && storedPassword != null;

        return Login.loginAccount(storedUsername, storedPassword, autoLogin);
    }

}