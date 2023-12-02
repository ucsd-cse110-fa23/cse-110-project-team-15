package server;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import static java.util.Arrays.asList;

public class SendRecipeDB {

    public static void sendRecipeDB(String id, String recipeName, String recipeIngredients, String recipeInstructions,
            String mealType) {
        String uri = "mongodb+srv://aditijain:cse110project@cluster0.yu0exzy.mongodb.net/?retryWrites=true&w=majority";
        try (MongoClient mongoClient = MongoClients.create(uri)) {

            MongoDatabase RecipeDB = mongoClient.getDatabase("recipe_db");
            MongoCollection<Document> accountsCollection = RecipeDB.getCollection("recipes");

            Document recipe = new Document("_id", new ObjectId())
                    .append("userID", server.Login.getID()).append("recipeName", recipeName)
                    .append("recipeIngredients", recipeIngredients).append("recipeInstructions", recipeInstructions)
                    .append("mealType", mealType);
            accountsCollection.insertOne(recipe);

            System.out.println("Recipe Stored for user:" + id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}