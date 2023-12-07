import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import server.Whisper;


public class WhisperTest {

    private Whisper whisp;

    @BeforeEach
    public void setUp() {
        whisp = mock(Whisper.class);
    }

    @Test
    public void testGenerate() {
        File prompt = new File("0");
        String location = "9500 Gilman Dr, La Jolla, CA 92093";

        when(whisp.transcribeAudio(any(File.class))).thenReturn(location);
        String generatedText = whisp.transcribeAudio(prompt);

        assertNotNull(generatedText);
        assertEquals(location, generatedText);
    }

    @Test
    public void testWhisperE2E() {
        testGenerate();
    }
}
