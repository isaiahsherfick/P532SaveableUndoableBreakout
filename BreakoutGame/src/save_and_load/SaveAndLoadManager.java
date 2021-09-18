/**
 * @author: Isaiah Sherfick
 * @CreationDate: Sep 16, 2021
 * @editors:
 **/

package save_and_load;

import java.util.ArrayList;

import org.json.simple.JSONObject;

import game.engine.GameObject;

//Class to manage saves and loads
//This class is responsible for knowing where savefiles are stored
//as well as parsing and processing them. This includes instantiating each of the saved objects
public class SaveAndLoadManager
{
    private String pathToSaveDirectory;

    //Save information will be stored as an arraylist of strings which each individual game object will be responsible for providing, one string per object
    private ArrayList<String> saveData; 

    private ArrayList<GameObject> gameObjects;

    //Default constructor
    public SaveAndLoadManager()
    {
        this.gameObjects = new ArrayList<>();   
        this.saveData = new ArrayList<>();

        //TODO test this default directory on Windows
        this.pathToSaveDirectory = "./";
    }

    //add each gameobject in an arraylist to the SaveAndLoadManager
    public void addGameObjects(ArrayList<GameObject> objectsToAdd)
    {
        for (int i = 0; i < objectsToAdd.size(); i++)
        {
            gameObjects.add(objectsToAdd.get(i));
        }
    }

    //add a single gameobject to the SaveAndLoadManager
    public void addGameObject(GameObject objectToAdd)
    {
        gameObjects.add(objectToAdd);
    }

    public ArrayList<GameObject> getGameObjects()
    {
        return gameObjects;
    }
    
    //Save all objects in the SaveAndLoadManager
    public JSONObject save()
    {
    	JSONObject obj = new JSONObject();
    	return obj;
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

