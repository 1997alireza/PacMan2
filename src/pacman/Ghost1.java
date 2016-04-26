package pacman;


import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Ghost1  extends Ghost	//Guzuta	-> Orange , Ignorance , Slow 
{

	public Ghost1(int x, int y, int dir,boolean [][] map,Pacman pacman,ArrayList<Ghost> ghosts) 
	{
		super(x, y, dir,map,pacman,ghosts);
		
		sleepTime = 190;
		
		images[0][0] = new ImageIcon("src/pacman/images/ghost/"+"1"+"R"+"1"+".png");
		images[0][1] = new ImageIcon("src/pacman/images/ghost/"+"1"+"R"+"2"+".png");
		
		images[1][0] = new ImageIcon("src/pacman/images/ghost/"+"1"+"U"+"1"+".png");
		images[1][1] = new ImageIcon("src/pacman/images/ghost/"+"1"+"U"+"2"+".png");
		
		images[2][0] = new ImageIcon("src/pacman/images/ghost/"+"1"+"L"+"1"+".png");
		images[2][1] = new ImageIcon("src/pacman/images/ghost/"+"1"+"L"+"2"+".png");
		
		images[3][0] = new ImageIcon("src/pacman/images/ghost/"+"1"+"D"+"1"+".png");
		images[3][1] = new ImageIcon("src/pacman/images/ghost/"+"1"+"D"+"2"+".png");

	}

	@Override
	protected void makeMove() 
	{
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
