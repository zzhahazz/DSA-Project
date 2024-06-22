package utilz;

import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;

import entities.Player;
import main.Game;
import utilz.Constants.Map;

public class HelpMethods {
	
	

	public static boolean CanMoveHere(float x, float y, float width, float height, int[] levelData)
	{
		if(!IsSolid(x,y,levelData))
		{
			if(!IsSolid(x + width, y + height,levelData))
			{
				if(!IsSolid(x + width, y, levelData))
				{
					if(!IsSolid(x,y+height,levelData))
						return true;
				}
			}
			
		}
		return false;
	}
	
	
	
	
	public static boolean IsSolid(float x, float y, int[] levelData)
	{
		int maxWidth = levelData.length /(Constants.getLevelHInP(1) / Map.TILES_SIZE ) * Map.TILES_SIZE;
		if(x < 0 || x >= maxWidth)
		{
			return true;
		}
		if(y < 0 || y >= Constants.getLevelHInP(1))
			return true;
		
		float xIndex = x / Map.TILES_SIZE;
		float yIndex = y / Map.TILES_SIZE;
		
		return isTileSolid((int)xIndex,(int)yIndex,levelData);
	}
	
	public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[] lvlData) {
		// Check the pixel below bottomleft and bottomright
		if (!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData))
			if (!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData))
				return false;

		return true;

	}
	
	public static float GetEntityXPosNextToWall(Rectangle2D.Float hitBox,float xSpeed )
	{
		int currentTile = (int)(hitBox.x/Map.TILES_SIZE);
		if(xSpeed > 0)
		{
			//right
			int tileXPos = currentTile * Map.TILES_SIZE;
			int xOffSet = (int)(Map.TILES_SIZE - hitBox.width);
			return tileXPos + xOffSet - 1;
			
		}
		else
		{
			return currentTile * Map.TILES_SIZE;
		}
	}
	
	public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitBox, float airSpeed)
	{
		int currentTile = (int)(hitBox.y /Map.TILES_SIZE);
		if(airSpeed > 0)
		{
			int tileYPos = currentTile * Map.TILES_SIZE;
			int yOffSet = (int)(Map.TILES_SIZE - hitBox.height );
			return tileYPos + yOffSet - 1;
		}
		else
		{
			return currentTile * Map.TILES_SIZE;
		}
	}
	
	public static boolean IsFloor(Rectangle2D.Float hitBox, float xSpeed, int[] lvlData) {
		// TODO Auto-generated method stub
		if( xSpeed > 0)
			return IsSolid(hitBox.x + xSpeed + hitBox.width,hitBox.y + hitBox.height + 1,lvlData);
		else
			return IsSolid(hitBox.x + xSpeed,hitBox.y + hitBox.height + 1,lvlData);
	}
	
	public static boolean isAllTileWalkable(int xStart, int xEnd, int y, int[] lvlData)
	{
		for(int i = 0 ; i < xEnd - xStart; i ++ )
		{
			if(isTileSolid(xStart + i, y, lvlData))
			{
				return false;
			}
		}
		return true;
	}
	
	public static boolean isSightClear(int[] lvlData, Rectangle2D.Float firstHitBox, Rectangle2D.Float secondHitBox, int yTile) {
		// TODO Auto-generated method stub
		int firstXTile = (int) (firstHitBox.x / Map.TILES_SIZE);
		int secondXTile = (int) (secondHitBox.x / Map.TILES_SIZE);
		
		if(firstXTile > secondXTile)
		{
			return isAllTileWalkable(secondXTile, firstXTile,yTile, lvlData);
		}
		else
		{
			return isAllTileWalkable(firstXTile, secondXTile,yTile, lvlData);
		}
	}
	
	public static boolean isTileSolid(int xTile, int yTile, int[] lvlData)
	{
		int tile = (int) (Constants.getLevelWInP(1) / Map.TILES_SIZE );
		int value = lvlData[(int)yTile * tile + (int)xTile];
		if(value != 0 && value < 10 )
		{
			
			return true;
		}
		return false;
	}
}

