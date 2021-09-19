package game.engine;
 //* @author: unknown
 //* @creation date: unknown
 //* @editors: Isaiah Sherfick
 //* Last modified on: 15 Sep 2021
 //* Last modified by: Isaiah Sherfick
 //* Changes: Added comments
import breakout.GameManager;
import input.ClickPolling;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

//Singleton to handle the initial dialogue displayed to the user
public class InitDialog {

    //Shows the initial dialogue
	static void showInitialDialog(Stage stage, AnimationTimerGameLoop gameLoop, GameManager gameManager,
			Scene gameScene) {
		String contentText = "Welcome to the Breakout game";
        //Create customize button
		Button customiseButton = new Button("Customise");
        //Add some css
		customiseButton.setStyle("-fx-background-color: #DEB887; -fx-font-size: 2em; -fx-text-fill: #00008B");
    
        //Start game button
		Button startGameButton = new Button("New Game");
        //css
		startGameButton.setStyle("-fx-background-color: #5F9EA0; -fx-font-size: 2em; -fx-text-fill: #00008B");

		TilePane tilePane = new TilePane();
		
        //ChoiceDialog for the user to select the stage
		ChoiceDialog<String> customisedInputDialog = new ChoiceDialog<String>("DEFAULT", "FUN");


        //Add the buttons to the tilepane
		tilePane.getChildren().add(startGameButton);
		tilePane.getChildren().add(customiseButton);
        //Set the alignment for the tilepane
		tilePane.setAlignment(Pos.CENTER);
        //Give it some nice colors
		tilePane.setBackground(new Background(new BackgroundFill(Color.DARKRED, null, null)));

        //Some decoration for the dialog
		Scene sc = new Scene(tilePane, 500, 300);
		sc.setFill(Color.BLACK);
		stage.setScene(sc);
		stage.setTitle(contentText);
		stage.show();

		Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
		stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
		stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);

        //Eventhandler for the user clicking on the new game button
		EventHandler<ActionEvent> newGameEvent = e -> {
			setNewGameEvent(stage, gameManager, gameLoop, gameScene);
		};
        //Eventhandler for the user clicking on the customize button
		EventHandler<ActionEvent> customiseEvent = e -> {
			setCustomiseEvent(customisedInputDialog, stage, gameManager, gameLoop, gameScene);
		};

		// set on action of event
		startGameButton.setOnAction(newGameEvent);
		customiseButton.setOnAction(customiseEvent);
	}

    //Set the event for the customize button
	private static void setCustomiseEvent(ChoiceDialog<String> customisedInputDialog, Stage stage, GameManager gameManager,
			AnimationTimerGameLoop gameLoop, Scene gameScene) {
		customisedInputDialog.setHeaderText("");
		customisedInputDialog.setContentText("Select stage: ");

		customisedInputDialog.showAndWait().ifPresent(result -> {
			
			gameManager.setStageType(result);
			stage.setX(300);
			stage.setY(100);
			stage.setScene(gameScene);
			stage.show();
			gameManager.start();
			ClickPolling.getInstance().setClickables(gameManager.getClickables());
			gameLoop.start();
			//System.out.println("Started your game");
		});

	}

    //Set the event for the new game button
	private static void setNewGameEvent(Stage stage, GameManager gameManager, AnimationTimerGameLoop gameLoop,
			Scene gameScene) {
		stage.setX(300);
		stage.setY(100);
		stage.setScene(gameScene);
		stage.show();
		gameManager.start();
		ClickPolling.getInstance().setClickables(gameManager.getClickables());
		gameLoop.start();
		//System.out.println("Started your game");

	}

}
