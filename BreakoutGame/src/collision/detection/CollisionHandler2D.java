/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 4, 2021
 * @editors:
 * @References:
 * https://stackoverflow.com/questions/4904308/axis-aligned-bounding-boxes-collision-what-sides-are-colliding
 **/
package collision.detection;

import java.util.ArrayList;
import java.util.Hashtable;
import breakout.Ball;
import breakout.Brick;
import breakout.SpecialBrick;
import game.engine.AnimationTimerGameLoop;
import game.engine.GameObject;
import javafx.geometry.Point2D;

public class CollisionHandler2D {
	    
    private ArrayList<GameObject> gameObjects;
    private Hashtable<GameObject, GameObject> collidedObjects = new Hashtable<GameObject, GameObject>();
    private double gameSceneWidth;
    private double gameSceneHeight;
	private final static int ZERO_BOUND = 0;
	private final static int ONE = 1;
	private final static int FLIP_DIRECTION = -1;
	private final static String LEFT = "LEFT";
	private final static String RIGHT = "RIGHT";
	private final static String TOP = "TOP";
	private final static String BOTTOM = "BOTTOM";
	
    public CollisionHandler2D() {
    	gameObjects = new ArrayList<GameObject>();
    	collidedObjects = new Hashtable<GameObject, GameObject>();
    }
    
	public void setGameSceneDimensions(double width, double height) {
		gameSceneWidth = width;
		gameSceneHeight = height;
	}
    
    public void restart() {
        // Dump gameObjects to start fresh
    	gameObjects.clear();
    	gameObjects = null;
    	gameObjects = new ArrayList<GameObject>();
    	// Dump any tracked collided objects to start fresh
    	collidedObjects.clear();
    	collidedObjects = null;
    	collidedObjects = new Hashtable<GameObject, GameObject>();
    }
	
    public void addGameObject(GameObject object) {
		// Prevent double registration
		if(!gameObjects.contains(object)) {
			gameObjects.add(object);
		}
    }
    
    public void removeGameObject(GameObject object) {
		// Ensure DrawBehavior is already registered
		int objectIndex = gameObjects.indexOf(object);
		// Then remove it
		if (objectIndex >= 0) {
			gameObjects.remove(object);
		}
    }
    
    public void processCollisions(AnimationTimerGameLoop gameLoop) {
		for (GameObject object1 : gameObjects) {
			for (GameObject object2 : gameObjects) {
				// Skip processing the same two objects
				if(object1 == object2) {
					continue;
				}
				// Skip if both are bricks 
				// TODO how to make this cleaner?
				if(object1 instanceof Brick && object2 instanceof Brick) {
					continue;
				}
				checkObjectCollision(object1, object2);
			}
			checkScreenBoundsCollision(object1, gameLoop);
		}
		// Reset collided tracker per tick
		collidedObjects.clear();
    }

	private void checkObjectCollision(GameObject object1, GameObject object2) {
		// Obtain object positions
		Point2D object1Position = object1.getPosition();
		Point2D object2Position = object2.getPosition();
		
		// Check if positions are overlapping
		if(checkIfOverlap(object1, object2, object1Position, object2Position)) 
		{
//			// TODO figure out how to not have to process the ball as the first object parameter
//			// HACK
//            if (object1 instanceof Ball) {
            handleObjectCollision(object1, object2, checkIfNotFireBall(object1, object2));                
//            }
//            if (object2 instanceof Ball) {
//                handleObjectCollision(object2, object1);
//            }
		}
	}
    
    private boolean checkIfNotFireBall(GameObject object1, GameObject object2) {
		if (object1 instanceof SpecialBrick) {
			object1.handleObjectCollision(object2, object1.getPosition(), object1.getPosition());
			return false;
		}
		
		if (object2 instanceof SpecialBrick) {
			object2.handleObjectCollision(object1, object2.getPosition(), object2.getPosition());
			return false;
		}
		
		return true;
	}

	private boolean checkIfOverlap(GameObject object1, GameObject object2, Point2D object1Position, Point2D object2Position) {
		return object1.getUpperRight(object1Position).getX() >= object2.getUpperLeft(object2Position).getX() &&
				object1.getUpperLeft(object1Position).getX() <= object2.getUpperRight(object2Position).getX() &&
				object1.getLowerLeft(object1Position).getY() >= object2.getUpperLeft(object2Position).getY() &&
				object1.getUpperLeft(object1Position).getY() <= object2.getLowerLeft(object2Position).getY();
	}

