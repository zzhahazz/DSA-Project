package entities;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamestates.Playing;
import levels.Level;
import utilz.LoadSave;
import static utilz.Constants.EnemyConstants.*;

public class EnemyManager {

	private Playing playing;
	private	BufferedImage[] demonArr,demonAtk, demonH, demonD;
	private ArrayList<Demon> demon = new ArrayList<>();
	
	public  EnemyManager(Playing playing)
	{
		this.playing = playing;
		loadEnemyImgs();
		addEnemies();
	}
	private void addEnemies() {
		// TODO Auto-generated method stub
		demon = LoadSave.getDemon();
	}
	
	private void resetList()
	{
		demon.removeAll(demon);
		addEnemies();
	}
	
	private void loadEnemyImgs() {
		// TODO Auto-generated method stub
		demonArr = LoadSave.GetSprite(DEMON_SPRITE,4,DEMON_WIDTH_DEFAULT,DEMON_HEIGHT_DEFAULT);
		demonAtk = LoadSave.GetSprite("Demon_attack.png",4,60,DEMON_HEIGHT_DEFAULT);
		demonH = LoadSave.GetSprite("demon hurt.png",3,DEMON_WIDTH_DEFAULT,DEMON_HEIGHT_DEFAULT);
		demonD = LoadSave.GetSprite("demon die.png",4,DEMON_WIDTH_DEFAULT,DEMON_HEIGHT_DEFAULT);
	}
	
	public void draw(Graphics g, int xLvlOffset, int yLvlOffset)
	{
		drawDemon(g,xLvlOffset,yLvlOffset);
	}
	
	public void checkEnemyHit(Rectangle2D.Float attackBox) {
		for (Demon c : demon)
			if (c.isActive())
				if (attackBox.intersects(c.getHitBox())) {
					c.hurt(10);
					return;
				}
	}
	
	private void drawDemon(Graphics g, int xLvlOffset, int yLvlOffset) {
		// TODO Auto-generated method stub
		for(Demon c: demon)
		{
			if (c.isActive()) 
			{
				g.drawImage(GetAni(c.getSprite())[c.getAniIndex()], (int) c.getHitBox().x - xLvlOffset + c.flipX(),  (int) c.getHitBox().y - yLvlOffset, GetAni(c.getSprite())[c.getAniIndex()].getWidth() * c.flipW(), GetAni(c.getSprite())[c.getAniIndex()].getHeight(), null);
			}
		}
	}
	private BufferedImage[] GetAni(int sprite) {
		switch(sprite)
		{
		case 1:
			return demonArr;
		case 2:
			return demonAtk;
		case 3: 
			return demonH;
		case 4: 
			return demonD;
		}
		return demonArr;
	}
	public void resetAllEnemies() {
		for (Demon c : demon)
			c.resetEnemy();
	}
	
	public void update(int[] lvlData, Player player) {
		for (Demon c : demon)
			if (c.isActive())
				c.update(lvlData, player);
	}

}
