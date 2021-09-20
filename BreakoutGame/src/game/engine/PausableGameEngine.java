/**
 * @author: Ed Eden-Rump
 * @CreationDate: Sep 1, 2021
 * @editors: Ethan Taylor Behar, Aditi Shivaji Pednekar, Isaiah Sherfick, Abhishek Tiwari
 //* Last modified on: 19 Sep 2021
 //* Last modified by: Abhishek Tiwari
 //* Changes: Added comments
 * @References: 
 * https://github.com/edencoding/javafx-game-dev/blob/master/SpaceShooter/src/main/java/com/edencoding/animation/GameLoopTimer.java
 * https://github.com/edencoding/javafx-animation/blob/master/animation-timer-pause/src/main/java/com/edencoding/animation/PausableAnimationTimer.java
 * https://edencoding.com/animation-timer-pausing/
 **/
package game.engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import org.json.simple.parser.ParseException;

import breakout.GameManager;
import collision.detection.CollisionHandler2D;
import command.pattern.CommandInvoker;
import custom.layout.LayoutFunctions;
import input.ClickPolling;
import input.KeyPolling;
import javafx.scene.Scene;
import javafx.stage.Stage;
import observer.pattern.Observable;
import observer.pattern.Observer;
import rendering.Renderer;
import save_and_load.SaveAndLoadManager;
import save_and_load.Saveable;

import breakout.Paddle;

public class PausableGameEngine implements Observable {

	// Design Pattern Vars
	private ArrayList<Observer> observers;
	private CommandInvoker commandInvoker;
	
	// Game Engine Vars
	private AnimationTimerGameLoop gameLoop;
	private double timeDelta = 0;
	// private double totalTime = 0;
	private boolean rewind = false;
	private boolean fastForward = false;
    private Renderer renderer;
    private CollisionHandler2D collisionHandler;
    private GameManager gameManager;
    
    private SaveAndLoadManager saveAndLoadManager;
    
	private Stage gameStage;
	private Scene gameScene;
	
	private static Map<String, BiFunction<Renderer, GameManager, Scene>> layoutFunctionMap = new HashMap<>();
	
	private static final String FLOW_LAYOUT = "FLOW";
	private static final String BORDER_LAYOUT = "BORDER";
	
	private String currentLayout;

	public PausableGameEngine() {
    }    
    
    public PausableGameEngine(CommandInvoker commandInvoker, Renderer renderer, CollisionHandler2D collisionHandler, GameManager gameManager, Stage gameStage) {
    	this.commandInvoker = commandInvoker;
    	this.renderer = renderer;
    	this.collisionHandler = collisionHandler;
    	this.gameManager = gameManager;
    	this.gameStage = gameStage;
    	this.saveAndLoadManager = new SaveAndLoadManager(commandInvoker, gameManager);
    	
    	layoutFunctionMap.put(FLOW_LAYOUT, LayoutFunctions.flowLayoutManagerFunction);
    	layoutFunctionMap.put(BORDER_LAYOUT, LayoutFunctions.borderLayoutManagerFunction);
    	
		instantiateGameCanvas();
		observers = new ArrayList<Observer>();
		instantiateGameLoop();
    }
    
    private void instantiateGameCanvas() {
    	//If no layout is set, default is flow
    	String currentLayout = Optional.ofNullable(this.getCurrentLayout()).orElse(FLOW_LAYOUT);
    	
    	Scene gameScene = layoutFunctionMap.get(currentLayout).apply(renderer, gameManager);
    	
    	this.gameScene = gameScene;
    	gameStage.setScene(gameScene);
    	
    	// Let the input handlers poll the scene
    	KeyPolling.getInstance().pollScene(gameScene);
    	ClickPolling.getInstance().pollScene(gameScene);
    	
    	// Start showing the stage
        gameStage.show();
        
        // Interesting... width and height isn't populated until gameStage.show() is called
    	collisionHandler.setGameSceneDimensions(850, 600);
    }

	private void instantiateGameLoop() {
    	gameLoop = new AnimationTimerGameLoop() {
	        @Override
	        public void update(float secondsSinceLastFrame) {
	        	if(!rewind) {
	        		 //totalTime += secondsSinceLastFrame;
	        		timeDelta = secondsSinceLastFrame;
	        	} else { 
	        		 //totalTime -= secondsSinceLastFrame;
	        		timeDelta = -secondsSinceLastFrame;
	        	}
	        	
		        tick();
	        }
	    };
    }
    
	public void start() {
        //Display the initial dialog for the user
		InitDialog.showInitialDialog(gameStage, gameLoop, gameManager, this.gameScene);
	}
	
	public void restart() {
		reset();
		start();
	}
	
	public void restartWithLayout(String layout) {
		reset();
		setCurrentLayout(layout);
		instantiateGameCanvas();
		start();
	}
	
	private void reset() {
		// Restart the big 3
    	renderer.restart();
		collisionHandler.restart();
		gameManager.restart();
		commandInvoker.restart();
		
		// Restart engine vars
		rewind = false;
		fastForward = false;
    	gameLoop.stop();
		gameLoop = null;
		instantiateGameLoop();
		observers.clear();
		observers = null;
		observers = new ArrayList<Observer>();
		
		gameManager.stopMusic();
	}
	
    //pause the game
	public void pause() {
		gameLoop.pause();
	}
		
    //resume the game
	public void resume() {
		gameLoop.play();
	}

    //undo the previous command
	public void undo() {
		if (gameLoop.isPlaying()) {
			pause();
		}
		if(commandInvoker.undosAvailable())
		{
			commandInvoker.undoCurrentTickCommands();
		}
		renderer.render();
	}

