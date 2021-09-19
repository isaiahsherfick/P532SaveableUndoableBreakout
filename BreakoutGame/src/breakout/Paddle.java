/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 4, 2021
 * @editors: Isaiah Sherfick
 * Last modified on: 18 Sep 2021
 * Last modified by: Isaiah Sherfick
 * Changes: Added Save/Load functionality
 **/
package breakout;

import org.json.simple.JSONObject;

import command.pattern.PaddleMoveCommand;
import game.engine.Drawable;
import game.engine.GameObject;
import game.engine.Movable;
import input.KeyPolling;
import input.SpawnBallListener;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import movement.behaviors.SimpleMovement;
import rendering.DrawSquare;
import save_and_load.Saveable;
import userinterface.Clickable;

public class Paddle extends GameObject implements Clickable {
	
	protected Movable moveBehaviour;
	protected double speed = 550f;
	
	private SpawnBallListener spawnBallListener;

	public Paddle() {
		super();
		//default move and draw behavior
		this.moveBehaviour = new SimpleMovement();
		this.drawBehaviour = new DrawSquare();
	}
	
	public Paddle(Drawable drawBehaviour, Color color, int locationX, int locationY, int width, int height, Movable moveBehaviour) {
		super(drawBehaviour, color, new Point2D(locationX, locationY), new Point2D(width, height));
		this.moveBehaviour = moveBehaviour;
	}

    //on tick
	@Override
	public void update(double timeDelta) {
		commandListener.receiveCommand(new PaddleMoveCommand(this, timeDelta));
	}
	
    //move the paddle
	public void performMove(double timeDelta) {
		captureMoveDirection();
		velocity = moveBehaviour.move(timeDelta, moveDirection, speed);
		previousPosition = position;
		position = position.add(velocity);
	}
	
	// TODO get keypolling instance out of here
	private void captureMoveDirection() {
		if (KeyPolling.getInstance().isDown(KeyCode.RIGHT)) 
	    {
			moveDirection = new Point2D(1, 0);
	    } 
	    else if (KeyPolling.getInstance().isDown(KeyCode.LEFT)) 
	    {
	    	moveDirection = new Point2D(-1, 0);
	    } else {
	    	// Not moving
	    	moveDirection = new Point2D(0,0);
	    }
	}

	@Override
    //When the paddle hits the wall
	public void handleScreenCollision(Point2D newPosition) {
		previousPosition = position;
		position = newPosition;
	}
	
	public double getSpeed()
	{
		return speed;
	}

    //When the paddle collides with another object
	@Override
	public void handleObjectCollision(GameObject collider, Point2D newPosition, Point2D newMoveDirection) {
		// nothing
	}

	@Override
	public void onPress() {
		// nothing
	}

	@Override
	public void onRelease() {
		// nothing
	}

	@Override
    //Spawn a ball on clicking the paddle
	public void onClick() {
		spawnBallListener.spawnBall();
	}
	
	public void setSpawnBallListener(SpawnBallListener listener) {
		spawnBallListener = listener;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject save()
	{
		JSONObject obj = new JSONObject();
		
		//Put the type of the object into the JSON
		obj.put("type","Paddle");
		
		//Save basic data types first
		obj.put("speed", speed);
		
		//Save nested JSON objects without a save() method using the appropriate static
		//method in the Saveable interface.
		obj.put("color", Saveable.saveColor(color));
		obj.put("position", Saveable.savePoint2D(position));
		obj.put("dimensions",Saveable.savePoint2D(dimensions));
		obj.put("previousPosition",Saveable.savePoint2D(previousPosition));
		obj.put("velocity",Saveable.savePoint2D(velocity));
		obj.put("moveDirection",Saveable.savePoint2D(moveDirection));
		
		//Save nested JSON objects with their corresponding save() method
		obj.put("moveBehaviour",moveBehaviour.save());
		obj.put("drawBehaviour",drawBehaviour.save());
		
		//Return the JSONObject
		return obj;
	}
	
	public void load(JSONObject data)
	{
		speed = (double)data.get("speed");
		color = Saveable.loadColor((JSONObject)data.get("color"));
		position = Saveable.loadPoint2D((JSONObject)data.get("position"));
		dimensions = Saveable.loadPoint2D((JSONObject)data.get("dimensions"));
		previousPosition = Saveable.loadPoint2D((JSONObject)data.get("previousPosition"));
		velocity = Saveable.loadPoint2D((JSONObject)data.get("velocity"));
		moveDirection = Saveable.loadPoint2D((JSONObject)data.get("moveDirection"));
		
		JSONObject moveBehaviourObj = (JSONObject)data.get("moveBehaviour");
		JSONObject drawBehaviourObj = (JSONObject)data.get("drawBehaviour");
		
		moveBehaviour = Saveable.getMoveBehaviour(moveBehaviourObj);
		drawBehaviour = Saveable.getDrawBehaviour(drawBehaviourObj);
	}
	
	public boolean equals(Object o)
	{
		if (o instanceof Paddle)
		{
			Paddle b = (Paddle)o;
			return speed == b.getSpeed() && velocity.equals(b.getVelocity()) && position.equals(b.getPosition());
		}
		return false;
	}
}
