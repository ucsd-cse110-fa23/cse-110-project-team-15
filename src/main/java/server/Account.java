package server;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

import java.sql.Timestamp;
import org.bson.conversions.Bson;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.sun.net.httpserver.*;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import java.util.prefs.Preferences;

import static java.util.Arrays.asList;
import java.util.ArrayList;
import java.util.List;

public class Account implements HttpHandler {
    private static volatile String userID;
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        boolean success = true;
        if ("POST".equals(exchange.getRequestMethod())) {
            String requestUri = exchange.getRequestURI().toString();
            // Obtain the input stream from the request
            InputStream requestBody = exchange.getRequestBody();
            InputStreamReader isr = new InputStreamReader(requestBody);
            BufferedReader br = new BufferedReader(isr);

            // Read the JSON data from the request
            StringBuilder requestData = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                requestData.append(line);
            }

            // Parse the JSON data
            JSONObject json = new JSONObject(requestData.toString());

            // Extract necessary data from the JSON
            String username = json.getString("username");
            String password = json.getString("password");
            String autoLogin = json.getString("autoLogin");

            System.out.println("URI: " + requestUri);
            System.out.println("Name: " + username);
            if (requestUri.contains("create")) {
                userID = createAccount(username, password);
            } else if (requestUri.contains("login")) {
                System.out.println(autoLogin);
                userID = loginAccount(username, password, Boolean.parseBoolean(autoLogin));
            }
        }
        //Sending back response to the client
        System.out.println(userID);
        if (userID != null) {
            exchange.sendResponseHeaders(200, userID.length());
            OutputStream outStream = exchange.getResponseBody();
            outStream.write(userID.getBytes());
            outStream.close();
        } else {
            exchange.sendResponseHeaders(200, "".length());
            OutputStream outStream = exchange.getResponseBody();
            outStream.write("".getBytes());
            outStream.close();
        }
    }

    public static String createAccount(String username, String password) {
        String uri = "mongodb+srv://sraswan:pandapanda777@cluster0.fefhkg8.mongodb.net/?retryWrites=true&w=majority";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            userID = null;
            MongoDatabase PantryPalDB = mongoClient.getDatabase("PantryPal");
            MongoCollection<Document> accountsCollection = PantryPalDB.getCollection("accounts");
            Document u = accountsCollection.find(and(eq("username", username), eq("password", password))).first();
            Document user;
            if (u == null) {
                user = new Document("_id", new ObjectId())
                    .append("username", username).append("password", password);
                accountsCollection.insertOne(user);
                userID = user.getObjectId("_id").toString();
                System.out.println("Account created for: " + username);
                return userID;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static String loginAccount(String username, String password, boolean autoLogin) {
        String uri = "mongodb+srv://sraswan:pandapanda777@cluster0.fefhkg8.mongodb.net/?retryWrites=true&w=majority";
        MongoClient mongoClient = MongoClients.create(uri);
        System.out.println("Auto:"+autoLogin);
        MongoDatabase PantryPalDB = mongoClient.getDatabase("PantryPal");
        MongoCollection<Document> accountsCollection = PantryPalDB.getCollection("accounts");
        
        System.out.println("U:"+username);
        System.out.println("p:"+password);
        // find one document with Filters.eq()
        Document user = accountsCollection.find(and(eq("username", username), eq("password", password))).first();
        System.out.println("User" + user);
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

            return userID;
        }
        return null;
    }

    public static String getID() {
        return userID;
    }
    public static void setID(String id) {
        userID = id;
    }

    private static void storeCredentials(String username, String password) {
        // Use Preferences API to securely store credentials
        Preferences prefs = Preferences.userNodeForPackage(Account.class);
        prefs.put("username", username);
        prefs.put("password", password);
    }

    public static void clearCredentials() {
        // Use Preferences API to securely store credentials
        Preferences prefs = Preferences.userNodeForPackage(Account.class);
        prefs.remove("username");
        prefs.remove("password");
    }

    public static ArrayList<String> attemptAutoLogin() {
        // Attempt automatic login using stored credentials
        Preferences prefs = Preferences.userNodeForPackage(Account.class);
        String storedUsername = prefs.get("username", null);
        String storedPassword = prefs.get("password", null);

        boolean autoLogin = storedUsername != null && storedPassword != null;
        ArrayList<String> arr = new ArrayList<>();
        arr.add(storedUsername);
        arr.add(storedPassword);
        arr.add(String.valueOf(autoLogin));
        return arr;
    }
}