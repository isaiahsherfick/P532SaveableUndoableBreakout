/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 4, 2021
 * @editors: Isaiah Sherfick
 * Last modified on: 15 Sep 2021
 * Last modified by: Isaiah Sherfick
 * Changes: Added comments
 **/
package game.engine;

import collision.detection.ObjectCollidable;
import collision.detection.ScreenCollidable;
import save_and_load.Saveable;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

//Abstract class GameObject
//Handles only position, velocity, and movement information
public abstract class GameObject extends DrawObject implements ScreenCollidable, ObjectCollidable, Saveable {
	
	protected Point2D previousPosition;
    protected Point2D velocity;
    protected Point2D moveDirection;
  
    public GameObject() {
		super();
		previousPosition = new Point2D(0, 0);
        velocity = new Point2D(0, 0);
        moveDirection = new Point2D(0, 0);
    }
    
    public GameObject(Drawable drawBehaviour, Color color, Point2D position, Point2D dimensions) {
		super(drawBehaviour, color, position, dimensions);
		this.previousPosition = position;
		this.velocity = new Point2D(0, 0);
        moveDirection = new Point2D(0, 0);
    }
    
    public Point2D getPreviousPosition() {
    	return previousPosition;
    }
    
    public void setPreviousPosition(Point2D previousPosition) {
    	this.previousPosition = previousPosition;
    }
    
    public Point2D getVelocity() {
        return velocity;
    } 
    
    public void setVelocity(Point2D velocity) {
    	this.velocity = velocity;
    }
    
	public Point2D getMoveDirection() {
		return moveDirection;
	}
	
	public void setMoveDirection(Point2D moveDirection) {
		this.moveDirection = moveDirection;
	}
    
    public Point2D getCenter(Point2D position) {
        return new Point2D(position.getX() + dimensions.getX() / 2, position.getY() + dimensions.getY() / 2);
    }
    
    public Point2D getUpperLeft(Point2D position) {
        return new Point2D(getCenter(position).getX() - dimensions.getX() / 2, getCenter(position).getY() - dimensions.getY() / 2);
    }
    
    public Point2D getLowerLeft(Point2D position) {
        return new Point2D(getCenter(position).getX() - dimensions.getX() / 2, getCenter(position).getY() + dimensions.getY() / 2);
    }
    
    public Point2D getUpperRight(Point2D position) {
        return new Point2D(getCenter(position).getX() + dimensions.getX() / 2, getCenter(position).getY() - dimensions.getY() / 2);
    }
    
    public Point2D getLowerRight(Point2D position) {
        return new Point2D(getCenter(position).getX() + dimensions.getX() / 2, getCenter(position).getY() + dimensions.getY() / 2);
    }
    
    //Return a string containing the state of the GameObject
    public String save()
    {
    	
    }
}
