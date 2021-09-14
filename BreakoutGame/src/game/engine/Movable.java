/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 4, 2021
 * @editors:
 **/
package game.engine;

import javafx.geometry.Point2D;

public interface Movable {
	public Point2D move(double timeDelta, Point2D moveDirection, double speed);
}
