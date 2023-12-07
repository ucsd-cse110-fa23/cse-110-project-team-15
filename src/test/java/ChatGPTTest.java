import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import com.sun.net.httpserver.HttpExchange;

import server.ChatGPT;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;


public class ChatGPTTest {

    private ChatGPT chatGPT;
    @Mock
    private HttpExchange exchange;
    @Mock
    private InputStream inputStream;
    @Mock
    private OutputStream outputStream;

    @BeforeEach
    public void setUp() throws IOException {
        chatGPT = mock(ChatGPT.class);
        // when(exchange.getRequestBody()).thenReturn(inputStream);
        // when(exchange.getResponseBody()).thenReturn(outputStream);
    }

    @Test
    public void testGenerateSuccess() throws IOException, InterruptedException, URISyntaxException {
        String prompt = "What is the smallest country?";
        String expectedResponse = "Generated Text";

        when(chatGPT.generate(anyString())).thenReturn(expectedResponse);
        String response = chatGPT.generate(prompt);

        assertNotNull(response);
        assertEquals(expectedResponse, response);
    }

        @Test
        public void testHandle() throws IOException, InterruptedException, URISyntaxException {
            String requestText = "Test Prompt";
            String responseText = "Generated Response";
        
            InputStream bis = new ByteArrayInputStream(requestText.getBytes(StandardCharsets.UTF_8));
        
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bos.write(responseText.getBytes(StandardCharsets.UTF_8));
        
            String responseBody = bos.toString(StandardCharsets.UTF_8);
        
            assertEquals(responseText, responseBody);
        }

    @Test
    public void testChatGPTE2E() throws IOException, InterruptedException, URISyntaxException {
        setUp();
        testGenerateSuccess();
    }
}
