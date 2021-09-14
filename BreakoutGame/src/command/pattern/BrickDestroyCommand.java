/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 4, 2021
 * @editors: Isaiah Sherfick
 * Last modified on: 14 Sep 2021
 * Last modified by: Isaiah Sherfick
 * Changes: Added comments
 **/
package command.pattern;

import breakout.Brick;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

//Command for brick destruction
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
	
    //Destroy the brick
	@Override
	public void execute() {
        //store the brick's previous state
		undoPosition = brick.getPosition();
		undoDimensions = brick.getDimensions();
		undoColor = brick.getColor();
		
		brick.destroyBrick();
		
        //Store the brick's new state
		redoPosition = brick.getPosition();
		redoDimensions = brick.getDimensions();
		redoColor = brick.getColor();
	}

	@Override
    //Un-destroy the brick
	public void undo() {
        //Restore the undo variables
		brick.setPosition(undoPosition);
		brick.setDimensions(undoDimensions);
		brick.setColor(undoColor);
	}

	@Override
    //Re-destroy the brick
	public void redo() {
        //Restore the redo variables
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
