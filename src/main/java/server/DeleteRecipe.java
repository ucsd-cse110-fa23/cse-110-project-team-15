package server;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.sun.net.httpserver.*;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONObject;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DeleteRecipe implements HttpHandler{

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
            deleteRecipe(userID, recipeName, recipeIngredients, recipeInstructions, recipeMealType);
        }
        return;

    }

    public void deleteRecipe(String id, String recipeName, String recipeIngredients, String recipeInstructions,
            String mealType) {
        String uri = "mongodb+srv://aditijain:cse110project@cluster0.yu0exzy.mongodb.net/?retryWrites=true&w=majority";
        try (MongoClient mongoClient = MongoClients.create(uri)) {

            System.out.println("Name: " + recipeName);
            System.out.println("ID: " + id);
            MongoDatabase RecipeDB = mongoClient.getDatabase("recipe_db");
            MongoCollection<Document> recipesCollection = RecipeDB.getCollection("recipes");

            // DOESNT ACTUALLY DELETE FIND OUT HOW TO DO THIS MAYBE FILTER IS WRONG LOL
            Bson filter = recipesCollection.find(and(eq("userID", id), eq("recipeName", recipeName))).first();
            System.out.println(filter);               
            DeleteResult delteteResult = recipesCollection.deleteOne(filter);
            System.out.println("Deleted");
            System.out.println(delteteResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }
}