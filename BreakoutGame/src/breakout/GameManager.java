/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 12, 2021
 * @editors: Aditi Pednekar, Snehal Patare, Isaiah Sherfick
 * Last modified on: 14 Sep 2021
 * Last modified by: Isaiah Sherfick
 * Changes: Added comments
 **/
package breakout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import collision.detection.BrickDestroyedListener;
import command.pattern.BallSpawnCommand;
import command.pattern.CommandListener;
import game.engine.GameObject;
import game.engine.PausableGameEngine;
import input.SpawnBallListener;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import movement.behaviors.SimpleMovement;
import rendering.DrawButton;
import rendering.DrawCircle;
import rendering.DrawSpecialBrick;
import rendering.DrawSquare;
import rendering.DrawText;
import userinterface.Clickable;
import userinterface.Text;
import userinterface.TextButton;

/**
 * If we implement a level loader...
 * It makes sense that object would handle
 * deleting all of the destroyed game objects.
 * Also, then we would need to implement an object pool.
 * 
 * TODO
 * This should be abstracted to a GameManager class/interface
 * And then Extended/Implemented to BreakoutGameManager
 **/
public class GameManager implements BrickDestroyedListener, SpawnBallListener {

	private PausableGameEngine gameEngine;
	private ArrayList<Object> allObjects;
	private ArrayList<Clickable> clickables;
	private ArrayList<Object> removableObjects;
	private String stageType;
	private static final String DEFAULT_STAGE = "DEFAULT";
	private static final String FUN_STAGE = "FUN";
	
	private SpecialBrick specialBrick;
	
	private static Map<String, Function<String, Boolean>> stageFunctionMap = new HashMap<>();
		
	private CommandListener commandListener;
	
	public GameManager() {
		stageType = DEFAULT_STAGE;
		allObjects = new ArrayList<Object>();
		clickables = new ArrayList<Clickable>();
		removableObjects = new ArrayList<Object>();
		
		stageFunctionMap.put(DEFAULT_STAGE, defaultStageFunction);
		stageFunctionMap.put(FUN_STAGE, funStageFunction);
	}

	public void setEngine(PausableGameEngine gameEngine) {
		this.gameEngine = gameEngine;
	}
	
	public void setCommandListener(CommandListener listener) {
		commandListener = listener;
	}
	
	public void start() {
		loadGame();
	}
	
	public void restart() {
        //Clear objects from the allObjects ArrayList
		allObjects.clear();
		allObjects = null;
        //re-initialize allObjects
		allObjects = new ArrayList<Object>();


        //Do the same to the removableObjects ArrayList
		removableObjects.clear();
		removableObjects = null;
		removableObjects = new ArrayList<Object>();
		
        //And the clickable ArrayList
		clickables.clear();
		clickables = null;
		clickables = new ArrayList<Clickable>();
	}

