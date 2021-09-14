/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 5, 2021
 * @editors: Isaiah Sherfick
 * Last modified on: 14 Sep 2021
 * Last modified by: Isaiah Sherfick
 * Changes: Added comments
 **/
package command.pattern;

import breakout.Ball;
import breakout.GameManager;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

//Command for spawning a ball
public class BallSpawnCommand implements Command {

	private GameManager gameManager;
	private Ball spawnedBall;
	private Point2D undoPosition;
	private Point2D undoPreviousPosition;
	private Point2D undoVelocity;
	private Point2D undoMoveDirection;
	private Color undoColor;
	private Point2D redoPosition;
	private Point2D redoPreviousPosition;
	private Point2D redoVelocity;
	private Point2D redoMoveDirection;
	private Color redoColor;
	
	public BallSpawnCommand(GameManager gameManager) { 
		this.gameManager = gameManager;
	}
	
    //Spawn the ball
	@Override
	public void execute() {
		spawnedBall = gameManager.spawnNewBall();
		
		redoPosition = spawnedBall.getPosition();
		redoPreviousPosition = spawnedBall.getPreviousPosition();
		redoVelocity = spawnedBall.getVelocity();
		redoMoveDirection = spawnedBall.getMoveDirection();
		redoColor = spawnedBall.getColor();
		
		undoPosition = new Point2D(-1, -1);
		undoPreviousPosition = new Point2D(-1, -1);
		undoVelocity = new Point2D(0, 0);
		undoMoveDirection = new Point2D(0, 0);
		undoColor = Color.TRANSPARENT;
	}

    //despawn the ball
	@Override
	public void undo() {
		spawnedBall.setPosition(undoPosition);
		spawnedBall.setPreviousPosition(undoPreviousPosition);
		spawnedBall.setVelocity(undoVelocity);
		spawnedBall.setMoveDirection(undoMoveDirection);
		spawnedBall.setColor(undoColor);
	}

	@Override
	public void redo() {
		spawnedBall.setPosition(redoPosition);
		spawnedBall.setPreviousPosition(redoPreviousPosition);
		spawnedBall.setVelocity(redoVelocity);
		spawnedBall.setMoveDirection(redoMoveDirection);
		spawnedBall.setColor(redoColor);
	}

	@Override
	public void store() {
		// TODO Auto-generated method stub
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub
	}

}
