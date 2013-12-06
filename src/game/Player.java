package game;

import anim.util.*;
import anim.images.*;

import java.awt.Rectangle;
import java.awt.Image;

/**
 * Describes the player's character.
 * 
 * @author Dan Easterling
 * @course CPSC 470
 * @section Summer
 * @date 20 June 2013
 * @descr - Final Project - A scolling game, wherein a hyena shoots zombies.
 */
public class Player implements GamePiece {

	public static final int UP = -1;
	public static final int DOWN = 1;
	public static final int LEFT = -1;
	public static final int RIGHT = 1;

	private int dx = 0, dy =0;

	private Rectangle collisionBounds;
	private int centerX, centerY;
	private int minX, minY, maxX, maxY;
	private int speed;
	private int bulletCooldown;

	private AnimatedImage left;
	private AnimatedImage right;
	private AnimatedImage currentImage;
	private int curFrame = 0;

	public Player(int x, int y, int width, int height, int speed,
			Rectangle field) {
		collisionBounds = new Rectangle(x, y, width, height);
		this.speed = speed;
		updateCenter(); 
		minX = field.x;
		minY = field.y;
		maxX = (field.width + field.x) - collisionBounds.width;
		maxY = (field.height + field.y) - collisionBounds.height;

		bulletCooldown = 250;

		left = new AnimatedImage(2, "hyenaleft/", "", 1, "gif");
		right = new AnimatedImage(2, "hyenaright/", "", 1, "gif");
		currentImage = left;
	}

	public Rectangle getCollisionBounds() {
		return collisionBounds;
	}

	public int getCenterX() {
		return centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	public int getBulletCooldown() {
		return bulletCooldown;
	}

	public void setBulletCooldown(int cooldown) {
		bulletCooldown = cooldown;
	}

	public void update() {
		moveY(dy);
		moveX(dx);

	}

	public void setDirY(int dy) {
		this.dy = dy;
	}

	public void setDirX(int dx) {
		this.dx = dx;
		if (dx < 0) {
			currentImage = left;
		} else if (dx > 0) {
			currentImage = right;
		}
		curFrame = (curFrame + 1) % currentImage.numImages();
	}

	public void moveY(int delta) {
		delta *= speed;
		collisionBounds.y += delta;
		if (collisionBounds.y > maxY) {
			collisionBounds.y = maxY;
		} else if (collisionBounds.y < minY) {
			collisionBounds.y = minY;
		}
		updateCenter();
	}

	private void updateCenter() {
		centerX = collisionBounds.x + collisionBounds.width / 2;
		centerY = collisionBounds.y + collisionBounds.height / 2;
	}

	public void moveX(int delta) {
		delta *= speed;
		collisionBounds.x += delta;
		if (collisionBounds.x > maxX) {
			collisionBounds.x = maxX;
		} else if (collisionBounds.x < minX) {
			collisionBounds.x = minX;
		}
		updateCenter();
	}

	public Image getImage() {
		return currentImage.getImage(curFrame);
	}

	@Override
	public boolean shouldDie() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setDie(boolean die) {
		// TODO Auto-generated method stub
		
	}

}
