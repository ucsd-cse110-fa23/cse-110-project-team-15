package server;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;

import org.bson.Document;
import org.bson.conversions.Bson;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.*;

public class UpdateRecipes {

    public void updateRecipe(String id, String recipeName, String recipeIngredients, String recipeInstructions,
            String mealType) {
        String uri = "mongodb+srv://aditijain:cse110project@cluster0.yu0exzy.mongodb.net/?retryWrites=true&w=majority";
        try (MongoClient mongoClient = MongoClients.create(uri)) {

            MongoDatabase RecipeDB = mongoClient.getDatabase("recipe_db");
            MongoCollection<Document> recipesCollection = RecipeDB.getCollection("recipes");

            Bson filter = recipesCollection.find(and(eq("userID", server.Login.getID()), eq("recipeName", recipeName))).first();
            Bson updateOperation = and(set("recipeIngredients", recipeIngredients), set("recipeInstructions", recipeInstructions));
            UpdateResult updateResult = recipesCollection.updateOne(filter, updateOperation);
            System.out.println("Updated");
            System.out.println(updateResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}