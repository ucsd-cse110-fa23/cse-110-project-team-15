package server;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.CompletableFuture;
import java.util.Scanner;

import com.sun.net.httpserver.*;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDB implements HttpHandler {

    private static final String uri = "mongodb+srv://aditijain:cse110project@cluster0.yu0exzy.mongodb.net/?retryWrites=true&w=majority";

    public void handle(HttpExchange exchange) throws IOException{
        // Retrieve username and password from an HTTP server (replace the URL with your actual endpoint)
        String username = getUsernameFromServer("http://localhost/username");
        String password = getPasswordFromServer("http://your-http-server/password");

        // Connect to MongoDB and insert the data
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("PantryPal");
            MongoCollection<Document> collection = database.getCollection("accounts");
            // Create a document to insert into the collection
            Document user = new Document("_id", new ObjectId());
            user.append("username", username).append("password", password);
            
            // Insert the document into the "accounts" collection
            collection.insertOne(user);

            // Close the MongoDB client
            mongoClient.close();
        }

    }

    private static String getUsernameFromServer(String url) {
        return fetchDataFromServer(url);
    }

    private static String getPasswordFromServer(String url) {
        return fetchDataFromServer(url);
    }

    private static String fetchDataFromServer(String url) {
        // Use HttpClient to make a simple GET request (replace with your actual logic)
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        CompletableFuture<HttpResponse<String>> responseFuture = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());


        try {
            HttpResponse<String> response = responseFuture.get();
            if (response.statusCode() == 200) {
                return response.body();
            } else {
                System.out.println("Failed to fetch data from server. Status code: " + response.statusCode());
                return null;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Preserve interrupt status
            System.err.println("Request was interrupted: " + e.getMessage());
            return null;
        } catch (ExecutionException e) {
            System.err.println("Exception during HTTP request: " + e.getMessage());
            return null;
        }
    }
}

