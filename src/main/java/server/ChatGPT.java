package server;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.Scanner;

import com.sun.net.httpserver.*;

import org.json.JSONArray;
import org.json.JSONObject;

public class ChatGPT implements HttpHandler {
    private static final String API_ENDPOINT = "https://api.openai.com/v1/completions";
    private static final String API_KEY = "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";
    private static final String MODEL = "text-davinci-003";

    public ChatGPT() {}

    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "Request Received";
        try {
            InputStream inStream = httpExchange.getRequestBody();
            Scanner scanner = new Scanner(inStream);
            String prompt = scanner.nextLine();
            response = generate(prompt);
            System.out.println(response);
            scanner.close();
        } catch (Exception e) {
            System.out.println("An erroneous request");
            response = e.toString();
            e.printStackTrace();
        }
        //Sending back response to the client
        httpExchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream outStream = httpExchange.getResponseBody();
        outStream.write(response.getBytes());
        outStream.close();
    }

    public String generate(String prompt) throws IOException, InterruptedException, URISyntaxException {
        // Set request parameters
        int maxTokens = 512;

        // Create a request body which you will pass into request object
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", MODEL);
        requestBody.put("prompt", prompt);
        requestBody.put("max_tokens", maxTokens);
        requestBody.put("temperature", 1.0);

        HttpClient client = HttpClient.newHttpClient();

        // Create the request object
        HttpRequest request = HttpRequest
        .newBuilder()
        .uri(new URI(API_ENDPOINT))
        .header("Content-Type", "application/json")
        .header("Authorization", String.format("Bearer %s", API_KEY))
        .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
        .build();

        // Send the request and receive the response
        HttpResponse<String> response = client.send(
        request,
        HttpResponse.BodyHandlers.ofString()
        );

        // Process the response
        String responseBody = response.body();
        
        JSONObject responseJson = new JSONObject(responseBody);
        

        JSONArray choices = responseJson.getJSONArray("choices");
        String generatedText = choices.getJSONObject(0).getString("text");

        System.out.println(generatedText);
        return generatedText;
    }
}
