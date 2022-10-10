package entities.tiles;

import entities.Entity;
import graphics.Sprite;

public class Grass extends Tile {

	public Grass(int x, int y, Sprite sprite) {
		super(x, y, sprite);
		// TODO Auto-generated constructor stub
	}
	
	public Grass( int x , int y ) {
		super(x, y, Sprite.grass);
	}
	
	@Override
	public boolean canThrough(Entity e) {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public boolean collide(Entity e) {
		// TODO Auto-generated method stub
		return true;
	}
	
}
