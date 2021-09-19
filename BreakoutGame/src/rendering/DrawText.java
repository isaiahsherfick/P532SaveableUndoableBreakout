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
import userinterface.Text;

public class DrawText implements Drawable {
	
	@Override
	public void draw(DrawObject drawMe, GraphicsContext context) {
        Point2D objectPosition = drawMe.getPosition();
        Text text = (Text)drawMe;

        context.setFill(drawMe.getColor());
        context.setFont(text.getFont());
        context.fillText(text.getLabel(), objectPosition.getX(), objectPosition.getY());
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject save()
	{
		JSONObject obj = new JSONObject();
		obj.put("drawStrategy","DrawText");
		return obj;
	}
	
	public void load(JSONObject loadString)
	{
		return;
	}
}
