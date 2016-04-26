package pacman;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public abstract class Ghost 
{
	protected int x,y;
	protected int sleepTime;
	protected int pos = 1;
	private boolean available = true;
	public void setAvailable(boolean available) {
		this.available = available;
	}
	protected boolean isStoped = false;
	private boolean [][] map;
	protected Pacman pacman;
	protected int dir; // 1->Right , 2->Up , 3->Left , 4->Down
	protected ImageIcon [][] images = new ImageIcon[4][2];
	private Rectangle rec ;
	protected ArrayList<Ghost> ghosts;
	public Ghost(int x,int y,int dir ,boolean [][] map,Pacman pacman,ArrayList<Ghost> ghosts)
	{
		this.ghosts = ghosts;
		this.pacman = pacman;
		this.map = map;
		this.x = x;
		this.y = y;
		this.dir = dir;
		rec = new Rectangle(x, y+1, 17, 15);
		(new Thread( this :: footMove)).start();
		(new Thread( this :: move)).start();
	}	
	private void move()
	{
		while(available)
		{
			isStoped = true;
			switch (dir)
			{
			case 1: // Right
				if (available &&y%23==3 && !map[((y-26)/23)+1][((x-27)/23)+1+1])
				{
					for(int i=0;i<23 && available;i++)
					{
						try 
						{
							Thread.sleep(sleepTime/10);
						} catch (InterruptedException e) {}
						if(available)
						{
							boolean t = false;
							while(!t)
							{
								t = true;
								for(int k=this.getOrderNum()+1;k<ghosts.size();k++)
									if(ghosts.get(k).getRec().intersects(this.rec))
									{
										t = false;
										break;
									}
							}
							x++;
	
							rec.x = x;
						}
					}
					isStoped = false;
				}
				break;

			case 2: // Up
				if (available &&x%23==4 && !map[((y-26)/23)-1+1][((x-27)/23)+1]) 
				{
					for(int i=0;i<23 && available;i++)
					{
						try 
						{
							Thread.sleep(sleepTime/10);
						} catch (InterruptedException e) {}
						if(available)
						{
							boolean t = false;
							while(!t)
							{
								t = true;
								for(int k=this.getOrderNum()+1;k<ghosts.size();k++)
									if(ghosts.get(k).getRec().intersects(this.rec))
									{
										t = false;
										break;
									}
							}
							y--;
	
							rec.y = y;
						}
					}
					isStoped = false;
				}
				break;

			case 3: // Left
				if (available &&y%23==3 && !map[((y-26)/23)+1][((x-27)/23)-1+1] ) 
				{
					for(int i=0;i<23 && available;i++)
					{
						try 
						{
							Thread.sleep(sleepTime/10);
						} catch (InterruptedException e) {}
						if(available)
						{
							boolean t = false;
							while(!t)
							{
								t = true;
								for(int k=this.getOrderNum()+1;k<ghosts.size();k++)
									if(ghosts.get(k).getRec().intersects(this.rec))
									{
										t = false;
										break;
									}
							}
							x--;
	
							rec.x = x;
						}
					}
					isStoped = false;
				}
				break;

			case 4: // Down
				if (available && x%23==4 && !map[((y-26)/23)+1+1][((x-27)/23)+1] ) 
				{
					for(int i=0;i<23 && available;i++)
					{
						try 
						{
							Thread.sleep(sleepTime/10);
						} catch (InterruptedException e) {}
						if(available)
						{
							boolean t = false;
							while(!t)
							{
								t = true;
								for(int k=this.getOrderNum()+1;k<ghosts.size();k++)
									if(ghosts.get(k).getRec().intersects(this.rec))
									{
										t = false;
										break;
									}
							}
							y++;
	
							rec.y = y;
						}
					}
					isStoped = false;
				}
				break;
			}
			makeMove();
			
		}
	}
	
	protected String dirToString(int dir)
	{
		String d="";
		switch(dir)
		{
		case 1:
			d = "R";
			break;
		case 2:
			d = "U";
			break;
		case 3:
			d = "L";
			break;
		case 4:
			d = "D";
		}
		return d;
	}

	private void footMove()
	{
		while(available)
		{
			pos = (pos%2)+1;
			try 
			{
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {}
		}
	}
	protected abstract void makeMove();
	public Image getImage() 
	{
		return images[dir-1][pos-1].getImage();
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	public void setX(int x) {
		this.x = x;
		this.rec.x = x;
	}
	public void setY(int y) {
		this.y = y;
		this.rec.y = y+1;
	}
	public Rectangle getRec() {
		return rec;
	}
	
	protected void goRight()
	{
		dir = 1;
	}
	protected void goUp()
	{
		dir = 2;
	}
	protected void goLeft()
	{
		dir = 3;
	}
	protected void goDown()
	{
		dir = 4;
	}
	public void setPacman(Pacman pacman)
	{
		this.pacman = pacman;
	}
	private int getOrderNum()
	{
		for(int i=0;i<ghosts.size();i++)
			if(ghosts.get(i)==this)
				return i;
		return -1;
	}
}
