package entities.tiles;

import entities.Entity;
import graphics.Screen;
import graphics.Sprite;

public  class Tile extends Entity {

	public Tile(int x , int y, Sprite sprite) {
		this._x = x;
		this._y = y;
		this._sprite = sprite;
	}
	
	
	@Override
	public void render(Screen screen) {
		// TODO Auto-generated method stub
		screen.renderEntity((int)this._x * Sprite.DEFAULT_SIZE , (int)this._y * Sprite.DEFAULT_SIZE , this);
	}

	@Override
	public boolean canThrough(Entity e) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean collide(Entity e) {
		// TODO Auto-generated method stub
		return false;
	}

}
