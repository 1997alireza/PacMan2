package pacman;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Pacman implements KeyListener
{
	private ImageIcon [] images;
	private ImageIcon image;
	private boolean running = true;
	public void setRunning(boolean running) {
		this.running = running;
	}

	private JPanel panel; // have setter for setting
	private int x,y;
	private int dir=1; // 1->Right , 2->Up , 3->Left , 4->Down
	private int bufferDir = 0;
	private boolean map [][];
	private Rectangle rec;



	public Pacman(int x,int y,boolean [][] map)
	{
		this.map = map;
		this.x=x;
		this.y=y;
		images = new ImageIcon[4];
		images[0] = new ImageIcon("src/pacman/images/pac/4.png");
		images[1] = new ImageIcon("src/pacman/images/pac/1R.png");
		images[2] = new ImageIcon("src/pacman/images/pac/2R.png");
		images[3] = new ImageIcon("src/pacman/images/pac/3R.png");
		image = images[1];
		rec = new Rectangle(x, y, 17, 17);
	}
	
	private boolean isMovable = true;
	public void moveMouth()
	{
		boolean asec = true;
		for(int i=1;running;)
		{
			if(isMovable)
			{
				if(asec)
					i++;
				else
					i--;
				if(i==4)
				{
					i=3;
					asec = false;
				}
				if(i==-1)
				{
					i=0;
					asec = true;
				}
			}
			else
			{
				i=1;
			}
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {}
			image = images[i];
			panel.repaint();
		}
	}


	public void move()
	{
		while(running)
		{
			
			switch (bufferDir)
			{
			
			case 1: // Right
				if (y%23==3 && !map[((x-27)/23)+1+1][((y-26)/23)+1])
				{
					goRight();
				}
				break;

			case 2: // Up
				if (x%23==4 && !map[((x-27)/23)+1][((y-26)/23)-1+1]) 
				{
					goUp();
				}
				break;

			case 3: // Left
				if (y%23==3 && !map[((x-27)/23)-1+1][((y-26)/23)+1]) 
				{
					goLeft();
				}
				break;

			case 4: // Down
				if (x%23==4 && !map[((x-27)/23)+1][((y-26)/23)+1+1]) 
				{
					goDown();
				}
				break;
					
			}
			
			boolean canMove = false;
				switch (dir)
				{
				case 1: // Right
					if (y%23==3 && !map[((x-27)/23)+1+1][((y-26)/23)+1])
					{
						isMovable = true;
						for(int i=0;i<23 && running;i++)
						{
							try 
							{
								Thread.sleep(8);
							} catch (InterruptedException e) {}
							x++;

							rec.x = x;
						}
						canMove = true;
					}
					break;
	
				case 2: // Up
					if (x%23==4 && !map[((x-27)/23)+1][((y-26)/23)-1+1]) 
					{
						isMovable = true;
						for(int i=0;i<23 && running;i++)
						{
							try 
							{
								Thread.sleep(8);
							} catch (InterruptedException e) {}
							y--;

							rec.y = y;
						}
						canMove = true;
					}
					break;
	
				case 3: // Left
					if (y%23==3 && !map[((x-27)/23)-1+1][((y-26)/23)+1]) 
					{
						isMovable = true;
						for(int i=0;i<23 && running;i++)
						{
							try 
							{
								Thread.sleep(8);
							} catch (InterruptedException e) {}
							x--;

							rec.x = x;
						}
						canMove = true;
					}
					break;
	
				case 4: // Down
					if (x%23==4 && !map[((x-27)/23)+1][((y-26)/23)+1+1]) 
					{
						isMovable = true;
						for(int i=0;i<23 && running;i++)
						{
							try 
							{
								Thread.sleep(8);
							} catch (InterruptedException e) {}
							y++;

							rec.y = y;
						}
						canMove = true;
					}
					break;
						
				}
				
				if(!canMove)
					isMovable = false;
				
				
		}
	}
	public int getX() {
		return x;
	}
	public void setX(int x) 
	{
		this.x = x;
		rec.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) 
	{
		this.y = y;
		rec.y = y;
	}
	public Image getImage() {
		return image.getImage();
	}
	public void setPanel(JPanel panel) 
	{
		this.panel = panel;
		(new Thread(() -> this.moveMouth())).start();
		(new Thread(() -> this.move())).start();
	}
	
	public Rectangle getRec() {
		return rec;
	}
	@Override
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode()==KeyEvent.VK_RIGHT)
		{
			bufferDir = 1;
		}
		if(e.getKeyCode()==KeyEvent.VK_UP)
		{
			bufferDir = 2;
		}
		if(e.getKeyCode()==KeyEvent.VK_LEFT)
		{
			bufferDir = 3;
		}
		if(e.getKeyCode()==KeyEvent.VK_DOWN)
		{
			bufferDir = 4;
		}
		if(e.getKeyCode()== KeyEvent.VK_ESCAPE) {
			panel.dispatchEvent(new ComponentEvent(panel, Messages.exit));
			running=false;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e)
	{

	}

	@Override
	public void keyTyped(KeyEvent e)
	{

	}
	
	private void goRight()
	{
		dir=1;
		images[1] = new ImageIcon("src/pacman/images/pac/1R.png");
		images[2] = new ImageIcon("src/pacman/images/pac/2R.png");
		images[3] = new ImageIcon("src/pacman/images/pac/3R.png");
	}
	private void goUp()
	{
		dir=2;
		images[1] = new ImageIcon("src/pacman/images/pac/1U.png");
		images[2] = new ImageIcon("src/pacman/images/pac/2U.png");
		images[3] = new ImageIcon("src/pacman/images/pac/3U.png");
	}
	private void goLeft()
	{
		dir=3;
		images[1] = new ImageIcon("src/pacman/images/pac/1L.png");
		images[2] = new ImageIcon("src/pacman/images/pac/2L.png");
		images[3] = new ImageIcon("src/pacman/images/pac/3L.png");
	}
	private void goDown()
	{
		dir=4;
		images[1] = new ImageIcon("src/pacman/images/pac/1D.png");
		images[2] = new ImageIcon("src/pacman/images/pac/2D.png");
		images[3] = new ImageIcon("src/pacman/images/pac/3D.png");
	}
	public void reset(int x,int y)
	{
		this.x = x;
		rec.x = x;
		this.y = y;
		rec.y = y;
		dir = 1;
		images[1] = new ImageIcon("src/pacman/images/pac/1R.png");
		images[2] = new ImageIcon("src/pacman/images/pac/2R.png");
		images[3] = new ImageIcon("src/pacman/images/pac/3R.png");
		bufferDir = 0;
		image = images[1];
		running = true;
		(new Thread(() -> this.moveMouth())).start();
		(new Thread(() -> this.move())).start();
	}
}
