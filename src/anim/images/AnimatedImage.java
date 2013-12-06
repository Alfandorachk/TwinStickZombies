package anim.images;

import java.awt.Image;
import javax.swing.ImageIcon;
/** 
 * Automates the loading of a split animated gif
 * 
 * @author Dan Easterling
 * @course CPSC 470
 * @section Summer
 */
public class AnimatedImage {

	private Image images[];

	public AnimatedImage(int numImages, String dir, String base, 
			int numW, String ext) {
		images = new Image[numImages];
		loadImages(dir, base, numW, ext);
	}

	private void loadImages(String dir, String base, int numW, String ext) {
		String file;
		String filename = String.format("%s%s%%0%dd.%s", dir, base, numW, ext);
		for (int i = 0; i < images.length; i++) {
			file = String.format(filename, i);
			System.out.printf("Loading %s\n", file);
			if ((images[i] = (new ImageIcon(file)).getImage()).getWidth(null) < 1)
				System.out.println("Could not load " + file);
		}
	}

	public Image getImage(int image) {
		return images[image];
	}

	public int numImages() {
		return images.length;
	}

	public int getWidth(int image) {
		return images[image].getWidth(null);
	}

	public int getHeight(int image) {
		return images[image].getHeight(null);
	}

}
