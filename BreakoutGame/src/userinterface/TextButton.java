/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 4, 2021
 * @editors:
 **/
package userinterface;

import org.json.simple.JSONObject;

import game.engine.Drawable;
import game.engine.UIObject;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class TextButton extends UIObject implements Clickable {
	
	private Text text;

	public TextButton(Drawable drawBehaviour, Color color, Point2D position, Point2D dimensions, Text text) {
		super(drawBehaviour, color, position, dimensions);
		this.text = text;
	}
	
	public void setText(Text text) {
		this.text = text;
	}
	
	public Text getText() { 
		return text;
	}
	
	@Override
	public void update(double timeDelta) {
		// nothing 
	}

	@Override
	public void onPress() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRelease() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick() {
		// Override at creation
	}
	
	public JSONObject save()
	{
		System.out.println("Don't call save on a TextButton object!!!");
		return new JSONObject();
	}
	
	public void load(JSONObject saveObj)
	{
		
	}
}
