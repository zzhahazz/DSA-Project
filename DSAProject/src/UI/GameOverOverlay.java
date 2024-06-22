package UI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import gamestates.GameState;
import gamestates.Playing;
import main.Game;
import utilz.Constants.Map;

public class GameOverOverlay {

	private Playing playing;

	public GameOverOverlay(Playing playing) {
		this.playing = playing;
	}

	public void draw(Graphics g) {
		g.setColor(new Color(0, 0, 0, 200));
		g.fillRect(0, 0, Map.GAME_WIDTH, Map.GAME_HEIGHT);

		g.setColor(Color.white);
		g.drawString("Game Over", Map.GAME_WIDTH / 2, 150);
		g.drawString("Press esc to enter Main Menu!", Map.GAME_WIDTH / 2, 300);

	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			playing.resetAll();
			GameState.state = GameState.MENU;
		}
	}
}
