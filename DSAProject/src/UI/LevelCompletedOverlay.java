package UI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.GameState;
import gamestates.Playing;
import main.Game;
import utilz.Constants.Map;
import utilz.LoadSave;
import static utilz.Constants.UI.URMButtons.*;

public class LevelCompletedOverlay {

	private Playing playing;
	private UrmButton menu, next;
	private BufferedImage[] img;
	private int bgX, bgY, bgW, bgH;

	public LevelCompletedOverlay(Playing playing) {
		this.playing = playing;
		initImg();
		initButtons();
	}

	private void initButtons() {
		int menuX = (int) (330 * Map.SCALE);
		int nextX = (int) (445 * Map.SCALE);
		int y = (int) (195 * Map.SCALE);
		next = new UrmButton(nextX, y, 32, 32, 0);
		menu = new UrmButton(menuX, y, 32, 32, 2);
	}

	private void initImg() {
		img = LoadSave.GetSprite("momiji.png",1, 100, 50);
		bgW = (int) (img[0].getWidth() * Map.SCALE);
		bgH = (int) (img[0].getHeight() * Map.SCALE);
		bgX = Map.GAME_WIDTH / 2 - bgW / 2;
		bgY = (int) (75 * Map.SCALE);
	}

	public void draw(Graphics g) {
		// Added after youtube upload
		g.setColor(new Color(0, 0, 0, 200));
		g.fillRect(0, 0, Map.GAME_WIDTH, Map.GAME_HEIGHT);

		g.drawImage(img[0], bgX, bgY, bgW, bgH, null);
		next.draw(g);
		menu.draw(g);
	}

	public void update() {
		next.update();
		menu.update();
	}

	private boolean isIn(UrmButton b, MouseEvent e) {
		return b.getBounds().contains(e.getX(), e.getY());
	}

	public void mouseMoved(MouseEvent e) {
		next.setMouseOver(false);
		menu.setMouseOver(false);

		if (isIn(menu, e))
			menu.setMouseOver(true);
		else if (isIn(next, e))
			next.setMouseOver(true);
	}

	public void mouseReleased(MouseEvent e) {
		if (isIn(menu, e)) {
			if (menu.isMousePressed()) {
				playing.resetAll();
				GameState.state = GameState.MENU;
			}
		} else if (isIn(next, e))
			if (next.isMousePressed())
				playing.loadNextLevel();

		menu.resetBools();
		next.resetBools();
	}

	public void mousePressed(MouseEvent e) {
		if (isIn(menu, e))
			menu.setMousePressed(true);
		else if (isIn(next, e))
			next.setMousePressed(true);
	}

}
