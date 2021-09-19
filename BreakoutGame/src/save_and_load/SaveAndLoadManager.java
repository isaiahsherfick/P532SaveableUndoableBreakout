/**
 * @author: Isaiah Sherfick
 * @CreationDate: Sep 16, 2021
 * @editors:
 **/

package save_and_load;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import breakout.*;
import game.engine.GameObject;

//Class to manage saves and loads
//This class is responsible for knowing where savefiles are stored
//as well as parsing and processing them. This includes instantiating each of the saved objects
public class SaveAndLoadManager
{
    private String pathToSaveDirectory;

    //Save information will be stored as an arraylist of strings which each individual game object will be responsible for providing, one string per object
    private ArrayList<String> saveData; 

    private ArrayList<Saveable> saveObjects;

    //Default constructor
    public SaveAndLoadManager()
    {
        this.saveObjects = new ArrayList<>();   
        this.saveData = new ArrayList<>();

        //TODO test this default directory on Windows
        this.pathToSaveDirectory = "./";
    }

    //add each gameobject in an arraylist to the SaveAndLoadManager
    public void addSaveObjects(ArrayList<Saveable> objectsToAdd)
    {
        for (int i = 0; i < objectsToAdd.size(); i++)
        {
            saveObjects.add(objectsToAdd.get(i));
        }
    }

    //add a single gameobject to the SaveAndLoadManager
    public void addSaveObject(Saveable objectToAdd)
    {
        saveObjects.add(objectToAdd);
    }

    public ArrayList<Saveable> getSaveObjects()
    {
        return saveObjects;
    }
    
    //Save all objects in the SaveAndLoadManager
    @SuppressWarnings("unchecked")
	public JSONObject save()
    {
    	JSONObject obj = new JSONObject();
    	
    	for (int i = 0; i < saveObjects.size(); i++)
    	{
    		Saveable g = saveObjects.get(i);
    		JSONObject newObj = g.save();
    		obj.put(String.format("%d",i),newObj);
    	}
    	return obj;
    }
    
    //Populate GameObjects array using the JSONObject created by save()
    @SuppressWarnings("unchecked")
	public void load(JSONObject saveObj)
    {
		for(Iterator<String> iterator = saveObj.keySet().iterator(); iterator.hasNext();) 
		{
			  String key = (String) iterator.next();
			  JSONObject val = (JSONObject) saveObj.get(key);
			  String type = (String)val.get("type");
			  switch(type)
			  {
					case "Ball":
						Ball b = new Ball();
						b.load(val);
						saveObjects.add(b);
						break;
					case "Paddle":
						Paddle p = new Paddle();
						p.load(val);
						saveObjects.add(p);
						break;
					case "Brick":
						Brick br = new Brick();
						br.load(val);
						saveObjects.add(br);
						break;
					case "DigitalTimer":
						DigitalTimer dt = new DigitalTimer();
						dt.load(val);
						saveObjects.add(dt);
						break;
					case "SpecialBrick":
						SpecialBrick sb = new SpecialBrick();
						sb.load(val);
						saveObjects.add(sb);
						break;
					
					default:
						System.out.println("Unknown key encountered in SaveAndLoadManager.load()");
						System.out.println("Put an exception here to actually debug it");
						break;
			  }

		}
    }
    
    
    //ORDER OF LOADING IS VERY IMPORTANT
    //None of the objects will have a reference to the CommandInvoker
    //CommandInvoker must be restored FIRST, then each time an object is instantiated
    //Its setCommandListener() method needs called on the commandinvoker
    //This is the only way to prevent creating a copy of the commandinvoker for each object
    //Objects that do this: Ball,
    
    //Similarly, anything containing a reference to GameManager will not save that
    //Reference to their JSON! Therefore those objects will also need to manually
    //have that reference added after instantiation
    //Objects that do this: Paddle,
    public void load(String pathToFile)
    {
    	//TODO
    }
}

