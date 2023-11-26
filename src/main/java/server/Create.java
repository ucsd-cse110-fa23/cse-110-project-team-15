package server;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;


import static java.util.Arrays.asList;

public class Create {

public static void insertDataIntoMongoDB(String username, String password) {
    String uri =
    "mongodb+srv://aditijain:cse110project@cluster0.yu0exzy.mongodb.net/?retryWrites=true&w=majority";
    try (MongoClient mongoClient = MongoClients.create(uri)) {

        MongoDatabase PantryPalDB = mongoClient.getDatabase("PantryPal");
        MongoCollection<Document> accountsCollection = PantryPalDB.getCollection("accounts");

        Document user = new Document("_id", new ObjectId());
        user.append("username", username).append("password", password);
        accountsCollection.insertOne(user);

        }
    }
}

