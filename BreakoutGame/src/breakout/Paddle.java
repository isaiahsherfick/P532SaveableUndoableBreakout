/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 4, 2021
 * @editors:
 **/
package breakout;

import command.pattern.PaddleMoveCommand;
import game.engine.Drawable;
import game.engine.GameObject;
import game.engine.Movable;
import input.KeyPolling;
import input.SpawnBallListener;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import userinterface.Clickable;

public class Paddle extends GameObject implements Clickable {
	
	protected Movable moveBehaviour;
	protected double speed = 550f;
	
	private SpawnBallListener spawnBallListener;

	public Paddle() {
		super();
	}
	
	public Paddle(Drawable drawBehaviour, Color color, int locationX, int locationY, int width, int height, Movable moveBehaviour) {
		super(drawBehaviour, color, new Point2D(locationX, locationY), new Point2D(width, height));
		this.moveBehaviour = moveBehaviour;
	}

	@Override
	public void update(double timeDelta) {
		commandListener.receiveCommand(new PaddleMoveCommand(this, timeDelta));
	}
	
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
	public void handleScreenCollision(Point2D newPosition) {
		previousPosition = position;
		position = newPosition;
	}

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
	public void onClick() {
		spawnBallListener.spawnBall();
	}
	
	public void setSpawnBallListener(SpawnBallListener listener) {
		spawnBallListener = listener;
	}
}
