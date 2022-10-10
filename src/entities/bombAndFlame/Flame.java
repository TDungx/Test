package entities.bombAndFlame;



import entities.Entity;
import entities.tiles.Wall;
import entities.tiles.destroyable.Brick;
import graphics.Screen;
import graphics.Sprite;
import game.Board;
import entities.bombAndFlame.FlameSegment;
import entities.character.Bomber;

public class Flame extends Entity{

	protected FlameSegment[] _flameSegments = new FlameSegment[0];
	protected int radius = 1;
	protected int direction;
	
	public Flame(int x , int y , Sprite sprite) {
		this._x = x;
		this._y = y;
		this._sprite = sprite;
	}
	
	public Flame(int x, int y, int direction, int radius, Board board) {
		
		_x = x;
		_y = y;
		this.direction = direction;
		this.radius = radius;
		this.board = board;
		createFlameSegments();
	}
	
	private void createFlameSegments() {
		
		_flameSegments = new FlameSegment[calculatePermitedDistance()];

                boolean last = false;
		
		int x = (int)_x;
		int y = (int)_y;
		for (int i = 0; i < _flameSegments.length; i++) {
			if( i == _flameSegments.length - 1 ) last = true;
			else last = false;
			switch (direction) {
				case 0: y--; break;
				case 1: x++; break;
				case 2: y++; break;
				case 3: x--; break;
			}
			_flameSegments[i] = new FlameSegment(x, y, direction, last);
		}
	}
	
	private int calculatePermitedDistance() {
        int r = 0;
		int x = (int)_x;
		int y = (int)_y;
		while(r < radius) {
			if(direction == 0) y--;
			if(direction == 1) x++;
			if(direction == 2) y++;
			if(direction == 3) x--;
			
			Entity a = board.getEntity(x, y, null);
			
			if(a instanceof Bomb) ++radius; 
			
			if(a.collide(this) == false) //cannot pass thru
				break;
			
			++r;
		}
		return r;
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Screen screen) {
		// TODO Auto-generated method stub
		for (int i = 0; i < _flameSegments.length; i++) {
			_flameSegments[i].render(screen);
		}
	}

	@Override
	public boolean canThrough(Entity e) {
		// TODO Auto-generated method stub
		if( e instanceof Wall ) {
			return false;
		}
		return true;
	}
	
	public boolean collide(Entity e) {
		
		if(e instanceof Bomber) ((Bomber) e).kill();
        if( e instanceof Brick ) ((Brick)e).destroy();
                return true;
	}
	
	public FlameSegment flameSegmentAt(int x, int y) {
		for (int i = 0; i < _flameSegments.length; i++) {
			if(_flameSegments[i].getX() == x && _flameSegments[i].getY() == y)
				return _flameSegments[i];
		}
		return null;
	}
	
}