	/*
	 * TODO - Can we do better with those click methods?
	 * Not a fan of inline constructor method overriding.
	 * A bunch of click methods in this class?
	 */
	public void loadGame() {
		// TODO encapsulate in a level loader component
		// Since we only have 1 level
		// It's reasonable to let it live here for now
		// The moment we have multiple levels a level loader is necessary
		// AND POOLING!
		
		stageFunctionMap.get(this.stageType).apply("Stage");

		//BuildFunLevel();
		//BuildSeriousLevel();
		//BuildBallCollisionTests();
		
        //Make the paddle
		Paddle paddle = new Paddle(new DrawSquare(), Color.BLACK, 350, 550, 100, 10, new SimpleMovement());
		paddle.setSpawnBallListener(this);
        //Add it to the AllObjects ArrayList
		addObject(paddle);

        //Make the clock, add it to allobjects
		DigitalTimer clock = new DigitalTimer(new DrawText(), Color.BLACK, 1, 30, "Verdana", 30, "Timer: ");
		addObject(clock);
		
        //Make the pause button
		TextButton pauseButton = new TextButton(new DrawButton(), Color.BLACK, new Point2D(200, 7), new Point2D(60, 30), 
				new Text(new DrawText(), Color.WHITE, 210, 25, "Verdana", 12, "Pause")) 
				{ 
                    //Supply it with onClick behavior
					@Override 
					public void onClick() {
						gameEngine.pause();
					} 
				};
		addObject(pauseButton);
		
        //Make the resumeButton
		TextButton resumeButton = new TextButton(new DrawButton(), Color.BLACK, new Point2D(275, 7), new Point2D(60, 30), 
				new Text(new DrawText(), Color.WHITE, 282, 25, "Verdana", 12, "Resume")) 
				{ 
                    //Supply it with onClick behavior
					@Override 
					public void onClick() {
						gameEngine.resume();
					} 
				};
		addObject(resumeButton);
		
        //Make the restartButton
		TextButton restartButton = new TextButton(new DrawButton(), Color.BLACK, new Point2D(350, 7), new Point2D(60, 30), 
				new Text(new DrawText(), Color.WHITE, 357, 25, "Verdana", 12, "Restart")) 
				{ 
					@Override 
					public void onClick() {
						gameEngine.restart();
					} 
				};
		addObject(restartButton);
		
        //Make the undoButon
		TextButton undoButton = new TextButton(new DrawButton(), Color.BLACK, new Point2D(425, 7), new Point2D(60, 30), 
				new Text(new DrawText(), Color.WHITE, 432, 25, "Verdana", 12, "Undo")) 
				{ 
					@Override 
					public void onClick() {
						gameEngine.undo();
					} 
				};
		addObject(undoButton);
		
        //Make the rewindButton
		TextButton rewindButton = new TextButton(new DrawButton(), Color.BLACK, new Point2D(500, 7), new Point2D(60, 30), 
				new Text(new DrawText(), Color.WHITE, 507, 25, "Verdana", 12, "Rewind")) 
				{ 
					@Override 
					public void onClick() {
						gameEngine.rewind();
					} 
				};
		addObject(rewindButton);
		
        //Make the redobutton
		TextButton redoButton = new TextButton(new DrawButton(), Color.BLACK, new Point2D(575, 7), new Point2D(60, 30), 
				new Text(new DrawText(), Color.WHITE, 582, 25, "Verdana", 12, "Redo")) 
				{ 
					@Override 
					public void onClick() {
						gameEngine.redo();
					} 
				};
		addObject(redoButton);
		
        //Make the fast forward button
		TextButton fastForwardButton = new TextButton(new DrawButton(), Color.BLACK, new Point2D(650, 7), new Point2D(60, 45), 
				new Text(new DrawText(), Color.WHITE, 657, 25, "Verdana", 12, "Fast\nForward")) 
				{ 
					@Override 
					public void onClick() {
						gameEngine.fastForward();
					} 
				};
		addObject(fastForwardButton);
		
        //Make the replaybutton
		TextButton replayButton = new TextButton(new DrawButton(), Color.BLACK, new Point2D(725, 7), new Point2D(60, 30), 
				new Text(new DrawText(), Color.WHITE, 732, 25, "Verdana", 12, "Replay")) 
				{ 
					@Override 
					public void onClick() {
						gameEngine.replay();
					} 
				};
		addObject(replayButton);
	}

    //Add an object to the allObjects arraylist
	public void addObject(Object object) {
		allObjects.add((Object)object);
		if (object instanceof Clickable) {
			clickables.add((Clickable)object);
		}
		gameEngine.addObjectToEngine(object);
	}
	
	public ArrayList<Object> getGameObjects() {
		return allObjects;
	}
	
	public ArrayList<Clickable> getClickables() {
		return clickables;
	}
	
    //Spawn a new ball, add it to allobjects
	public Ball spawnNewBall() {
		Ball ball = new Ball(new DrawCircle(), Color.WHITE, 400, 395, 15, 15, new SimpleMovement());
		addObject(ball);
		return ball;
	}
	
    //Spawn the first ball as a command
	@Override 
	public void spawnBall() { 
		commandListener.receiveCommand(new BallSpawnCommand(this));
	}
	
