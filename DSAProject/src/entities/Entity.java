package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public abstract class Entity {

	protected float x;
	protected float y;
	protected int width, height;
	protected Rectangle2D.Float hitBox;
	
	public Entity(float x, float y, int width, int height)
	{
		this.setX(x);
		this.setY(y);
		this.setWidth(width);
		this.setHeight(height);
		
	}
	
	protected void drawHitBox(Graphics g, int xLvlOffset, int yLvlOffset)
	{
		g.setColor(Color.PINK);
		g.drawRect((int)hitBox.x - xLvlOffset, (int)hitBox.y - yLvlOffset, (int)hitBox.width, (int)hitBox.height);
	}
	
	protected void initHitbox(float x, float y, int width, int height) {
		hitBox = new Rectangle2D.Float(x ,y,width,height );
		
	}
	


	
	//protected void updateHitBox()
	//{
	//	hitBox.x = (int)x;
	//	hitBox.y = (int)y;
	//}
	
	public Rectangle2D.Float getHitBox()
	{
		return hitBox;
	}
	
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
}
