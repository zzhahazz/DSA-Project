package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import gamestates.GameState;	
import main.Game;
import utilz.Constants.Map;
import utilz.LoadSave;

public class LevelManager {
	
	private Game game;
	private BufferedImage[] levelSprite;
	
	private static int lvlIndex = 0;
	private static int maxLevel = 4;
	private static int maxSprite = 10;
	
	private Level[] levels = new Level[maxLevel];
	
	public static int getLvlIndex()
	{
		return lvlIndex;
	}
		
	public static int getmaxLevel()
	{
		return maxLevel;
	}
	
	public LevelManager(Game game)
	{
		this.game = game;
		levelSprite = LoadSave.GetSprite(Map.MAP_TILE, maxSprite, Map.TILE_DEFAULT_SIZE, Map.TILE_DEFAULT_SIZE);
		for(int i = 0; i < maxLevel; i ++)
		{
			levels[i] = new Level(i+1);
		}
	}
	
	public void draw(Graphics g, int xLvlOffset, int yLvlOffset)
	{
		for(int i = 0; i < levels[lvlIndex].getH();i++)
		{
			for(int j = 0; j < levels[lvlIndex].getW();j++)
			{
				if(levels[lvlIndex].getLevelData()[i* levels[lvlIndex].getW() + j] < maxSprite)
				{
					g.drawImage(levelSprite[levels[lvlIndex].getLevelData()[i* levels[lvlIndex].getW() + j]],(int) (j*Map.TILE_DEFAULT_SIZE*Map.SCALE - xLvlOffset), (int)(i*Map.TILE_DEFAULT_SIZE*Map.SCALE - yLvlOffset),(int)(Map.TILE_DEFAULT_SIZE*Map.SCALE), (int)(Map.TILE_DEFAULT_SIZE*Map.SCALE), null);
					
				}
			}
		}
		
	}

	
	public void loadNextLevel() 
	{
		if(lvlIndex < maxLevel)
			lvlIndex += 1;
	}

	
	public void update()
	{
		
	}
	
	public Level getCurrentLevel()
	{
		return levels[lvlIndex];
	}
	
}
