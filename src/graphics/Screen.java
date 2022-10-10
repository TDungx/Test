package graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import entities.Entity;
import entities.character.Bomber;
import game.Board;
import game.Game;

public class Screen {
	private int screenWidth;
	private int screenHeight;
	private int _transparentColor = 0xffff00ff;
	public static int startX = 0;
	public static int startY = 0;
	
	public int[] _pixels;

	public Screen(int screenWidth, int screenHeight) {
		super();
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		_pixels = new int[screenHeight * screenWidth];
	}
	
	public void clear() {
		for (int i = 0; i < _pixels.length; i++) {
			_pixels[i] = 0;
		}
	}
	
	public void drawEndGame(Graphics g, int points) {
		g.setColor(Color.black);
		g.fillRect(0, 0, getScreenWidth() * Game.scale, getScreenHeight() * Game.scale);
		
		Font font = new Font("Arial", Font.PLAIN, 20 * Game.scale);
		g.setFont(font);
		g.setColor(Color.white);
		drawCenteredString("GAME OVER", getScreenWidth() * Game.scale, getScreenHeight() * Game.scale, g);
		
		font = new Font("Arial", Font.PLAIN, 10 * Game.scale);
		g.setFont(font);
		g.setColor(Color.yellow);
		drawCenteredString("POINTS: " + points, getScreenWidth() * Game.scale, getScreenHeight() * Game.scale + (Game.TILES_SIZE * 2) * Game.scale, g);
	}

	public void drawChangeLevel(Graphics g, int level) {
		g.setColor(Color.black);
		g.fillRect(0, 0, getScreenWidth() * Game.scale, getScreenHeight() * Game.scale);
		
		Font font = new Font("Arial", Font.PLAIN, 20 * Game.scale);
		g.setFont(font);
		g.setColor(Color.white);
		drawCenteredString("LEVEL " + level, getScreenWidth(), getScreenHeight(), g);
		
	}
	
	public void drawPaused(Graphics g) {
		Font font = new Font("Arial", Font.PLAIN, 20 * Game.scale);
		g.setFont(font);
		g.setColor(Color.white);
		drawCenteredString("PAUSED", getScreenWidth(), getScreenHeight(), g);
		
	}
	
	public void drawCenteredString(String s, int w, int h, Graphics g) {
	    FontMetrics fm = g.getFontMetrics();
	    int x = (w - fm.stringWidth(s)) / 2;
	    int y = (fm.getAscent() + (h - (fm.getAscent() + fm.getDescent())) / 2);
	    g.drawString(s, x, y);
	 }

	public int getScreenWidth() {
		return screenWidth;
	}
	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}
	public int getScreenHeight() {
		return screenHeight;
	}
	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}
	public int get_transparentColor() {
		return _transparentColor;
	}
	public void set_transparentColor(int _transparentColor) {
		this._transparentColor = _transparentColor;
	}
	
	public void clearScreen() {
		for( int i = 0 ; i < this._pixels.length ; i++ ) {
			_pixels[i] = 0;
		}
	}
	
	public void renderEntity(int x, int y, Entity entity) {
		x = x - startX;
		y = y - startY;
		
		for( int i = 0 ; i < entity.getSprite().SIZE ; i++ ) {
			for( int j = 0 ; j < entity.getSprite().SIZE ; j++ ) {
				int xTmp = j + x;
				int yTmp = i + y;
				if( xTmp < - entity.getSprite().SIZE || xTmp >= screenWidth || yTmp < 0 || yTmp >= screenHeight )
				{
					break;
				}
				if( xTmp < 0 ) xTmp = 0;
				if( yTmp < 0 ) yTmp = 0;
				int color = entity.getSprite().getPixel(j + i * entity.getSprite().SIZE);
				if(color != _transparentColor) this._pixels[xTmp + yTmp * screenWidth] = color;
			}
		}
	}
	
	public void renderEntityWithBelowSprite(int xp, int yp, Entity entity, Sprite below) {
		xp -= startX;
		yp -= startY;
		for (int y = 0; y < entity.getSprite().getSize(); y++) {
			int ya = y + yp;
			for (int x = 0; x < entity.getSprite().getSize(); x++) {
				int xa = x + xp;
				if(xa < -entity.getSprite().getSize() || xa >= screenWidth || ya < 0 || ya >= screenHeight) break; //fix black margins
				if(xa < 0) xa = 0;
				int color = entity.getSprite().getPixel(x + y * entity.getSprite().getSize());
				if(color != _transparentColor) 
					_pixels[xa + ya * screenWidth] = color;
				else
					_pixels[xa + ya * screenWidth] = below.getPixel(x + y * below.getSize());
			}
		}
	}
	
	public static int calculateXStartPosition(Board board, Bomber bomber) {
		if(bomber == null) return 0;
		int tmp = startX;
		
		double BomberX = bomber.getX() / 16;
		double complement = 0.5;
		int firstBreakpoint = board.getWidth() / 4;
		int lastBreakpoint = board.getWidth() - firstBreakpoint;
		
		if( BomberX > firstBreakpoint + complement && BomberX < lastBreakpoint - complement) {
			tmp = (int)bomber.getX()  - (Game.WIDTH / 2);
		}
		return tmp;
	}
	
	public static void setStartPosition( int x , int y ) {
		startX = x;
		startY = y;
	}
	
	
}
