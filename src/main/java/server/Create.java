// package server;

// import com.mongodb.client.MongoClient;
// import com.mongodb.client.MongoClients;
// import com.mongodb.client.MongoCollection;
// import com.mongodb.client.MongoDatabase;
// import org.bson.Document;
// import org.bson.types.ObjectId;

// import java.util.Random;

// import static java.util.Arrays.asList;

// public class Create {

// public static void main(String[] args) {
// String uri =
// "mongodb+srv://aditijain:cse110project@cluster0.yu0exzy.mongodb.net/?retryWrites=true&w=majority";
// try (MongoClient mongoClient = MongoClients.create(uri)) {

// MongoDatabase PantryPalDB = mongoClient.getDatabase("PantryPal");
// MongoCollection<Document> accountsCollection =
// PantryPalDB.getCollection("accounts");

// // Random rand = new Random();
// Document user = new Document("_id", new ObjectId());
// user.append("username", 10000d).append("password", 1d);
// accountsCollection.insertOne(user);
// }
// }
// }
