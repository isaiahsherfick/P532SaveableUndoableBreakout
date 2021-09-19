/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 5, 2021
 * @editors: Isaiah Sherfick
 * Last modified by: Isaiah Sherfick
 * Last modified on: 18 Sep 2021
 * Changes: Extended Saveable
 **/
package input;

import save_and_load.Saveable;

/**
 * I'm not sure if this is the best package 
 * to keepo this listener in.
 * It's here because this action triggers
 * on clicking the paddle.
 **/
public interface SpawnBallListener extends Saveable {
	public void spawnBall();

}