	public void handleObjectCollision(GameObject object1, GameObject object2, boolean isNotFireBall) {
    	// Break if we already dealt with this collision
    	// Due to the nature of the double loop it is possible to detect a collision twice in one tick()
    	if((collidedObjects.containsKey(object1) && collidedObjects.get(object1) == object2) ||
    			(collidedObjects.containsKey(object2) && collidedObjects.get(object2) == object1))
    	{
    		return;
    	} else {
    		// Track that these two objects collided
        	collidedObjects.put(object1, object2);
        	// Determine collision direction
        	String collisionDirection = determineCollisionDirection(object1, object2);
        	if (collisionDirection == "") {
        		System.out.println("ERRROOOOORRRRRRRRRRRRRRRRRRRRRRRRR");
        	}
        	// Tell objects they collided
			// TODO does collision direction have to flip to fix the TODO in checkObjectCollision(...)?
        	// This causes hugging of objects
        	kissObjects(object1, object2, collisionDirection, isNotFireBall);
    	}
    }
    
    private String determineCollisionDirection(GameObject object1, GameObject object2) {
    	double leftPenetration = 9999;
    	double rightPenetration = 9999;
    	double topPenetration = 9999;
    	double bottomPenetration = 9999;
    	String direction = "";
    	
    	System.out.println("Ball is object1 " + (object1 instanceof Ball) + "Color is " + object1.getColor().toString());
    	
    	if(object1.getPosition().getX() < object2.getPosition().getX() 
    			&& object2.getPosition().getX() < object1.getUpperRight(object1.getPosition()).getX()) {   
    	    leftPenetration = object1.getUpperRight(object1.getPosition()).getX() - object2.getPosition().getX();
    	    System.out.println("LeftPen: " + leftPenetration);
//    		direction = LEFT;
    	}

    	if(object2.getPosition().getX() < object1.getPosition().getX() 
    			&& object1.getPosition().getX() < object2.getUpperRight(object2.getPosition()).getX()) {
    	    rightPenetration = object1.getPosition().getX() - object2.getUpperRight(object2.getPosition()).getX();
    	    System.out.println("RightPen: " + rightPenetration);
//    		direction = RIGHT;
    	}

    	if(object1.getPosition().getY() < object2.getPosition().getY() 
    			&& object2.getPosition().getY() < object1.getLowerLeft(object1.getPosition()).getY()) {
    		topPenetration = object2.getPosition().getY() - object1.getLowerLeft(object1.getPosition()).getY();
    	    System.out.println("TopPen: " + topPenetration);
//    		direction = TOP;
    	}

    	if(object2.getPosition().getY() < object1.getPosition().getY() 
    			&& object1.getPosition().getY() < object2.getLowerLeft(object2.getPosition()).getY()) {
    		bottomPenetration = object1.getPosition().getY() - object2.getLowerLeft(object2.getPosition()).getY();
    	    System.out.println("BottomPen: " + bottomPenetration);
//    		direction = BOTTOM;
    	}
    	
    	leftPenetration = Math.abs(leftPenetration);
    	rightPenetration = Math.abs(rightPenetration);
    	topPenetration = Math.abs(topPenetration);
    	bottomPenetration = Math.abs(bottomPenetration);
    	
    	if (leftPenetration < rightPenetration && leftPenetration < topPenetration && leftPenetration < bottomPenetration) {
    		direction = LEFT;
    	} else if (rightPenetration < leftPenetration && rightPenetration < topPenetration && rightPenetration < bottomPenetration) {
    		direction = RIGHT;
    	} else if (topPenetration < leftPenetration && topPenetration < rightPenetration && topPenetration < bottomPenetration) {
    		direction = TOP;
    	} else if (bottomPenetration < leftPenetration && bottomPenetration < rightPenetration && bottomPenetration < topPenetration) {
    		direction = BOTTOM;
    	} else {
    		if (leftPenetration == topPenetration) {
    			direction = "TOPLEFT";
    		}
    		if (rightPenetration == topPenetration) {
    			direction = "TOPRIGHT";
    		}
    		if (leftPenetration == bottomPenetration) {
    			direction = "BOTTOMLEFT";
    		}
    		if (rightPenetration == bottomPenetration) {
    			direction = "BOTTOMRIGHT";
    		}
    	}
      	System.out.println(direction);
    	return direction;

    	
    }
    
