/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 4, 2021
 * @editors:
 **/
package game.engine;

import javafx.scene.canvas.GraphicsContext;

public interface Drawable {
	public void draw(DrawObject drawMe, GraphicsContext context);
}
