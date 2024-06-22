package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import UI.MenuButton;
import main.Game;
import utilz.Constants.Map;
import utilz.LoadSave;

public class Menu extends State implements Statemethods {

	private MenuButton[] buttons = new MenuButton[3];
	private BufferedImage[]  backgroundImg, backgroundImgPink;
	private int menuX,menuY,menuWidth,menuHeight;
	
	public Menu(Game game) {
		super(game);
		loadButtons();
		loadBackground();
		//backgroundImgPink = ;
		// TODO Auto-generated constructor stub
	}

	private void loadBackground() {
		// TODO Auto-generated method stub
		menuX = Map.GAME_WIDTH/2 - menuWidth/2;
		menuY = (int) (45 * Map.SCALE);
	}

	private void loadButtons() {
		// TODO Auto-generated method stub
		buttons[0] = new MenuButton(32, 32,0,GameState.PLAYING);
		buttons[1] = new MenuButton(32, 64,0,GameState.OPTIONS);
		buttons[2] = new MenuButton(32, 96,0,GameState.QUIT);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		for(MenuButton mb: buttons)
		{
			mb.update();
		}
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		//g.drawImage(backgroundImgPink, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null)
		g.drawString("This is a fangame of the Touhou Project series, all right reserved for Team Shanghai Alice" ,Map.GAME_WIDTH/2 -400, Map.GAME_HEIGHT/2 - 200);
		g.drawString("Press Enter to play" ,Map.GAME_WIDTH/2 -200, Map.GAME_HEIGHT/2 - 100);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		for(MenuButton mb: buttons)
		{
			if(isIn(e,mb))
			{
				mb.setMousePressed(true);
				break;
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		for(MenuButton mb: buttons)
		{
			if(isIn(e,mb))
			{
				if(mb.isMousePressed())
					mb.applyGamestate();
				break;
			}
		}
		resetButton();
	}

	private void resetButton() {
		// TODO Auto-generated method stub
		for(MenuButton mb: buttons)
		{
			mb.resetBools();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		for(MenuButton mb: buttons)
		{
			mb.setMouseOver(false);
		}
		for(MenuButton mb: buttons)
		{
			if(isIn(e,mb))
			{
				mb.setMouseOver(true);
				break;
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			GameState.state = GameState.PLAYING;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
