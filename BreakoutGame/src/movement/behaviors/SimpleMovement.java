/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 4, 2021
 * @editors:
 **/
package movement.behaviors;

import game.engine.Movable;
import javafx.geometry.Point2D;

public class SimpleMovement implements Movable {

	@Override
	public Point2D move(double timeDelta, Point2D moveDirection, double speed) {
		double horizontalVelocity = 0;
		double verticalVelocity = 0;
		
		if(moveDirection.getX() == 1) {
			horizontalVelocity = speed * timeDelta;
		} else if (moveDirection.getX() == -1) {
			horizontalVelocity = -speed * timeDelta;
		}
		
		if(moveDirection.getY() == 1) {
			verticalVelocity = -speed * timeDelta;
		} else if (moveDirection.getY() == -1) {
			verticalVelocity = speed * timeDelta;
		}
		
		return new Point2D(horizontalVelocity, verticalVelocity);
	}
}
