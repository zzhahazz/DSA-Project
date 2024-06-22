package main;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

import input.KeyboardInputs;
import input.MouseInputs;
import utilz.Constants.Map;

import static main.Game.*;

public class GamePanel extends JPanel{
	
	private MouseInputs mouseInputs;
	private Game game;
	
	public GamePanel(Game game)
	{
		mouseInputs = new MouseInputs(this);
		this.game = game;
		
		setPanelSize();
		addKeyListener(new KeyboardInputs(this));
		addMouseListener(mouseInputs);
		addMouseMotionListener(mouseInputs);
	}

	private void setPanelSize()
	{
		Dimension size = new Dimension(Map.GAME_WIDTH,Map.GAME_HEIGHT);
		setPreferredSize(size);
		
	}
	
	
	public void updateGame()
	{

		
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		game.render(g);
	}
	public Game getGame()
	{
		return game;
	}
}
