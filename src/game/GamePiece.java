package game;

import java.awt.Image;

import anim.util.Collidable;

/**
 * Interface that indicates the implementing class has all the indicators
 * of being a piece in a game.
 * 
 * @author Dan Easterling
 * @course CPSC 470
 * @section Summer
 * @date 20 June 2013
 * @descr - Final Project - A scolling game, wherein a hyena shoots zombies.
 */
public interface GamePiece extends Collidable, Updateable, Drawable {

	public boolean shouldDie();

	public void setDie(boolean die);

}
