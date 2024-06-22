package entities;

import static utilz.Constants.Direction.DOWN;
import static utilz.Constants.Direction.LEFT;
import static utilz.Constants.Direction.RIGHT;
import static utilz.Constants.Direction.UP;
import static utilz.Constants.PlayerConstants.*;
import static utilz.HelpMethods.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import gamestates.Playing;
import main.Game;
import utilz.Constants.Map;
import utilz.LoadSave;

public class Player extends Entity {
	private BufferedImage[] idleAni, runAni,attackAni,jumpAni,fallAni;
	private int aniTick, aniIndex, aniSpeed = 20;
	private int playerAction = IDLE;
	private int playerDir = -1;
	private boolean moving = false, attacking = false;
	private boolean right, up, left, down, jump;
	private float playerSpeed = 1.50f * Map.SCALE;
	private int[] lvlData;
	private float xDrawOffSet = 21 * Map.SCALE;
	private float yDrawOffSet = 4 * Map.SCALE;
	private float airSpeed = 0f;
	private float gravity = 0.04f * Map.SCALE;
	private float jumpSpeed = -3.25f * Map.SCALE;
	private float fallSpeedAfterCollision = 0.5f * Map.SCALE;
	private boolean inAir = false;	
	

		// StatusBarUI
		private BufferedImage[] statusBarImg;

		private int statusBarWidth = (int) (192 * Map.SCALE);
		private int statusBarHeight = (int) (58 * Map.SCALE);
		private int statusBarX = (int) (10 * Map.SCALE);
		private int statusBarY = (int) (10 * Map.SCALE);

		private int healthBarWidth = (int) (150 * Map.SCALE);
		private int healthBarHeight = (int) (4 * Map.SCALE);
		private int healthBarXStart = (int) (34 * Map.SCALE);
		private int healthBarYStart = (int) (14 * Map.SCALE);

		private int maxHealth = 100;
		private int currentHealth = maxHealth;
		private int healthWidth = healthBarWidth;
		
		private Rectangle2D.Float attackBox;

		private int flipX = 0;
		private int flipW = 1;

		private boolean attackChecked;
		private Playing playing;
		
	public Player(float x, float y, int width, int height, Playing playing) {
		super(x, y,width,height);
		// TODO Auto-  generated constructor stub
		loadAnimations();
		initHitbox(x,y,(int) (10 * Map.SCALE),(int) (51*Map.SCALE));
		initAttackBox();
		this.playing = playing;
	}
	
	private void initAttackBox() {
		attackBox = new Rectangle2D.Float(x, y, (int) (60 * Map.SCALE), (int) (30 * Map.SCALE));
	}
	
	private void initAttackBoxL() {
		attackBox = new Rectangle2D.Float(x - 60, y, (int) (60 * Map.SCALE), (int) (30 * Map.SCALE));
	}
	
	public void update()
	{
		updateHealthBar();
		
		if (currentHealth <= 0) {
			playing.setGameOver(true);
			return;
		}
		
		updateAttackBox();
		
		updatePos();
		updateAnimationTick();
		setAnimation();
		if (attacking)
			checkAttack();
		updateAnimationTick();
		setAnimation();
		
	}
	
	private void checkAttack() {
		if (attackChecked || aniIndex != 1)
			return;
		attackChecked = true;
		playing.checkEnemyHit(attackBox);

	}
	
	private void updateAttackBox() {
		if (right)
			attackBox.x = hitBox.x + hitBox.width + (int) (Map.SCALE * 10) - 20;
		else if (left)
			attackBox.x = hitBox.x - hitBox.width - (int) (Map.SCALE * 10)-40;

		attackBox.y = hitBox.y + (Map.SCALE * 10);
	}
	
	private void updateHealthBar() {
		healthWidth = (int) ((currentHealth / (float) maxHealth) * healthBarWidth);
	}
	
	public void render(Graphics g, int xLvlOffset, int yLvlOffset)
	{
		g.drawImage(setImg(playerAction)[aniIndex], (int)(hitBox.x - xDrawOffSet) + setXOffSide(playerAction) - xLvlOffset + flipX + flipX /2,(int)(hitBox.y - yDrawOffSet) + setYOffSide(playerAction) - yLvlOffset,(int)(setImg(playerAction)[aniIndex].getWidth()*Map.SCALE*flipW),(int)(setImg(playerAction)[aniIndex].getHeight()*Map.SCALE), null);
		
		drawUI(g);
	}
	
