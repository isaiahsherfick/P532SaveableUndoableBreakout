package custom.layout;

import java.util.HashMap;

/**
 * @author: Abhishek Tiwari
 * @CreationDate: Sep 19, 2021
 * @editors: Abhishek Tiwari
 * Last modified on: 19 Sep 2021
 * Last modified by: Abhishek Tiwari
 **/

import java.util.function.BiFunction;

import breakout.GameManager;
import game.engine.PausableGameEngine;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import rendering.Renderer;

public class LayoutFunctions {

	private static PausableGameEngine gameEngine;
	private static final HashMap<String, EventHandler<ActionEvent>> EVENT_HANDLER_MAP = new HashMap<>();

	private static final ClassLoader CLASS_LOADER = LayoutFunctions.class.getClassLoader();

	private static final Image PAUSE = new Image(CLASS_LOADER.getResourceAsStream("pause.png"), 15, 15, false, false);
	private static final Image RESUME = new Image(CLASS_LOADER.getResourceAsStream("play.png"), 15, 15, false, false);
	private static final Image RESTART = new Image(CLASS_LOADER.getResourceAsStream("restart.png"), 15, 15, false,
			false);
	private static final Image UNDO = new Image(CLASS_LOADER.getResourceAsStream("undo.png"), 15, 15, false, false);
	private static final Image REDO = new Image(CLASS_LOADER.getResourceAsStream("redo.png"), 15, 15, false, false);
	private static final Image REWIND = new Image(CLASS_LOADER.getResourceAsStream("rewind.png"), 15, 15, false, false);
	private static final Image FAST_FORWARD = new Image(CLASS_LOADER.getResourceAsStream("fastforward.png"), 15, 15,
			false, false);
	private static final Image REPLAY = new Image(CLASS_LOADER.getResourceAsStream("replay.png"), 15, 15, false, false);
	private static final Image CHANGE = new Image(CLASS_LOADER.getResourceAsStream("change.png"), 15, 15, false, false);
	
	private static final Image SAVE = new Image(CLASS_LOADER.getResourceAsStream("download.png"), 15, 15, false, false);
	private static final Image LOAD = new Image(CLASS_LOADER.getResourceAsStream("load.png"), 15, 15, false, false);

	private static double orgSceneX, orgSceneY;
	private static double orgTranslateX, orgTranslateY;
	
