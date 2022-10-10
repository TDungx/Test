package entities.tiles;

import entities.Entity;
import graphics.Sprite;

public class Wall extends Tile {

	public Wall(int x, int y, Sprite sprite) {
		super(x, y, sprite);
		// TODO Auto-generated constructor stub
	}
	
	public Wall(int x, int y) {
		super(x, y, Sprite.wall);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean canThrough(Entity e) {
		// TODO Auto-generated method stub
		return false;
	}

}
