/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 4, 2021
 * @editors:
 **/
package input;

import java.util.ArrayList;

import game.engine.DrawObject;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import userinterface.Clickable;

public class ClickPolling {
	
    // Singleton Pattern
    private static ClickPolling uniqueInstance;
    private static Scene scene;
    private static ArrayList<Clickable> clickables; 
        
    private ClickPolling() {    
    }
    
    public static ClickPolling getInstance() {
        if(uniqueInstance == null) {
            uniqueInstance = new ClickPolling();
        }
        return uniqueInstance;
    }
    
    public void pollScene(Scene scene) {
        removeCurrentClickHandlers();
        setScene(scene);
    }
    
    private void setScene(Scene scene) {
    	ClickPolling.scene = scene;
    	ClickPolling.scene.setOnMouseClicked(
    			new EventHandler<MouseEvent>()
                {
                    public void handle(MouseEvent event)
                    {
                		for (Clickable button : ClickPolling.getInstance().getClickables()) {
                			if (containsPoint((DrawObject)button, event.getX(), event.getY())) {
                				button.onClick();
                				break;
                			}
                		}
                	}
                });
    }
    
    private void removeCurrentClickHandlers() {
        if(scene != null) {
            scene.setOnMouseClicked(null);;
        }
    }
    
    private boolean containsPoint(DrawObject button, double clickX, double clickY) {
    	Point2D position = button.getPosition();
    	Point2D dimensions = button.getDimensions();
		return clickX >= position.getX() && clickX <= position.getX() + dimensions.getX() &&
				clickY >= position.getY() && clickY <= position.getY() + dimensions.getY();		    	
    }
    
    public ArrayList<Clickable> getClickables() {
    	return clickables;
    }
    
    public void setClickables(ArrayList<Clickable> clickables) {
    	ClickPolling.clickables = clickables;
    }
}
