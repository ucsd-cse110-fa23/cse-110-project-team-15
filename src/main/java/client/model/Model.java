/* 
 * Sources:
 * https://chat.openai.com/
 * date: 11/13/2023
 * version number: GPT-4
 * prompt1: how do i make a http request in the front-end that posts the prompt string to the localhost and get the response from chatgpt api
 * prompt2: how to send a request that contains the audio file we just recorded
 * 
 * Lab 5
 * date: 11/13/2023
 * Copy code from our lab 5 github
*/

package client.model;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.nio.file.Files;
import java.nio.file.Path;

import org.bson.types.ObjectId;
import org.json.JSONObject;

import client.view.Recipe;
import server.Login;

import java.net.URI;

public class Model {

    public String recipeRequest(String method, String language, String year, String query) {
        try {
            String urlString = "http://localhost:8100/";
            if (query != null) {
                urlString += "?=" + query;
            }
            URL url = new URI(urlString).toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setDoOutput(true);

            if (method.equals("POST") || method.equals("PUT")) {
                OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
                out.write(language + "," + year);
                out.flush();
                out.close();
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response = in.readLine();
            in.close();
            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Error: " + ex.getMessage();
        }
    }

    public String sendAccount(String username, String password) {
        String url = "http://localhost:8100/api/accounts";

        try {
            HttpClient client = HttpClient.newHttpClient();
            JSONObject json = new JSONObject();
            json.put("_id", new ObjectId());
            json.put("username", username);
            json.put("password", password);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                    .build();

            System.out.println("username on server: " + json.toString());

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Error: " + ex.getMessage();
        }
    }

    public String sendRecipe(Recipe recipe) {
        String url = "http://localhost:8100/api/recipes";

        try {
            HttpClient client = HttpClient.newHttpClient();
            JSONObject json = new JSONObject();
            // json.put("id", getUserID());
            json.put("name", recipe.getName().getText());
            json.put("mealType", recipe.getMealType().getText());
            json.put("ingredients", recipe.getIngredient().getText());
            json.put("instructions", recipe.getInstruction().getText());

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Error: " + ex.getMessage();
        }
    }

    // Doenst work all the time because of byte fixed content-lenth
    public String requestInstruction(String prompt) {
        String url = "http://localhost:8100/instruction";

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("prompt", prompt);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .method("PUT", HttpRequest.BodyPublishers.ofString(jsonObject.toString()))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Error: " + ex.getMessage();
        }
    }

    public String requestTranscript() {
        String url = "http://localhost:8100/transcribe";
        String filePath = "bin/audio/recording.wav";
        try {
            Path path = Path.of(filePath);
            byte[] fileContent = Files.readAllBytes(path);
            String response = sendAudio(url, filePath);
            System.out.println("Response: " + response);
            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Error: " + ex.getMessage();
        }
    }

    public static String sendAudio(String url, String filePath) throws IOException, InterruptedException {
        File audioFile = new File(filePath);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "audio/wav")
                .POST(BodyPublishers.ofFile(audioFile.toPath()))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Parse the response as JSON and get the transcript field
        return response.body();
    }

    // private static String sendAudio(String urlString, String filePath) throws
    // IOException{
    // final String POST_URL = urlString;
    // final File uploadFile = new File(filePath);

    // String boundary = Long.toHexString(System.currentTimeMillis());
    // String CRLF = "\r\n";
    // String charset = "UTF-8";
    // URLConnection connection = new URL(POST_URL).openConnection();
    // connection.setDoOutput(true);
    // connection.setRequestProperty("Content-Type", "multipart/form-data;
    // boundary=" + boundary);

    // try (
    // OutputStream output = connection.getOutputStream();
    // PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, charset),
    // true);
    // ) {
    // writer.append("--" + boundary).append(CRLF);
    // writer.append("Content-Disposition: form-data; name=\"binaryFile\";
    // filename=\"" + uploadFile.getName() + "\"").append(CRLF);
    // writer.append("Content-Length: " + uploadFile.length()).append(CRLF);
    // writer.append("Content-Type: " +
    // URLConnection.guessContentTypeFromName(uploadFile.getName())).append(CRLF);
    // writer.append("Content-Transfer-Encoding: binary").append(CRLF);
    // writer.append(CRLF).flush();
    // Files.copy(uploadFile.toPath(), output);
    // output.flush();

    // int responseCode = ((HttpURLConnection) connection).getResponseCode();
    // System.out.println("Response code: [" + responseCode + "]");
    // }

    // BufferedReader reader = new BufferedReader(new
    // InputStreamReader(connection.getInputStream()));
    // StringBuilder response = new StringBuilder();
    // String line;
    // while ((line = reader.readLine()) != null) {
    // response.append(line);
    // }
    // return response.toString();

    // }
}