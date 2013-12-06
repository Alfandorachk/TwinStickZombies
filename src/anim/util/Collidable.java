package anim.util;

import java.awt.Rectangle;

/**
 * Interface that indicates implementing class can be collided with.
 * 
 * @author Dan Easterling
 * @course CPSC 470
 * @section Summer
 * @date 20 June 2013
 * @descr - Final Project - A scolling game, wherein a hyena shoots zombies.
 */
public interface Collidable {
		
	public Rectangle getCollisionBounds();

}
