package client.controller;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class AudioRecorder {
    TargetDataLine targetDataLine;
    private File audioFile;
    boolean isRecording;

    public AudioRecorder() {
        isRecording = false;
    }

    public void startRecording() {
        if (isRecording)
            return;
        isRecording = true;
        AudioFormat audioFormat = new AudioFormat(16000, 16, 1, true, false);
        int bufferSize = 4096;
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat, bufferSize);
        try {
            targetDataLine = (TargetDataLine) AudioSystem.getLine(info);
            targetDataLine.open(audioFormat);
            targetDataLine.start();
            audioFile = new File("./bin/audio/recording.wav");

            Thread recordingThread = new Thread(() -> {
                try (AudioInputStream audioInputStream = new AudioInputStream(targetDataLine)) {
                    AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, audioFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            recordingThread.start();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void stopRecording() {
        if (isRecording) {
            isRecording = false;
            if (targetDataLine != null) {
                targetDataLine.stop();
                targetDataLine.close();
            }
        }
    }

    public boolean getIsRecording() {
        return isRecording;
    }

    public void setIsRecording(boolean recording) {
        isRecording = recording;
    }

    public TargetDataLine getTargetDataLine() {
        return targetDataLine;
    }

    public void setTargetDataLine(TargetDataLine targetDataLine) {
        this.targetDataLine = targetDataLine;
    }

    // public void setIsRecording(TargetDataLine recording) {
    // isRecording = recording;
    // }
}