    //rewind
	public void rewind() {
        //resume the game
		resume();
        //set rewind to true
		rewind = true;
        //set fast forward to false
		fastForward = false;
	}
	
    //opposite of undo
	public void redo() {
        //Pause the game if not paused
		if (gameLoop.isPlaying()) {
			pause();
		}
        //make sure there are things to be redone in the queue
		if(commandInvoker.redosAvailable()) {
			commandInvoker.redoCurrentTickCommands();			
		}
        //Render what's happening
		renderer.render();
	}
	
    //redo over and over
	public void fastForward() {
        //resume the game
		resume();
        //modify relevant booleans
		fastForward = true;
		rewind = false;
	}
	
    //replay from the beginning
	public void replay() {
        //Undo all the way to the beginning of the game
		commandInvoker.undosToRedos();
		fastForward();
	}
	
	public void save() {
		List<Object> gameObjects = gameManager.getGameObjects();
		
		ArrayList<Saveable> saveableList = (ArrayList) gameObjects.stream().filter(object -> {
			return object instanceof Saveable;
		}).map(object -> {
			return (Saveable) object;
		}).collect(Collectors.toList());
		
		saveAndLoadManager.addSaveObjects(saveableList);
		
		try {
			saveAndLoadManager.saveFile();
		} catch (IOException e) {
			System.out.println(saveAndLoadManager.pathToSaveFile);
			System.out.println("Path not found " + e);
		} finally {
			saveAndLoadManager.resetSaveObjects();
		}
	}
	
	public void load() {
		try {
			commandInvoker = new CommandInvoker();
			saveAndLoadManager.loadFile();
			ArrayList<Saveable> saveableList = saveAndLoadManager.getSaveObjects();
			
			renderer.restart();
			collisionHandler.restart();
			gameManager.restart();
			commandInvoker.restart();
			
			saveableList.stream().forEach(object -> {
				gameManager.addObject((Object) object);
				
				if (object instanceof Paddle) {
					((Paddle)object).setSpawnBallListener(gameManager);
				}
			});
			
			resume();
			saveAndLoadManager.resetSaveObjects();
			
		} catch (IOException | ParseException e) {
			System.out.println("Path not found");
		}
	}
	
    //Add all objects in an arraylist to the engine
	public void addObjectsToEngine(ArrayList<Object> objects) {
		for (Object object : objects) { 
			addObjectToEngine(object);
		}
	}
    
    //Add a single object to the engine
	public void addObjectToEngine(Object object) {
		if (object instanceof Observer) {
			registerObserver((Observer) object);
		}
		if (object instanceof DrawObject) {
			renderer.addDrawable((DrawObject) object);
			((DrawObject)object).setCommandListener(commandInvoker);
		}
		if (object instanceof GameObject) {
			collisionHandler.addGameObject((GameObject) object);
		}
	}
	
    //Remove all objects present in gameManager.getRemovableObjects()
	private void removeObjectsFromEngine() {
		List<Object> removables = gameManager.getRemovableObjects();
		for (Object object : removables) {
			if (object instanceof Observer) {
				unregisterObserver((Observer)object);
			}
			if (object instanceof DrawObject) {
				renderer.removeDrawable((DrawObject)object);
			}
			if (object instanceof GameObject) {
				collisionHandler.removeGameObject((GameObject)object);
			}
		}
		gameManager.engineRemovalFinished();
	}

	/*
	 * Main Game Loop!
	 * Move Objects
	 * Detect Collision
	 * Draw
	 * Delete Check
	 */
	@Override
	public void tick() {
		if(!rewind && !fastForward) {
			// System.out.println("New Tick: " + timeDelta);

			// Update observers with timeDelta
			for (Observer observer : observers) {
				observer.update(timeDelta);
			}
		
			// Invoke the commands!
			commandInvoker.executeCurrentTickCommands();
		
			// Process collisions
			collisionHandler.processCollisions(gameLoop);
		
			// Draw frame
			renderer.render();
		
			// Delete objects that are removed from game
			// Currently not really doing anything b/c of Command Pattern
			// Need to figure out how to delete objects but preserve reference in commands
			// TODO it would be nice if there was a pooling system here instead
			if(gameManager.hasObjectsForDeletion()) {
				removeObjectsFromEngine();
			}
		} else {
			if(rewind) {
				if(commandInvoker.undosAvailable()) {
					// System.out.println("Invoker undo");
					commandInvoker.undoCurrentTickCommands();
					renderer.render();
				} else {
					//System.out.println("Out of undos - pausing");
					rewind = false;
					pause();
				}
			} else if (fastForward) {
				if(commandInvoker.redosAvailable()) {
					// System.out.println("Invoker redo");
					commandInvoker.redoCurrentTickCommands();
					renderer.render();
				} else {
					//System.out.println("Out of redos - pausing");
					fastForward = false;
					pause();
				}
			}
		}
	}

	@Override
	public void registerObserver(Observer observer) {
		// Prevent double registration
		if(!observers.contains(observer)) {
			observers.add(observer);
		}
	}

	@Override
	public void unregisterObserver(Observer observer) {
		// Ensure observer is already registered
		int observerIndex = observers.indexOf(observer);
		if (observerIndex >= 0) {
			observers.remove(observerIndex);
		}
	}
	
	private String getCurrentLayout() {
		return currentLayout;
	}

	private void setCurrentLayout(String currentLayout) {
		this.currentLayout = currentLayout;
	}
}
