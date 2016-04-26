package pacman;

import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;

public class Food
{
	private int x,y;
	private ImageIcon image;
	private Rectangle rec ;
	private boolean available;
	public void setRec(Rectangle rec) {
		this.rec = rec;
	}
	public Food(int x,int y)
	{
		this.x=x;
		this.y=y;
		rec = new Rectangle(x*23+10, y*23+10, 3,3);
		available = true;
		image = new ImageIcon("src/pacman/images/food/food.png");
	}
	public void eat()
	{
		available = false;
		(new Thread(this :: eatSound)).start();
	}
	private void eatSound()
	{
		Clip clip = null;
		try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e1) {
			// TODO Auto-generated catch block
		}
        AudioInputStream inputStream;
		try {
			File sound = new File("src/pacman/sounds/eating.wav");
			inputStream = AudioSystem.getAudioInputStream(sound);
	        try {
				clip.open(inputStream);
			} catch (LineUnavailableException e) {
				// TODO Auto-generated catch block
			}
	        clip.start();
	        try 
	        {
				Thread.sleep(300);
			} catch (InterruptedException e) {}
	        clip.stop();
		} catch (UnsupportedAudioFileException| IOException e) {}

	}
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public Image getImage() {
		return image.getImage();
	}
	public Rectangle getRec() {
		return rec;
	}
}
