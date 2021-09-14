/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 4, 2021
 * @editors: Isaiah Sherfick
 * Last modified on: 14 Sep 2021
 * Last modified by: Isaiah Sherfick
 * Changes: Added comments
 **/
package collision.detection;

import game.engine.GameObject;
import javafx.geometry.Point2D;

//Interface for a collidable object
public interface ObjectCollidable {
    //What do I do when I collide with another object
	public void handleObjectCollision(GameObject collider, Point2D newPosition, Point2D newMoveDirection);
}
