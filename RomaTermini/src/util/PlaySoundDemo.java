package util;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class PlaySoundDemo {

    private static Clip clip;
    
    private static long lastPlayTime = 0; // Store last footstep time
    private static final int FOOTSTEP_INTERVAL = 300; // Time in milliseconds

    public static void playSound(String fileName) {
            long currentTime = System.currentTimeMillis();
            
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
                       //System.out.println("Sound ended: " + fileName);
                   }
               });
   
           } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
               e.printStackTrace();
    }
    }


    
    public static void playSound(String fileName,  float volume) {
        long currentTime = System.currentTimeMillis();
        
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

           
           FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
           float minVolume = gainControl.getMinimum(); // Lowest possible volume
           float maxVolume = gainControl.getMaximum(); // Highest possible volume
           
           // Convert percentage volume (0.0 to 1.0) into decibels
           float gain = minVolume + (maxVolume - minVolume) * volume;
           gainControl.setValue(gain); // Apply the volume change


           clip.start(); // Play sound asynchronously

           // Add a listener to detect when sound finishes
           clip.addLineListener(event -> {
               if (event.getType() == LineEvent.Type.STOP) {
                   clip.close();
                   //System.out.println("Sound ended: " + fileName);
               }
           });

       } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
           e.printStackTrace();
}
}

    public static void playBackgroundSound(String fileName, float volume) {
        new Thread(() -> {
    try { 
           // Load sound file from resources
           URL soundURL = PlaySoundDemo.class.getClassLoader().getResource(fileName);
           if (soundURL == null) {
               System.err.println("Sound file not found: " + fileName);
               return;
           }

           AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL);
           clip = AudioSystem.getClip();
           clip.open(audioInputStream);

           FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
           float minVolume = gainControl.getMinimum(); // Lowest possible volume
           float maxVolume = gainControl.getMaximum(); // Highest possible volume
           
           // Convert percentage volume (0.0 to 1.0) into decibels
           float gain = minVolume + (maxVolume - minVolume) * volume;
           gainControl.setValue(gain); // Apply the volume change

           clip.loop(Clip.LOOP_CONTINUOUSLY);
           clip.start(); // Play sound asynchronously

       } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
           e.printStackTrace();
}
        }).start();
}

public static void stopBackgroundSound() {
    if (clip != null && clip.isRunning()) {
        clip.stop();
        clip.close();
        System.out.println("Background music stopped.");
    }
}


}
