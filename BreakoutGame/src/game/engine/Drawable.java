/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 4, 2021
 * @editors: Isaiah Sherfick
 * Last modified on: 15 Sep 2021
 * Last modified by: Isaiah Sherfick
 * Changes: Added comments
 **/
package game.engine;

import javafx.scene.canvas.GraphicsContext;

//Drawable interface
public interface Drawable {
    //Method that will draw drawMe onto context
	public void draw(DrawObject drawMe, GraphicsContext context);
}
