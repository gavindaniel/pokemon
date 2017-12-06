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

	Media hit;
	String filepath;
	MediaPlayer mediaPlayer;
   // Constructor
   public SoundClip(String filepath) throws UnsupportedAudioFileException {
     filepath=filepath;
      hit = new Media(new File(filepath).toURI().toString());
	  mediaPlayer = new MediaPlayer(hit);
	  mediaPlayer.play();
   }
   
   public void setSound(String filepath) {
	   filepath=filepath;
   }
   
   public MediaPlayer getMedia() {
	   return mediaPlayer;
   }
}