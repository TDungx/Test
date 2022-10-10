package entities.character;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import entities.Entity;
import entities.bombAndFlame.Bomb;
import entities.bombAndFlame.Flame;
import entities.tiles.Wall;
import entities.tiles.destroyable.Brick;
import game.Board;
import graphics.Screen;
import graphics.Sprite;
import input.Input;
import game.Game;

public class Bomber extends Character{

	List<Bomb> bombsList = new ArrayList<>();
	public int timePutsBomb = 20;
	protected Input input;
	
	
	
	public Bomber(int x, int y, Board board) {
		this._x = x;
		this._y = y;
		this.board = board;
		bombsList = this.board.getBombs();
		this.input = this.board.getInput();
		this._sprite = Sprite.player_right;
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		if( !isAlive ) {
			this.die();
			return;
		}
		if( timePutsBomb >= 0 ) timePutsBomb--;
		
		animate();
		this.move();
		clearBombs();
		detectPlaceBomb();
	}

	@Override
	public void render(Screen screen) {
		// TODO Auto-generated method stub
		
		Screen.setStartPosition(Screen.calculateXStartPosition(board, this), 0);
		
		if (isAlive)
            chooseSprite();
        else
            _sprite = Sprite.player_dead1;

        screen.renderEntity((int) _x , (int) _y , this);
	}

	@Override
	public void kill() {
		// TODO Auto-generated method stub
		this.isAlive = false;
		
	}

	@Override
	public void die() {
		// TODO Auto-generated method stub
		if (timeAnimate > 0) timeAnimate--;
        else {
            board.endGame();
        }
		
	}
	
	public boolean canMoveRight() {
		
		int xt = ((int)this._x + 1 + 2 * Game.TILES_SIZE / 3) / Game.TILES_SIZE;
		Entity a = board.getEntity(xt , (this._y + 4) / Game.TILES_SIZE , this);
		Entity b = board.getEntity(xt , ( this._y + Game.TILES_SIZE - 1 ) / Game.TILES_SIZE, this);
		if(!a.collide(this) || !b.collide(this))
			return false;
		return true;
	}
	
	public boolean canMoveLeft() {
		
		int xt = ((int)this._x - 1 )/ Game.TILES_SIZE;
		
		Entity a = board.getEntity(xt, (this._y + 4) / Game.TILES_SIZE  , this);
		Entity b = board.getEntity(xt , ( this._y + Game.TILES_SIZE - 1 ) / Game.TILES_SIZE, this);
		if(!a.collide(this) || !b.collide(this))
			return false;
		return true;
	}
	
	public boolean canMoveUp() {
		
		int yt = ((int)this._y + 3 )/ Game.TILES_SIZE;
		
		Entity a = board.getEntity(this._x / Game.TILES_SIZE , yt, this);
		Entity b = board.getEntity((this._x + 2 * Game.TILES_SIZE / 3) / Game.TILES_SIZE , yt, this);
		if(!a.collide(this) || !b.collide(this))
			return false;
		return true;
	}
	
	public boolean canMoveDown() {
		
		int yt = ((int)this._y  + Game.TILES_SIZE )/ Game.TILES_SIZE;
		
		Entity a = board.getEntity((this._x) / Game.TILES_SIZE, yt, this);
		Entity b = board.getEntity((this._x + 2 * Game.TILES_SIZE / 3) / Game.TILES_SIZE, yt, this);
		if(!a.collide(this) || !b.collide(this))
			return false;
		return true;
	}
	
	@Override
    public void move() {
		
		int xa = 0, ya = 0;
		if(input.up) ya--;
		if(input.down) ya++;
		if(input.left) xa--;
		if(input.right) xa++;
		if( xa == 0 && ya == 0 ) isMoving = false;
		else isMoving = true;
		/*
		System.out.println("Right " + this.canMoveRight());
		System.out.println("Down " + this.canMoveDown());
		System.out.println("Up " + this.canMoveUp());
		System.out.println("Left " + this.canMoveLeft());
		*/
        if(xa > 0 && this.canMoveRight() ) {
        	direction = 1;
        	moveRight();
        }
		if(xa < 0 && this.canMoveLeft() ) {
			direction = 3;
			moveLeft();
		}
		if(ya > 0 && this.canMoveDown() ) {
			direction = 2;
			moveDown();
		}
		if(ya < 0 && this.canMoveUp()) {
			direction = 0;
			this.moveUp();
		}
		
		
    }
	
	private void chooseSprite() {
        switch (direction) {
            case 0:
                _sprite = Sprite.player_up;
                if (isMoving) {
                    _sprite = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, _animate, 20);
                }
                break;
            case 1:
                _sprite = Sprite.player_right;
                if (isMoving) {
                    _sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, _animate, 20);
                }
                break;
            case 2:
                _sprite = Sprite.player_down;
                if (isMoving) {
                    _sprite = Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, _animate, 20);
                }
                break;
            case 3:
                _sprite = Sprite.player_left;
                if (isMoving) {
                    _sprite = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, _animate, 20);
                }
                break;
            default:
                _sprite = Sprite.player_right;
                if (isMoving) {
                    _sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, _animate, 20);
                }
                break;
        }
    }
	
	private void detectPlaceBomb() {
        
		if(input.space && Game.getBombRate() > 0 && timePutsBomb < 0) {
			
			int x = (int)(this._x  + _sprite.getSize() / 2) / Game.TILES_SIZE;
			int y = (int)( _y + _sprite.getSize() / 2 ) / Game.TILES_SIZE; 
			
			placeBomb(x,y);
			Game.setBombRate(Game.getBombRate() - 1);
			System.out.println("Boom");
			timePutsBomb = 50;
		}
    }

    protected void placeBomb(int x, int y) {
        Bomb bom = new Bomb(x, y, board);
        board.addBomb(bom);
    }
    
    private void clearBombs() {
    	System.out.println("clb " + Game.getBombRate());
    	for( int i = 0 ; i < bombsList.size() ; i++ ) {
    		System.out.println(123);
    		Bomb b = bombsList.get(i);
    		if (b.isRemoved()) {
            	System.out.println("rm2");
                bombsList.remove(i);
                Game.setBombRate(Game.getBombRate() + 1);
            }
    	}

    }

	@Override
	public boolean canThrough(Entity e) {
		// TODO Auto-generated method stub
		if(e instanceof Wall ) return false;
		if(e instanceof Brick ) return false;
		if( e instanceof Flame ) this.kill();
		return true;
	}

	@Override
	public boolean collide(Entity e) {
		// TODO Auto-generated method stub
		if(e instanceof Flame){
            this.kill();
		}
		return false;
	}

}
