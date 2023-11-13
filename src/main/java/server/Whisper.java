package server;

import java.io.*;
import java.net.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.*;

public class Whisper implements HttpHandler {
    private static final String API_ENDPOINT = "https://api.openai.com/v1/audio/transcriptions";
    private static final String TOKEN = "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";
    private static final String MODEL = "whisper-1";

    public Whisper() {}

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!"POST".equals(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(405, 0); // Method Not Allowed
            exchange.getResponseBody().close();
            return;
        }

        // Process the incoming request and extract the audio file
        InputStream requestBody = exchange.getRequestBody();
        File audioFile = createFileFromInputStream(requestBody);

        // Transcribe the audio file
        String transcription = transcribeAudio(audioFile);

        // Send the response
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, transcription.length());
        OutputStream responseBody = exchange.getResponseBody();
        responseBody.write(transcription.getBytes());
        responseBody.close();
    }

    private File createFileFromInputStream(InputStream inputStream) throws IOException {
        File tempFile = File.createTempFile("audio", ".wav");
        tempFile.deleteOnExit();
        try (FileOutputStream out = new FileOutputStream(tempFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
        return tempFile;
    }

    // Helper method to write a parameter to the output stream in multipart form
    // data format
    private void writeParameterToOutputStream(
            OutputStream outputStream,
            String parameterName,
            String parameterValue,
            String boundary) throws IOException {
        outputStream.write(("--" + boundary + "\r\n").getBytes());
        outputStream.write(
                ("Content-Disposition: form-data; name=\"" + parameterName + "\"\r\n\r\n").getBytes());
        outputStream.write((parameterValue + "\r\n").getBytes());
    }

    // Helper method to write a file to the output stream in multipart form data
    // format
    private void writeFileToOutputStream(
            OutputStream outputStream,
            File file,
            String boundary) throws IOException {
        outputStream.write(("--" + boundary + "\r\n").getBytes());
        outputStream.write(
                ("Content-Disposition: form-data; name=\"file\"; filename=\"" +
                        file.getName() +
                        "\"\r\n").getBytes());
        outputStream.write(("Content-Type: audio/mpeg\r\n\r\n").getBytes());

        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        fileInputStream.close();
    }

    // Helper method to handle a successful response
    private String handleSuccessResponse(HttpURLConnection connection) throws IOException, JSONException {
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JSONObject responseJson = new JSONObject(response.toString());
        String generatedText = responseJson.getString("text");

        return generatedText;
    }

    private void handleErrorResponse(HttpURLConnection connection)
            throws IOException, JSONException {
        BufferedReader errorReader = new BufferedReader(
                new InputStreamReader(connection.getErrorStream()));
        String errorLine;
        StringBuilder errorResponse = new StringBuilder();
        while ((errorLine = errorReader.readLine()) != null) {
            errorResponse.append(errorLine);
        }
        errorReader.close();
        String errorResult = errorResponse.toString();
        System.out.println("Error Result: " + errorResult);
    }

    public String transcribeAudio(File audioFile) {
        File file = new File("../../../bin/audio/recording.wav");
        String transcription = null;
        try {
            URL url = new URI(API_ENDPOINT).toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            
            String boundary = "Boundary-" + System.currentTimeMillis();
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            connection.setRequestProperty("Authorization", "Bearer " + TOKEN);

            OutputStream outputStream = connection.getOutputStream();

            writeParameterToOutputStream(outputStream, "model", MODEL, boundary);
            writeFileToOutputStream(outputStream, file, boundary);

            outputStream.write(("\r\n--" + boundary + "--\r\n").getBytes());
            outputStream.flush();
            outputStream.close();

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                transcription = handleSuccessResponse(connection);
            } else {
                this.handleErrorResponse(connection);
            }
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transcription;
    }

}
