/**
 * @author: Isaiah Sherfick
 * @CreationDate: Sep 16, 2021
 * @editors:
 **/

package save_and_load;
import java.io.IOException;
import java.io.StringWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import game.engine.*;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import movement.behaviors.*;
import rendering.*;

//Interface for save and load functionality
//Every game object needs to implement this
//
//Will get messy with recursive calls in complex objects,
//but hopefully I will figure out a way to make it cleanish
public interface Saveable
{
    //Produce the saveString in the JSON format
    public JSONObject save();

    //reconstruct the saveable from the saveString
    //Call this method on a freshly default constructed
    //member of the corresponding class
    public void load(JSONObject saveData);
    
    //DEPRECATED
    public static JSONArray loadNestedJSON(String nestedJSONString) 
    {
    	JSONParser parser = new JSONParser();
    	try
    	{
    		Object obj = parser.parse(nestedJSONString);
    		JSONArray array = (JSONArray)obj;
    		return array;
    	}
    	catch(ParseException pe)
    	{
    		pe.printStackTrace();
    		return new JSONArray();
    	}
    }
    
    @SuppressWarnings("unchecked")
	public static JSONObject savePoint2D(Point2D p2d)
    {
    	JSONObject obj = new JSONObject();
    	obj.put("x", p2d.getX());
    	obj.put("y", p2d.getY());
    	return obj;
    }
    public static Point2D loadPoint2D(JSONObject obj)
    {
    	double x = (double)obj.get("x");
    	double y = (double)obj.get("y");
    	return new Point2D(x,y);
    }
    
    @SuppressWarnings("unchecked")
	public static JSONObject saveColor(Color c)
    {
    	JSONObject obj = new JSONObject();
    	obj.put("r", c.getRed());
    	obj.put("g", c.getGreen());
    	obj.put("b", c.getBlue());
    	obj.put("opacity", c.getOpacity());
    	return obj;
    }
    public static Color loadColor(JSONObject obj)
    {
    	double r = (double)obj.get("r");
    	double g = (double)obj.get("g");
    	double b = (double)obj.get("b");
    	double opacity = (double)obj.get("opacity");
    	return new Color(r,g,b,opacity);
    }
    
    public static String getJSONString(JSONObject obj)
    {
    	StringWriter out = new StringWriter();
    	try
    	{
    		obj.writeJSONString(out);
    		return out.toString();
    	}
    	catch(IOException e)
    	{
    		e.printStackTrace();
    		return "";
    	}
    	
    }
    
    public static Movable getMoveBehaviour(JSONObject obj)
    {
    	Movable moveBehaviour;
    	switch((String)obj.get("movementStrategy"))
	         {
	         	case "SimpleMovement":
	         		moveBehaviour = new SimpleMovement();
	         		break;

	         	default:
	         		moveBehaviour = new SimpleMovement();
	         		break;
	         }
    	return moveBehaviour;
    }
    
    public static Drawable getDrawBehaviour(JSONObject obj)
    {
    	Drawable drawBehaviour;
    	switch((String)obj.get("drawStrategy"))
	    {
			 case "DrawText":
				 drawBehaviour = new DrawText();
				 break;
			 case "DrawButton":
				 drawBehaviour = new DrawButton();
				 break;
			 case "DrawSpecialBrick":
				 drawBehaviour = new DrawSpecialBrick();
				 break;
			 case "DrawSquare":
				 drawBehaviour = new DrawSquare();
				 break;
			 case "DrawCircle":
				 drawBehaviour = new DrawCircle();
				 break;

			 default:
				 drawBehaviour = new DrawNothing();
				 break;
	    }
    	return drawBehaviour;
    }
    
    @SuppressWarnings("unchecked")
	public static JSONObject saveFont(Font f)
    {
    	String fontName = f.getName();
    	double fontSize = f.getSize();
    	JSONObject obj = new JSONObject();
    	obj.put("name",fontName);
    	obj.put("size",fontSize);
    	return obj;
    }
    public static Font loadFont(JSONObject data)
    {
    	String fontName = (String)data.get("name");
    	double fontSize = (double)data.get("size");
    	return new Font(fontName,fontSize);
    }
    
}
