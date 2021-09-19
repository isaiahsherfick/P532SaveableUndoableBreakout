/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 4, 2021
 * @editors: Isaiah Sherfick
 * Last modified on: 14 Sep 2021
 * Last modified by: Isaiah Sherfick
 * Changes: Added comments
 * @References:
 * https://guides.codepath.com/android/Creating-Custom-Listeners
 **/
package breakout;

import org.json.simple.JSONObject;

import collision.detection.BrickDestroyedListener;
import command.pattern.BrickDestroyCommand;
import game.engine.Drawable;
import game.engine.GameObject;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import movement.behaviors.SimpleMovement;
import rendering.DrawSquare;
import save_and_load.Saveable;

public class Brick extends GameObject implements Saveable {
		
	@SuppressWarnings("unused")
	private BrickDestroyedListener brickDestroyedListener;
	
	public Brick() {
		super();
		this.drawBehaviour = new DrawSquare();
	}
	
	public Brick(Drawable drawBehaviour, Color color, int locationX, int locationY, int width, int height) {
		super(drawBehaviour, color, new Point2D(locationX, locationY), new Point2D(width, height));
		previousPosition = position;
	}

	@Override
    //Do nothing since the bricks don't move
    //Potential for strategy object here in case we want moving bricks or something
	public void update(double timeDelta) {
	}
	
	public void setBrickDestroyedListener(BrickDestroyedListener listener) {
		brickDestroyedListener = listener;
	}

	/* 
	 * TODO - how do we undo the deletion of an object?
	 * Pooling might be the answer...
	 */
	public void destroyBrick() {
		// Send message to listener I was destroyed
		// brickDestroyedListener.brickDestroyed(this);
		color = Color.TRANSPARENT;
		position = new Point2D(-1, -1);
		dimensions = new Point2D(-1, -1);
	}
	
	@Override
    //Called on object collision
	public void handleObjectCollision(GameObject collider, Point2D newPosition, Point2D newMoveDirection) {
        //color the ball to match my color
		collider.setColor(this.getColor());

        //Send the command listener a note that I've been destroyed
		commandListener.receiveCommand(new BrickDestroyCommand(this));
	}
	
	@Override
	public void handleScreenCollision(Point2D newPosition) {
		// nothing
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject save() {
		
		JSONObject obj1 = new JSONObject();
		
		//Put the type of the object at the start of the JSON
		obj1.put("type","Brick");
		
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

	@Override
	public void load(JSONObject saveData) {
		
		 color = Saveable.loadColor((JSONObject)saveData.get("color"));
		 position = Saveable.loadPoint2D((JSONObject)saveData.get("position"));
		 dimensions = Saveable.loadPoint2D((JSONObject)saveData.get("dimensions"));
		
		 
		 JSONObject drawBehaviourObj = (JSONObject)saveData.get("drawBehaviour");
		 drawBehaviour = Saveable.getDrawBehaviour(drawBehaviourObj);
		 
		 color = Saveable.loadColor((JSONObject)saveData.get("color"));
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (o instanceof Brick)
		{
			Brick b = (Brick) o;
			return position.equals(b.getPosition()) && dimensions.equals(b.getDimensions());
		}
		return false;
	}
}
