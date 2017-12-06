package controller;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;
import javax.swing.*;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

// To play sound using Clip, the process need to be alive.
// Hence, we use a Swing application.
public class SoundClip {

   // Constructor
   public SoundClip(String filepath) throws UnsupportedAudioFileException {
     
      Media hit = new Media(new File(filepath).toURI().toString());
	  MediaPlayer mediaPlayer = new MediaPlayer(hit);
	  mediaPlayer.play();
   }
}