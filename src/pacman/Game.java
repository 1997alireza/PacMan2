package pacman;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class Game extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Pacman pacman ;
	private boolean map [][] ;
	private ArrayList <Wall> walls = new ArrayList<Wall>();
	int maxRow=0,mapLines=0;
	public Game(String mapS,int mapLines,boolean ghost1,boolean ghost2,boolean ghost3,String playerName)
	{
		
		Clip clip = null;
		try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e1) {
			// TODO Auto-generated catch block
		}
        AudioInputStream inputStream;
		try {
			File sound = new File("src/pacman/sounds/start.wav");
			inputStream = AudioSystem.getAudioInputStream(sound);
	        try {
				clip.open(inputStream);
			} catch (LineUnavailableException e2) {
				// TODO Auto-generated catch block
			}
	        clip.start();
		} catch(UnsupportedAudioFileException | IOException e2) {}
		//for play sound
		
		//(new Thread(this :: runPlaySound)).start();
		mapS = mapS+'\n';
		int maxRow=0;
		for(int i=0,j=0;i<mapS.length();i++)
		{
			if(mapS.charAt(i)=='\n')
			{
				maxRow = (j>maxRow)?j:maxRow;
				j=0;
			}
			j++;
		}
		maxRow--;
		this.maxRow = maxRow;
		this.mapLines = mapLines;
		map = new boolean[mapLines+2][maxRow+2];
		for(int k=0,i=0,j=0;k<mapS.length();k++)
		{
			if(mapS.charAt(k)=='\n')
			{
				i++;
				j=0;
			}
			else
			{
				map[i+1][j+1] = (mapS.charAt(k)=='x')?true:false; // false -> blank , true -> wall 
				j++;
			}
		}
		for(int i=0;i<map.length;i++)
			for(int j=0;j<map[i].length;j++)
			{
				if(i==0 || j==0 || i==mapLines+1 || j==maxRow+1)
					map[i][j] = true;
				if(map[i][j])
					walls.add(new Wall(j,i,(i==0 || j==0 || i==mapLines+1 || j==maxRow+1)));
			}
		setLayout(null);
		setResizable(false);
		setBackground(Color.BLACK);
		boolean mapT [][] = new boolean [maxRow+2][mapLines+2];
		for(int i=0;i<map.length;i++)
			for(int j=0;j<map[i].length;j++)
				mapT[j][i] = map[i][j];
		pacman = new Pacman( 50, 49,mapT);
		addKeyListener(pacman);
		setSize(map[0].length*23+8,map.length*23+37+45);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		HealthPanel healthPanel = new HealthPanel(map[0].length*23+8,map.length*23+2,3,playerName);
		getContentPane().add(healthPanel);
		GamePanel panel = new GamePanel(map,pacman,walls,ghost1,ghost2,ghost3,this,healthPanel);
		healthPanel.setGamePanel(panel);
		getContentPane().add(panel);

		
		setVisible(true);
		
	}
