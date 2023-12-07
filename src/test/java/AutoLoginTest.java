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

public class AutoLoginTest {
    private static MongoClient mongoClient;
    private static MongoDatabase PantryPalDB;

    @BeforeAll
    public static void setUp() {
        String testUri = "mongodb+srv://sraswan:pandapanda777@cluster0.fefhkg8.mongodb.net/?retryWrites=true&w=majority";
        mongoClient = MongoClients.create(testUri);
        PantryPalDB = mongoClient.getDatabase("PantryPal");
    }

    @Test
    public void testAutoLoginSuccess() {
        String username = "test";
        String password = "password";
        String userID = server.Account.loginAccount(username, password, false);
        assertNotNull((userID));
    }

    @Test
    public void testAutoLoginUnsuccessful() {
        String username = "testuser";
        String password = "testpasswordd";
        String userID = server.Account.loginAccount(username, password, false);
        assertEquals(userID, null);
    }

    @Test
    public void testLoginE2E() {
        testAutoLoginSuccess();
        testAutoLoginUnsuccessful();
    }
}

