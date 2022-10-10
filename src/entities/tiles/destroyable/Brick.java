package entities.tiles.destroyable;

import entities.Entity;
import game.Game;
import graphics.Sprite;
import graphics.Screen;

public class Brick extends DestroyableTile {

	public Brick(int x, int y, Sprite sprite) {
		super(x, y, sprite);

		this.addBelowSprite(Sprite.grass);
		// TODO Auto-generated constructor stub
	}
	
	public Brick(int x, int y) {
		super(x, y, Sprite.brick);
		this.addBelowSprite(Sprite.grass);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void update() {
		super.update();
	}
	
	public void remove() {
		this._removed = true;
		this._sprite = Sprite.grass;
	}
	
	public void destroy() {
		isDestroyed = true;
	}
	
	@Override
	public void render(Screen screen) {
		int x = (int)this._x ;
		int y = (int)this._y ;
		
		if(isDestroyed && !isRemoved()) {
			_sprite = Sprite.movingSprite(Sprite.brick_exploded, Sprite.brick_exploded1, Sprite.brick_exploded2, animation, TIMEANIMATION);
			//System.out.println(isRemoved());
			screen.renderEntityWithBelowSprite(x * Sprite.DEFAULT_SIZE, y * Sprite.DEFAULT_SIZE, this, belowSprite);
		}
		else
			screen.renderEntity( x * Sprite.DEFAULT_SIZE , y * Sprite.DEFAULT_SIZE , this);
	}

	public boolean canThrough(Entity e) {
		// TODO Auto-generated method stub
		
		return false;
	}
}
