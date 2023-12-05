import server.MongoDB;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.mongodb.client.model.Filters.eq;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CreateAccountTest {
    private static MongoClient mongoClient;
    private static MongoDatabase PantryPalDB;

    @BeforeAll
    public static void setUp() {
        String testUri = "mongodb+srv://aditijain:cse110project@cluster0.yu0exzy.mongodb.net/?retryWrites=true&w=majority";
        mongoClient = MongoClients.create(testUri);
        PantryPalDB = mongoClient.getDatabase("PantryPal");
    }

    @Test
    public void testCreateAccount() {
        String username = "testuser";
        String password = "testpassword";
        // server.CreateAccount.createAccount(username, password);
        MongoCollection<Document> accountsCollection = PantryPalDB.getCollection("accounts");
        Document queryDocument = accountsCollection.find(eq("username", username)).first();
        assertNotNull(queryDocument, "User document should exist");
        assertEquals(username, queryDocument.getString("username"), "Username should match");
        assertEquals(password, queryDocument.getString("password"), "Password should match");
    }
}