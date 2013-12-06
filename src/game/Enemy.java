package game;

import java.awt.Image;
import java.awt.Rectangle;

import anim.images.AnimatedImage;

/**
 * Represents an enemy.
 * 
 * @author Dan Easterling
 * @course CPSC 470
 * @section Summer
 * @date 20 June 2013
 * @descr - Final Project - A scolling game, wherein a hyena shoots zombies.
 */
public class Enemy implements GamePiece {

	private Rectangle collisionBounds;
	private Rectangle field;
	private boolean shouldDie;
	private int speed;
	private int dx = 0, dy = 0;

	private AnimatedImage left;
	private AnimatedImage right;
	private AnimatedImage currentImage;
	private int curFrame = 0;

	public Enemy(int x, int y, int width, int height, 
			int speed, Rectangle field) {
		collisionBounds = new Rectangle(x, y, width, height);
		this.speed = speed;
		this.field = field;

		left = new AnimatedImage(8, "zombieleft/", "", 1, "gif");
		right = new AnimatedImage(8, "zombieright/", "", 1, "gif");
		currentImage = left;
	}

	@Override
	public Rectangle getCollisionBounds() {
		return collisionBounds;
	}

	@Override
	public void update() {
		collisionBounds.x += dx * speed;
		collisionBounds.y += dy * speed;

		curFrame = (curFrame + 1) % currentImage.numImages();
	}

	public void setXDir(int dx) {
		this.dx = dx;
		if (dx > 0) {
			currentImage = right;
		} else if (dx < 0) {
			currentImage = left;
		}
	}

	public void setYDir(int dy) {
		this.dy = dy;
	}

	@Override
	public boolean shouldDie() {
		return shouldDie;	
	}

	@Override
	public void setDie(boolean die) {
		shouldDie = die;
	}

	@Override
	public Image getImage() {
		return currentImage.getImage(curFrame);
	}

}
