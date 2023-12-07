import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClients;

import static com.mongodb.client.model.Filters.eq;

import java.net.InetAddress;
import java.net.UnknownHostException;


import org.bson.Document;

public class MongoTest {

    public static void main(String[] args) throws UnknownHostException{
        InetAddress localhost = InetAddress.getLocalHost();

            // Print the local IP address
            System.out.println("Local IP Address: " + localhost.getHostAddress());
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
            
            for (Document document : documents) {
                System.out.println(document);
            }

            mongoClient.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}