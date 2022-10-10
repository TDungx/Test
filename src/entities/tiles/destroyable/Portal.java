package entities.tiles.destroyable;

import graphics.Sprite;

public class Portal extends DestroyableTile{

	public Portal(int x, int y, Sprite sprite) {
		super(x, y, sprite);
		// TODO Auto-generated constructor stub
	}
	
	public Portal(int x, int y) {
		super(x, y, Sprite.brick);
		this.addBelowSprite(Sprite.portal);
	}

}
