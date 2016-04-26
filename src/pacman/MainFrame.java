package pacman;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class MainFrame extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1571170358514480941L;
	public MainFrame()
	{
		setSize(600,670);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Pacman");
		setLayout(null);
		
		JLabel enterMap = new JLabel("Enter map (Max : 30*30):");
		enterMap.setLocation(10,10);
		enterMap.setSize(200, 15);
		getContentPane().add(enterMap);
		
		JLabel enterName = new JLabel("Player Name:");
		enterName.setLocation(315,100);
		enterName.setSize(110, 15);
		getContentPane().add(enterName);
		
		JTextArea mapArea = new mapTextArea();
		getContentPane().add(mapArea);
		
		JTextField playerName = new JTextField();
		playerName.setSize(100,20);
		playerName.setLocation(415,100);
		getContentPane().add(playerName);
		
		JLabel ghost = new JLabel("Ghosts:");
		ghost.setLocation(315,135);
		ghost.setSize(110, 15);
		getContentPane().add(ghost);
		
		JLabel type1 = new JLabel("Clyde :"); // -> Orange , Random , Slow 
		type1.setLocation(335,155);
		type1.setSize(110, 15);
		getContentPane().add(type1);
		
		JLabel type2 = new JLabel("Pinky :"); // -> Pink , Age nazdik taqib , Speedy 
		type2.setLocation(335,175);
		type2.setSize(110, 15);
		getContentPane().add(type2);
		
		JLabel type3 = new JLabel("Blinky :"); // -> Red , Taqib 
		type3.setLocation(335,195);
		type3.setSize(110, 15);
		getContentPane().add(type3);
		
		JCheckBox check1 = new JCheckBox();
		check1.setSize(20, 20);
		check1.setLocation(380,152);
		getContentPane().add(check1);
		
		JCheckBox check2 = new JCheckBox();
		check2.setSize(20, 20);
		check2.setLocation(380,172);
		getContentPane().add(check2);
		
		JCheckBox check3 = new JCheckBox();
		check3.setSize(20, 20);
		check3.setLocation(380,192);
		getContentPane().add(check3);
		
		JButton play = new JButton("Play");
		play.setSize(60, 30);
		play.setLocation(330, 250);
		getContentPane().add(play);
		play.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				String map = mapArea.getText();
				String name = playerName.getText();
				if(map.length()!=0)
					new Game(map,mapArea.getLineCount(),check1.isSelected(),check2.isSelected(),check3.isSelected(),name);
				else if(map.length()==0)
					JOptionPane.showMessageDialog(null,"Enter the map.");
				
			}
		});
		
		JButton exit = new JButton("Exit");
		exit.setSize(60, 30);
		exit.setLocation(430, 250);
		getContentPane().add(exit);
		exit.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				System.exit(0);
			}
		});
		
		setVisible(true);
	}
	private class mapTextArea extends JTextArea
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public mapTextArea() 
		{
			setFont(new Font("Monospaced",getFont().getStyle(),15));
			setLocation(10,30);
			setSize(270,590);
			setVisible(true);
			setLineWrap(true);
			setBorder(new LineBorder(Color.blue));
			setText
					(
					  "      xxx          xxx  "+"\n"
					+ "      x x          x x  "+"\n"
					+ "      xxx    xxx xxxxx  "+"\n"
					+ "        xx              "+"\n"
					+ "     xxx    xxxx        "+"\n"
					+ "     x      x           "+"\n"
					+ "     xxxxxxxx     xxxxxx"+"\n"
					+ "     x            x     "+"\n"
					+ "xxxxxx            xxxxxx"+"\n"
					+ "     x x                "+"\n"
					+ "  xxxx x xxxxxx   xxxx  "+"\n"
					+ "       x x           x  "+"\n"
					+ "xxxxxxxx x        xx x  "+"\n"
					+ "         x xx xx   x x  "+"\n"
					+ "         x x   x   x x  "+"\n"
					+ "         x x   x   x x  "+"\n"
					+ "           xxxxx        "+"\n"
					+ "                        "+"\n"
					+ "                        "
					);
			
		}
		
		 @Override
			protected void processKeyEvent(KeyEvent e) 
			{
				if(e.getKeyChar()=='x' || e.getKeyChar()==' ' || e.getKeyCode()==KeyEvent.VK_DOWN || e.getKeyCode()==KeyEvent.VK_UP || e.getKeyCode()==KeyEvent.VK_RIGHT || e.getKeyCode()==KeyEvent.VK_LEFT || e.getKeyCode()==KeyEvent.VK_ENTER || e.getKeyCode()==KeyEvent.VK_DELETE || e.getKeyCode()==KeyEvent.VK_BACK_SPACE)
					super.processKeyEvent(e);
			}
	}

}
