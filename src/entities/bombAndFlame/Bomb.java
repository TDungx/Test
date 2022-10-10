package entities.bombAndFlame;

import entities.Entity;
import entities.EntityUseAnimation;
import graphics.Screen;
import entities.bombAndFlame.FlameSegment;
import graphics.Sprite;
import entities.character.Character;
import game.Board;
import game.Game;
import entities.bombAndFlame.Flame;

public class Bomb extends EntityUseAnimation  {

	protected int timeToExplode = 120;
	protected final int timeBomb = 20;
	protected Flame[] flamesList;
	protected boolean isExploded = false;
	protected int timeAfterBomb = 20;
	public int bombRadius = 1;
	
	public Bomb(int x, int y, Board board) {
		// TODO Auto-generated constructor stub
		_x = x;
		_y = y;
		this.board = board;
		_sprite = Sprite.bomb;
	}

	public void explode() {
		this.isExploded = true;
		Character x = board.getCharacterAtExcluding((int)_x, (int)_y, null);
        if(x != null){
            x.kill();
        }
        
        flamesList = new Flame[4];
        for (int i = 0; i < flamesList.length; i++) {
            flamesList[i] = new Flame((int) _x, (int) _y, i, Game.getBombRadius(), board);
        }
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		if(timeToExplode >= 0) {
			timeToExplode --;
		}
		else {
			if( !isExploded ) {
				explode();
			}
			else {
				
				updateFlames();
			}
			if( timeAfterBomb >= 0 ) timeAfterBomb--;
			else {
				this.remove();
				
			}
		}
		this.animate();
	}
	
	public FlameSegment flameAt(int x, int y) {
		if(!isExploded) return null;
		
		for (int i = 0; i < flamesList.length; i++) {
			if(flamesList[i] == null) return null;
			FlameSegment e = flamesList[i].flameSegmentAt(x, y);
			if(e != null) return e;
		}
		
		return null;
	}

	@Override
	public void render(Screen screen) {
		// TODO Auto-generated method stub
		if(isExploded) {
			_sprite =  Sprite.bomb_exploded2;
			renderFlames(screen);
		} else _sprite = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, _animate, 60);
		
		int x = (int)_x * Game.TILES_SIZE;
		int y = (int)_y * Game.TILES_SIZE;
		if( !isRemoved() )
		screen.renderEntity(x, y , this);
		
	}
	
	public void updateFlames() {
		for (int i = 0; i < flamesList.length; i++) {
			flamesList[i].update();
		}
	}
	
	public void renderFlames(Screen screen) {
		for (int i = 0; i < flamesList.length; i++) {
			flamesList[i].render(screen);
		}
	}
	
	

	@Override
	public boolean canThrough(Entity e) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean collide(Entity e) {
		// TODO Auto-generated method stub
		if(e instanceof Flame ) {
			timeToExplode = 0;
		}
		return true;
	}
	
}
