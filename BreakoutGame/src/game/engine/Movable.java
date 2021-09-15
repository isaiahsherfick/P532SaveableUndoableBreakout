/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 4, 2021
 //* @editors: Isaiah Sherfick
 //* Last modified on: 15 Sep 2021
 //* Last modified by: Isaiah Sherfick
 //* Changes: Added comments
 **/
package game.engine;

import javafx.geometry.Point2D;

//Movable interface
public interface Movable {
    //How to move the movable
	public Point2D move(double timeDelta, Point2D moveDirection, double speed);
}
