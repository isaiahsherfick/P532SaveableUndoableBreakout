/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 4, 2021
 * @editors:
 **/
package command.pattern;

import breakout.Brick;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class BrickDestroyCommand implements Command {

	private Brick brick;
	private Point2D undoPosition;
	private Point2D undoDimensions;
	private Color undoColor;
	private Point2D redoPosition;
	private Point2D redoDimensions;
	private Color redoColor;
	
	public BrickDestroyCommand(Brick brick) {
		this.brick = brick;
	}
	
	@Override
	public void execute() {
		undoPosition = brick.getPosition();
		undoDimensions = brick.getDimensions();
		undoColor = brick.getColor();
		
		brick.destroyBrick();
		
		redoPosition = brick.getPosition();
		redoDimensions = brick.getDimensions();
		redoColor = brick.getColor();
	}

	@Override
	public void undo() {
		brick.setPosition(undoPosition);
		brick.setDimensions(undoDimensions);
		brick.setColor(undoColor);
	}

	@Override
	public void redo() {
		brick.setPosition(redoPosition);
		brick.setDimensions(redoDimensions);
		brick.setColor(redoColor);
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
