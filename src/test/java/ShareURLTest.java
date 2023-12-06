import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.sun.net.httpserver.HttpExchange;
import server.MongoDB;

import client.view.DetailsPopup;
import javafx.application.Platform;
import server.RequestHandler;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URI;

public class ShareURLTest extends ApplicationTest {
    private static MongoClient mongoClient;
    private static MongoDatabase PantryPalDB;



    @Override
    public void stop() throws Exception {
        FxToolkit.hideStage();
    }

    @Test
    public void testbuildRecipeUrl() {
        Platform.runLater(() -> {
            DetailsPopup detailsPopup = new DetailsPopup();
            String recipeID = "123";
            String url = detailsPopup.buildRecipeUrl(recipeID);
            String expectedUrl = "http://localhost:8100/recipe/?=123";
            assertEquals(expectedUrl, url);
        });

    }

    @Test
    public void testfetchRecipeDetails() throws Exception {
        String username = "testuser";
        String password = "testpassword";
        String userID = server.Account.loginAccount(username, password, false);
        server.MongoDB.createRecipe(userID, "123", "testName", "testIng", "testIns", "testMeal", "testURL");
        
        String[] actual = MongoDB.fetchRecipeDetails("123");

        String expected = "Recipe Name: " + "testName" + "<br>" +
        "Ingredients: " + "testIng" + "<br>" +
        "Instructions: " + "testIns";

        String[] expectedArr = {expected, "testURL"};

        assertEquals(actual[0], expectedArr[0]);
        assertEquals(actual[1], expectedArr[1]);
    }

    @Test
    public void testHandleGet() throws Exception {

        HttpExchange httpExchange = mock(HttpExchange.class);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        when(httpExchange.getResponseBody()).thenReturn(byteArrayOutputStream);

        InputStream inputStream = new ByteArrayInputStream("".getBytes());
        when(httpExchange.getRequestBody()).thenReturn(inputStream);

        URI testURI = new URI("/recipe/?=123");

        when(httpExchange.getRequestMethod()).thenReturn("GET");
        when(httpExchange.getRequestURI()).thenReturn(testURI);

        RequestHandler requestHandler = new RequestHandler();
        requestHandler.handle(httpExchange);

        String capturedOutput = byteArrayOutputStream.toString();

        String[] actualRecipeDetails = MongoDB.fetchRecipeDetails("123");

        String expectedHtml = "<html><head><title>Recipe Details</title></head><body>"
                + "<h1>Recipe Details</h1>"
                + "<p>"
                + "<img src=\"" + actualRecipeDetails[1] + "\">"
                + "<br>"
                + actualRecipeDetails[0]
                + "</p>"
                + "</body>"
                + "</html>";

        assertEquals(expectedHtml, capturedOutput);
    }

}