	static {
		EVENT_HANDLER_MAP.put("Pause", new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				gameEngine.pause();
			}
		});

		EVENT_HANDLER_MAP.put("Resume", new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				gameEngine.resume();
			}
		});

		EVENT_HANDLER_MAP.put("Restart", new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				gameEngine.restart();
			}
		});

		EVENT_HANDLER_MAP.put("Undo", new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				gameEngine.undo();
			}
		});

		EVENT_HANDLER_MAP.put("Redo", new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				gameEngine.redo();
			}
		});

		EVENT_HANDLER_MAP.put("Rewind", new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				gameEngine.rewind();
			}
		});

		EVENT_HANDLER_MAP.put("FastForward", new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				gameEngine.fastForward();
			}
		});

		EVENT_HANDLER_MAP.put("Replay", new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				gameEngine.replay();
			}
		});
		
		EVENT_HANDLER_MAP.put("Save", new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				gameEngine.save();
			}
		});
		
		EVENT_HANDLER_MAP.put("Load", new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				gameEngine.load();
			}
		});

		EVENT_HANDLER_MAP.put("Border", new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				gameEngine.restartWithLayout("BORDER");
			}
		});

		EVENT_HANDLER_MAP.put("Flow", new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				gameEngine.restartWithLayout("FLOW");
			}
		});
	}

	public static BiFunction<Renderer, GameManager, Scene> flowLayoutManagerFunction = (renderer, gameManager) -> {

		Canvas gameCanvas = new Canvas(850, 600);

		BorderPane pane = new BorderPane();
		FlowPane gameFlow = new FlowPane();

		CustomLayout customLayout = new CustomLayout(10, 30);
		customLayout.addNewChild(createButton(gameManager, "Pause", PAUSE), 0, 1);
		customLayout.addNewChild(createButton(gameManager, "Resume", RESUME), 1, 1);
		customLayout.addNewChild(createButton(gameManager, "Restart", RESTART), 2, 1);
		customLayout.addNewChild(createButton(gameManager, "Undo", UNDO), 3, 1);
		customLayout.addNewChild(createButton(gameManager, "Redo", REDO), 4, 1);
		customLayout.addNewChild(createButton(gameManager, "Rewind", REWIND), 5, 1);
		customLayout.addNewChild(createButton(gameManager, "FastForward", FAST_FORWARD), 6, 1);
		customLayout.addNewChild(createButton(gameManager, "Replay", REPLAY), 7, 1);
		
		customLayout.addNewChild(createButton(gameManager, "Save", SAVE), 8, 1);
		customLayout.addNewChild(createButton(gameManager, "Load", LOAD), 9, 1);

		customLayout.addNewChild(createLayoutButton(gameManager, "Border", CHANGE), 10, 1);

		gameFlow.getChildren().add(gameCanvas);

		pane.setBottom(customLayout);
		pane.setCenter(gameFlow);

		// Give renderer the canvas
		renderer.setCanvas(gameCanvas);

		// Build scene and give it root
		Scene gameScene = new Scene(pane);

		return gameScene;
	};

	public static BiFunction<Renderer, GameManager, Scene> borderLayoutManagerFunction = (renderer, gameManager) -> {

		Canvas gameCanvas = new Canvas(850, 600);

		BorderPane pane = new BorderPane();
		FlowPane gameFlow = new FlowPane();

		CustomLayout customLayout = new CustomLayout(30, 10);
		customLayout.addNewChild(createButton(gameManager, "Pause", PAUSE), 1, 1);
		customLayout.addNewChild(createButton(gameManager, "Resume", RESUME), 1, 3);
		customLayout.addNewChild(createButton(gameManager, "Restart", RESTART), 1, 5);
		customLayout.addNewChild(createButton(gameManager, "Undo", UNDO), 1, 7);
		customLayout.addNewChild(createButton(gameManager, "Redo", REDO), 1, 9);
		customLayout.addNewChild(createButton(gameManager, "Rewind", REWIND), 1, 11);
		customLayout.addNewChild(createButton(gameManager, "FastForward", FAST_FORWARD), 1, 13);
		customLayout.addNewChild(createButton(gameManager, "Replay", REPLAY), 1, 15);
		
		customLayout.addNewChild(createButton(gameManager, "Save", REPLAY), 1, 17);
		customLayout.addNewChild(createButton(gameManager, "Load", REPLAY), 1, 19);

		customLayout.addNewChild(createLayoutButton(gameManager, "Flow", CHANGE), 1, 21);

		gameFlow.getChildren().add(gameCanvas);

		pane.setRight(customLayout);
		pane.setCenter(gameFlow);

		// Give renderer the canvas
		renderer.setCanvas(gameCanvas);

		// Build scene and give it root
		Scene gameScene = new Scene(pane);

		return gameScene;
	};

	public void setEngine(PausableGameEngine gameEngine) {
		LayoutFunctions.gameEngine = gameEngine;
	}

	private static GridPane createButton(GameManager gameManager, String actionType, Image image) {
		GridPane gridPane = new GridPane();
		Button button = new Button(actionType, new ImageView(image));
		button.setOnAction(EVENT_HANDLER_MAP.get(actionType));
		
		button.setOnMousePressed(buttonMousePressedEventHandler);
        button.setOnMouseDragged(buttonOnMouseDraggedEventHandler);
		
		button.setFocusTraversable(false);

		gridPane.add(button, 1, 0);

		return gridPane;
	}
	
	static EventHandler<MouseEvent> buttonMousePressedEventHandler = new EventHandler<MouseEvent>() {

	    @Override
	    public void handle(MouseEvent t) {
	        orgSceneX = t.getSceneX();
	        orgSceneY = t.getSceneY();
	        orgTranslateX = ((Button) (t.getSource())).getTranslateX();
	        orgTranslateY = ((Button) (t.getSource())).getTranslateY();

	        ((Button) (t.getSource())).toFront();
	    }
	};

	static EventHandler<MouseEvent> buttonOnMouseDraggedEventHandler = new EventHandler<MouseEvent>() {

	    @Override
	    public void handle(MouseEvent t) {
	        double offsetX = t.getSceneX() - orgSceneX;
	        double offsetY = t.getSceneY() - orgSceneY;
	        double newTranslateX = orgTranslateX + offsetX;
	        double newTranslateY = orgTranslateY + offsetY;

	        ((Button) (t.getSource())).setTranslateX(newTranslateX);
	        ((Button) (t.getSource())).setTranslateY(newTranslateY);
	    }
	};

	private static GridPane createLayoutButton(GameManager gameManager, String nextLayout, Image image) {
		GridPane gridPane = new GridPane();
		MenuButton menuButton = new MenuButton("Change Layout");
		MenuItem item = new MenuItem(nextLayout);
		
		menuButton.setFocusTraversable(false);

		item.setOnAction(EVENT_HANDLER_MAP.get(nextLayout));

		// add menu items to menu
		menuButton.getItems().add(item);
		gridPane.add(menuButton, 1, 0);

		return gridPane;
	}

}
