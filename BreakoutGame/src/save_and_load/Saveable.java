/**
 * @author: Isaiah Sherfick
 * @CreationDate: Sep 16, 2021
 * @editors:
 **/

package save_and_load;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

//Interface for save and load functionality
//Every game object needs to implement this
//
//Will get messy with recursive calls in complex objects,
//but hopefully I will figure out a way to make it cleanish
public interface Saveable
{
    //Produce the saveJSON
    public JSONObject save();

    //reconstruct the saveable from the saveString
    //Call this method on a freshly default constructed
    //member of the corresponding class
    public void load(String saveData);
    
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
    
}