    private void kissObjects(GameObject object1, GameObject object2, String collisionDirection, boolean isNotFireBall) {
		double object1XMoveDirection = object1.getMoveDirection().getX();
		double object1YMoveDirection = object1.getMoveDirection().getY();
		double object1XPosition = object1.getPosition().getX();
		double object1YPosition = object1.getPosition().getY();
		
		double object2XMoveDirection = object2.getMoveDirection().getX();
		double object2YMoveDirection = object2.getMoveDirection().getY();
		double object2XPosition = object2.getPosition().getX();
		double object2YPosition = object2.getPosition().getY();
		
		
		if (isNotFireBall) {
			if (collisionDirection.contains("TOP")) {
				object1YMoveDirection *= FLIP_DIRECTION;
				object2YMoveDirection *= FLIP_DIRECTION;

				object1YPosition = object2.getPosition().getY() - object1.getDimensions().getY() - ONE;
				object2YPosition = object1.getPosition().getY() + object1.getDimensions().getY() + ONE;
			}
			if (collisionDirection.contains("BOTTOM")) {
				object1YMoveDirection *= FLIP_DIRECTION;
				object2YMoveDirection *= FLIP_DIRECTION;
				
				object1YPosition = object2.getPosition().getY() + object2.getDimensions().getY() + ONE;
				object2YPosition = object1.getPosition().getY() - object2.getDimensions().getY() - ONE;
			} 
			if (collisionDirection.contains("RIGHT")) {
				object1XMoveDirection *= FLIP_DIRECTION;
				object2XMoveDirection *= FLIP_DIRECTION;
				
				object1XPosition = object2.getPosition().getX() + object2.getDimensions().getX() + ONE;
				object2XPosition = object1.getPosition().getX() - object2.getDimensions().getX() - ONE;
			}
			if(collisionDirection.contains("LEFT")) {
				object1XMoveDirection *= FLIP_DIRECTION;
				object2XMoveDirection *= FLIP_DIRECTION;
				
				object1XPosition = object2.getPosition().getX() - object1.getDimensions().getX() - ONE;
				object2XPosition = object1.getPosition().getX() + object1.getDimensions().getX() + ONE;
			}
		}
		
		object1.handleObjectCollision(object2, 
				new Point2D(object1XPosition, object1YPosition), 
				new Point2D(object1XMoveDirection, object1YMoveDirection));
		object2.handleObjectCollision(object1, 
				new Point2D(object2XPosition, object2YPosition), 
				new Point2D(object2XMoveDirection, object2YMoveDirection));
	}

    private void checkScreenBoundsCollision(GameObject object, AnimationTimerGameLoop gameLoop) {
    	Point2D position = object.getPosition();
    	Point2D dimensions = object.getDimensions();
		double positionX = position.getX();
		double positionY = position.getY();
		Point2D velocity = object.getVelocity();
		
		// Horizontal Scene Check
		// if moving left and beyond pixel 0 - force inside bounds
		if (velocity.getX() < 0 && position.getX() < ZERO_BOUND) {
			positionX = ZERO_BOUND;
		}
		// if moving right and beyond scene width - force inside bounds
		else if (velocity.getX() > 0 && (position.getX() + dimensions.getX()) >= gameSceneWidth) { 
			positionX = gameSceneWidth - dimensions.getX();
		} 
		
		// Vertical Scene Check
		// if moving up and beyound pixel 0 - force inside bounds
		if (velocity.getY() < 0 && position.getY() < ZERO_BOUND) {
			positionY = ZERO_BOUND;
		}
		// if moving down and beyond scene height - force inside bounds
		else if (velocity.getY() > 0 && (position.getY() + dimensions.getY()) >= gameSceneHeight) { 
			positionY = gameSceneHeight - dimensions.getY();
		} 

		// Tell object it hit the screen bounds
		// Give it new position flush with screen bounds
		object.handleScreenCollision(new Point2D(positionX, positionY));
		// Tell it to bounce ie update its moveDirection var
		wallBounceCheck(object, gameSceneWidth, gameSceneHeight, gameLoop);
    }
    
	public void wallBounceCheck(GameObject object, double gameSceneWidth, double gameSceneHeight, AnimationTimerGameLoop gameLoop) {
		double horizontalDirection = object.getMoveDirection().getX();
		double verticalDirection = object.getMoveDirection().getY();
		
		if (object.getPosition().getX() == ZERO_BOUND || object.getPosition().getX() == (gameSceneWidth - object.getDimensions().getX())) {
			horizontalDirection *= FLIP_DIRECTION;
		}
		
		if (object.getPosition().getY() == ZERO_BOUND) {
			verticalDirection *= FLIP_DIRECTION;
		}
		
		if (object.getPosition().getY() == (gameSceneHeight - object.getDimensions().getY())) {
			gameLoop.pause();
		}
	
		object.setMoveDirection(new Point2D(horizontalDirection, verticalDirection));
	}
}
