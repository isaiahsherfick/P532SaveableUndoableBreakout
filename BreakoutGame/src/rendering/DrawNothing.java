package rendering;

import org.json.simple.JSONObject;

import game.engine.DrawObject;
import game.engine.Drawable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class DrawNothing implements Drawable {

	@Override
	public void draw(DrawObject drawMe, GraphicsContext context) {
		return;
	}
	@SuppressWarnings("unchecked")
	public JSONObject save()
	{
		JSONObject obj = new JSONObject();
		obj.put("drawStrategy","DrawNothing");
		return obj;
	}
	public void load(JSONObject loadString)
	{
		return;
	}
}
