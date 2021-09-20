/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 12, 2021
 * @editors: Aditi Pednekar, Snehal Patare, Isaiah Sherfick, Abhishek Tiwari
 * Last modified on: 19 Sep 2021
 * Last modified by: Abhishek Tiwari
 * Changes: Added comments
 **/
package breakout;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.json.simple.JSONObject;

import collision.detection.BrickDestroyedListener;
import command.pattern.BallSpawnCommand;
import command.pattern.CommandListener;
import game.engine.GameObject;
import game.engine.PausableGameEngine;
import input.SpawnBallListener;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import movement.behaviors.SimpleMovement;
import rendering.DrawCircle;
import rendering.DrawSpecialBrick;
import rendering.DrawSquare;
import rendering.DrawText;
import userinterface.Clickable;

/**
 * If we implement a level loader... It makes sense that object would handle
 * deleting all of the destroyed game objects. Also, then we would need to
 * implement an object pool.
 * 
 * TODO This should be abstracted to a GameManager class/interface And then
 * Extended/Implemented to BreakoutGameManager
 **/
public class GameManager implements BrickDestroyedListener, SpawnBallListener {

	private PausableGameEngine gameEngine;
	private ArrayList<Object> allObjects;
	private ArrayList<Clickable> clickables;
	private ArrayList<Object> removableObjects;
	private String stageType;
	private static final String DEFAULT_STAGE = "DEFAULT";
	private static final String FUN_STAGE = "FUN";

	private static final ClassLoader CLASS_LOADER = GameManager.class.getClassLoader();
	private static final File DEFAULT_MUSIC_FILE = new File(CLASS_LOADER.getResource("default.mp3").getFile());
	private static final File ARCADE_MUSIC_FILE = new File(CLASS_LOADER.getResource("arcade.mp3").getFile());

	private AudioClip plonkSound;

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

	public void resetLists() {
		allObjects = new ArrayList<Object>();
		clickables = new ArrayList<Clickable>();
		removableObjects = new ArrayList<Object>();
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
		// Clear objects from the allObjects ArrayList
		allObjects.clear();
		allObjects = null;
		// re-initialize allObjects
		allObjects = new ArrayList<Object>();

		// Do the same to the removableObjects ArrayList
		removableObjects.clear();
		removableObjects = null;
		removableObjects = new ArrayList<Object>();

		// And the clickable ArrayList
		clickables.clear();
		clickables = null;
		clickables = new ArrayList<Clickable>();
	}

	/*
	 * TODO - Can we do better with those click methods? Not a fan of inline
	 * constructor method overriding. A bunch of click methods in this class?
	 */
	public void loadGame() {

		stageFunctionMap.get(this.stageType).apply("Stage");

		// Make the paddle
		Paddle paddle = new Paddle(new DrawSquare(), Color.BLACK, 350, 550, 100, 10, new SimpleMovement());
		paddle.setSpawnBallListener(this);

		// Add it to the AllObjects ArrayList
		addObject(paddle);

		// Make the clock, add it to allobjects
		DigitalTimer clock = new DigitalTimer(new DrawText(), Color.BLACK, 1, 30, "Verdana", 30, "Timer: ");
		addObject(clock);
	}

	// Add an object to the allObjects arraylist
	public void addObject(Object object) {
		allObjects.add((Object) object);
		if (object instanceof Clickable) {
			clickables.add((Clickable) object);
		}
		gameEngine.addObjectToEngine(object);
	}

	public ArrayList<Object> getGameObjects() {
		return allObjects;
	}

	public ArrayList<Clickable> getClickables() {
		return clickables;
	}

	// Spawn a new ball, add it to allobjects
	public Ball spawnNewBall() {
		Ball ball = new Ball(new DrawCircle(), Color.WHITE, 400, 395, 15, 15, new SimpleMovement());
		addObject(ball);
		return ball;
	}

	// Spawn the first ball as a command
	@Override
	public void spawnBall() {
		commandListener.receiveCommand(new BallSpawnCommand(this));
	}

	// Used for testing ball collisions
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
		queueForDeletion((GameObject) brick);
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
		if (!removableObjects.contains(object)) {
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
	public void setStageType(String stageType) {
		this.stageType = stageType;
	}

	// TODO add in a separate Stages class
	public Function<String, Boolean> funStageFunction = (args) -> {
		
		stopMusic();

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

		playMusic(ARCADE_MUSIC_FILE);

		return true;
	};

	// TODO add in a separate Stages class
	public Function<String, Boolean> defaultStageFunction = (args) -> {
		
		stopMusic();

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
		playMusic(DEFAULT_MUSIC_FILE);

		return true;
	};

	@Override
	public JSONObject save() {
		// TODO Auto-generated method stub
		return null;
	}

	private void playMusic(File musicFile) {
		Platform.runLater(() -> {
			playAudio(musicFile);
		});
	}

	private void playAudio(File musicFile) {
		/*********************************************************************************************************************
		 * Superepic by Alexander Nakarada | https://www.serpentsoundstudios.com Music
		 * promoted by https://www.chosic.com/free-music/all/ Attribution 4.0
		 * International (CC BY 4.0) https://creativecommons.org/licenses/by/4.0/
		 * 
		 *  Monkeys Spinning Monkeys Kevin MacLeod (incompetech.com)
		 * Licensed under Creative Commons: By Attribution 3.0 License
		 * http://creativecommons.org/licenses/by/3.0/
		 * Music promoted by https://www.chosic.com/free-music/all/
		 **********************************************************************************************************************/

		plonkSound = new AudioClip(musicFile.toURI().toString());
		plonkSound.play();
	}
	
	public void stopMusic() {
		if (plonkSound != null) plonkSound.stop();
	}

	@Override
	public void load(JSONObject saveData) {
		// TODO Auto-generated method stub

	}
}
