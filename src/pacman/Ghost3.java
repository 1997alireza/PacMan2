package pacman;


import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Ghost3  extends Ghost	//Akabei	-> Red , Chaser , Shadow
{
	private boolean [][] map;
	public Ghost3(int x, int y, int dir,boolean [][] map,Pacman pacman,ArrayList<Ghost> ghosts) 
	{
		super(x, y, dir,map,pacman,ghosts);
		this.map = map;

		sleepTime = 160;
		
		images[0][0] = new ImageIcon("src/pacman/images/ghost/"+"3"+"R"+"1"+".png");
		images[0][1] = new ImageIcon("src/pacman/images/ghost/"+"3"+"R"+"2"+".png");
		
		images[1][0] = new ImageIcon("src/pacman/images/ghost/"+"3"+"U"+"1"+".png");
		images[1][1] = new ImageIcon("src/pacman/images/ghost/"+"3"+"U"+"2"+".png");
		
		images[2][0] = new ImageIcon("src/pacman/images/ghost/"+"3"+"L"+"1"+".png");
		images[2][1] = new ImageIcon("src/pacman/images/ghost/"+"3"+"L"+"2"+".png");
		
		images[3][0] = new ImageIcon("src/pacman/images/ghost/"+"3"+"D"+"1"+".png");
		images[3][1] = new ImageIcon("src/pacman/images/ghost/"+"3"+"D"+"2"+".png");

	}

	@Override
	protected void makeMove() 
	{
		int x = (this.x-4)/23;
		int y = (this.y-3)/23;
		int xP,yP;
		try
		{
			xP = (pacman.getX()-4)/23 ;
			yP = (pacman.getY()-3)/23;
		}catch(NullPointerException e)
		{
			xP = (pacman.getX()-4)/23 ;
			yP = (pacman.getY()-3)/23;
		};
		Dijkstra dijkstra = new Dijkstra(map);
		int d1=-1,d2=-1,d3=-1,d4=-1;
		if(x>0)
			d1 = dijkstra.getDistance(x-1, y, xP, yP); //left
		if(x<map[0].length-1)
			d2 = dijkstra.getDistance(x+1, y, xP, yP); //right
		if(y>0)
			d3 = dijkstra.getDistance(x, y-1, xP, yP); //up
		if(y<map.length-1)
			d4 = dijkstra.getDistance(x, y+1, xP, yP); //down
		
		if(d1>map.length*map[0].length)
			d1 = -1;
		if(d2>map.length*map[0].length)
			d2 = -1;
		if(d3>map.length*map[0].length)
			d3 = -1;
		if(d4>map.length*map[0].length)
			d4 = -1;
		if(d1>=0 && (d1<=d2||d2<0) && (d1<=d3||d3<0) && (d1<=d4||d4<0))
			goLeft();
		else if(d2>=0 && (d2<=d3||d3<0) && (d2<=d4||d4<0))
			goRight();
		else if(d3>=0 && (d3<=d4||d4<0))
			goUp();
		else if(d4>=0)
			goDown();
			
	}
}
