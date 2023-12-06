import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClients;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;

public class MongoTest {

    public static void main(String[] args) {
        System.out.println("Hi");
        // MongoDB connection string
        String uri = "mongodb+srv://sraswan:pandapanda777@cluster0.fefhkg8.mongodb.net/?retryWrites=true&w=majority";

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            System.out.println(mongoClient);
            // Connect to the "your_database" database
            MongoDatabase database = mongoClient.getDatabase("PantryPal");
            System.out.println(database);

            // Choose the "your_collection" collection
            MongoCollection<Document> collection = database.getCollection("recipes");
            System.out.println(collection);
            // Query for all documents in the collection
            Document documents = collection.find(eq("_id", "2023-12-05 19:40:51.838")).first();
            
            System.out.println(documents);

            mongoClient.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}