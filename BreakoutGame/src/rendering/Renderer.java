/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 4, 2021
 * @editors:
 **/
package rendering;

import java.util.ArrayList;
import game.engine.DrawObject;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Renderer {
        
    private Canvas canvas;
    private GraphicsContext context;
    private ArrayList<DrawObject> drawables;

    public Renderer() {
    	drawables = new ArrayList<DrawObject>();
    }

    public void restart() {
        // Dump drawables to start fresh
    	drawables.clear();
    	drawables = null;
    	drawables = new ArrayList<DrawObject>();
    }
    
    public void setCanvas(Canvas canvas) {
    	// Save canvas and canvas graphics context
        this.canvas = canvas;
        this.context = canvas.getGraphicsContext2D();
    }
    
    public void addDrawable(DrawObject object) {
		// Prevent double registration
		if(!drawables.contains(object)) {
			drawables.add(object);
		}
    }
    
    public void removeDrawable(DrawObject object) {
    	// Ensure DrawBehavior is already registered
		int objectIndex = drawables.indexOf(object);
		// Then remove it
		if (objectIndex >= 0) {
			drawables.remove(object);
		}
    }
   
    public void render() {
        // Clear canvas for clean drawing
        context.setFill(Color.GREY);
        context.fillRect(0,0, canvas.getWidth(), canvas.getHeight());
    	
    	// TODO determine if save call is needed...
        // Saves attributes onto a stack.
        // context.save();
        
        // Draw each drawable
        for (DrawObject object : drawables) {
        	object.performDraw(context);
        }
    }
}
