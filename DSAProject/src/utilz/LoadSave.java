package utilz;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import entities.Demon;
import levels.Level;
import levels.LevelManager;
import main.Game;
import utilz.Constants.Map;

import static utilz.Constants.EnemyConstants.*;

public class LoadSave {
	
	public static ArrayList<Demon> getDemon()
	{
		//String img = "Demon.png";
		ArrayList<Demon> list = new ArrayList<>();
		
		Level[] enemyLevels = new Level[LevelManager.getmaxLevel()];;
		
		for(int i = 0; i < LevelManager.getmaxLevel(); i ++)
		{
			enemyLevels[i] = new Level(i + 1);
		}
		
		
		for(int i = 0; i < enemyLevels[LevelManager.getLvlIndex()].getH();i++)
		{
			for(int j = 0; j < enemyLevels[LevelManager.getLvlIndex()].getW();j++)
			{
				if(enemyLevels[LevelManager.getLvlIndex()].getLevelData()[i*enemyLevels[LevelManager.getLvlIndex()].getW() + j] == DEMON)
				{
					list.add(new Demon(j*Map.TILES_SIZE*Map.SCALE, (i*Map.TILES_SIZE - 11)*Map.SCALE));
				}
			}
		}
		
		
		return list;
		
	}

	
	public static final String MENU_BUTTONS = "";
	public static BufferedImage[] GetSprite(String fileName,int frames, int width, int height)
	{
		BufferedImage Img = null;
		InputStream is = LoadSave.class.getResourceAsStream("/"+ fileName);
		
		try {
			 Img = ImageIO.read(is);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		finally
		{
			try
			{
				is.close();
			}catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		BufferedImage[] ani = new BufferedImage[frames];
				
				for(int i = 0; i < frames; i ++)
				{
					ani[i] = Img.getSubimage(i*width, 0, width, height);
				}
				
		return ani;
	}
	
}
