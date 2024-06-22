package entities;
import static utilz.Constants.Direction.LEFT;
import static utilz.Constants.Direction.RIGHT;
import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.CanMoveHere;
import static utilz.HelpMethods.GetEntityYPosUnderRoofOrAboveFloor;
import static utilz.HelpMethods.IsEntityOnFloor;
import static utilz.HelpMethods.IsFloor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import main.Game;
import utilz.Constants.Map;

public class Demon extends Enemy {

	private Rectangle2D.Float attackBox;
	private int attackBoxOffsetX;
	private boolean hitted;
	
	
	
	public Demon(float x, float y) {
		super(x, y, DEMON_WIDTH, DEMON_HEIGHT, DEMON);
		// TODO Auto-generated constructor stub
		initHitbox(x,y,(int)(14 * Map.SCALE),(int)(50 * Map.SCALE));
		initAttackBox();
	}
	
	private void initAttackBox() {
		attackBox = new Rectangle2D.Float(x, y + 20, (int) (100 * Map.SCALE), (int) (19 * Map.SCALE));
		attackBoxOffsetX = (int) (Map.SCALE * 30);
	}
	
	public void update(int[] lvlData, Player player)
	{
		updateAnimationTick();
		updateBehavior(lvlData,player);
		updateAttackBox();
	}
	
	private void updateAttackBox() {
		attackBox.x = hitBox.x - attackBoxOffsetX;
		attackBox.y = hitBox.y;

	}

	protected void newState(int enemyState)
	{
		this.enemyState = enemyState;
		aniTick = 0;
		aniIndex = 0;
	}
	
	
	private void updateBehavior(int[] lvlData, Player player) {
		if (firstUpdate)
			firstUpdateCheck(lvlData);

		if (inAir)
			updateInAir(lvlData);
		else {
			switch (enemyState) {
			case IDLE:
				newState(RUNNING);
				break;
			case RUNNING:
				if (canSeePlayer(lvlData, player))
				{
					turnTowardPlayer(player);
					if (isPlayerCloseForAttack(player) && !hitted)
						newState(ATTACK);
				}

				move(lvlData);
				break;
			case ATTACK:
				if (aniIndex == 0)
					attackChecked = false;

				// Changed the name for checkEnemyHit to checkPlayerHit
				if (aniIndex >= 1 && !attackChecked)
					checkPlayerHit(attackBox, player);
				break;
			case HIT:
				hitted = true;
				if(aniIndex == 2)
				{
					hitted = false;
				}
				break;
			}
		}

	}

	public void drawAttackBox(Graphics g, int xLvlOffset, int yLvlOffset) {
		g.setColor(Color.red);
		g.drawRect((int) (attackBox.x - xLvlOffset), (int) attackBox.y - yLvlOffset, (int) attackBox.width, (int) attackBox.height);
	}

	protected void checkPlayerHit(Rectangle2D.Float attackBox, Player player) {
		if (attackBox.intersects(player.hitBox))
			player.changeHealth(-GetEnemyDmg(enemyType));
		attackChecked = true;

	}
	
	
	
	public int flipX() {
		if (walkDir == RIGHT)
			return -width;
		else
			return 0;
	}
	

	public int flipW() {
		if (walkDir == RIGHT)
		{
			attackBox = new Rectangle2D.Float(hitBox.x, hitBox.y + 25, (int) (100 * Map.SCALE), (int) (19 * Map.SCALE));
			return 1;
		}
		else
		{
			attackBox = new Rectangle2D.Float(hitBox.x-100, hitBox.y + 25, (int) (100 * Map.SCALE), (int) (19 * Map.SCALE));
			return -1;
		}

	}

	public int getSprite() {
		
		switch (enemyState) {
		case IDLE:
			return 1;
		case ATTACK:
			return 2;
		case HIT:
			return 3;
		case DEAD:
			return 4;
	}
		return attackBoxOffsetX;
	}
}
