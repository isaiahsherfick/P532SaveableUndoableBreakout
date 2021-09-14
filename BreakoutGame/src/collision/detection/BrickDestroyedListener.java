/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 4, 2021
 * @editors: Isaiah Sherfick
 * Last modified on: 14 Sep 2021
 * Last modified by: Isaiah Sherfick
 * Changes: Added comments
 **/
package collision.detection;

import breakout.Brick;

//Interface for the brickdestroyed listener
public interface BrickDestroyedListener {
    //What to do when a brick is destroyed
	public void brickDestroyed(Brick brick);
}
