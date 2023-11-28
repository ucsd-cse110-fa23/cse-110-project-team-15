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
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import com.sun.net.httpserver.*;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDB implements HttpHandler {
    private static  MongoCollection<Document> accountsCollection;

    public MongoDB() {}

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // MongoDB connection setup
        String uri = "mongodb+srv://aditijain:cse110project@cluster0.yu0exzy.mongodb.net/?retryWrites=true&w=majority";
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase database = mongoClient.getDatabase("PantryPal");
        accountsCollection = database.getCollection("accounts");


        String response;
        int statusCode;

        Document doc = accountsCollection.find(eq("username", "group15")).first();
        System.out.println(doc.toJson());
        System.out.println(exchange.getRequestMethod());
            
        // Handle only the GET request for simplicity
        if ("GET".equals(exchange.getRequestMethod())) {
            try {
                // Read JSON data from the request body
                InputStream requestBody = exchange.getRequestBody();
                String jsonData = new String(requestBody.readAllBytes(), StandardCharsets.UTF_8);

                // Parse JSON data and insert into MongoDB collection
                Document document = Document.parse(jsonData);
                accountsCollection.insertOne(document);

                System.out.println("username on server: "+ jsonData);

                response = "Data added to MongoDB collection 'accounts'";
                statusCode = 200;
            } catch (Exception e) {
                e.printStackTrace();
                response = "Error processing the request";
                statusCode = 500;
            }
        } else {
            response = "Unsupported HTTP method";
            statusCode = 405;  // Method Not Allowed
        }

        // Set response headers
        exchange.getResponseHeaders().set("Content-Type", "text/plain");
        exchange.sendResponseHeaders(statusCode, response.length());

        // Send the response
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

}
