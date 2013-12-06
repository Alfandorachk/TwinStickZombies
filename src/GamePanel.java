import game.*;

import java.awt.Color;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import anim.util.Collidable;

/**
 * Takes care of all the drawing for the game.
 * 
 * @author Dan Easterling
 * @course CPSC 470
 * @section Summer
 * @date 20 June 2013
 * @descr - Final Project - A scolling game, wherein a hyena shoots zombies.
 */
public class GamePanel extends JPanel {

	private Image background;
	private int bgWidth;
	private int bgHeight;
	private int width;
	private int height;
	private int x = 0;
	private int y = 0;
	private int maxX;
	private int maxY;
	private int minX;
	private int minY;
	private Game game;
	private Player player;

	public GamePanel(int width, int height, String bgFile) {
		this.width = width;
		this.height = height;
		background = (new ImageIcon(bgFile)).getImage();
		setPreferredSize(new Dimension(width, height));
		maxX = maxY = 0;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		bgWidth = background.getWidth(null);
		bgHeight = background.getHeight(null);
		minX = screenSize.width - bgWidth;
		minY = screenSize.height - bgHeight;
	}

	public void setGame(Game game) {
		this.game = game;
		this.player = game.getPlayer();
	}

	public Rectangle getBGBounds() {
		return new Rectangle(0, 0, background.getWidth(null),
				background.getHeight(null));
	}

	@Override
	public void paintComponent(Graphics g) {
		setScreen();
		g.drawImage(background, x, y, null);
		for (int i = 0; i < game.getPieces().size(); i++) {
			drawGamePiece(g, game.getPieces().get(i));
		}
		//drawCollidable(g, player);
		//for (Collidable c : game.getPieces()) {
		//	drawCollidable(g, c);
		//}
		drawGamePiece(g, player);	
		if (game.wasWon()) {
			g.drawString("You won!", width / 2, height / 2);
		} else if (game.wasLost()) {
			g.drawString("You lost.", width / 2, height / 2);
		}
	}

	private void drawGamePiece(Graphics g, GamePiece piece) {
		Rectangle r = piece.getCollisionBounds();
		Rectangle r2 = translate(r);
		g.drawImage(piece.getImage(), r2.x, r2.y, r2.width, r2.height, this);
	}

	public void drawCollidable(Graphics g, Collidable c) {
		Rectangle r = c.getCollisionBounds();
		Rectangle r2 = translate(r);
		g.setColor(Color.BLACK);
		g.drawRect(r2.x, r2.y, r2.width, r2.height);
	}

	private Rectangle translate(Rectangle r) {
		return new Rectangle(r.x + x, r.y + y, r.width, r.height);
	}

	private void setScreen() {
		Rectangle pr = player.getCollisionBounds();
		setX((width / 2) - player.getCenterX());
		setY((height / 2) - player.getCenterY());
	}

	public void moveVert(int delta) {
		setY(y + delta);
	}

	public void moveHoriz(int delta) {
		setX(x + delta);
	}

	public void setX(int x) {
		this.x = x;
		if (this.x > maxX) {
			this.x = maxX;
		} else if (this.x < minX) {
			this.x = minX;
		}
	}

	public void setY(int y) {
		this.y = y;
		if (this.y > maxY) {
			this.y = maxY;
		} else if (this.y < minY) {
			this.y = minY;
		}
	}

	public boolean canMoveUp() {
		return !(y == minY);
	}

	public boolean canMoveDown() {
		return !(y == maxY);
	}

	public boolean canMoveLeft() {
		return !(y == minX);
	}

	public boolean canMoveRight() {
		return !(x == maxX);
	}

}
