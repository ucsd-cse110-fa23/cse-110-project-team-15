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

public class LoginTest {
    private static MongoClient mongoClient;
    private static MongoDatabase PantryPalDB;

    @BeforeAll
    public static void setUp() {
        String testUri = "mongodb+srv://sraswan:pandapanda777@cluster0.fefhkg8.mongodb.net/?retryWrites=true&w=majority";
        mongoClient = MongoClients.create(testUri);
        PantryPalDB = mongoClient.getDatabase("PantryPal");
    }

    @Test
    public void testLoginAccount() {
        String username = "testuser";
        String password = "testpassword";
        String userID = server.Account.loginAccount(username, password, false);
        assertNotNull((userID));
    }
}