	private void drawAttackBox(Graphics g, int lvlOffsetX, int yLvlOffset) {
		g.setColor(Color.red);
		g.drawRect((int) attackBox.x - lvlOffsetX, (int) attackBox.y - yLvlOffset, (int) attackBox.width, (int) attackBox.height);

	}
		
		
		private void drawUI(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(statusBarImg[0], statusBarX, statusBarY, statusBarWidth,statusBarHeight,null);
		g.setColor(Color.red);
		g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);
	}


		private void updateAnimationTick() 
		{
			aniTick ++;
			if(aniTick >= aniSpeed)
			{
				aniTick = 0;
				aniIndex ++;
				
				switch (playerAction)
				{
				
				case ATTACK_1:
					if(aniIndex >= attackAni.length)
					{
						if(CanMoveHere(hitBox.x + getPlayerSpeed(), hitBox.y, hitBox.width,hitBox.height,lvlData))
						{
							hitBox.x += getPlayerSpeed();
						}
						attacking = false;
					}
					break;
				default:
				case HIT:
					attackChecked = false;
					break;
			}
		}


	}
		
		
		public void changeHealth(int value) {
			currentHealth += value;

			if (currentHealth <= 0)
				currentHealth = 0;
			else if (currentHealth >= maxHealth)
				currentHealth = maxHealth;
		}
		
		private void updatePos() {
			
			moving = false;
			
			if(jump)
				jump();
			
			if(!isInAir())
				if((!left && !right) || (left && right))
				{
					return;
				}
			float xSpeed = 0, ySpeed = 0;
			
			
			if (left) 
			{
				if(flipW == 1)
				{
					initAttackBoxL();
				}
				xSpeed -= getPlayerSpeed();
				flipX = width;
				flipW = -1;
			}
			
			if (right) 
			{
				
				if(flipW == -1)
				{
					initAttackBox();
				}
				xSpeed += getPlayerSpeed();
				flipX = 0;
				flipW = 1;
			}
			
			if(!isInAir())
			{
				if(!IsEntityOnFloor(hitBox, lvlData))
				{
					setInAir(true);
				}
			}
			
			if(isInAir() )
			{
				if(CanMoveHere(hitBox.x, hitBox.y + airSpeed, hitBox.width,hitBox.height,lvlData))
				{
					hitBox.y += airSpeed;
					airSpeed += gravity;
					updateXPos(xSpeed);
				}
				else
				{
					hitBox.y = GetEntityYPosUnderRoofOrAboveFloor(hitBox, airSpeed);
					if(airSpeed > 0)
						ResetInAir();
					else
						airSpeed = fallSpeedAfterCollision;
					updateXPos(xSpeed);
				}
			}
			else
			{
				updateXPos(xSpeed);
				moving = true;
			}
			/*
			if(CanMoveHere(hitBox.x + xSpeed, hitBox.y + ySpeed, hitBox.width,hitBox.height,lvlData))
			{
				hitBox.x += xSpeed;
				hitBox.y += ySpeed;
				moving = true;
			}
			*/
		}


		public static  boolean IsEntityOnFloor(Rectangle2D.Float hitBox, int[] lvlData) 
		{
			if(!IsSolid(hitBox.x,hitBox.y + hitBox.height + 1,lvlData))
			{
				if(!IsSolid(hitBox.x + hitBox.width, hitBox.y + hitBox.height + 1,lvlData))
				{
					return false;
				}
			}
			return true;
		}


		private void jump() {
			// TODO Auto-generated method stub
			if(isInAir())
				return;
			setInAir(true);
			airSpeed = jumpSpeed;
		}


		private void ResetInAir() {
			// TODO Auto-generated method stub
			setInAir(false);
			airSpeed = 0;
		}


		private void updateXPos(float xSpeed) {
			// TODO Auto-generated method stub

			if(CanMoveHere(hitBox.x + xSpeed, hitBox.y, hitBox.width,hitBox.height,lvlData))
			{
				hitBox.x += xSpeed;
			}else
			{
				hitBox.x = GetEntityXPosNextToWall(hitBox,xSpeed );
			}
			
		}


		private int setXOffSide(int playerAction2) {
			switch(playerAction)
			{
			case IDLE:
				
				
			return 0;
			
			case RUNNING:
				
				
			return 0;
				
			case ATTACK_1:
				if(flipW == 1)
				{
					return -20;
				}
				else if(flipW == -1)
				{
					return 20;
				}

			default:
				return 0;
			}
		}

		private int setYOffSide(int playerAction2) {
			switch(playerAction)
			{
			case IDLE:
				
				
			return 0;
			
			case RUNNING:
				
				
				return 8;
			case ATTACK_1:
				
				
				return -1;
				
			default:
				return 0;
			}
			
		}
		
		private void setAnimation() {
			int startAni = playerAction;
			if(moving)
			{
				playerAction = RUNNING;
			
				if(aniIndex >= runAni.length)
				{
					aniIndex = 0;
				}
			}
			else 
			{
				playerAction = IDLE;
			
				if(aniIndex >= idleAni.length && !attacking)
				{
					aniIndex = 0;
				}
			}
			
			if(isInAir())
			{
				if(airSpeed < 0)
				{
					playerAction = JUMP;
				}
				else
				{
					playerAction = FALLING;
				}
				
			}
			if(attacking)
			{
				playerAction = ATTACK_1;
				if(aniIndex >= attackAni.length)
				{
					aniIndex = 0;
				}
			}
			if(startAni != playerAction)
			{
				resetAniTick(); 
			}
		}
		
		private void resetAniTick() {
			// TODO Auto-generated method stub
			aniTick = 0;
			aniIndex = 0;
		}


		private BufferedImage[] setImg(int playerAction)
		{
			switch(playerAction)
			{
			case IDLE:
				
				
			return idleAni;
			
			case RUNNING:
				
				
				return runAni;
			
			case JUMP:
				return jumpAni;
			
			case FALLING:
				return fallAni;
				
			case ATTACK_1:
				return attackAni;
				
			default:
				return idleAni;
			}
			
		}
	
	private void loadAnimations()
	{
		
		idleAni = LoadSave.GetSprite("momiji_new.png", 1, 36, 55);
		runAni = LoadSave.GetSprite("momiji run.png", 8, 45, 47);
		attackAni = LoadSave.GetSprite("momiji attack 1 ani.png", 4, 92, 57);
		jumpAni = LoadSave.GetSprite("momiji jump.png",1, 36,45);
		fallAni = LoadSave.GetSprite("momiji fall.png", 1, 36, 48);
		statusBarImg = LoadSave.GetSprite("momiji.png", 1, healthBarWidth, healthBarHeight);
	}
	
	public void loadLvlData(int[] lvlData)
	{
		this.lvlData = lvlData;
		if(!IsEntityOnFloor(hitBox, lvlData))
		{
			setInAir(true);
		}
	}
	
	public void setAttack(boolean attacking)
	{
		this.attacking = attacking;
	}


	public boolean isRight() {
		return right;
	}


	public void setRight(boolean right) {
		this.right = right;
	}


	public boolean isUp() {
		return up;
	}


	public void setUp(boolean up) {
		this.up = up;
	}


	public boolean isLeft() {
		return left;
	}


	public void setLeft(boolean left) {
		this.left = left;
	}


	public boolean isDown() {
		return down;
	}


	public void setDown(boolean down) {
		this.down = down;
	}


	public void resetDirBooleans() {
		// TODO Auto-generated method stub
		left = false;
		right = false;
		up = false;
		down = false;
	}


	public void setJump(boolean jump) {
		this.jump = jump;
		
	}

	public void resetAll() {
		resetDirBooleans();
		setInAir(false);
		attacking = false;
		moving = false;
		playerAction = IDLE;
		currentHealth = maxHealth;

		hitBox.x = x;
		hitBox.y = y;

		if (!IsEntityOnFloor(hitBox, lvlData))
			setInAir(true);
	}
	
	public void setSpawn(Point spawn) {
		this.x = spawn.x;
		this.y = spawn.y;
		hitBox.x = x;
		hitBox.y = y;
	}

	public boolean isInAir() {
		return inAir;
	}

	public void setInAir(boolean inAir) {
		this.inAir = inAir;
	}

	public float getPlayerSpeed() {
		return playerSpeed;
	}

	public void setPlayerSpeed(float playerSpeed) {
		this.playerSpeed = playerSpeed;
	}
}
