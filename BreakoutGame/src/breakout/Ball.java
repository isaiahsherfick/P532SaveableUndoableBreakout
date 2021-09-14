/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 4, 2021
 * @editors:
 **/
package breakout;

import command.pattern.BallMoveCommand;
import game.engine.Drawable;
import game.engine.GameObject;
import game.engine.Movable;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class Ball extends GameObject {
	
	protected Movable moveBehaviour;
//	private final static int SPEED_INCREMENT = 15;
	private double speed = 200f;
	
	private boolean isFireBall;

	private double fireBallBeginDelta;

	public Ball() {
		super();
	}
	
	public Ball(Drawable drawBehaviour, Color color, int locationX, int locationY, int width, int height, Movable moveBehaviour) {
		super(drawBehaviour, color, new Point2D(locationX, locationY), new Point2D(width, height));
		setFireBall(false);
		this.fireBallBeginDelta = -1;
		this.moveBehaviour = moveBehaviour;
		this.moveDirection = new Point2D(1, -1);
	}

	@Override
	public void update(double timeDelta) {
		commandListener.receiveCommand(new BallMoveCommand(this, timeDelta));
		
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
	
	public void performMove(double timeDelta) {
		velocity = moveBehaviour.move(timeDelta, moveDirection, speed);
		position = position.add(velocity);
	}

	@Override
	public void handleObjectCollision(GameObject collider, Point2D newPosition, Point2D newMoveDirection) {
		previousPosition = position;
		position = newPosition;
		moveDirection = newMoveDirection;
		
		if (collider instanceof SpecialBrick) {
			setFireBall(true);
			this.fireBallBeginDelta = 0;
		}
	}
	
	@Override
	public void handleScreenCollision(Point2D newPosition) {
		previousPosition = position;
		position = newPosition;
//		increaseSpeed()
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

}