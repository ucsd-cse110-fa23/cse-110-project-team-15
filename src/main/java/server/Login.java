package server;

import com.mongodb.client.*;

import org.bson.Document;

import static com.mongodb.client.model.Filters.*;

import java.util.prefs.Preferences;

public class Login {
    private static String userID;


    public static boolean loginAccount(String username, String password, boolean autoLogin) {
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

            if (autoLogin){
                storeCredentials(username, password);
            }
            else {
                clearCredentials();
            }

            return true;
        }
        return false;
    }

    public static String getID() {
        return userID;
    }

    
    private static void storeCredentials(String username, String password) {
        // Use Preferences API to securely store credentials
        Preferences prefs = Preferences.userNodeForPackage(Login.class);
        prefs.put("username", username);
        prefs.put("password", password);
    }

    public static void clearCredentials() {
        // Use Preferences API to securely store credentials
        Preferences prefs = Preferences.userNodeForPackage(Login.class);
        prefs.remove("username");
        prefs.remove("password");
    }

    public static boolean attemptAutoLogin() {
        // Attempt automatic login using stored credentials
        Preferences prefs = Preferences.userNodeForPackage(Login.class);
        String storedUsername = prefs.get("username", null);
        String storedPassword = prefs.get("password", null);

        boolean autoLogin = storedUsername != null && storedPassword != null;

        return loginAccount(storedUsername, storedPassword, autoLogin);
    }
    
}