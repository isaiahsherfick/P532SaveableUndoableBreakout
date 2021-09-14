/**
 * @author: Abhishek Dharmendra Tiwari
 * @CreationDate: Sep 13, 2021
 * @editors:
 * @References:
 * https://guides.codepath.com/android/Creating-Custom-Listeners
 **/
package breakout;

import command.pattern.SpecialBrickDestroyCommand;
import game.engine.Drawable;
import game.engine.GameObject;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class SpecialBrick extends Brick {
	
	public SpecialBrick(Drawable drawBehaviour, Color color, int locationX, int locationY, int width, int height) {
		super(drawBehaviour, color, locationX, locationY, width, height);
		previousPosition = position;
	}
	
	@Override
	public void handleObjectCollision(GameObject collider, Point2D newPosition, Point2D newMoveDirection) {
		collider.setColor(this.getColor());
		commandListener.receiveCommand(new SpecialBrickDestroyCommand(this));
	}
	
}
