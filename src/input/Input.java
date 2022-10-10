package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Input implements KeyListener{

	
	private boolean[] keys = new boolean[90]; //120 is enough to this game
	public boolean up, down, left, right, space;
	
	public void update() {
		
		if( keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W] ) up = true;
		else up = false;
		if( keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S] ) down = true;
		else down = false;
		if( keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A] ) left = true;
		else left = false;
		if( keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D] ) right = true;
		else right = false;
		space = keys[KeyEvent.VK_SPACE];
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
		keys[e.getKeyCode()] = true;
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		keys[e.getKeyCode()] = false;
	}
	
}
