/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 4, 2021
 * @editors:
 **/
package rendering;

import game.engine.DrawObject;
import game.engine.Drawable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import userinterface.Text;
import userinterface.TextButton;

public class DrawButton implements Drawable {

	@Override
	public void draw(DrawObject drawMe, GraphicsContext context) {
        Point2D objectPosition = drawMe.getPosition();
        Point2D objectDimensions = drawMe.getDimensions();
        
        context.setFill(drawMe.getColor());
        context.fillRect(objectPosition.getX(), objectPosition.getY(), objectDimensions.getX(), objectDimensions.getY());
        Text text = ((TextButton)drawMe).getText();
        text.performDraw(context);
	}
}