    //Used for testing ball collisions
	@SuppressWarnings("unused")
	private void BuildBallCollisionTests() {
		Ball ball = new Ball(new DrawCircle(), Color.BLACK, 600, 600, 10, 10, new SimpleMovement());
		addObject(ball);
		ball.setMoveDirection(new Point2D(-1, 1));

		ball = new Ball(new DrawCircle(), Color.WHITE, 0, 0, 10, 10, new SimpleMovement());
		addObject(ball);
		ball.setMoveDirection(new Point2D(1, -1));
		
		ball = new Ball(new DrawCircle(), Color.GREEN, 0, 600, 10, 10, new SimpleMovement());
		addObject(ball);
		ball.setMoveDirection(new Point2D(1, 1));

		ball = new Ball(new DrawCircle(), Color.RED, 202, 398, 10, 10, new SimpleMovement());
		addObject(ball);
		ball.setMoveDirection(new Point2D(-1, -1));
	}
	
	// TODO Figure out how to incorporate deletion with Commands
	@Override
	public void brickDestroyed(Brick brick) {
		queueForDeletion((GameObject)brick);
	}
		
	// TODO Figure out how to incorpate deletion with Commands
	// TODO Encapsulate in a level loader/object pooling componenet
	public boolean hasObjectsForDeletion() {
		return removableObjects.size() > 0;
	}

	// TODO Figure out how to incorpate deletion with Commands
	// TODO encapsulate in a level loader componenet
	public void queueForDeletion(GameObject object) {
		// Prevent double registration
		if(!removableObjects.contains(object)) {
			removableObjects.add(object);
		}
	}
	
	// TODO Figure out how to incorpate deletion with Commands
	// TODO encapsulate in a level loader componenet
	public List<Object> getRemovableObjects() {
		return removableObjects;
	}
	
	// TODO Figure out how to incorpate deletion with Commands
	// TODO encapsulate in a level loader componenet
	public void engineRemovalFinished() {
		removableObjects.clear();
		removableObjects = null;
		removableObjects = new ArrayList<Object>();
	}
	
	// Stage type is set in the customised menu
	public void setStageType (String stageType) {
		this.stageType = stageType;
	}
	
