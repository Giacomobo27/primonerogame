package util;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class PlaySoundDemo {
    
    private static long lastPlayTime = 0; // Store last footstep time
    private static final int FOOTSTEP_INTERVAL = 300; // Time in milliseconds

    public static void playSound(String fileName) {
            long currentTime = System.currentTimeMillis();
            
            // Check if enough time has passed since last footstep sound
            if (currentTime - lastPlayTime < FOOTSTEP_INTERVAL) {
                return; // Skip playing sound too frequently
            }
            
            lastPlayTime = currentTime; // Update last play time
        try { 
			   // Load sound file from resources
               URL soundURL = PlaySoundDemo.class.getClassLoader().getResource(fileName);
               if (soundURL == null) {
                   System.err.println("Sound file not found: " + fileName);
                   return;
               }
   
               AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL);
               Clip clip = AudioSystem.getClip();
               clip.open(audioInputStream);
               clip.start(); // Play sound asynchronously
   
               // Add a listener to detect when sound finishes
               clip.addLineListener(event -> {
                   if (event.getType() == LineEvent.Type.STOP) {
                       clip.close();
                       System.out.println("Sound ended: " + fileName);
                   }
               });
   
           } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
               e.printStackTrace();
    }
    }
}