//	private void runPlaySound()
//	{
//		try 
//		{
//			Thread.sleep(4000);
//		} catch (InterruptedException e3) {}
//		
//		while(true)
//		{
//			Clip clip = null;
//			try {
//				clip = AudioSystem.getClip();
//			} catch (LineUnavailableException e1) {
//				// TODO Auto-generated catch block
//			}
//	        AudioInputStream inputStream;
//			try {
//				File sound = new File("src/pacman/sounds/moving.wav");
//				inputStream = AudioSystem.getAudioInputStream(sound);
//		        try {
//					clip.open(inputStream);
//				} catch (LineUnavailableException e2) {
//					// TODO Auto-generated catch block
//				}
//		        clip.start();
//			} catch(UnsupportedAudioFileException | IOException e2) {}
//			try
//			{
//				Thread.sleep(1530);
//			} catch (InterruptedException e) {}
//		}
//	}
}
class GamePanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean map [][];
	private Pacman pacman;
	private ArrayList <Food> foods;
	private ArrayList <Ghost> ghosts; 
	private int foodNum;
	private ArrayList <Wall> walls;
	private Game parent;
	private ArrayList <int []> mainGroup;
	private HealthPanel healthPanel;
	public GamePanel(boolean map[][],Pacman pacman,ArrayList <Wall> walls , boolean ghost1,boolean ghost2,boolean ghost3, Game parent,HealthPanel healthPanel)
	{
		
		
		this.parent = parent;
		this.walls = walls;
		this.pacman=pacman;
		this.healthPanel = healthPanel;
		setBorder(new LineBorder(new Color(17,47,226),3));
		this.map = map;
		setLayout(null);
		setSize(map[0].length*23+2,map.length*23+2);
		setLocation(0,0);
		boolean [][] mapFG = new boolean[map.length][map[0].length];
		for(int i=0;i<map.length;i++)
			for(int j=0;j<map[0].length;j++)
				mapFG[i][j]=map[i][j];
		
		
		ArrayList <ArrayList <int []>> groupRooms = new ArrayList<ArrayList<int []>>();
		while(true)
		{
			ArrayList <int[]> thisGroup = new ArrayList<int[]>();
			for(int i=1;i<mapFG.length-1;i++)
				for(int j=1;j<mapFG[0].length;j++)
				{
					if(mapFG[i][j]==false)
					{
						if(thisGroup.size()==0)
						{
							int [] temp = {i,j};
							thisGroup.add(temp);
							mapFG[i][j]=true;
						}
						else
						{
							if(isWay(thisGroup.get(0)[0],thisGroup.get(0)[1], i,j))
							{
								int [] temp = {i,j};
								thisGroup.add(temp);
								mapFG[i][j]=true;
							}
						}
					}
				}
			
			groupRooms.add(thisGroup);
			boolean haveRemaned = false;
			for(int i=1;i<mapFG.length-1;i++)
				for(int j=1;j<mapFG[0].length;j++)
					if(mapFG[i][j]==false)
						haveRemaned = true;
			if(!haveRemaned)
			{
				break;
			}
		}
		
		mainGroup = groupRooms.get(0);
		for(int i=1;i<groupRooms.size();i++)
			if(groupRooms.get(i).size()>mainGroup.size())
				mainGroup = groupRooms.get(i);
		
		ArrayList <int[]> mainGroupT = new ArrayList<int[]>();
		for(int i=0;i<mainGroup.size();i++)
			mainGroupT.add(mainGroup.get(i));
		
		int pacStart = (int)(Math.random() * mainGroupT.size()); //for pacman
		pacman.setX(mainGroupT.get(pacStart)[1]*23+4);
		pacman.setY(mainGroupT.get(pacStart)[0]*23+3);
		mainGroupT.remove(pacStart);
		ghosts = new ArrayList<Ghost>();			//for ghosts
							
		if(ghost1)
		{
			int gStart = (int)(Math.random() * mainGroupT.size());
			int dStart = (int)(Math.random() * 4) +1;
			ghosts.add(new Ghost1(mainGroupT.get(gStart)[1]*23+4,mainGroupT.get(gStart)[0]*23+3,dStart,map,pacman,ghosts));
			mainGroupT.remove(gStart);
			
			gStart = (int)(Math.random() * mainGroupT.size());
			dStart = (int)(Math.random() * 4) +1;
			ghosts.add(new Ghost1(mainGroupT.get(gStart)[1]*23+4,mainGroupT.get(gStart)[0]*23+3,dStart,map,pacman,ghosts));
			mainGroupT.remove(gStart);
		}
		if(ghost2)
		{
			int gStart = (int)(Math.random() * mainGroupT.size());
			int dStart = (int)(Math.random() * 4) +1;
			ghosts.add(new Ghost2(mainGroupT.get(gStart)[1]*23+4,mainGroupT.get(gStart)[0]*23+3,dStart,map,pacman,ghosts));
			mainGroupT.remove(gStart);
			
			gStart = (int)(Math.random() * mainGroupT.size());
			dStart = (int)(Math.random() * 4) +1;
			ghosts.add(new Ghost2(mainGroupT.get(gStart)[1]*23+4,mainGroupT.get(gStart)[0]*23+3,dStart,map,pacman,ghosts));
			mainGroupT.remove(gStart);
		}
		if(ghost3)
		{
			int gStart = (int)(Math.random() * mainGroupT.size());
			int dStart = (int)(Math.random() * 4) +1;
			ghosts.add(new Ghost3(mainGroupT.get(gStart)[1]*23+4,mainGroupT.get(gStart)[0]*23+3,dStart,map,pacman,ghosts));
			mainGroupT.remove(gStart);
			
			gStart = (int)(Math.random() * mainGroupT.size());
			dStart = (int)(Math.random() * 4) +1;
			ghosts.add(new Ghost3(mainGroupT.get(gStart)[1]*23+4,mainGroupT.get(gStart)[0]*23+3,dStart,map,pacman,ghosts));
			mainGroupT.remove(gStart);
		}		
		foods = new ArrayList<Food>(); //for foods
		for(int i=0;i<mainGroupT.size();i++)
		{
			if(Math.random()>0.6)
				foods.add(new Food(mainGroupT.get(i)[1], mainGroupT.get(i)[0]));
		}
		foodNum = foods.size();
		
		pacman.setPanel(this);
	} 	
	public Pacman getPacman() {
		return pacman;
	}
	public ArrayList<Ghost> getGhosts() {
		return ghosts;
	}
	@Override
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		setBackground(Color.BLACK);
		for(Wall wall : walls)
			g.drawImage(wall.getImage()	,wall.getX()*23 ,wall.getY()*23, 23,23 , null);
		g.setColor(new Color(40,27,102));
		for(int i=2;i<=parent.maxRow;i++)
			g.drawLine(i*23, 23, i*23, parent.mapLines*23+23);
		for(int i=2;i<=parent.mapLines;i++)
			g.drawLine(23, i*23, parent.maxRow*23+23, i*23);
		g.setColor(Color.red);
		for(int i=0;i<foods.size();i++)
		{
			if(foods.get(i).isAvailable())
			{
				if(foods.get(i).getRec().intersects(pacman.getRec()))
				{
					foods.get(i).eat();
					foodNum--;
					if(foodNum==0)
						healthPanel.dispatchEvent(new ComponentEvent(this, Messages.win));
					healthPanel.setScore(healthPanel.getScore()+10);
					healthPanel.repaint();
				}
				else
				{	
					g.drawImage(foods.get(i).getImage(), foods.get(i).getX()*23+10, foods.get(i).getY()*23+10, 4,4,null);
				}
			}
				
		}
		for(int i=0;i<ghosts.size();i++)
		{
			if(ghosts.get(i).getRec().intersects(pacman.getRec()))
			{
				try 
				{
					Thread.sleep(500);
				} catch (InterruptedException e) {}
				healthPanel.dispatchEvent(new ComponentEvent(this, Messages.die));
				if(healthPanel.getHealth()>0)
				{
					ArrayList <int[]> mainGroupT = new ArrayList<int[]>();
					for(int k=0;k<mainGroup.size();k++)
						mainGroupT.add(mainGroup.get(k));

					boolean mapT [][] = new boolean [map[0].length][map.length];
					for(int ii=0;ii<map.length;ii++)
						for(int jj=0;jj<map[i].length;jj++)
							mapT[jj][ii] = map[ii][jj];
					int pacStart = (int)(Math.random() * mainGroupT.size()); //	for live again
					mainGroupT.remove(pacStart);
					pacman.setRunning(false);
					pacman = new Pacman(mainGroup.get(pacStart)[1]*23+4,mainGroup.get(pacStart)[0]*23+3,mapT);
					parent.addKeyListener(pacman);
					pacman.setPanel(this);
					for(int j=0;j<ghosts.size();j++)
					{
						ghosts.get(i).setAvailable(false);
						if(ghosts.get(i) instanceof Ghost1)
						{
							int gStart = (int)(Math.random() * mainGroupT.size());
							int dStart = (int)(Math.random() * 4) +1;
							ghosts.remove(i);
							ghosts.add(i,new Ghost1(mainGroupT.get(gStart)[1]*23+4,mainGroupT.get(gStart)[0]*23+3,dStart,map,pacman,ghosts));
							mainGroupT.remove(gStart);
						}
						else if(ghosts.get(i) instanceof Ghost2)
						{
							int gStart = (int)(Math.random() * mainGroupT.size());
							int dStart = (int)(Math.random() * 4) +1;
							ghosts.remove(i);
							ghosts.add(i,new Ghost2(mainGroupT.get(gStart)[1]*23+4,mainGroupT.get(gStart)[0]*23+3,dStart,map,pacman,ghosts));
							mainGroupT.remove(gStart);
						}
						else if(ghosts.get(i) instanceof Ghost3)
						{
							int gStart = (int)(Math.random() * mainGroupT.size());
							int dStart = (int)(Math.random() * 4) +1;
							ghosts.remove(i);
							ghosts.add(i,new Ghost3(mainGroupT.get(gStart)[1]*23+4,mainGroupT.get(gStart)[0]*23+3,dStart,map,pacman,ghosts));
							mainGroupT.remove(gStart);
						}	
						
						ghosts.get(j).setPacman(pacman);
					}
					
					
					
						
						
					break;
				
				}
				else
				{
					pacman.setRunning(false);
				}
				
			}
			else
			{
				g.drawImage(ghosts.get(i).getImage(), ghosts.get(i).getX(),  ghosts.get(i).getY(), 18, 18, null);
			}
			
		}
		g.drawImage(pacman.getImage(),pacman.getX(),pacman.getY(),18,18, null);
		repaint();
	}
	@Override
	protected void processComponentEvent(ComponentEvent e)
	{
		if(e.getID()==Messages.exit)
			parent.dispose();

	}
	
	private boolean isWay(int y1,int x1,int y2, int x2)
	{
		boolean ans[] = {false};
		boolean mapT [][] = new boolean [map[0].length][map.length];
		for(int i=0;i<mapT.length;i++)
			for(int j=0;j<mapT[0].length;j++)
				mapT[i][j] = map[j][i];
		if(!mapT[x1][y1])
			goIn(x1,y1,mapT,ans,x2,y2);
		return ans[0];
		
	}
	
	private void goIn(int x,int y,boolean mapT [][],boolean ans [],int xF,int yF)
	{
		boolean is = !mapT[x][y];
		mapT[x][y] = true;
		if(x==xF && y==yF)
			ans[0] = true;
		else if(is)
		{
			goIn(x-1,y,mapT,ans,xF,yF);
			goIn(x+1,y,mapT,ans,xF,yF);
			goIn(x,y-1,mapT,ans,xF,yF);
			goIn(x,y+1,mapT,ans,xF,yF);
		}
			
	}
	

	
}

class HealthPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int score;
	private int health;
	private String playerName;
	private int sizeX;
	private GamePanel gamePanel;
	public HealthPanel(int x,int y,int msg,String playerName)  // msg : 0 -> die message , 5 -> win message
	{	
		score = 0;
		health = 3;
		this.playerName = playerName;
		this.sizeX = x;
		setSize(x,45);
		setLocation(0, y);
		setBackground(new Color(40,27,102));
		setVisible(true);
		
	}
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
			for(int i=0;i<health;i++)
			{
				g.drawImage((new ImageIcon("src/pacman/images/pac/health.png")).getImage(), i * 28 + sizeX - 110, 10, 22,22,null);
			}
			g.setColor(Color.YELLOW);
			g.setFont(new Font("PacFont",getFont().getStyle(),22));
			g.drawString("SCORE : ",20,28);
			g.setFont(new Font("namco regular",getFont().getStyle(),18));
			g.drawString(""+score,145,28);
	}
	 
	@Override
	protected void processEvent(AWTEvent e) 
	{
		super.processEvent(e);
		if(e.getID()==Messages.die)
		{
			health--;
			score = score - 30;
			if(score<0)
				score = 0;
			if(health==0)
			{
				Clip clip = null;
				try {
					clip = AudioSystem.getClip();
				} catch (LineUnavailableException e1) {
					// TODO Auto-generated catch block
				}
		        AudioInputStream inputStream;
				try {
					File sound = new File("src/pacman/sounds/die.wav");
					inputStream = AudioSystem.getAudioInputStream(sound);
			        try {
						clip.open(inputStream);
					} catch (LineUnavailableException e2) {
						// TODO Auto-generated catch block
					}
			        clip.start();
				} catch(UnsupportedAudioFileException | IOException e2) {}
				//for play sound
				
				
				
				gamePanel.getPacman().setRunning(false);
				for(Ghost gh : gamePanel.getGhosts())
					gh.setAvailable(false);
				
				JOptionPane.showMessageDialog(null, "GameOver "+playerName+"\nScore:"+score);
				System.exit(0);
			}
				
		}
		if(e.getID()==Messages.win)
		{
			Clip clip = null;
			try {
				clip = AudioSystem.getClip();
			} catch (LineUnavailableException e1) {
				// TODO Auto-generated catch block
			}
	        AudioInputStream inputStream;
			try {
				File sound = new File("src/pacman/sounds/win.wav");
				inputStream = AudioSystem.getAudioInputStream(sound);
		        try {
					clip.open(inputStream);
				} catch (LineUnavailableException e2) {
					// TODO Auto-generated catch block
				}
		        clip.start();
			} catch(UnsupportedAudioFileException | IOException e2) {}
			//for play sound

			gamePanel.getPacman().setRunning(false);
			for(Ghost gh : gamePanel.getGhosts())
				gh.setAvailable(false);
			
			JOptionPane.showMessageDialog(null, "You win "+playerName+"\nScore:"+score);
			System.exit(0);
		}
		repaint();
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getHealth() {
		return health;
	}
	public void setGamePanel(GamePanel gamePanel){
		this.gamePanel = gamePanel;
	}
}

