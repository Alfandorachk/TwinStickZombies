package game;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.Timer;

/**
 * Controls the "physics" of the game.
 * 
 * @author Dan Easterling
 * @course CPSC 470
 * @section Summer
 * @date 20 June 2013
 * @descr - Final Project - A scolling game, wherein a hyena shoots zombies.
 */
public class Game extends Thread {

	private Random rand = new Random();
	private boolean lost = false;
	private boolean won = false;

	private Player player;
	private Rectangle pRect;
	private KeyManager km;
	private Rectangle field;
	private int cycleTime;

	private List<GamePiece> pieces;
	private List<Bullet> bullets;
	private List<Enemy> enemies;

	private boolean canShoot = true;
	private int bulletWidth = 5;
	private int bulletHeight = 5;
	private Timer shotTimer;

	public Game(Rectangle field, KeyManager km, int cycleTime) {
		this.field = field;
		this.cycleTime = cycleTime;
		this.km = km;

		int playerWidth = 30;
		int playerHeight = 30;
		int playerX = field.x + (field.width / 2) - (playerWidth / 2);
		int playerY = field.y + (field.height / 2) - (playerHeight / 2);
		int playerSpeed = 5;
		player = new Player(playerX, playerY, playerWidth, playerHeight,
				playerSpeed, field);
		pRect = player.getCollisionBounds();

		pieces = new LinkedList<>();
		bullets = new LinkedList<>();
		enemies = new LinkedList<>();

		int numEnemies = 150;
		loadEnemies(numEnemies);
	
		shotTimer = new Timer(player.getBulletCooldown(), new BulletListener());
		shotTimer.setRepeats(false);
	}

	private void loadEnemies(int number) {
		int enemyWidth = 40;
		int enemyHeight = 40;
		int enemySpeed = 2;
		for (int i = 0; i < number; i++) {
			int ex = randX();
			int ey = randY();
			int minDistance = 300;
			while ((((int) Math.abs(ex - pRect.x)) < minDistance) ||
					((int) Math.abs(ey - pRect.y)) < minDistance) {
				ex = randX();
				ey = randY();
			}
			Enemy enemy = new Enemy(ex, ey, enemyWidth, enemyHeight,
						enemySpeed, field);
			enemies.add(enemy);
			pieces.add(enemy);
		}
	}

	public Player getPlayer() {
		return player;
	}

	public List<GamePiece> getPieces() {
		return pieces;
	}

	public void run() {
		long oldTime, curTime;
		oldTime = System.currentTimeMillis();
		while (true) {
			curTime = System.currentTimeMillis();
			if ((curTime - oldTime) > cycleTime) {
				update();
				oldTime = curTime;
			}
		}
	}

	public void update() {
		if (won || lost) {
			return;
		}
		movePlayer();
		player.update();
		for (int i = 0; i < pieces.size(); i++) {
			(pieces.get(i)).update();
		}
		spawnBullets();
		moveEnemies();
		checkCollisions();
		cull();
		checkWin();
	}

	private void movePlayer() {
		boolean up = km.isPressed(KeyManager.UP);
		boolean down = km.isPressed(KeyManager.DOWN);
		boolean left = km.isPressed(KeyManager.LEFT);
		boolean right = km.isPressed(KeyManager.RIGHT);

		if (up ^ down) {
			if (up) {
				player.setDirY(Player.UP);
			} else {
				player.setDirY(Player.DOWN);
			}
		} else {
			player.setDirY(0);
		}
		if (left ^ right) {
			if (left) {
				player.setDirX(Player.LEFT);
			} else {
				player.setDirX(Player.RIGHT);
			}
		} else {
			player.setDirX(0);
		}
	}

	private void spawnBullets() {
		if (!canShoot) {
			return;
		}

		boolean up = km.isPressed(KeyManager.SHOOT_UP);
		boolean down = km.isPressed(KeyManager.SHOOT_DOWN);
		boolean left = km.isPressed(KeyManager.SHOOT_LEFT);
		boolean right = km.isPressed(KeyManager.SHOOT_RIGHT);
		int bx, by;
		bx = by = 0;

		if (!((up ^ down) || (left ^ right)))
			return;
		if (up ^ down) {
			if (up) {
				by = Bullet.UP;
			} else {
				by = Bullet.DOWN;
			}
		}
		if (left ^ right) {
			if (left) {
				bx = Bullet.LEFT;
			} else {
				bx = Bullet.RIGHT;
			}
		}

		canShoot = false;
		shotTimer.start();
		Bullet b = new Bullet(player.getCenterX(), player.getCenterY(),
				bulletWidth, bulletHeight, bx, by, 10, field);
		bullets.add(b);
		pieces.add(b);
	}

	private void moveEnemies() {
		for (int i = 0; i < enemies.size(); i++) {
			Enemy enemy = enemies.get(i);
			Rectangle er = enemy.getCollisionBounds();
			if (er.x < pRect.x) {
				enemy.setXDir(1);
			} else {
				enemy.setXDir(-1);
			}
			if (er.y < pRect.y) {
				enemy.setYDir(1);
			} else {
				enemy.setYDir(-1);
			}
		}
	}

	private void cull() {
		for (int i = 0; i < pieces.size(); i++) {
			GamePiece piece  = pieces.get(i);
			if (piece.shouldDie()) {
				pieces.remove(piece);
			}
		}
		for (int i = 0; i < enemies.size(); i++) {
			GamePiece enemy  = enemies.get(i);
			if (enemy.shouldDie()) {
				enemies.remove(enemy);
			}
		}
		for (int i = 0; i < bullets.size(); i++) {
			GamePiece bullet  = bullets.get(i);
			if (bullet.shouldDie()) {
				bullets.remove(bullet);
			}
		}
	}

	private void checkCollisions() {
		for (int j = 0; j < enemies.size(); j++) {
			GamePiece enemy = enemies.get(j);
			Rectangle eb = enemy.getCollisionBounds();
			for (int i = 0; i < bullets.size(); i++) {
				GamePiece bullet = bullets.get(i);
				Rectangle bb = bullet.getCollisionBounds();
				if (bb.intersects(eb)) {
					enemy.setDie(true);
					bullet.setDie(true);
				}
			}
			if (eb.intersects(pRect)) {
				lose();
			}
		}
	}

	private void checkWin() {
		if (enemies.size() == 0) {
			won = !lost;
		}
	}

	public void start() {
		(new Thread(this)).start();
	}

	private class BulletListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			canShoot = true;
		}
	}

	private int randX() {
		return rand.nextInt(field.width);
	}

	private int randY() {
		return rand.nextInt(field.height);
	}

	private void lose() {
		lost = true;
	}

	public boolean wasWon() {
		return won;
	}

	public boolean wasLost() {
		return lost;
	}	

}
