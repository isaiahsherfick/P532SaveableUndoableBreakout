/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 4, 2021
 **/
package game.engine;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public abstract class UIObject extends DrawObject {

	public UIObject() {
	}
	
	public UIObject(Drawable drawBehaviour, Color color, Point2D position, Point2D dimensions) {
		super(drawBehaviour, color, position, dimensions);
	}
}
