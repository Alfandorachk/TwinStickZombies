package anim.swing;

import javax.swing.JFrame;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Frame;

/**
 * An extension for JFrame that double-buffers its painting.
 * Assumes that all painting in done through this frame, which calls the
 * paint() method of its components.
 *
 * @author Dan Easterling
 */
public class DoubleBufferedFrame extends JFrame implements Runnable {

	private Image offscreen;
	private Graphics goff;
	private int wait;

	public DoubleBufferedFrame(String title, int hz) { 
		super(title);
		wait = 1000 / hz;
	}

	public void start() {
		(new Thread(this)).start();
	}

	@Override
	public void paint(Graphics g) {
		offscreen = createImage(getWidth(), getHeight());
		goff = offscreen.getGraphics();
		paintComponent(goff);
		g.drawImage(offscreen, 0, 0, this);
	}

	public void paintComponent(Graphics g) {
		paintComponents(g);		
	}

	public void run() {
		long oldTime, newTime;
		oldTime = System.currentTimeMillis();
		while (true) {
			newTime = System.currentTimeMillis();
			if ((newTime - oldTime) > wait) {
				oldTime = newTime;
				repaint();	
			}
		}
	}

	public void setFullScreen() {
		setUndecorated(true);
		setExtendedState(Frame.MAXIMIZED_BOTH);
	}

}
