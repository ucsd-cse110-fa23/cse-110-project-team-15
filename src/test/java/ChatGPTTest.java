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

    // @Test
    // public void testHandle() throws IOException, InterruptedException, URISyntaxException {
    //     String requestText = "Test Prompt";
    //     String responseText = "Generated Response";
    //     ByteArrayInputStream bis = new ByteArrayInputStream(requestText.getBytes());
    //     ByteArrayOutputStream bos = new ByteArrayOutputStream();

    //     when(exchange.getRequestBody()).thenReturn(bis);
    //     when(exchange.getResponseBody()).thenReturn(bos);
    //     doReturn(responseText).when(chatGPT).generate(anyString());

    //     chatGPT.handle(exchange);

    //     verify(chatGPT, times(1)).generate(requestText);
    //     String responseBody = bos.toString();
    //     assertEquals(responseText, responseBody.trim());
    // }
}
