package br.com.sne.sistema.gui.util.components;

import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.JOptionPane;

public class Beep extends Thread {

	  public void run() {
          try {
        	 URL soundURL = getClass().getClassLoader().getResource("images/beep.wav");
             AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL);
             AudioFormat audioFormat = audioInputStream.getFormat();
             DataLine.Info dataLineInfo = new DataLine.Info( Clip.class, audioFormat);
             Clip clip = (Clip) AudioSystem.getLine(dataLineInfo);
             clip.open(audioInputStream);
             clip.start();
          } catch (Exception e) {
             e.printStackTrace();
          }
       }
	

}
