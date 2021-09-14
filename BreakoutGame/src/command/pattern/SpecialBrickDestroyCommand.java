/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 4, 2021
 * @editors:
 **/
package command.pattern;

import breakout.SpecialBrick;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class SpecialBrickDestroyCommand implements Command {

	private SpecialBrick specialBrick;
	private Point2D undoPosition;
	private Point2D undoDimensions;
	private Color undoColor;
	private Point2D redoPosition;
	private Point2D redoDimensions;
	private Color redoColor;
	
	public SpecialBrickDestroyCommand(SpecialBrick specialBrick) {
		this.specialBrick = specialBrick;
	}
	
	@Override
	public void execute() {
		undoPosition = specialBrick.getPosition();
		undoDimensions = specialBrick.getDimensions();
		undoColor = specialBrick.getColor();
		
		specialBrick.destroyBrick();
		
		redoPosition = specialBrick.getPosition();
		redoDimensions = specialBrick.getDimensions();
		redoColor = specialBrick.getColor();
	}

	@Override
	public void undo() {
		specialBrick.setPosition(undoPosition);
		specialBrick.setDimensions(undoDimensions);
		specialBrick.setColor(undoColor);
	}

	@Override
	public void redo() {
		specialBrick.setPosition(redoPosition);
		specialBrick.setDimensions(redoDimensions);
		specialBrick.setColor(redoColor);
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
