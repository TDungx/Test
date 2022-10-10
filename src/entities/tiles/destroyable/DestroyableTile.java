package entities.tiles.destroyable;


import java.util.Objects;

import entities.Entity;
import entities.bombAndFlame.Flame;
import entities.tiles.Tile;
import graphics.Sprite;

public class DestroyableTile extends Tile{

	protected boolean isDestroyed = false;
	protected Sprite belowSprite = Sprite.grass;
	protected static int animation = 0;
	protected final int maxAnimation = 7500;
	protected static int timeAnimation = 30;
	protected final static int TIMEANIMATION = timeAnimation;
	public DestroyableTile(int x, int y, Sprite sprite) {
		super(x, y, sprite);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		if( isDestroyed ) {
			if( animation < maxAnimation ) animation++;
			else animation = 0;
			if( timeAnimation >= 0 ) {
				timeAnimation--; System.out.println(timeAnimation);;
			}
			else {
				this.remove();
				
			}
		}
	}
	
	@Override
	public boolean collide(Entity e) {
		
        if(e instanceof Flame) isDestroyed = true;
        if(Objects.equals(this._sprite, Sprite.grass)) return true;
		return false;
	}
	
	@Override
	public boolean canThrough(Entity e) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void addBelowSprite(Sprite sprite) {
		belowSprite = sprite;
	}

}
