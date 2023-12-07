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
        String str = "";
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
            String url = json.getString("url");

            System.out.println("URI: " + requestUri);
            System.out.println("User ID: " + userID);
            System.out.println("Recipe ID: " + recipeId);
            System.out.println("Name: " + recipeName);
            str = userID;
            
            if (requestUri.contains("create")) {
                str = createRecipe(userID, recipeId, recipeName, recipeIngredients, recipeInstructions, recipeMealType, url);
            } else if (requestUri.contains("delete")) {
                str = deleteRecipe(userID, recipeId, recipeName, recipeIngredients, recipeInstructions, recipeMealType, url);
            } else if (requestUri.contains("update")) {
                str = updateRecipe(userID, recipeId, recipeName, recipeIngredients, recipeInstructions, recipeMealType, url);
            }
        }
        //Sending back response to the client
        exchange.sendResponseHeaders(200, str.length());
        OutputStream outStream = exchange.getResponseBody();
        outStream.write(str.getBytes());
        outStream.close();
        return;

    }

    public String updateRecipe(String id, String recipeId, String recipeName, String recipeIngredients, String recipeInstructions,
            String mealType, String url) {
        String uri = "mongodb+srv://sraswan:pandapanda777@cluster0.fefhkg8.mongodb.net/?retryWrites=true&w=majority";
        try (MongoClient mongoClient = MongoClients.create(uri)) {

            MongoDatabase RecipeDB = mongoClient.getDatabase("PantryPal");
            MongoCollection<Document> recipesCollection = RecipeDB.getCollection("recipes");

            Bson filter = recipesCollection.find(and(eq("userID", id), eq("_id", recipeId))).first();
            System.out.println(filter);
            Bson updateOperation = combine(set("url", url), set("recipeName", recipeName), set("recipeIngredients", recipeIngredients), set("recipeInstructions", recipeInstructions));
               
            UpdateResult updateResult = recipesCollection.updateOne(filter, updateOperation);
            System.out.println("Updated");
            System.out.println(updateResult);
            mongoClient.close();

            return recipeId;
        } catch (Exception e) {
            e.printStackTrace();
            return recipeId;
        }
    }

    public String deleteRecipe(String id, String recipeId, String recipeName, String recipeIngredients, String recipeInstructions,
            String mealType, String url) {
        String uri = "mongodb+srv://sraswan:pandapanda777@cluster0.fefhkg8.mongodb.net/?retryWrites=true&w=majority";
        try (MongoClient mongoClient = MongoClients.create(uri)) {

            MongoDatabase RecipeDB = mongoClient.getDatabase("PantryPal");
            MongoCollection<Document> recipesCollection = RecipeDB.getCollection("recipes");

            // DOESNT ACTUALLY DELETE FIND OUT HOW TO DO THIS MAYBE FILTER IS WRONG LOL
            Bson filter = recipesCollection.find(and(eq("userID", id), eq("_id", recipeId))).first();
            System.out.println(filter);               
            DeleteResult delteteResult = recipesCollection.deleteOne(filter);
            System.out.println("Deleted");
            System.out.println(delteteResult);
            
            mongoClient.close();

            return recipeId;
        } catch (Exception e) {
            e.printStackTrace();
            return recipeId;
        }
    }

    public static String createRecipe(String id, String recipeId, String recipeName, String recipeIngredients, String recipeInstructions,
    String mealType, String url) {
        String uri = "mongodb+srv://sraswan:pandapanda777@cluster0.fefhkg8.mongodb.net/?retryWrites=true&w=majority";
        try (MongoClient mongoClient = MongoClients.create(uri)) {

            MongoDatabase RecipeDB = mongoClient.getDatabase("PantryPal");
            MongoCollection<Document> accountsCollection = RecipeDB.getCollection("recipes");

            Document recipe = new Document("_id", recipeId)
                    .append("userID", id)
                    .append("recipeName", recipeName)
                    .append("recipeIngredients", recipeIngredients)
                    .append("recipeInstructions", recipeInstructions)
                    .append("mealType", mealType)
                    .append("url", url);
            accountsCollection.insertOne(recipe);
            
            System.out.println("Created");
            System.out.println("Recipe Stored for user:" + id);
            mongoClient.close();
            return recipeId;
        } catch (Exception e) {
            e.printStackTrace();
            return recipeId;
        }
    }


    public static String[] fetchRecipeDetails(String recipeId) {
        String uri = "mongodb+srv://sraswan:pandapanda777@cluster0.fefhkg8.mongodb.net/?retryWrites=true&w=majority";


        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("PantryPal");
            MongoCollection<Document> collection = database.getCollection("recipes");
            Document recipeDoc = collection.find(eq("_id", recipeId)).first();

            System.out.println(recipeDoc);

            if (recipeDoc != null) {
                String recipeName = recipeDoc.getString("recipeName");
                String recipeIngredients = recipeDoc.getString("recipeIngredients");
                String recipeInstructions = recipeDoc.getString("recipeInstructions");
                String url = recipeDoc.getString("url");
                
                mongoClient.close();

                // Construct the recipe details string
                String str = "Recipe Name: " + recipeName + "<br>" +
                        "Ingredients: " + recipeIngredients + "<br>" +
                        "Instructions: " + recipeInstructions;
                String[] str1 = {str, url};

                return str1;
            } else {
                String[] str2 = {"Recipe not found", ""};
                return str2;
            }
        } catch (Exception e) {
            e.printStackTrace();
            String[] str2 = {"Error fetching recipe details", ""};
            return str2;
        }
    }



}
