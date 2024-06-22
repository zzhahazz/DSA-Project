package gamestates;

import java.awt.event.MouseEvent;

import UI.MenuButton;
import main.Game;
public class State {

	protected Game game;
	public State(Game game)
	{
		this.setGame(game);
	}
	public Game getGame() {
		return game;
	}
	
	public boolean isIn(MouseEvent e, MenuButton mb)
	{
		return mb.getBounds().contains(e.getX(), e.getY());
	}
	public void setGame(Game game) {
		this.game = game;
	}
	
}