	//TODO add in a separate Stages class
	public Function<String, Boolean> funStageFunction = (args) -> {

		spawnNewBall();

		Brick brick;
		
		// Initial Catch
		brick = new Brick(new DrawSquare(), Color.ORANGERED, 440, 50, 50, 25);
		brick.setBrickDestroyedListener(this);
		addObject(brick);
		
		brick = new Brick(new DrawSquare(), Color.ORANGERED, 500, 100, 50, 25);
		brick.setBrickDestroyedListener(this);
		addObject(brick);
		
		brick = new Brick(new DrawSquare(), Color.ORANGERED, 540, 200, 50, 25);
		brick.setBrickDestroyedListener(this);
		addObject(brick);
		
		specialBrick = new SpecialBrick(new DrawSpecialBrick(), Color.DARKRED, 675, 150, 60, 25);
		specialBrick.setBrickDestroyedListener(this);
		addObject(specialBrick);
		
		// Upper Catch
		brick = new Brick(new DrawSquare(), Color.ORANGE, 300, 50, 50, 25);
		brick.setBrickDestroyedListener(this);
		addObject(brick);
		
		brick = new Brick(new DrawSquare(), Color.ORANGE, 225, 50, 50, 25);
		brick.setBrickDestroyedListener(this);
		addObject(brick);
		
		brick = new Brick(new DrawSquare(), Color.ORANGE, 150, 50, 50, 25);
		brick.setBrickDestroyedListener(this);
		addObject(brick);
		
		brick = new Brick(new DrawSquare(), Color.ORANGE, 60, 50, 50, 25);
		brick.setBrickDestroyedListener(this);
		addObject(brick);

		// Second Catch
		brick = new Brick(new DrawSquare(), Color.YELLOW, 140, 200, 50, 25);
		brick.setBrickDestroyedListener(this);
		addObject(brick);
		
		brick = new Brick(new DrawSquare(), Color.YELLOW, 190, 125, 50, 25);
		brick.setBrickDestroyedListener(this);
		addObject(brick);
		
		brick = new Brick(new DrawSquare(), Color.YELLOW, 230, 200, 50, 25);
		brick.setBrickDestroyedListener(this);
		addObject(brick);

		brick = new Brick(new DrawSquare(), Color.YELLOW, 265, 125, 50, 25);
		brick.setBrickDestroyedListener(this);
		addObject(brick);
		
		brick = new Brick(new DrawSquare(), Color.YELLOW, 285, 200, 50, 25);
		brick.setBrickDestroyedListener(this);
		addObject(brick);
		
		brick = new Brick(new DrawSquare(), Color.YELLOW, 330, 125, 50, 25);
		brick.setBrickDestroyedListener(this);
		addObject(brick);
		
		// Second To Third
		specialBrick = new SpecialBrick(new DrawSpecialBrick(), Color.DARKRED, 425, 200, 60, 25);
		specialBrick.setBrickDestroyedListener(this);
		addObject(specialBrick);
		
		// Third Catch
		brick = new Brick(new DrawSquare(), Color.GREEN, 340, 275, 50, 25);
		brick.setBrickDestroyedListener(this);
		addObject(brick);
		
		brick = new Brick(new DrawSquare(), Color.GREEN, 310, 225, 50, 25);
		brick.setBrickDestroyedListener(this);
		addObject(brick);
		
		brick = new Brick(new DrawSquare(), Color.GREEN, 285, 290, 50, 25);
		brick.setBrickDestroyedListener(this);
		addObject(brick);
		
		brick = new Brick(new DrawSquare(), Color.GREEN, 247, 225, 50, 25);
		brick.setBrickDestroyedListener(this);
		addObject(brick);
		
		brick = new Brick(new DrawSquare(), Color.GREEN, 205, 275, 50, 25);
		brick.setBrickDestroyedListener(this);
		addObject(brick);
		
		brick = new Brick(new DrawSquare(), Color.GREEN, 195, 225, 50, 25);
		brick.setBrickDestroyedListener(this);
		addObject(brick);
		
		// Third To Fourth
//		brick = new Brick(new DrawSquare(), Color.TEAL, 50, 375, 50, 25);
//		brick.setBrickDestroyedListener(this);
//		addObject(brick);
		
		// Fourth Catch
//		brick = new Brick(new DrawSquare(), Color.BLUE, 150, 475, 50, 25);
//		brick.setBrickDestroyedListener(this);
//		addObject(brick);
//		
//		brick = new Brick(new DrawSquare(), Color.BLUE, 200, 400, 50, 25);
//		brick.setBrickDestroyedListener(this);
//		addObject(brick);
//		
//		brick = new Brick(new DrawSquare(), Color.BLUE, 250, 475, 50, 25);
//		brick.setBrickDestroyedListener(this);
//		addObject(brick);
		
//		brick = new Brick(new DrawSquare(), Color.BLUE, 300, 400, 50, 25);
//		brick.setBrickDestroyedListener(this);
//		addObject(brick);
//		
//		brick = new Brick(new DrawSquare(), Color.BLUE, 350, 475, 50, 25);
//		brick.setBrickDestroyedListener(this);
//		addObject(brick);
//		
//		brick = new Brick(new DrawSquare(), Color.BLUE, 400, 400, 50, 25);
//		brick.setBrickDestroyedListener(this);
//		addObject(brick);	
		
		// spawnNewBall();
	

		return true;
	};
	
	//TODO add in a separate Stages class
		public Function<String, Boolean> defaultStageFunction = (args) -> {

			Brick brick;
			ArrayList<Color> colors = new ArrayList<Color>();
			colors.add(Color.RED);
			colors.add(Color.ORANGE);
			colors.add(Color.YELLOW);
			colors.add(Color.GREENYELLOW);
			colors.add(Color.GREEN);
			colors.add(Color.TEAL);
			colors.add(Color.BLUE);
			colors.add(Color.INDIGO);
			colors.add(Color.VIOLET);


			int x = 15;
			int y = 60;
			for (Color color : colors) {
				y = 60;
				for (int i = 0; i < 7; i++) {
					brick = new Brick(new DrawSquare(), color, x, y, 50, 25);
					brick.setBrickDestroyedListener(this);
					addObject(brick);
					y += 35;
				}
				x += 90;
			}
			
			spawnNewBall();
			
			return true;
		};	
}
