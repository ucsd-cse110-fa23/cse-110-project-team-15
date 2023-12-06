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
        String uri = "mongodb+srv://sraswan:pandapanda777@cluster0.fefhkg8.mongodb.net/?retryWrites=true&w=majority";
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase PantryPalDB = mongoClient.getDatabase("PantryPal");
        MongoCollection<Document> recipesCollection = PantryPalDB.getCollection("recipes");
        System.out.println("pip"+userID);
        List<Document> recipes = new ArrayList<>();
        if (userID != null) {
            recipes = recipesCollection.find(eq("userID", userID)).into(new ArrayList<>());
        }
        System.out.println(userID);
        mongoClient.close();
        return recipes;
    }
}