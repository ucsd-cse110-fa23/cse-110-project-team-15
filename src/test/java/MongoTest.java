import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClients;

import static com.mongodb.client.model.Filters.eq;

import client.model.*;
import client.view.AppFrame;
import server.ChatGPT;
import server.Server;
import java.net.InetAddress;
import java.net.UnknownHostException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.bson.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class MongoTest {
    private static MongoClient mongoClient;
    private static MongoDatabase PantryPalDB;
    private static Model model;
    private static Server server;

    @BeforeAll
    public static void setUp() {
        String testUri = "mongodb+srv://sraswan:pandapanda777@cluster0.fefhkg8.mongodb.net/?retryWrites=true&w=majority";
        mongoClient = MongoClients.create(testUri);
        PantryPalDB = mongoClient.getDatabase("PantryPal");
        model = new Model();
    }

    @Test
    public void testMongo() throws UnknownHostException{
        InetAddress localhost = InetAddress.getLocalHost();

        // Print the local IP address
        System.out.println("Local IP Address: " + localhost.getHostAddress());
    
        assertNotNull(PantryPalDB);
        // Choose the "your_collection" collection
        MongoCollection<Document> collection = PantryPalDB.getCollection("recipes");
        System.out.println(collection);
        // Query for all documents in the collection
        FindIterable<Document> documents = collection.find();
        
        for (Document document : documents) {
            System.out.println(document);
        }
        assertNotNull(documents);

        mongoClient.close();
    }

    @Test
    public void testModel() throws UnknownHostException{
        
        assertEquals(model.isServerOnline(), false);
        server = new Server();
        model = mock(Model.class);
        assertEquals(model.isServerOnline(), false);
        when(model.isServerOnline()).thenReturn(true);
        when(model.sendAccount("login", "test", "password", "false")).thenReturn("65714a6024ebf878f9f2b35e");
        assertEquals(model.isServerOnline(), true);
        String id = model.sendAccount("login", "test", "password", "false");
        assertEquals(id, "65714a6024ebf878f9f2b35e");
        id = model.sendAccount("create", "test", "password", "false");
        assertEquals(id, null);
        id = model.sendAccount("login", "goglab", "goglab", "false");
        assertEquals(id, null);


    }
}