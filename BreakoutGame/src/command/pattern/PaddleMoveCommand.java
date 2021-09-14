/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 4, 2021
 * @editors:
 **/
package command.pattern;

import breakout.Paddle;
import javafx.geometry.Point2D;

public class PaddleMoveCommand implements Command {
	
	private Paddle paddle;
	private double timeDelta;
	private Point2D undoPosition;
	private Point2D undoPreviousPosition;
	private Point2D undoVelocity;
	private Point2D undoMoveDirection;
	private Point2D redoPosition;
	private Point2D redoPreviousPosition;
	private Point2D redoVelocity;
	private Point2D redoMoveDirection;
	
	public PaddleMoveCommand(Paddle paddle, double timeDelta) {
		this.paddle = paddle;
		this.timeDelta = timeDelta;
	}
	
	@Override
	public void execute() {
		undoPosition = paddle.getPosition();
		undoPreviousPosition = paddle.getPreviousPosition();
		undoVelocity = paddle.getVelocity();
		undoMoveDirection = paddle.getMoveDirection();
		
		paddle.performMove(timeDelta);
		
		redoPosition = paddle.getPosition();
		redoPreviousPosition = paddle.getPreviousPosition();
		redoVelocity = paddle.getVelocity();
		redoMoveDirection = paddle.getMoveDirection();
	}

	@Override
	public void undo() {
		paddle.setPosition(undoPosition);
		paddle.setPreviousPosition(undoPreviousPosition);
		paddle.setVelocity(undoVelocity);
		paddle.setMoveDirection(undoMoveDirection);
	}
	
	@Override
	public void redo() {
		paddle.setPosition(redoPosition);
		paddle.setPreviousPosition(redoPreviousPosition);
		paddle.setVelocity(redoVelocity);
		paddle.setMoveDirection(redoMoveDirection);	}

	@Override
	public void store() {
		// Something like...
		// Save out vars
	}

	@Override
	public void load() {
		// Something like...
		// Create ball(?)
		// Load with variables
	}
}
