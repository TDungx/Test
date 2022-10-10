package game;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import entities.Entity;
import entities.bombAndFlame.Bomb;
import entities.bombAndFlame.FlameSegment;
import graphics.Screen;
import input.Input;
import entities.character.Bomber;
import entities.character.Character;



public class Board {

	protected LevelLoader _levelLoader;
	protected Game game;
	protected Input input;
	protected Screen screen;
	
	public Entity[] entitiesList;
	public List<Character> charactersList = new ArrayList<>();
	protected List<Bomb> bombsList = new ArrayList<>();
	
	private int _screenToShow = -1; //1:endgame, 2:changelevel, 3:paused
	
	private int _time = Game.TIME;
	private int _points = Game.POINTS;
	
	public Board(Game game, Input input, Screen screen) {
		this.game = game;
		this.input = input;
		this.screen = screen;
		
		loadLevel(1); //start in level 1
	}
	
	
	public void update() {
		if( game.isPaused() ) return;
		
		updateEntities();
		updateCharacters();
		updateBombs();
		//updateMessages();
		detectEndGame();
		
		for (int i = 0; i < charactersList.size(); i++) {
			Character a = charactersList.get(i);
			if(a.isRemoved()) {
				charactersList.remove(i);
				i--;
			}
		}
	}

	
	public void render(Screen screen) {
		if( game.isPaused() ) return;
		
		int x0 = Screen.startX / Game.TILES_SIZE; 
		
		int x1 = (Screen.startX + screen.getScreenWidth()) / Game.TILES_SIZE + 1; 
		int y0 = Screen.startY / Game.TILES_SIZE;
		int y1 = (Screen.startY + screen.getScreenHeight()) / Game.TILES_SIZE; 
		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				
				entitiesList[x + y * _levelLoader.getLevelWidth()].render(screen);
			}
		}
		
		renderBombs(screen);
		renderCharacter(screen);
		
	}
	
	
	
	public void loadLevel(int level) {
		_time = Game.TIME;
		_screenToShow = 2;
		game.resetScreenDelay();
		game.isPaused();
		charactersList.clear();
		bombsList.clear();
		
		
		try {
			_levelLoader = new LevelLoadFromFile(this, level);
			entitiesList = new Entity[_levelLoader.getLevelHeight() * _levelLoader.getLevelWidth()];
			
			_levelLoader.createEntities();
			
		} catch (Exception e) {
			endGame();
		}
	}
	
	protected void detectEndGame() {
		if(_time <= 0)
			endGame();
	}
	
	public void endGame() {
		_screenToShow = 1;
		game.resetScreenDelay();
		this.game.isPaused = true;
	}
	
	public boolean detectNoEnemies() {
		int total = 0;
		for (int i = 0; i < charactersList.size(); i++) {
			if(charactersList.get(i) instanceof Bomber == false)
				++total;
		}
		
		return total == 0;
	}
	
	public void drawScreen(Graphics g) {
		switch (_screenToShow) {
			case 1:
				screen.drawEndGame(g, _points);
				break;
			case 2:
				screen.drawChangeLevel(g, _levelLoader.getLevel());
				break;
			case 3:
				screen.drawPaused(g);
				break;
		}
	}
	
	public Entity getEntity(double x, double y, Character m) {
		
		Entity res = null;
		
		res = getFlameSegmentAt((int)x, (int)y);
		if( res != null) return res;
		
		res = getBombAt(x, y);
		if( res != null) return res;
		
		res = getCharacterAtExcluding((int)x, (int)y, m);
		if( res != null) return res;
		
		res = getEntityAt((int)x, (int)y);
		
		return res;
	}
	
	public List<Bomb> getBombs() {
		return bombsList;
	}
	
	public Bomb getBombAt(double x, double y) {
		Iterator<Bomb> bs = bombsList.iterator();
		Bomb b;
		while(bs.hasNext()) {
			b = bs.next();
			if(b.getX() == (int)x && b.getY() == (int)y)
				return b;
		}
		
		return null;
	}

	public Bomber getBomber() {
		Iterator<Character> itr = charactersList.iterator();
		
		Character cur;
		while(itr.hasNext()) {
			cur = itr.next();
			
			if(cur instanceof Bomber)
				return (Bomber) cur;
		}
		
		return null;
	}
	
	public Character getCharacterAtExcluding(int x, int y, Character a) {
		Iterator<Character> itr = charactersList.iterator();
		
		Character cur;
		while(itr.hasNext()) {
			cur = itr.next();
			if(cur == a) {
				continue;
			}
			
			if(cur.getXTile() == x && cur.getYTile() == y) {
				return cur;
			}
				
		}
		
		return null;
	}
	
	public FlameSegment getFlameSegmentAt(int x, int y) {
		Iterator<Bomb> bs = bombsList.iterator();
		Bomb b;
		while(bs.hasNext()) {
			b = bs.next();
			
			FlameSegment e = b.flameAt(x, y);
			if(e != null) {
				return e;
			}
		}
		
		return null;
	}
	
	public Entity getEntityAt(double x, double y) {
		return entitiesList[(int)x + (int)y * _levelLoader.getLevelWidth()];
	}
	
	public void addEntity(int pos, Entity e) {
		entitiesList[pos] = e;
	}
	
	public void addCharacter(Character e) {
		charactersList.add(e);
	}
	
	public void addBomb(Bomb e) {
		bombsList.add(e);
	}
	
	protected void renderCharacter(Screen screen) {
		Iterator<Character> itr = charactersList.iterator();
		while(itr.hasNext())
			itr.next().render(screen);
	}
	
	protected void renderBombs(Screen screen) {
		Iterator<Bomb> itr = bombsList.iterator();
		
		while(itr.hasNext())
			itr.next().render(screen);
	}
	
	protected void updateEntities() {
		if( game.isPaused() ) return;
		for (int i = 0; i < entitiesList.length; i++) {
			entitiesList[i].update();
		}
	}
	
	protected void updateCharacters() {
		if( game.isPaused() ) return;
		Iterator<Character> itr = charactersList.iterator();
		
		while(itr.hasNext() && ! game.isPaused())
			itr.next().update();
	}
	
	protected void updateBombs() {
		if( game.isPaused() ) return;
		Iterator<Bomb> itr = bombsList.iterator();
		
		while(itr.hasNext()) {
			itr.next().update();
		}
			
	}
	
	public int subtractTime() {
		if(game.isPaused())
			return this._time;
		else
			return this._time--;
	}

	public Input getInput() {
		return input;
	}

	public LevelLoader getLevel() {
		return _levelLoader;
	}

	public Game getGame() {
		return game;
	}

	public int getShow() {
		return _screenToShow;
	}

	public void setShow(int i) {
		_screenToShow = i;
	}

	public int getTime() {
		return _time;
	}

	public int getPoints() {
		return _points;
	}

	public void addPoints(int points) {
		this._points += points;
	}
	
	public int getWidth() {
		return _levelLoader.getLevelWidth();
	}

	public int getHeight() {
		return _levelLoader.getLevelHeight();
	}
	
	
}
