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


import static java.util.Arrays.asList;

public class Account implements HttpHandler {
    private static volatile String userID;
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("bruh");
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

            System.out.println("URI: " + requestUri);
            System.out.println("Name: " + username);
            if (requestUri.contains("create")) {
                createAccount(username, password);
            } else if (requestUri.contains("login")) {
                success = loginAccount(username, password);
            }
        }
        //Sending back response to the client
        if (success) {
            exchange.sendResponseHeaders(200, userID.length());
            OutputStream outStream = exchange.getResponseBody();
            outStream.write(userID.getBytes());
            outStream.close();
        }
        else {
            exchange.sendResponseHeaders(500, 0);
            OutputStream outStream = exchange.getResponseBody();
            outStream.write(null);
            outStream.close();
        }
    }

    public static void createAccount(String username, String password) {
        String uri = "mongodb+srv://aditijain:cse110project@cluster0.yu0exzy.mongodb.net/?retryWrites=true&w=majority";
        try (MongoClient mongoClient = MongoClients.create(uri)) {

            MongoDatabase PantryPalDB = mongoClient.getDatabase("PantryPal");
            MongoCollection<Document> accountsCollection = PantryPalDB.getCollection("accounts");

            Document user = new Document("_id", new ObjectId())
                    .append("username", username).append("password", password);
            accountsCollection.insertOne(user);
            userID = user.getObjectId("_id").toString();

            System.out.println("Account created for: " + username);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
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
            setID(user.getObjectId("_id").toString());
            System.out.println("User ID: " + getID());
            return true;
        }
        return false;
    }

    public static String getID() {
        return userID;
    }
    public static void setID(String id) {
        userID = id;
    }
}