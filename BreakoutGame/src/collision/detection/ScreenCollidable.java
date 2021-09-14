/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 4, 2021
 * @editors: Isaiah Sherfick
 * Last modified on: 14 Sep 2021
 * Last modified by: Isaiah Sherfick
 * Changes: Added comments
 **/
package collision.detection;

import javafx.geometry.Point2D;

//Interface for an object which can collide with the screen
public interface ScreenCollidable {
    //define how to handle that collision
	public void handleScreenCollision(Point2D newPosition);
}
