package UI;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import utilz.LoadSave;

public class UrmButton extends PauseButton{

	private BufferedImage[] imgs;
	private int rowIndex, index;
	private boolean mouseOver, mousePressed;
	public UrmButton(int x, int y, int width, int height, int rowIndex) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
		this.rowIndex = rowIndex;
		loadImgs();
	}

	private void loadImgs() {
		// TODO Auto-generated method stub
		BufferedImage[] temp;
		switch(rowIndex)
		{
			case 0:
				temp = LoadSave.GetSprite("menu.png", 3, 32, 32);
				break;
			case 1:
				 temp = LoadSave.GetSprite("replay.png", 3, 32, 32);
				break;
			case 2:
				 temp = LoadSave.GetSprite("unpased.png", 3, 32, 32);
				break;
			default:
				temp = LoadSave.GetSprite("momiji.png", 3, 32, 32);
		}
		imgs = new BufferedImage[3];
		for(int i = 0; i < imgs.length;i++)
		{
			imgs[i] = temp[i];
		}
	}	

	public void update()
	{
		index = 0;
		if(mouseOver)
			index = 1;
		if(mousePressed)
			index = 2;
		
	}
	public void draw(Graphics g)
	{
		g.drawImage(imgs[index], x, y, 32, 32, null);
	}
	public void resetBools()
	{
		mouseOver = false;
		mousePressed = false;
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
	
}
