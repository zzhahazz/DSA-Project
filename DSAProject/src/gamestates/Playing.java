package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import UI.GameOverOverlay;
import UI.LevelCompletedOverlay;
import UI.PauseOverLay;
import entities.EnemyManager;
import entities.Player;
import levels.LevelManager;
import main.Game;
import utilz.Constants;
import utilz.Constants.Map;
import utilz.LoadSave;

public class Playing extends State implements Statemethods{
	private Player player;
	private LevelManager levelManager;
	private PauseOverLay pauseOverLay;
	private EnemyManager enemyManager;
	private GameOverOverlay gameOverOverlay;
	private boolean paused = false;
	
	private int xLvlOffset;
	private int yLvlOffset = 1200;
	private int borderX = (int)(0.5*Map.GAME_WIDTH) - 200;
	private int borderY = (int)(0.25*Map.GAME_HEIGHT);
	private int maxX;
	private int maxY;
	private int preX,preY;
	
	private BufferedImage[] backgroundImg;
	private boolean gameOver = false;
	private boolean lvlCompleted = false;
	
	public Playing(Game game) {
		super(game);
		initClasses();
		 
		maxX = Constants.getLevelWInP(LevelManager.getLvlIndex());
		maxY = Constants.getLevelHInP(LevelManager.getLvlIndex());
		backgroundImg = LoadSave.GetSprite(Map.MAP_IMAGE,1, Map.BACKGROUND_SIZE_W, Map.BACKGROUND_SIZE_H) ;
		preX = (int)player.getHitBox().x;
		preY = (int)player.getHitBox().y;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
	
	private void initClasses() {
		// TODO Auto-generated method stub
		levelManager = new LevelManager(game);
		enemyManager = new EnemyManager(this);
		player = new Player(levelManager.getCurrentLevel().getPlayerSpawn().x,levelManager.getCurrentLevel().getPlayerSpawn().y ,(int)( 30 * Map.SCALE),(int)(50 * Map.SCALE),this);
		player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
		pauseOverLay = new PauseOverLay(this);
		gameOverOverlay = new GameOverOverlay(this);
	}
	
	public Player getPlayer()
	{
		return player;
	}

	public void windowFocusLost() {
		// TODO Auto-generated method stub
		player.resetDirBooleans();
		
	}


	@Override
	public void update() {
		// TODO Auto-generated method stub
		if(paused)
		{
			pauseOverLay.update();
		}
		else if (!gameOver)
		{
			levelManager.update();
			enemyManager.update(levelManager.getCurrentLevel().getLevelData(),player);
			player.update();
			checkCloseToborderX();
			checkCloseToborderY();
		}
	}

	
	private void checkCloseToborderX() {
		// TODO Auto-generated method stub
		int playerX = (int)player.getHitBox().x;
		
			if(!(playerX + borderX > maxX - borderX) && !(playerX < borderX))
			{
				xLvlOffset += playerX - preX;
			}
			preX = playerX;
		}
	
	private void checkCloseToborderY() {
		// TODO Auto-generated method stub
		int playerY = (int)player.getHitBox().y;
			if(player.isInAir() == true)
			{
				if(!(playerY + borderY > maxY - borderY) && !(playerY < borderY))
				{
					yLvlOffset += playerY - preY;
				}
				preY = playerY;
			}
			preY = playerY;
			
		}

	public void loadNextLevel() {
		resetAll();
		levelManager.loadNextLevel();
		player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		//g.drawImage(backgroundImg, maxTileOffsetX, maxLvlOffsetX, lvlTilesWide, borderX, null);
		//drawClouds(g);
		
		for(int i = 0; i < 3; i ++)
		{
			g.drawImage(backgroundImg[0], (i *  Map.BACKGROUND_SIZE_W) - xLvlOffset , 0 - yLvlOffset,  Map.BACKGROUND_SIZE_W + 5,  Map.BACKGROUND_SIZE_H, null); 
		}
		
		levelManager.draw(g,xLvlOffset,yLvlOffset);
		enemyManager.draw(g,xLvlOffset,yLvlOffset);
		player.render(g,xLvlOffset,yLvlOffset);

		
		
		if(paused)
		{
			g.setColor(new Color(0,0,0,150));
			g.fillRect(0, 0, Map.GAME_WIDTH, Map.GAME_HEIGHT);
			pauseOverLay.draw(g);	
			
		}
		else if(gameOver)
		{
			gameOverOverlay.draw(g);
		}
		else if(lvlCompleted)
		{
			
		}
	}


	public void unpauseGame()
	{
		 paused = false;
	}




	
	public void checkEnemyHit(Rectangle2D.Float attackBox) {
		enemyManager.checkEnemyHit(attackBox);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (!gameOver)
			if (e.getButton() == MouseEvent.BUTTON1)
				player.setAttack(true);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (gameOver)
			gameOverOverlay.keyPressed(e);
		else
			switch (e.getKeyCode()) {
			case KeyEvent.VK_A:
				player.setLeft(true);
				break;
			case KeyEvent.VK_D:
				player.setRight(true);
				break;
			case KeyEvent.VK_SPACE:
				player.setJump(true);
				break;
			case KeyEvent.VK_ESCAPE:
				paused = !paused;
				break;
			}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (!gameOver)
			switch (e.getKeyCode()) {
			case KeyEvent.VK_A:
				player.setLeft(false);
				break;
			case KeyEvent.VK_D:
				player.setRight(false);
				break;
			case KeyEvent.VK_SPACE:
				player.setJump(false);
				break;
			}

	}

	public void mouseDragged(MouseEvent e) {
		if (!gameOver)
			if (paused)
				pauseOverLay.mouseDragged(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (!gameOver)
			if (paused)
				pauseOverLay.mousePressed(e);
			else if(lvlCompleted);
				
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (!gameOver)
			if (paused)
				pauseOverLay.mouseReleased(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (!gameOver)
			if (paused)
				pauseOverLay.mouseMoved(e);
	}
	
	public EnemyManager getEnemyManager() {
		return enemyManager;
	}
	
	public void setLevelCompleted(boolean levelCompleted) {
		this.lvlCompleted = levelCompleted;
	} 
	
	public void resetAll() {
		gameOver = false;
		paused = false;
		player.resetAll();
		enemyManager.resetAllEnemies();
	}
}
