package game;

import java.awt.Rectangle;

import anim.util.Collidable;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * Represents a bullet.
 * 
 * @author Dan Easterling
 * @course CPSC 470
 * @section Summer
 * @date 20 June 2013
 * @descr - Final Project - A scolling game, wherein a hyena shoots zombies.
 */
public class Bullet implements GamePiece {

	public static final int UP = -1;
	public static final int DOWN = 1;
	public static final int LEFT = -1;
	public static final int RIGHT = 1;

	private Rectangle collisionBounds;
	private int dx, dy;
	private Rectangle field;
	private boolean shouldDie = false;

	private Image image;

	public Bullet(int x, int y, int width, int height,
			int xDir, int yDir, int speed, Rectangle field) {
		collisionBounds = new Rectangle(x, y, width, height);
		dx = xDir * speed;
		dy = yDir * speed;
		this.field = field;
		
		image = (new ImageIcon("bullet.gif")).getImage();
	}

	public Rectangle getCollisionBounds() {
		return collisionBounds;
	}

	public void update() {
		collisionBounds.x += dx;
		collisionBounds.y += dy;
		checkBounds();
	}

	private void checkBounds() {
		if (!collisionBounds.intersects(field)) {
			shouldDie = true;
		}
	}
	
	public void setDie(boolean die) {
		shouldDie = die;
	}

	public boolean shouldDie() {
		return shouldDie;
	}

	public Image getImage() {
		return image;
	}

}
