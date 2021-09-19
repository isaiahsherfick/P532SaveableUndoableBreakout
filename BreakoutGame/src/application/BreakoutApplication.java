/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 4, 2021
 * @editors:
 **/
package application;

import breakout.GameManager;
import collision.detection.CollisionHandler2D;
import command.pattern.CommandInvoker;
import custom.layout.LayoutFunctions;
import game.engine.PausableGameEngine;
import javafx.stage.Stage;
import rendering.Renderer;

public class BreakoutApplication {
	
	private PausableGameEngine pausableGameEngine;
	private GameManager gameManager;
	private LayoutFunctions layoutFunctions;
	
	public BreakoutApplication() {
	}
	
	public BreakoutApplication(Stage gameStage) {		
		CommandInvoker commandInvoker = new CommandInvoker();
		Renderer renderer = new Renderer();
        CollisionHandler2D collisionHandler = new CollisionHandler2D();
    	gameManager = new GameManager();
    	layoutFunctions = new LayoutFunctions();
    	gameManager.setCommandListener(commandInvoker);    	
    	
    	pausableGameEngine = new PausableGameEngine(commandInvoker, renderer, collisionHandler, gameManager, gameStage);
    	gameManager.setEngine(pausableGameEngine);
    	layoutFunctions.setEngine(pausableGameEngine);
	}
	
	public void run() {
		pausableGameEngine.start();		
	}
}
