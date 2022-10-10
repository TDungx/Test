package entities.character;

import entities.EntityUseAnimation;
import game.Board;
import graphics.Screen;
import graphics.Sprite;

public abstract class Character extends EntityUseAnimation{
	protected int direction = -1;
	protected boolean isAlive = true;
	protected boolean isMoving = false;
	protected Board board;
	protected int timeAnimate = 40;
	
	@Override
	public abstract void update();
	
	public void remove() {
		_removed = true;
	}
	
	public boolean isRemoved() {
		return _removed;
	}
	
	@Override
	public abstract void render(Screen screen);
	
	public boolean canMoveRight() {
		return true;
	}
	
	public boolean canMoveLeft() {
		return true;
	}
	
	public boolean canMoveUp() {
		return true;
	}
	
	public boolean canMoveDown() {
		return true;
	}
	
	public void moveRight() {
		System.out.println("R");
		this._x += 1;
	}
	
	public void moveLeft() {
		System.out.println("L");
		this._x -= 1;
	}
	
	public void moveUp() {
		System.out.println("U");
		this._y -= 1;
	}
	
	public void moveDown() {
		System.out.println("D");
		this._y += 1;
	}
	
	public abstract void kill();
	
	public abstract void die();
}
