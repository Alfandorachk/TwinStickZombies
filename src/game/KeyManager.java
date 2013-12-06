package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.HashMap;

/**
 * Manages key presses.  Other classes may poll this one to find out
 * which keys are being pressed.
 * 
 * @author Dan Easterling
 * @course CPSC 470
 * @section Summer
 * @date 20 June 2013
 * @descr - Final Project - A scolling game, wherein a hyena shoots zombies.
 */
public class KeyManager implements KeyListener {

	public static final int UP_KEY = KeyEvent.VK_W;
	public static final int DOWN_KEY = KeyEvent.VK_S;
	public static final int LEFT_KEY = KeyEvent.VK_A;
	public static final int RIGHT_KEY = KeyEvent.VK_D;
	public static final int S_U_KEY = KeyEvent.VK_UP;
	public static final int S_D_KEY = KeyEvent.VK_DOWN;
	public static final int S_R_KEY = KeyEvent.VK_RIGHT;
	public static final int S_L_KEY = KeyEvent.VK_LEFT;
	public static final int EXIT_KEY = KeyEvent.VK_ESCAPE;

	public static final int UP = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 4;
	public static final int RIGHT = 8;
	public static final int SHOOT_UP = 16;
	public static final int SHOOT_DOWN = 32;
	public static final int SHOOT_LEFT = 64;
	public static final int SHOOT_RIGHT = 128;
	public static final int EXIT = 256;

	private int keysPressed = 0;

	public static final HashMap<Integer, Integer> binds = new HashMap<>();
	static {
		binds.put(UP_KEY, UP);
		binds.put(DOWN_KEY, DOWN);
		binds.put(LEFT_KEY, LEFT);
		binds.put(RIGHT_KEY, RIGHT);
		binds.put(S_U_KEY, SHOOT_UP); 
		binds.put(S_D_KEY, SHOOT_DOWN);
		binds.put(S_R_KEY, SHOOT_RIGHT);
		binds.put(S_L_KEY, SHOOT_LEFT);
		binds.put(EXIT_KEY, EXIT);
	}

	public KeyManager() {
		/* do nothing yet */
	}

	public boolean isPressed(int key) {
		return (keysPressed & key) != 0;
	}

	@Override
	public void keyPressed(KeyEvent ke) {
		int key = ke.getKeyCode();
		if (binds.containsKey(key)) {
			keysPressed |= binds.get(key);
		}
	}

	@Override
	public void keyReleased(KeyEvent ke) {
		int key = ke.getKeyCode();
		if (binds.containsKey(key)) {
			keysPressed &= ~binds.get(key);
		}
	}

	@Override
	public void keyTyped(KeyEvent ke) {
		/* This method not used */
	}

}
