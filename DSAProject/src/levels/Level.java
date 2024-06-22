package levels;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Demon;
import main.Game;
import utilz.Constants.*;

public class Level 
{
	private int h,w,lvl;
	private Point playerSpawn;
	
	public Level(int level)
	{
		this.lvl = level;
		this.setW(utilz.Constants.getLevelW(level));
		this.setH(utilz.Constants.getLevelH(level));
		this.playerSpawn = utilz.Constants.getPlayerSpawn(level);
	}

	
	public Level() {
		// TODO Auto-generated constructor stub
	}
	
	
	public int[] getLevelData() {
		return utilz.Constants.getLevelData(lvl);
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	
	public Point getPlayerSpawn() {
		return playerSpawn;
	}
	
}
