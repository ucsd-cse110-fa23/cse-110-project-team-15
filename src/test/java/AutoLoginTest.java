import server.Account;
import server.MongoDB;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.prefs.Preferences;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
        Preferences prefs = Preferences.userNodeForPackage(Account.class);
        String userID = Account.loginAccount(username, password, true);
        assertNotNull((userID));
        assertEquals(username, prefs.get("username", null));
    }

    @Test
    public void testAutoLoginUnsuccessful() {
        Account.clearCredentials();
        String username = "testuser";
        String password = "testpasswordd";
        Preferences prefs = Preferences.userNodeForPackage(Account.class);
        String userID = server.Account.loginAccount(username, password, false);
        assertEquals(userID, null);
        assertEquals(null, prefs.get("username", null));
    }

    @Test
    public void testLoginE2E() {
        testAutoLoginSuccess();
        testAutoLoginUnsuccessful();
    }
}

