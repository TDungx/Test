package entities.bombAndFlame;

import entities.Entity;
import entities.character.Bomber;
//import entities.character.enemy.Enemy;
import graphics.Screen;
import graphics.Sprite;


public class FlameSegment extends Entity {

	protected boolean _last;

	/**
	 *
	 * @param x
	 * @param y
	 * @param direction
	 * @param last cho biết segment này là cuối cùng của Flame hay không,
	 *                segment cuối có sprite khác so với các segment còn lại
	 */
	public FlameSegment(int x, int y, int direction, boolean last) {
		_x = x;
		_y = y;
		_last = last;

		switch (direction) {
			case 0:
				if(!last) {
					_sprite = Sprite.explosion_vertical2;
				} else {
					_sprite = Sprite.explosion_vertical_top_last2;
				}
			break;
			case 1:
				if(!last) {
					_sprite = Sprite.explosion_horizontal2;
				} else {
					_sprite = Sprite.explosion_horizontal_right_last2;
				}
				break;
			case 2:
				if(!last) {
					_sprite = Sprite.explosion_vertical2;
				} else {
					_sprite = Sprite.explosion_vertical_down_last2;
				}
				break;
			case 3: 
				if(!last) {
					_sprite = Sprite.explosion_horizontal2;
				} else {
					_sprite = Sprite.explosion_horizontal_left_last2;
				}
				break;
		}
	}
	
	@Override
	public void render(Screen screen) {
		int xt = (int)_x * 16;
		int yt = (int)_y * 16;
		if(!super.isRemoved())
		screen.renderEntity(xt, yt , this);
	}
	
	@Override
	public void update() {}

	
	public boolean collide(Entity e) {
		// TODO: xử lý khi FlameSegment va chạm với Character
            if(e instanceof Bomber) ((Bomber) e).kill();
            //if(e instanceof Enemy) ((Enemy) e).kill();
		return true;
	}

	@Override
	public boolean canThrough(Entity e) {
		// TODO Auto-generated method stub
		return false;
	}
	

}