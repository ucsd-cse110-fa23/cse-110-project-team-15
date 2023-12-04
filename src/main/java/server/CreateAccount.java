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

public class CreateAccount implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
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
            } 
        }
        //Sending back response to the client
        exchange.sendResponseHeaders(200, "Bruh".length());
        OutputStream outStream = exchange.getResponseBody();
        outStream.write("BRUH".getBytes());
        outStream.close();
        return;

    }

    public static void createAccount(String username, String password) {
        String uri = "mongodb+srv://aditijain:cse110project@cluster0.yu0exzy.mongodb.net/?retryWrites=true&w=majority";
        try (MongoClient mongoClient = MongoClients.create(uri)) {

            MongoDatabase PantryPalDB = mongoClient.getDatabase("PantryPal");
            MongoCollection<Document> accountsCollection = PantryPalDB.getCollection("accounts");

            Document user = new Document("_id", new ObjectId())
                    .append("username", username).append("password", password);
            accountsCollection.insertOne(user);

            System.out.println("Account created for: " + username);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}