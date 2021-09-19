/**
 * @author: Abhishek Dharmendra Tiwari
 * @CreationDate: Sep 13, 2021
 * @editors: Isaiah Sherfick
 * Last modified on: 14 Sep 2021
 * Last modified by: Isaiah Sherfick
 * Changes: Added comments
 * @References:
 * https://guides.codepath.com/android/Creating-Custom-Listeners
 **/
package breakout;

import org.json.simple.JSONObject;

import command.pattern.SpecialBrickDestroyCommand;
import game.engine.Drawable;
import game.engine.GameObject;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import save_and_load.Saveable;

//Special brick class that will turn the ball into a fireball
public class SpecialBrick extends Brick {
	
	public SpecialBrick()
	{
		super();
	}
	public SpecialBrick(Drawable drawBehaviour, Color color, int locationX, int locationY, int width, int height) {
		super(drawBehaviour, color, locationX, locationY, width, height);
		previousPosition = position;
	}
	
	@Override
    //When the ball hits the special brick
	public void handleObjectCollision(GameObject collider, Point2D newPosition, Point2D newMoveDirection) {
        //change the ball's color
		collider.setColor(this.getColor());
        //let the commandlistener know that this special brick has been destroyed
		commandListener.receiveCommand(new SpecialBrickDestroyCommand(this));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject save()
	{
		JSONObject obj1 = new JSONObject();
		
		//Put the type of the object at the start of the JSON
		obj1.put("type","SpecialBrick");
		
		obj1.put("color",Saveable.saveColor(color));

        //Use the static method in Saveable for point2ds
        obj1.put("position",Saveable.savePoint2D(position));
        obj1.put("dimensions",Saveable.savePoint2D(dimensions));
        obj1.put("previousPosition", Saveable.savePoint2D(previousPosition));
        obj1.put("drawBehaviour", drawBehaviour.save());
        //obj1.put("brickDestroyedListener", brickDestroyedListener.save());
        obj1.put("color",Saveable.saveColor(color));
        
		return obj1;
	}
	
}
