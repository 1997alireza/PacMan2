package pacman;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class MainFromFile 
{
	public MainFromFile()
	{
		List <String> file = null;
		try 
		{
			file = Files.readAllLines(Paths.get("src/pacman/map/map.txt"));
		} catch (IOException e) {}
		String map = "";
		for(int i=0;i<file.size()-1;i++)
			map=map+file.get(i)+'\n';
		map=map+file.get(file.size()-1);
		new Game(map,file.size(),true,true,true,"");
	}
}
