/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 4, 2021
 * @editors:
 **/
package userinterface;

import game.engine.Drawable;
import game.engine.UIObject;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Text extends UIObject {
	
	protected String fontName;
	protected int fontSize;
	protected Font font;
	protected String label;

	public Text() {
		super();
		fontName = "Verdana";
        fontSize = 14;
        font = new Font(fontName, fontSize);
        label = "";
	}
	
	public Text(Drawable drawBehaviour, Color color, int locationX, int locationY, String fontName, int fontSize, String label) {
		super(drawBehaviour, color, new Point2D(locationX, locationY), new Point2D(0, 0));
    	this.fontName = fontName;
    	this.fontSize = fontSize;
    	font = new Font(this.fontName, this.fontSize);
        this.label = label;
    }
	
	public Text(Drawable drawBehaviour, Color color, Point2D position, String fontName, int fontSize, String label) {
		super(drawBehaviour, color, position, new Point2D(0, 0));
    	this.fontName = fontName;
    	this.fontSize = fontSize;
    	font = new Font(this.fontName, this.fontSize);
        this.label = label;
    }
	
	public Font getFont() {
		return font;
	}
	
	public void setFont(String fontName, int fontSize) {
    	this.fontName = fontName;
    	this.fontSize = fontSize;
    	font = new Font(this.fontName, this.fontSize);
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	@Override
	public void update(double timeDelta) {
	}
}
