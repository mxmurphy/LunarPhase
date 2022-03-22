package com.spaceforce.Sound;

import com.spaceforce.util.ui.View;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

import static java.lang.ClassLoader.getSystemResourceAsStream;

public enum Sounds {
    MUSIC("SFX/from-the-dust-cosmos.wav"), DOOR("SFX/DoorSFX.wav"), SCREAM("SFX/Wilhelm.wav");


    // Nested class for specifying volume
    public static enum Volume {
        MUTE, LOW, MEDIUM, HIGH
    }

    public static Volume volume = Volume.LOW;

    // Each sound effect has its own clip, loaded with its own sound file.
    private Clip clip;
    public static boolean playing;
    public static boolean sound = true;

    // Constructor to construct each element of the enum with its own sound file.
    Sounds(String soundFileName) {
        try {
            // Use URL (instead of File) to read from disk and JAR.
            URL url = this.getClass().getClassLoader().getResource(soundFileName);
            // Set up an audio input stream piped from the sound file.
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getSystemResourceAsStream(soundFileName));
            // Get a clip resource.
            clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Play or Re-play the sound effect from the beginning, by rewinding.
    public void playMusic() {
        if (volume != Volume.MUTE) {
            if (clip.isRunning())
                clip.stop();   // Stop the player if it is still running
            clip.setFramePosition(0); // rewind to the beginning
            clip.start();     // Start playing
        }
        if (this == MUSIC) {
            playing = true;
        }
        View.renderText("Playing music.\n");
    }

    public void stopMusic() {
        clip.stop();
        playing = false;
        View.renderText("Stopping music.\n");
    }

    // Optional static method to pre-load all the sound files.
    static void init() {
        values(); // calls the constructor for all the elements
    }
    public void playSFX(){
        if (volume != Volume.MUTE&&sound) {
            if (clip.isRunning())
                clip.stop();   // Stop the player if it is still running
            clip.setFramePosition(0); // rewind to the beginning
            clip.start();     // Start playing
        }
        if(this!=MUSIC){
            sound=true;
        }
    }
}

//playMusic
//stopMusic
//playSFX
//stopSFX
//individual methods for each SFX