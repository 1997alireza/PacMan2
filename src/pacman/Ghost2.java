package pacman;


import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Ghost2  extends Ghost	//Pinky		-> Pink , Ambusher , Speedy 
{
	private boolean [][] map;
	public Ghost2( int x, int y, int dir,boolean [][] map,Pacman pacman,ArrayList<Ghost> ghosts) 
	{
		super(x, y, dir,map,pacman,ghosts);
		this.map = map;
		
		sleepTime = 105;
		
		images[0][0] = new ImageIcon("src/pacman/images/ghost/"+"2"+"R"+"1"+".png");
		images[0][1] = new ImageIcon("src/pacman/images/ghost/"+"2"+"R"+"2"+".png");
		
		images[1][0] = new ImageIcon("src/pacman/images/ghost/"+"2"+"U"+"1"+".png");
		images[1][1] = new ImageIcon("src/pacman/images/ghost/"+"2"+"U"+"2"+".png");
		
		images[2][0] = new ImageIcon("src/pacman/images/ghost/"+"2"+"L"+"1"+".png");
		images[2][1] = new ImageIcon("src/pacman/images/ghost/"+"2"+"L"+"2"+".png");
		
		images[3][0] = new ImageIcon("src/pacman/images/ghost/"+"2"+"D"+"1"+".png");
		images[3][1] = new ImageIcon("src/pacman/images/ghost/"+"2"+"D"+"2"+".png");
	}

	@Override
	protected void makeMove() 
	{
		boolean t = false;
		if(y==pacman.getY())
		{
			t = false;
			for(int i = x+23;i<x+6*23 && i<map[0].length;i+=1)
			{
				if(map[(y-4)/23][(i-3)*23])
					break;
				if(i==pacman.getX())
					t = true;
			}
			if(t)
				goRight();
			else
			{

				t = false;
				for(int i = x-23;i>x-6*23 && i>=0;i-=1)
				{
					if(map[(y-4)/23][(i-3)/23])
						break;
					if(i==pacman.getX())
						t = true;
				}
				if(t)
					goLeft();
			}
		}
		else if(x==pacman.getX())
		{
			t = false;
			for(int j = y+23;j<y+6*23 && j<map.length;j+=1)
			{
				if(map[(j-4)/23][(x-3)/23])
					break;
				if(j==pacman.getY())
					t = true;
			}
			if(t)
				goDown();
			else
			{
				t = false;
				for(int j = y-23;j>y-6*23 && j>=0;j-=1)
				{
					if(map[(j-4)/23][(x-3)/23])
						break;
					if(j==pacman.getY())
						t = true;
				}
				if(t)
					goUp();
			}
		}
		if(!t) //still not moving
			if(isStoped || System.currentTimeMillis()%5000<=200 || System.currentTimeMillis()%5000>=4800)
			{
				int d = (int)(Math.random() * 4 +1);
				switch(d)
				{
				case 1 :
					goRight();
					break;
				case 2 :
					goUp();
					break;
				case 3 :
					goLeft();
					break;
				case 4 :
					goDown();
					break;
				}
			}
		
		
	}
	
}
