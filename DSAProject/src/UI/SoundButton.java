package UI;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import utilz.LoadSave;
import utilz.Constants.UI.PauseButtons.*;

public class SoundButton extends PauseButton {
	
	private BufferedImage[][] soundImgs;
	private boolean mouseOver, mousePressed;
	private boolean muted;
	private int rowIndex, colIndex;
	public BufferedImage[][] getSoundImgs() {
		return soundImgs;
	}
	public void setSoundImgs(BufferedImage[][] soundImgs) {
		this.soundImgs = soundImgs;
	}
	public boolean isMouseOver() {
		return mouseOver;
	}
	public void setMouseOver(boolean mouseOver) {
		this.mouseOver = mouseOver;
	}
	public boolean isMousePressed() {
		return mousePressed;
	}
	public void setMousePressed(boolean mousePressed) {
		this.mousePressed = mousePressed;
	}
	public boolean isMuted() {
		return muted;
	}
	public void setMuted(boolean muted) {
		this.muted = muted;
	}
	public SoundButton(int x, int y, int width, int height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
		
		loadSoundImgs();
	}
	private void loadSoundImgs() {
		// TODO Auto-generated method stub
		BufferedImage[] temp = LoadSave.GetSprite("momiji.png",6,utilz.Constants.UI.PauseButtons.SOUND_SIZE,utilz.Constants.UI.PauseButtons.SOUND_SIZE);
		soundImgs = new BufferedImage[2][3];
		for(int j = 0; j < soundImgs.length; j ++)
		{
			for(int i = 0; i < soundImgs[j].length; i ++)
			{
				soundImgs[j][i] = temp[j*(soundImgs[j].length) + i];
			}
		}
	}
	
	public void update()
	{
		if(muted)
		{
			rowIndex = 1;
		}
		else
		{
			rowIndex = 0;
		}
		colIndex = 0;
		if(mouseOver)
		{
			colIndex = 1;
		}
		if(mousePressed)
		{
			colIndex = 2;
		}
	}
	
	public void resetBools()
	{
		mouseOver = false;
		mousePressed = false;
	}
	public void draw(Graphics g)
	{
		g.drawImage(soundImgs[rowIndex][colIndex], x, y, width, height, null);
		System.out.println("true");
	}

}
