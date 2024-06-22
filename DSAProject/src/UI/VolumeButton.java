package UI;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import utilz.LoadSave;

public class VolumeButton extends PauseButton{

	private BufferedImage[] imgs;
	private BufferedImage[] slider;
	private int index = 0;
	private boolean mouseOver, mousePressed;
	private int buttonX,minX,maxX;
	
	public VolumeButton(int x, int y, int width, int height) {
		super(x + width/2, y, 32, height);
		bounds.x -= 32/2;
		this.x = x;
		this.width = width;
		// TODO Auto-generated constructor stub
		buttonX = x + width/2;
		minX = x + 32/2;
		maxX = x + width - 32/2;
		loadImgs();
	}

	
	private void loadImgs() {
		// TODO Auto-generated method stub
		BufferedImage[] temp = LoadSave.GetSprite("tile.png", 3, 40, 32);
		imgs = new BufferedImage[3]; 
		for(int i = 0; i < imgs.length;i++)
		{
			imgs[i] = temp[i];
		}
		slider = LoadSave.GetSprite("momiji.png", 1, 100, 32);
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
		g.drawImage(slider[0], x, y, width, height, null);
		g.drawImage(imgs[index],buttonX-32/2,y,32,32,null);
	}
	
	public void changeX(int x)
	{
		if(x < minX)
		{
			buttonX = minX;
		}
		else if(x > maxX)
		{
			buttonX = maxX;
		}
		else
		{
			buttonX = x;
		}
		bounds.x = buttonX-32/2;
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

