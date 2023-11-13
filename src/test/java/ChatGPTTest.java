import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import server.ChatGPT;

import java.io.IOException;
import java.net.URISyntaxException;

public class ChatGPTTest {

    private ChatGPT chatGPT;

    @BeforeEach
    public void setUp() {
        chatGPT = mock(ChatGPT.class);
    }

    @Test
    public void testGenerate() throws IOException, InterruptedException, URISyntaxException {
        String prompt = "Where is UC San Diego located?";
        String location = "9500 Gilman Dr, La Jolla, CA 92093";

        when(chatGPT.generate(any(Class.class))).thenReturn(location);
        String generatedText = chatGPT.generate(new HttpExchange());

        assertNotNull(generatedText);
        assertEquals(location, generatedText);

        System.out.println("Generated Text: " + generatedText);
    }
}
