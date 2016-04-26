package pacman;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Wall 
{
	private int x,y;
	private ImageIcon image;
	public Wall(int x,int y, boolean edge)
	{
		image = (!edge)? new ImageIcon("src/pacman/images/wall/wall.png") : new ImageIcon("src/pacman/images/wall/wall_edge.png");
		this.x=x;
		this.y=y;
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
}
