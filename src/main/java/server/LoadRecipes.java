package server;

import com.mongodb.client.*;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import static com.mongodb.client.model.Filters.*;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.types.ObjectId;

import static java.util.Arrays.asList;

import com.mongodb.client.result.UpdateResult;
import com.sun.net.httpserver.*;

import org.bson.conversions.Bson;
import org.json.JSONObject;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class LoadRecipes implements HttpHandler{
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            // Obtain the input stream from the request
            InputStream requestBody = exchange.getRequestBody();
            InputStreamReader isr = new InputStreamReader(requestBody);
            BufferedReader br = new BufferedReader(isr);

            // Read the JSON data from the request
            StringBuilder requestData = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                requestData.append(line);
                System.out.println(line);
            }

            // Parse the JSON data
            JSONObject json = new JSONObject(requestData.toString());

            // Extract necessary data from the JSON
            String userID = json.getString("id");
            String recipeName = json.getString("name");
            String recipeMealType = json.getString("mealType");
            String recipeIngredients = json.getString("ingredients");
            String recipeInstructions = json.getString("instructions");
            //loadRecipes(userID, recipeName, recipeIngredients, recipeInstructions, recipeMealType);
        }
        //Sending back response to the client
        exchange.sendResponseHeaders(200, "Bruh".length());
        OutputStream outStream = exchange.getResponseBody();
        outStream.write("BRUH".getBytes());
        outStream.close();
        return;

    }

    // public void loadRecipes(String id, String recipeName, String recipeIngredients, String recipeInstructions,
    //         String mealType) {
    //     String uri = "mongodb+srv://aditijain:cse110project@cluster0.yu0exzy.mongodb.net/?retryWrites=true&w=majority";
    //     try (MongoClient mongoClient = MongoClients.create(uri)) {
    //         MongoDatabase PantryPalDB = mongoClient.getDatabase("recipe_db");
    //         MongoCollection<Document> recipesCollection = PantryPalDB.getCollection("recipes");
    //         List<Document> recipes = new ArrayList<>();
    //         if (userID != null) {
    //             recipes = recipesCollection.find(eq("userID", userID)).into(new ArrayList<>());
    //         }
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return;
    //     }
    // }

    public static List<Document> loadRecipes(String userID) {
        String uri = "mongodb+srv://aditijain:cse110project@cluster0.yu0exzy.mongodb.net/?retryWrites=true&w=majority";
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase PantryPalDB = mongoClient.getDatabase("recipe_db");
        MongoCollection<Document> recipesCollection = PantryPalDB.getCollection("recipes");
        List<Document> recipes = new ArrayList<>();
        if (userID != null) {
            recipes = recipesCollection.find(eq("userID", userID)).into(new ArrayList<>());
        }
        System.out.println(recipes);
        return recipes;
    }
}