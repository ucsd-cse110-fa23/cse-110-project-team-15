package server;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

import org.bson.Document;
import org.bson.conversions.Bson;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.sun.net.httpserver.*;

import org.json.JSONObject;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import java.sql.Timestamp;

public class MongoDB implements HttpHandler {

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
            String userID = json.getString("id");
            String recipeId = json.getString("recipeId");
            String recipeName = json.getString("name");
            String recipeMealType = json.getString("mealType");
            String recipeIngredients = json.getString("ingredients");
            String recipeInstructions = json.getString("instructions");

            System.out.println("URI: " + requestUri);
            System.out.println("Name: " + recipeName);
            if (requestUri.contains("create")) {
                createRecipe(userID, recipeId, recipeName, recipeIngredients, recipeInstructions, recipeMealType);
            } else if (requestUri.contains("delete")) {
                deleteRecipe(userID, recipeId, recipeName, recipeIngredients, recipeInstructions, recipeMealType);
            } else if (requestUri.contains("update")) {
                updateRecipe(userID, recipeId, recipeName, recipeIngredients, recipeInstructions, recipeMealType);
            }
        }
        //Sending back response to the client
        exchange.sendResponseHeaders(200, "bruh".length());
        OutputStream outStream = exchange.getResponseBody();
        outStream.write("bruh".getBytes());
        outStream.close();
        return;

    }

    public void updateRecipe(String id, String recipeId, String recipeName, String recipeIngredients, String recipeInstructions,
            String mealType) {
        String uri = "mongodb+srv://aditijain:cse110project@cluster0.yu0exzy.mongodb.net/?retryWrites=true&w=majority";
        try (MongoClient mongoClient = MongoClients.create(uri)) {

            System.out.println("Name: " + recipeName);
            System.out.println("ID: " + id);
            MongoDatabase RecipeDB = mongoClient.getDatabase("recipe_db");
            MongoCollection<Document> recipesCollection = RecipeDB.getCollection("recipes");

            Bson filter = recipesCollection.find(and(eq("userID", id), eq("recipeName", recipeName))).first();
            System.out.println(filter);
            Bson updateOperation = combine(set("recipeIngredients", recipeIngredients), set("recipeInstructions", recipeInstructions));
               
            UpdateResult updateResult = recipesCollection.updateOne(filter, updateOperation);
            System.out.println("Updated");
            System.out.println(updateResult);
            return;
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    public void deleteRecipe(String id, String recipeId, String recipeName, String recipeIngredients, String recipeInstructions,
            String mealType) {
        String uri = "mongodb+srv://aditijain:cse110project@cluster0.yu0exzy.mongodb.net/?retryWrites=true&w=majority";
        try (MongoClient mongoClient = MongoClients.create(uri)) {

            System.out.println("Name: " + recipeName);
            System.out.println("ID: " + id);
            MongoDatabase RecipeDB = mongoClient.getDatabase("recipe_db");
            MongoCollection<Document> recipesCollection = RecipeDB.getCollection("recipes");

            // DOESNT ACTUALLY DELETE FIND OUT HOW TO DO THIS MAYBE FILTER IS WRONG LOL
            Bson filter = recipesCollection.find(and(eq("userID", id), eq("_id", recipeId))).first();
            System.out.println(filter);               
            DeleteResult delteteResult = recipesCollection.deleteOne(filter);
            System.out.println("Deleted");
            System.out.println(delteteResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }

    public void createRecipe(String id, String recipeId, String recipeName, String recipeIngredients, String recipeInstructions,
    String mealType) {
        String uri = "mongodb+srv://aditijain:cse110project@cluster0.yu0exzy.mongodb.net/?retryWrites=true&w=majority";
        try (MongoClient mongoClient = MongoClients.create(uri)) {

            MongoDatabase RecipeDB = mongoClient.getDatabase("recipe_db");
            MongoCollection<Document> accountsCollection = RecipeDB.getCollection("recipes");

            Document recipe = new Document("_id", recipeId)
                    .append("userID", id).append("recipeName", recipeName)
                    .append("recipeIngredients", recipeIngredients).append("recipeInstructions", recipeInstructions)
                    .append("mealType", mealType);
            accountsCollection.insertOne(recipe);
            
            System.out.println("Created");
            System.out.println("Recipe Stored for user:" + id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
