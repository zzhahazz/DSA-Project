package main;

import java.awt.Graphics;

import gamestates.GameState;
import gamestates.Menu;
import gamestates.Playing;
import levels.Level;

public class Game implements Runnable{

	private GameWindow gameWindow;
	private GamePanel gamePanel;
	private Thread gameThread;
	private final int FPS_SET = 120;
	private final int UPS_SET = 120;
	
	private Playing playing;
	private Menu menu;

	
	public Game()
	{
		initClasses();
		
		gamePanel = new GamePanel(this);
		gameWindow = new GameWindow(gamePanel);
		gamePanel.setFocusable(true);
		gamePanel.requestFocus();
		startGameLoop();
	}

	private void initClasses() {
		menu = new 	Menu(this);
		playing = new Playing(this);
	}

	private void startGameLoop()
	{
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void update()
	{
		switch(GameState.state)
		{
		case MENU:
		{
			menu.update();
			break;
		}
		case PLAYING:
		{
			playing.update();
			break;
		}
		
		case OPTIONS:
		case QUIT:
			System.exit(0);
		default:
			
		break;
		}
	}
	
	public void render(Graphics g)
	{
		switch(GameState.state)
		{
		case MENU:
		{
			menu.draw(g);
			break;
		}
		case PLAYING:
		{
			playing.draw(g);
			break;
		}
		
		default:
			
		break;
		}
	}
	
	@Override
	public void run() {
		
		double timePerFrame = 1000000000.0 / FPS_SET;
		double timePerUpdate = 1000000000.0 / UPS_SET;
		
		long previousTime = System.nanoTime();
		
		
		int frames = 0;
		int updates = 0;
		long lastCheck = System.currentTimeMillis();
		
		double deltaU = 0;
		double deltaF = 0;
		
		while(true)
		{
			long currentTime = System.nanoTime();
			
			deltaU += (currentTime - previousTime) / timePerUpdate;
			deltaF += (currentTime - previousTime) / timePerFrame;

			previousTime = currentTime;
			
			if(deltaU >= 1)
			{
				update();
				updates ++;
				deltaU --;
			}
			
			if(deltaF >= 1)
			{
				gamePanel.repaint();
				deltaF --;
				frames++;
			}
			
			if(System.currentTimeMillis() - lastCheck >= 1000)
			{
				lastCheck = System.currentTimeMillis();
				System.out.println("FPS" + frames + "UPS" + updates);
				frames = 0;
				updates = 0;
			}
			
		}
		// TODO Auto-generated method stub
		
	}
	
	public void windowFocusLost() {
		// TODO Auto-generated method stub
		if(GameState.state == GameState.PLAYING)
		{
			playing.getPlayer().resetDirBooleans();
		}
		
	}

	public Menu getMenu() {
		// TODO Auto-generated method stub
		return menu;
	}
	
	public Playing getPlaying()
	{
		return playing;
	}
}
