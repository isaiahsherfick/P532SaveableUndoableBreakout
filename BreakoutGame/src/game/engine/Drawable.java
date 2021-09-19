/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 4, 2021
 * @editors: Isaiah Sherfick
 * Last modified on: 16 Sep 2021
 * Last modified by: Isaiah Sherfick
 * Changes: extended Saveable for save/load
 **/
package game.engine;

import javafx.scene.canvas.GraphicsContext;
import save_and_load.Saveable;

//Drawable interface
public interface Drawable extends Saveable {
    //Method that will draw drawMe onto context
	public void draw(DrawObject drawMe, GraphicsContext context);
}
