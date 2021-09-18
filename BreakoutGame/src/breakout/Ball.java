/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 4, 2021
 * @editors: Isaiah Sherfick
 * Last modified on: 14 Sep 2021
 * Last modified by: Isaiah Sherfick
 * Changes: Added comments
 **/
package breakout;

import java.io.IOException;
import java.io.StringWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import command.pattern.BallMoveCommand;
import command.pattern.CommandListener;
import game.engine.Drawable;
import game.engine.GameObject;
import game.engine.Movable;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import movement.behaviors.SimpleMovement;
import rendering.DrawCircle;
import save_and_load.Saveable;

public class Ball extends GameObject implements Saveable{
	
    //Move strategy object
	protected Movable moveBehaviour;
//	private final static int SPEED_INCREMENT = 15;
	private double speed = 200f;

	
	private boolean isFireBall;

	private double fireBallBeginDelta;

	public Ball() {
		super();
		//default move and draw behavior
		this.moveBehaviour = new SimpleMovement();
		this.drawBehaviour = new DrawCircle();
	}
	
	public Ball(Drawable drawBehaviour, Color color, int locationX, int locationY, int width, int height, Movable moveBehaviour) {
		super(drawBehaviour, color, new Point2D(locationX, locationY), new Point2D(width, height));
		setFireBall(false);
		this.fireBallBeginDelta = -1;
		this.moveBehaviour = moveBehaviour;
		this.moveDirection = new Point2D(1, -1);
	}

	@Override
    //Called every frame
	public void update(double timeDelta) {
		commandListener.receiveCommand(new BallMoveCommand(this, timeDelta));
		
        //Handle fireball behavior
		if (isFireBall) {
			this.setDimensions(30, 30);
			fireBallBeginDelta += timeDelta;
			if ((int) (fireBallBeginDelta % 60) > 5) {
				setFireBall(false);
				fireBallBeginDelta = -1;
				this.setDimensions(15, 15);
			}
		} 
	}
	
    //Move the ball
	public void performMove(double timeDelta) {
        //Call the move strategy object to learn velocity
		velocity = moveBehaviour.move(timeDelta, moveDirection, speed);
        //update position
		position = position.add(velocity);
	}

	@Override
    //Called on collision with object
	public void handleObjectCollision(GameObject collider, Point2D newPosition, Point2D newMoveDirection) {
        //Update position and moveDirection
		previousPosition = position;
		position = newPosition;
		moveDirection = newMoveDirection;
		
        //Check to see if we're a fireball now
		if (collider instanceof SpecialBrick) {
			setFireBall(true);
			this.fireBallBeginDelta = 0;
		}
	}
	
	@Override
    //Called on collision with screen
	public void handleScreenCollision(Point2D newPosition) {
        //Update position
		previousPosition = position;
		position = newPosition;
//		increaseSpeed()
	}
	
	public double getSpeed()
	{
		return speed;
	}
	
	public boolean isFireBall() {
		return isFireBall;
	}

	public void setFireBall(boolean isFireBall) {
		this.isFireBall = isFireBall;
	}
	
//	private void increaseSpeed() {
//		speed += SPEED_INCREMENT;
//	}
	@SuppressWarnings("unchecked")
	public JSONObject save()
	{
		JSONObject obj = new JSONObject();
		//These are basic data types
		obj.put("speed", speed);
        obj.put("isFireBall",isFireBall);

        //These create nested JSON objects
        
        //Use the static method in Saveable for colors
        obj.put("color",Saveable.saveColor(color));

        //Use the static method in Saveable for point2ds
        obj.put("position",Saveable.savePoint2D(position));
        obj.put("dimensions",Saveable.savePoint2D(dimensions));
        obj.put("previousPosition", Saveable.savePoint2D(previousPosition));
        obj.put("velocity",  Saveable.savePoint2D(velocity));
        obj.put("moveDirection",  Saveable.savePoint2D(moveDirection));
        
        //These all need their own save method
        //To create another nested JSON object
        
        obj.put("moveBehaviour", moveBehaviour.save());
        obj.put("drawBehaviour", drawBehaviour.save());
        
        //DO NOT SAVE COMMAND LISTENER IN SAVE() METHODS
        //IT WILL BE SET IN THE SAVEANDLOADMANAGER LOAD() METHOD
        //obj.put("commandListener", commandListener.save());
        
        return obj;
	}
	public void load(JSONObject saveData) 
	{
		 speed = (double)saveData.get("speed");
		 isFireBall = (boolean)saveData.get("isFireBall");
		 color = Saveable.loadColor((JSONObject)saveData.get("color"));
		 position = Saveable.loadPoint2D((JSONObject)saveData.get("position"));
		 dimensions = Saveable.loadPoint2D((JSONObject)saveData.get("dimensions"));
		 previousPosition = Saveable.loadPoint2D((JSONObject)saveData.get("previousPosition"));
		 velocity = Saveable.loadPoint2D((JSONObject)saveData.get("velocity"));
		 moveDirection = Saveable.loadPoint2D((JSONObject)saveData.get("moveDirection"));
		 
		 JSONObject moveBehaviourObj = (JSONObject)saveData.get("moveBehaviour");
		 moveBehaviour = Saveable.getMoveBehaviour(moveBehaviourObj);
		 
		 JSONObject drawBehaviourObj = (JSONObject)saveData.get("drawBehaviour");
		 drawBehaviour = Saveable.getDrawBehaviour(drawBehaviourObj);
	}
	
	//simple equals override that only checks a few variables
	//for testing purposes
	@Override
	public boolean equals(Object o)
	{
		if (o instanceof Ball)
		{
			Ball b = (Ball) o;
			return speed == b.getSpeed() && velocity.equals(b.getVelocity()) && position.equals(b.getPosition());
		}
		return false;
	}

}
