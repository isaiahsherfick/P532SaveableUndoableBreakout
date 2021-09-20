/**
 * @author: Isaiah Sherfick
 * @CreationDate: Sep 16, 2021
 * @editors:
 **/

package save_and_load;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import breakout.Ball;
import breakout.Brick;
import breakout.DigitalTimer;
import breakout.GameManager;
import breakout.Paddle;
import breakout.SpecialBrick;
import command.pattern.CommandInvoker;

//Class to manage saves and loads
//This class is responsible for knowing where savefiles are stored
//as well as parsing and processing them. This includes instantiating each of the saved objects
public class SaveAndLoadManager
{
    public String pathToSaveFile;

    //Save information will be stored as an arraylist of strings which each individual game object will be responsible for providing, one string per object
    private ArrayList<String> saveData; 

    private ArrayList<Saveable> saveObjects;
    
    public void resetSaveObjects() {
		this.saveObjects = new ArrayList<Saveable>();
	}

	//Needs a reference to these two so we can 
    //pass them to restored objects when necessary
    private CommandInvoker commandInvoker;
    private GameManager gameManager;

    //Default constructor for unit testing only
    public SaveAndLoadManager()
    {
        this.saveObjects = new ArrayList<>();   
        this.saveData = new ArrayList<>();

        //TODO test this default directory on Windows
        this.pathToSaveFile = "save.json";
        
        //defaults for unit testing only
        this.commandInvoker = new CommandInvoker();
        this.gameManager = new GameManager();
    }

    //Constructor for testing with just the specified path 
    public SaveAndLoadManager(String pathToSaveFile)
    {
        this.saveObjects = new ArrayList<>();   
        this.saveData = new ArrayList<>();

        this.pathToSaveFile = pathToSaveFile;
        
        //defaults for unit testing only
        this.commandInvoker = new CommandInvoker();
        this.gameManager = new GameManager();
    }

    //Constructor for the actual game with default path 
    public SaveAndLoadManager(CommandInvoker c, GameManager g)
    {
        this.saveObjects = new ArrayList<>();   
        this.saveData = new ArrayList<>();

        //TODO test this default directory on Windows
        this.pathToSaveFile = "save.json";
        
        //defaults for unit testing only
        this.commandInvoker = c;
        this.gameManager = g;
    }

    //Constructor for the actual game with specified path 
    public SaveAndLoadManager(CommandInvoker c, GameManager g, String pathToSaveFile)
    {
        this.saveObjects = new ArrayList<>();   
        this.saveData = new ArrayList<>();

        this.pathToSaveFile = pathToSaveFile;
        
        //defaults for unit testing only
        this.commandInvoker = c;
        this.gameManager = g;
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
    
    //Save the JSONObject created by save() to the file at
    //pathToSaveFile
    //Throws a bunch of exceptions that we want to stop execution
    //So we're declaring it like this
    public void saveFile() throws IOException
    {
    	JSONObject saveJSON = save();
		File file = new File(pathToSaveFile);
		file.createNewFile();
		FileWriter fileWriter = new FileWriter(pathToSaveFile);
		fileWriter.write(saveJSON.toString());
		fileWriter.flush();
		fileWriter.close();
    }
    
    //Populate GameObjects array using the JSONObject created by save()
    ///////////////////////////////////////////////////////////////////////
    //ORDER OF LOADING IS VERY IMPORTANT
    //None of the objects will have a reference to the CommandInvoker
    //CommandInvoker must be restored FIRST, then each time an object is instantiated
    //Its setCommandListener() method needs called on the commandinvoker
    //This is the only way to prevent creating a copy of the CommandInvoker for each object
    //////////////////////////////////////////////////////////////////////////////
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
						b.setCommandListener(commandInvoker);
						break;
					case "Paddle":
						Paddle p = new Paddle();
						p.load(val);
						saveObjects.add(p);
						p.setCommandListener(commandInvoker);
						//TODO point paddle to gamemanager etc
						break;
					case "Brick":
						Brick br = new Brick();
						br.load(val);
						saveObjects.add(br);
						br.setCommandListener(commandInvoker);
						break;
					case "DigitalTimer":
						DigitalTimer dt = new DigitalTimer();
						dt.load(val);
						saveObjects.add(dt);
						dt.setCommandListener(commandInvoker);
						break;
					case "SpecialBrick":
						SpecialBrick sb = new SpecialBrick();
						sb.load(val);
						saveObjects.add(sb);
						sb.setCommandListener(commandInvoker);
						break;
					
					default:
						System.out.println("Unknown key encountered in SaveAndLoadManager.load()");
						System.out.println("Put an exception here to actually debug it");
						break;
			  }
		}
    }
    
    
    //Read the file stored at pathToSaveFile and 
    //call load() on it
    public void loadFile() throws IOException, ParseException
    {
    	Path path = FileSystems.getDefault().getPath(pathToSaveFile);
    	String fileContent = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);	
    	JSONParser parser = new JSONParser();
    	JSONObject JObj = (JSONObject)parser.parse(fileContent);
    	load(JObj);
    }
    
    public CommandInvoker getCommandInvoker()
    {
    	return commandInvoker;
    }
}

