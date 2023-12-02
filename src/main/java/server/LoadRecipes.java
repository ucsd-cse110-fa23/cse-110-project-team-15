package server;

import com.mongodb.client.*;

import javafx.scene.control.TextField;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Sorts.descending;

public class LoadRecipes {
    public static List<Document> loadRecipes(String userID) {
        String uri = "mongodb+srv://aditijain:cse110project@cluster0.yu0exzy.mongodb.net/?retryWrites=true&w=majority";
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase PantryPalDB = mongoClient.getDatabase("recipe_db");
        MongoCollection<Document> recipesCollection = PantryPalDB.getCollection("recipes");
        System.out.println(userID);
        List<Document> recipes = recipesCollection.find(gte("userID", userID)).into(new ArrayList<>());
        return recipes;
    }
}