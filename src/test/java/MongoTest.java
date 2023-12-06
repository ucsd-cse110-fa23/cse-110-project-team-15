import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClients;
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
            FindIterable<Document> documents = collection.find();
            
            System.out.println(documents);

            MongoDatabase adminDb = mongoClient.getDatabase("PantryPal");

            // Create a ping command
            Document pingCommand = new Document("ping", 1);

            // Run the ping command
            Document result = adminDb.runCommand(pingCommand);
            // Iterate over the documents and print them
            for (Document document : documents) {
                System.out.println(document.toJson());
            }
            mongoClient.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}