package entities;

import static utilz.Constants.PlayerConstants.ATTACK_1;
import static utilz.Constants.Direction.*;

import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;

import main.Game;
import utilz.Constants.Map;

import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.*;

public abstract class Enemy extends Entity{

	protected int aniIndex,enemyState,enemyType;
	protected int aniTick, aniSpeed = 25;
	protected boolean firstUpdate = true, inAir = true;
	protected float fallSpeed,gravity = 0.04f * Map.SCALE;
	protected float walkSpeed= 1.0f * Map.SCALE; 
	protected int walkDir = LEFT;
	protected int tileY;
	protected float attackDistance = 50;
	protected boolean attackChecked;
	private int currentHealth;
	private int maxHealth = 100;
	protected boolean active = true;
	
	public Enemy(float x, float y, int width, int height, int enemyType) {
		super(x, y, width, height);
		initHitbox(x,y,width,height);
		this.enemyType = enemyType;
		this.enemyState = IDLE;
		maxHealth = GetMaxHealth(enemyType);
		currentHealth = maxHealth;
		// TODO Auto-generated constructor stub
	}

	protected void firstUpdateCheck(int[] lvlData)
		{
			if(!IsEntityOnFloor(hitBox,lvlData))
			{
				inAir = true;
			}
			firstUpdate = true;
		}
	
	protected boolean canSeePlayer(int[] lvlData, Player player)
	{
		int playerTileY = (int) (player.getHitBox().y/Map.TILES_SIZE);
		if(playerTileY == tileY)
		{
			if(isPlayerInRange(player))
			{
				if(isSightClear(lvlData, hitBox, player.hitBox, tileY))
				{
					return true;
				}
			}
		}
		return false;	
	}
	
	protected void turnTowardPlayer(Player player)
	{
		if(player.hitBox.x > hitBox.x)
		{
			walkDir = RIGHT;
		}
		else
			walkDir = LEFT;
	}
	
	protected boolean isPlayerCloseForAttack(Player player)
	{
		int absValue = (int) Math.abs(player.hitBox.x - hitBox.x);
		return absValue <= attackDistance;
	}

	private boolean isPlayerInRange(Player player)
	{
		int absValue = (int) Math.abs(player.hitBox.x - hitBox.x);
		return absValue <= attackDistance * 5;
	}

	protected void updateInAir(int[] lvlData)
		{
		if(CanMoveHere(hitBox.x,hitBox.y,hitBox.width,hitBox.height,lvlData))
		{
			hitBox.y += fallSpeed;
			fallSpeed += gravity;
		}
		else
		{
			inAir = false;
			hitBox.y = GetEntityYPosUnderRoofOrAboveFloor(hitBox, fallSpeed);
			tileY = (int) (hitBox.y/Map.TILES_SIZE);
		}
	}
	
	protected void move(int[] lvlData)
	{
		float xSpeed = 0;
		if(walkDir == LEFT)
			xSpeed = -walkSpeed;
		else 
			xSpeed = walkSpeed;
		if(CanMoveHere(hitBox.x + xSpeed,hitBox.y,hitBox.width,hitBox.height,lvlData))
		{
			if(IsFloor(hitBox,xSpeed, lvlData))
			{
				hitBox.x += xSpeed;
				return;
			}
		}
		changeWalkDir();
	}
	
	protected void updateAnimationTick() {
		aniTick++;
		if (aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= GetSpriteAmount(enemyType, enemyState)) {
				aniIndex = 0;

				switch (enemyState) {
				case ATTACK, HIT -> enemyState = IDLE;

				case DEAD -> active = false;
				}
			}
		}
	}
	
	protected void changeWalkDir() {
		// TODO Auto-generated method stub
		if(walkDir == LEFT)
		{
			walkDir = RIGHT;
		}
		else
			walkDir = LEFT;
	}


	public void resetEnemy() {
		hitBox.x = x;
		hitBox.y = y;
		firstUpdate = true;
		currentHealth = maxHealth;
		newState(IDLE);
		active = true;
		fallSpeed = 0;
	}
	
	protected void checkPlayerHit(Rectangle2D.Float attackBox, Player player) {
		if (attackBox.intersects(player.hitBox))
			player.changeHealth(-GetEnemyDmg(enemyType));
		attackChecked = true;
	}
	
	protected void newState(int enemyState) {
		this.enemyState = enemyState;
		aniTick = 0;
		aniIndex = 0;
	}
	
	public int getAniIndex()
	{
		return aniIndex;
	}
	public int getEnemyState()
	{
		return enemyState;
	}
	public boolean isActive() {
		return active;
	}
	
	public void hurt(int amount) {
		currentHealth -= amount;
		if (currentHealth <= 0)
			newState(DEAD);
		else
			newState(HIT);
	}
}

