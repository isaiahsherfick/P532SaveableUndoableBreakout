/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 4, 2021
 //* @editors: Isaiah Sherfick
 //* Last modified on: 18 Sep 2021
 //* Last modified by: Isaiah Sherfick
 //* Changes: extended Saveable for save/load
 **/
package game.engine;

import javafx.geometry.Point2D;
import save_and_load.Saveable;

//Movable interface
public interface Movable extends Saveable{
    //How to move the movable
	public Point2D move(double timeDelta, Point2D moveDirection, double speed);
}
