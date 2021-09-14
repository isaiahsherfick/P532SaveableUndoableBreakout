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

import collision.detection.BrickDestroyedListener;
import command.pattern.BrickDestroyCommand;
import game.engine.Drawable;
import game.engine.GameObject;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class Brick extends GameObject {
		
	@SuppressWarnings("unused")
	private BrickDestroyedListener brickDestroyedListener;
	
	public Brick() {
		super();
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
}
