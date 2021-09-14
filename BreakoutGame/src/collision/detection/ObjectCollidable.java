/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 4, 2021
 * @editors:
 **/
package collision.detection;

import game.engine.GameObject;
import javafx.geometry.Point2D;

public interface ObjectCollidable {
	public void handleObjectCollision(GameObject collider, Point2D newPosition, Point2D newMoveDirection);
}
