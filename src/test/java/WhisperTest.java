// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.MockedStatic;
// import org.mockito.Mockito;

// import java.io.*;
// import java.net.*;
// import org.json.*;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.mockStatic;

// public class WhisperTest {

// private Whisper whisper;

// @BeforeEach
// public void setUp() {
// whisper = new Whisper();
// }

// @Test
// public void testTranscribeAudioSuccess() throws IOException {
// HttpURLConnection mockConnection = Mockito.mock(HttpURLConnection.class);
// ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
// ByteArrayInputStream inputStream = new
// ByteArrayInputStream("{\"text\":\"Transcribed text\"}".getBytes());

// Mockito.when(mockConnection.getOutputStream()).thenReturn(outputStream);
// Mockito.when(mockConnection.getInputStream()).thenReturn(inputStream);
// Mockito.when(mockConnection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);

// try (MockedStatic<URL> urlMock = Mockito.mockStatic(URL.class);
// MockedStatic<HttpURLConnection> connectionMock =
// Mockito.mockStatic(HttpURLConnection.class)) {

// urlMock.when(() -> new URL(any())).thenReturn(new
// URL("https://example.com"));
// connectionMock.when(() -> (HttpURLConnection)
// any()).thenReturn(mockConnection);

// String transcription = whisper.transcribeAudio();

// assertTrue(transcription.contains("Transcribed text"));
// }
// }

// @Test
// public void testTranscribeAudioFailure() throws IOException {
// HttpURLConnection mockConnection = Mockito.mock(HttpURLConnection.class);
// ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
// ByteArrayInputStream errorStream = new ByteArrayInputStream("Error
// message".getBytes());

// Mockito.when(mockConnection.getOutputStream()).thenReturn(outputStream);
// Mockito.when(mockConnection.getErrorStream()).thenReturn(errorStream);
// Mockito.when(mockConnection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_BAD_REQUEST);

// try (MockedStatic<URL> urlMock = Mockito.mockStatic(URL.class);
// MockedStatic<HttpURLConnection> connectionMock =
// Mockito.mockStatic(HttpURLConnection.class)) {

// urlMock.when(() -> new URL(any())).thenReturn(new
// URL("https://example.com"));
// connectionMock.when(() -> (HttpURLConnection)
// any()).thenReturn(mockConnection);

// String transcription = whisper.transcribeAudio();

// assertTrue(transcription.contains("Error Result: Error message"));
// }
// }

// @Test
// public void testTranscribeAudioException() throws IOException {
// try (MockedStatic<URL> urlMock = Mockito.mockStatic(URL.class)) {
// urlMock.when(() -> new URL(any())).thenThrow(new MalformedURLException());

// assertThrows(MalformedURLException.class, () -> whisper.transcribeAudio());
// }
// }
// }
