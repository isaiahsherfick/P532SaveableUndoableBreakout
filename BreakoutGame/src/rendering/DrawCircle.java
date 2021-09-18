/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 4, 2021
 * @editors:
 **/
package rendering;

import org.json.simple.JSONObject;

import game.engine.DrawObject;
import game.engine.Drawable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import save_and_load.BadSaveStringException;

public class DrawCircle implements Drawable {

	@Override
	public void draw(DrawObject drawMe, GraphicsContext context) {
        Point2D objectPosition = drawMe.getPosition();
        Point2D objectDimensions = drawMe.getDimensions();
        
        context.setFill(drawMe.getColor());
        context.fillOval(objectPosition.getX(), objectPosition.getY(), objectDimensions.getX(), objectDimensions.getY());
        // debug center calculation
//        context.setFill(Color.MAGENTA);
//        context.fillRect(object.getCenter().getX(), object.getCenter().getY(), 2, 2);
	}
	
	public JSONObject save()
	{
		JSONObject save = new JSONObject();
		save.put("DrawCircle","null");
	}
	
	public void load(JSONObject saveData) throws BadSaveStringException
	{
		
	}
}
