package game;

import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.*;

import graphics.Screen;
import graphics.Sprite;
import input.Input;

public class Game extends Canvas {
	
	public static final int TILES_SIZE = 16,
			WIDTH = TILES_SIZE * 15,
			HEIGHT = 13 * TILES_SIZE;
	public static int scale = Sprite.SCALED_SIZE / Sprite.DEFAULT_SIZE;
	
	public static final String TITLE = "BombermanGame";
	
	
	
	public static final int TIME = 200;
	public static final int POINTS = 0;
	
	protected static int SCREENDELAY = 3;
	
	protected static int bombRate = 1;
	protected static int bombRadius = 1;
	protected static double bomberSpeed = 1;
	
	
	protected int screenDelay = SCREENDELAY;
	
	private Input input;
	private boolean isRunning = false;
	public boolean isPaused = false;
	
	private Board board;
	private Screen screen;
	private Frame frame;
	
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	public Game(Frame frame) {
		this.frame = frame;
		this.frame.setTitle(TITLE);
		
		screen = new Screen(WIDTH, HEIGHT);
		input = new Input();
		
		board = new Board(this, input, screen);
		this.addKeyListener(input);
	}
	
	public void start() {
		isRunning = true;
		
		long  lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0; 
		double delta = 0;
		int frames = 0;
		int updates = 0;
		requestFocus();
		while(isRunning) {
			
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				update();
				updates++;
				delta--;
			}
			
			if(isPaused) {
				if(screenDelay <= 0) {
					board.setShow(-1);
					isPaused = false;
				}
					
				renderScreen();
			} else {
				renderGame();
			}
				
			
			frames++;
			if(System.currentTimeMillis() - timer > 1000) {
				board.subtractTime();
				frame.getInfoPanel().update(board.getTime(), board.getPoints());
				timer += 1000;
				frame.setTitle(TITLE + " | " + updates + " rate, " + frames + " fps");
				updates = 0;
				frames = 0;
				
				if(board.getShow() == 2)
					--screenDelay;
			}
		}
	}
	
	private void renderGame() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		screen.clear();
		
		board.render(screen);
		
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen._pixels[i];
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		
		g.dispose();
		bs.show();
	}
	
	private void renderScreen() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		screen.clear();
		
		Graphics g = bs.getDrawGraphics();
		
		board.drawScreen(g);

		g.dispose();
		bs.show();
	}
	
	private void update() {
		input.update();
		board.update();
	}
	
	public boolean isPaused() {
		return isPaused;
	}
	
	public int getTime() {
		return TIME;
	}
	public  int getPoints() {
		return POINTS;
	}
	
	public void resetScreenDelay() {
		screenDelay = SCREENDELAY;
	}

	public static int getBombRate() {
		// TODO Auto-generated method stub
		return bombRate;
	}
	
	public static void setBombRate( int i ) {
		bombRate = i;
	}

	public static int getBombRadius() {
		// TODO Auto-generated method stub
		return bombRadius;
	}

	
}
