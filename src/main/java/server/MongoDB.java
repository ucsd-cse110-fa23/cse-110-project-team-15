// package server;

// import static com.mongodb.client.model.Filters.eq;

// import org.bson.Document;

// import com.mongodb.client.MongoClient;
// import com.mongodb.client.MongoClients;
// import com.mongodb.client.MongoCollection;
// import com.mongodb.client.MongoDatabase;

// public class MongoDB {
//     public static void main(String[] args) {

//         // Replace the placeholder with your MongoDB deployment's connection string
//         String uri = "mongodb+srv://aditijain:cse110project@cluster0.yu0exzy.mongodb.net/?retryWrites=true&w=majority";

//         try (MongoClient mongoClient = MongoClients.create(uri)) {
//             MongoDatabase database = mongoClient.getDatabase("PantryPal");
//             MongoCollection<Document> collection = database.getCollection("accounts");

//             Document doc = collection.find(eq("username", "group15")).first();
//             if (doc != null) {
//                 System.out.println(doc.toJson());
//             } else {
//                 System.out.println("No matching documents found.");
//             }
//         }
//     }
// }

package server;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDB {
    public static void main(String[] args) {

        // Replace the placeholder with your MongoDB deployment's connection string
        String uri = "mongodb+srv://aditijain:cse110project@cluster0.yu0exzy.mongodb.net/?retryWrites=true&w=majority";

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("PantryPal");
            MongoCollection<Document> collection = database.getCollection("accounts");

            Document doc = collection.find(eq("title", "Back to the Future")).first();
            if (doc != null) {
                System.out.println(doc.toJson());
            } else {
                System.out.println("No matching documents found.");
            }
        }
    }
}
