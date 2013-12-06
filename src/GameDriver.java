import anim.swing.*;
import game.*;

import javax.swing.JFrame;
import javax.swing.Timer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Main driver for the game.
 * 
 * @author Dan Easterling
 * @course CPSC 470
 * @section Summer
 * @date 20 June 2013
 * @descr - Final Project - A scolling game, wherein a hyena shoots zombies.
 */
public class GameDriver {

	public static void main(String args[]) {
		DoubleBufferedFrame frame = new DoubleBufferedFrame("Game", 60);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setFullScreen();
		frame.setVisible(true);
		
		final GamePanel panel = new GamePanel(frame.getWidth(),
				frame.getHeight(), "BG.png");
		frame.add(panel);

		final KeyManager km = new KeyManager();
		frame.addKeyListener(km);

		Game game = new Game(panel.getBGBounds(), km, 17);
		panel.setGame(game);

		frame.revalidate();
		frame.start();

		game.start();

		int delay = 10;
		(new Timer(delay, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if (km.isPressed(KeyManager.EXIT)) {
					System.exit(0);
				}
			}
		})).start();
	}

}
