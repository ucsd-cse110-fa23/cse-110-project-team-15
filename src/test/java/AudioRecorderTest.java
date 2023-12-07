import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import client.controller.AudioRecorder;

import javax.sound.sampled.*;
import java.io.File;

public class AudioRecorderTest {
    public AudioRecorder audioRecorder;

    @BeforeEach
    public void setUp() {
        audioRecorder = new AudioRecorder();
    }

    // @Test
    // public void testStartRecording() {
    //     AudioRecorder mockedAudioRecorder = Mockito.spy(audioRecorder);
    //     AudioFormat audioFormat = new AudioFormat(16000, 16, 1, true, false);

    //     // Mock the TargetDataLine
    //     TargetDataLine targetDataLine = Mockito.mock(TargetDataLine.class);

    //     try {
    //         Mockito.when(AudioSystem.getLine(Mockito.any())).thenReturn(targetDataLine);

    //         // mockedAudioRecorder.startRecording();

    //         assertTrue(mockedAudioRecorder.getIsRecording());

    //         // Verify that the TargetDataLine was opened and started
    //         Mockito.verify(targetDataLine).open(audioFormat);
    //         Mockito.verify(targetDataLine).start();
    //     } catch (LineUnavailableException e) {
    //         fail("LineUnavailableException should not be thrown");
    //     }
    // }

    // @Test
    // public void testStopRecording() {
    //     AudioRecorder mockedAudioRecorder = Mockito.spy(audioRecorder);
    //     mockedAudioRecorder.setIsRecording(true);

    //     // Mock the TargetDataLine
    //     TargetDataLine targetDataLine = Mockito.mock(TargetDataLine.class);
    //     mockedAudioRecorder.setTargetDataLine(targetDataLine);

    //     // mockedAudioRecorder.stopRecording();

    //     assertFalse(mockedAudioRecorder.getIsRecording());

    //     // Verify that the TargetDataLine was stopped and closed
    //     Mockito.verify(targetDataLine).stop();
    //     Mockito.verify(targetDataLine).close();
    // }
    
}
