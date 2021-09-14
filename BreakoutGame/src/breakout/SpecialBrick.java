/**
 * @author: Abhishek Dharmendra Tiwari
 * @CreationDate: Sep 13, 2021
 * @editors: Isaiah Sherfick
 * Last modified on: 14 Sep 2021
 * Last modified by: Isaiah Sherfick
 * Changes: Added comments
 * @References:
 * https://guides.codepath.com/android/Creating-Custom-Listeners
 **/
package breakout;

import command.pattern.SpecialBrickDestroyCommand;
import game.engine.Drawable;
import game.engine.GameObject;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

//Special brick class that will turn the ball into a fireball
public class SpecialBrick extends Brick {
	
	public SpecialBrick(Drawable drawBehaviour, Color color, int locationX, int locationY, int width, int height) {
		super(drawBehaviour, color, locationX, locationY, width, height);
		previousPosition = position;
	}
	
	@Override
    //When the ball hits the special brick
	public void handleObjectCollision(GameObject collider, Point2D newPosition, Point2D newMoveDirection) {
        //change the ball's color
		collider.setColor(this.getColor());
        //let the commandlistener know that this special brick has been destroyed
		commandListener.receiveCommand(new SpecialBrickDestroyCommand(this));
	}
	
}
