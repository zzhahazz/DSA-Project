package UI;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import gamestates.GameState;
import utilz.LoadSave;
import utilz.Constants.UI.Buttons.*;

public class MenuButton {
	private int xPos, yPos, rowIndex, index;
	private int xOffSetCenter = utilz.Constants.UI.Buttons.B_WIDTH/2;
	private GameState  state;
	private BufferedImage[] imgs;
	private boolean mouseOver, mousePressed;
	private Rectangle bounds;
	public Rectangle getBounds() {
		return bounds;
	}
	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}
	public MenuButton(int xPos, int yPos, int rowIndex, GameState state)
	{
		this.xPos = xPos;
		this.yPos = yPos;	
		this.rowIndex = rowIndex;
		this.state = state;
		loadImgs();
		initBound();
	}
	private void initBound() {
		// TODO Auto-generated method stub
		bounds = new Rectangle(xPos - xOffSetCenter, yPos,utilz.Constants.UI.Buttons.B_WIDTH , utilz.Constants.UI.Buttons.B_HEIGHT);
	}
	private void loadImgs() {
		// TODO Auto-generated method stub
		imgs = LoadSave.GetSprite("tile.png",4,32 , 32);
	}
	
	public void draw(Graphics g)
	{
		g.drawImage(imgs[index], xPos-xOffSetCenter, yPos, utilz.Constants.UI.Buttons.B_WIDTH, utilz.Constants.UI.Buttons.B_HEIGHT, null);
	}
	
	public void update()
	{	
		index = 1;
		if(mouseOver)
			index = 2;
		if(mousePressed)
			index = 3 ;
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
	public void applyGamestate()
	{
		GameState.state = state;
	}
	public void resetBools()
	{
		mouseOver = false;
		mousePressed = false;
	}
}
