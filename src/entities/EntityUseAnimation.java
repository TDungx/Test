package entities;

import graphics.Screen;

public abstract class EntityUseAnimation extends Entity{

	protected static int _animate = 0;
	protected final int MAX_ANIMATE = 7500;
	
	protected void animate() {
		if(_animate < MAX_ANIMATE) _animate++; else _animate = 0;
	}

	public void move() {
		// TODO Auto-generated method stub
		
	}
	
}
