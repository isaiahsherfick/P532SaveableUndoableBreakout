/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 4, 2021
 * @editors:
 **/
package collision.detection;

import javafx.geometry.Point2D;

public interface ScreenCollidable {
	public void handleScreenCollision(Point2D newPosition);
}
