package save_and_load;

import java.io.File;
import java.util.Random;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.*;

import breakout.*;
import breakout.Ball;
import breakout.Brick;
import command.pattern.*;
import game.engine.GameObject;
import javafx.geometry.Point2D;
import movement.behaviors.SimpleMovement;
import rendering.DrawButton;
import rendering.DrawCircle;
import rendering.DrawSpecialBrick;
import rendering.DrawSquare;
import rendering.DrawText;
import static org.junit.jupiter.api.Assertions.*;

class TestSaveAndLoad 
{
	@Test
	void addGameObjectsTest()
	{
        //default constructor
        SaveAndLoadManager s = new SaveAndLoadManager();

        //create gameobjects arraylist to test against
        ArrayList<Saveable> saveObjects = new ArrayList<>();

        //balls for the gameobjects arraylist
        Ball b1 = new Ball();
        Ball b2 = new Ball();
        saveObjects.add(b1);
        saveObjects.add(b2);

        //pass the arraylist to the SaveAndLoadManager
        s.addSaveObjects(saveObjects);
        for (int i = 0; i < s.getSaveObjects().size(); i++)
        {
            //assert that it contains the same balls that we passed it
            assertEquals(saveObjects.get(i), s.getSaveObjects().get(i));
        }
        //make another ball to test the addGameObject method
        Ball b3 = new Ball();
        s.addSaveObject(b3);

        //assert that this method also works as expected
        assertEquals(b3,s.getSaveObjects().get(2));
	}

    @Test
    void saveBallTest() 
    {
    	//Create a new ball, change its velocity from the default
    	Ball b1 = new Ball();
    	b1.setVelocity(new Point2D(2,5));
    	b1.setPosition(new Point2D(69, 420));
    	
    	//Save it
    	JSONObject saveObj = b1.save();

    	Ball b2 = new Ball();
    	assertNotEquals(b1,b2);
    	b2.load(saveObj);
    	assertTrue(b1 instanceof Ball);
    	assertTrue(b2 instanceof Ball);
    	assertEquals(b1,b2);
    	
    	Ball b3 = new Ball();
    	b3.load(saveObj);
    	assertEquals(b3,b2);
    	assertEquals(b3,b1);
    }

    @Test
    void savePaddleTest()
    {
    	//Create a new paddle, change its velocity from the default
    	Paddle b1 = new Paddle();
    	b1.setVelocity(new Point2D(2,5));
    	b1.setPosition(new Point2D(69, 420));
    	
    	//Save it
    	JSONObject saveObj = b1.save();

    	Paddle b2 = new Paddle();
    	assertNotEquals(b1,b2);
    	b2.load(saveObj);
    	assertTrue(b1 instanceof Paddle);
    	assertTrue(b2 instanceof Paddle);
    	assertEquals(b1,b2);
    	
    	Paddle b3 = new Paddle();
    	b3.load(saveObj);
    	assertEquals(b3,b2);
    	assertEquals(b3,b1);
    }
    @Test
    void saveBrickTest()
    {
    	//Create a new paddle, change its velocity from the default
    	Brick b1 = new Brick();
    	b1.setVelocity(new Point2D(2,5));
    	b1.setPosition(new Point2D(69, 420));
    	
    	//Save it
    	JSONObject saveObj = b1.save();

    	Brick b2 = new Brick();
    	assertNotEquals(b1,b2);
    	b2.load(saveObj);
    	assertTrue(b1 instanceof Brick);
    	assertTrue(b2 instanceof Brick);
    	assertEquals(b1,b2);
    	
    	Brick b3 = new Brick();
    	b3.load(saveObj);
    	assertEquals(b3,b2);
    	assertEquals(b3,b1);
    }
    
    @Test
    void saveBrickTestSnehal() 
    {
    	//Create a new ball, change its velocity from the default
    	Brick brick = new Brick();
    	brick.setPosition(new Point2D(20, 100));
    	brick.setDimensions(new Point2D(100,40));
    	
    	//Save it
    	JSONObject saveObj = brick.save();

    	Brick brick2 = new Brick();
    	assertNotEquals(brick,brick2);
    	brick2.load(saveObj);
    	assertTrue(brick instanceof Brick);
    	assertTrue(brick2 instanceof Brick);
    	assertEquals(brick,brick2);
    }
    
    @Test
    //Passes
    void SimpleMovementSaveTest()
    {
    	SimpleMovement s = new SimpleMovement();
    	JSONObject saveObj = s.save();
    	assertNotEquals(saveObj,new JSONObject());
    	//System.out.println(Saveable.getJSONString(saveString));
    }
    
    @Test
    void DrawableSaveTest()
    {
    	DrawText dt = new DrawText();
    	DrawButton db = new DrawButton();
    	DrawSpecialBrick dsb = new DrawSpecialBrick();
    	DrawSquare ds = new DrawSquare();
    	DrawCircle dc = new DrawCircle();
    	
    	JSONObject ob1 = dt.save();
    	JSONObject ob2 = db.save();
    	JSONObject ob3 = dsb.save();
    	JSONObject ob4 = ds.save();
    	JSONObject ob5 = dc.save();
    	
    	//.System.out.println(Saveable.getJSONString(ob1));
    	//System.out.println(Saveable.getJSONString(ob2));
    	//System.out.println(Saveable.getJSONString(ob3));
    	//System.out.println(Saveable.getJSONString(ob4));
    	//System.out.println(Saveable.getJSONString(ob5));
    	
    	assertNotEquals(Saveable.getJSONString(ob1),Saveable.getJSONString(ob2));
    	assertNotEquals(Saveable.getJSONString(ob2),Saveable.getJSONString(ob4));
    	assertNotEquals(Saveable.getJSONString(ob3),Saveable.getJSONString(ob5));
    }
    
    @Test
    //As this test was being written, 
    //The impossibility of this problem given the current command
    //architecture in conjunction with the current save architecture
    //dawned on us. If you are ambitious enough to try rewriting both
    //and hacking it together, I hope you enjoy savefiles in the GBs
    void SaveCommandInvokerTest()
    {
    	Random r = new Random();
    	int randint;
    	CommandInvoker invoker = new CommandInvoker();
    	for (int i=0; i < 1000; i++)
    	{
    		randint = r.nextInt(7);
    		Command newCommand;
    		switch(randint)
    		{
    			case 0:
    				break;
    			case 1:
    				break;
    			case 2:
    				break;
    			case 3:
    				break;
    			case 5:
    				break;
    			case 6:
    				break;
    			case 7:
    				break;
    			default:
    		}
    	}
    }
    
    @Test
    void SaveEverythingTest()
    {
        SaveAndLoadManager s = new SaveAndLoadManager();

        //create gameobjects arraylist to test against
        ArrayList<Saveable> saveObjects = new ArrayList<>();

    	Ball b1 = new Ball();
    	b1.setVelocity(new Point2D(40,50));
    	b1.setPosition(new Point2D(400,20));
    	Ball b2 = new Ball();
    	b2.setVelocity(new Point2D(50,40));
    	b2.setPosition(new Point2D(20,400));
    	Paddle p = new Paddle();
    	p.setPosition(new Point2D(1,2));
    	Brick br = new Brick();
    	br.setPosition(new Point2D(2,2));
    	
    	DigitalTimer dt = new DigitalTimer();
    	dt.setFinalTime(55.00);
    	
    	SpecialBrick sb = new SpecialBrick();
    	sb.setPosition(new Point2D(1,2));
    	
    	saveObjects.add(b1);
    	saveObjects.add(b2);
    	saveObjects.add(p);
    	saveObjects.add(br);
    	saveObjects.add(dt);
    	saveObjects.add(sb);
    	
    	s.addSaveObjects(saveObjects);
    	
    	JSONObject saveObj = s.save();
    	
    	//Create a second s&lM to load the first's JSON stuff
    	SaveAndLoadManager s2 = new SaveAndLoadManager();
    	
    	//Populate s2's gameobjects array
    	s2.load(saveObj);
    	//System.out.print(saveObj);
    	
    	ArrayList<Saveable> sArray = s.getSaveObjects();
    	ArrayList<Saveable> s2Array = s2.getSaveObjects();
    	
    	assertEquals(sArray.size(), s2Array.size());
    	for (int i = 0; i < sArray.size(); i++)
    	{
    		assertEquals(sArray.get(i),s2Array.get(i));
    	}
    }
    
    @Test
    void SaveFileTest() throws IOException
    {
        SaveAndLoadManager s = new SaveAndLoadManager();

        //create gameobjects arraylist to test against
        ArrayList<Saveable> saveObjects = new ArrayList<>();

    	Ball b1 = new Ball();
    	b1.setVelocity(new Point2D(40,50));
    	b1.setPosition(new Point2D(400,20));
    	Ball b2 = new Ball();
    	b2.setVelocity(new Point2D(50,40));
    	b2.setPosition(new Point2D(20,400));
    	Paddle p = new Paddle();
    	p.setPosition(new Point2D(1,2));
    	Brick br = new Brick();
    	br.setPosition(new Point2D(2,2));
    	
    	DigitalTimer dt = new DigitalTimer();
    	dt.setFinalTime(55.00);
    	
    	SpecialBrick sb = new SpecialBrick();
    	sb.setPosition(new Point2D(1,2));
    	
    	saveObjects.add(b1);
    	saveObjects.add(b2);
    	saveObjects.add(p);
    	saveObjects.add(br);
    	saveObjects.add(dt);
    	saveObjects.add(sb);
    	
    	s.addSaveObjects(saveObjects);
    	
    	JSONObject saveObject = s.save();
    	String expectedString = saveObject.toString();
    	
    	s.saveFile();
    	
    	//NOTE: on Linux, "./" is evaluating to /Team4-Week3/BreakoutGame/
    	//TODO test on windows
    	Path path = FileSystems.getDefault().getPath("./", "save.json");
    	String fileContent = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);	
    	
    	assertEquals(fileContent,expectedString);
    	
    	//directory that doesn't exist
    	try
    	{
			SaveAndLoadManager s2 = new SaveAndLoadManager("./fdsafsaew/save.json");
			s2.addSaveObjects(saveObjects);
			JSONObject s2Obj = s2.save();
			expectedString = s2Obj.toString();
			s2.saveFile();
			path = FileSystems.getDefault().getPath("./fdsafsaew/", "save.json");
			fileContent = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);	
			
			//we don't want to reach this
			assertFalse(true);
    	}
    	catch(IOException e)
    	{
    		
    	}
    	//directory exists on isaiah's machine
		SaveAndLoadManager s2 = new SaveAndLoadManager("./saves/save.json");
		s2.addSaveObjects(saveObjects);
		JSONObject s2Obj = s2.save();
		expectedString = s2Obj.toString();
		s2.saveFile();
		path = FileSystems.getDefault().getPath("./saves/", "save.json");
		fileContent = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);	
		assertEquals(fileContent, expectedString);
    }
    
    @Test 
    void LoadFileTest()
    {
    	SaveAndLoadManager s1 = new SaveAndLoadManager("./saves/save.json");
    	try 
    	{
			s1.loadFile();
		} 
    	catch (IOException | ParseException e) 
    	{
			assertTrue(false);
		}
    	assertTrue(s1.getSaveObjects().size() != 0);
    	SaveAndLoadManager s2 = new SaveAndLoadManager("./fdsafhjdskahui/save.json");
    	try 
    	{
			s2.loadFile();
			assertTrue(false);
		} 
    	catch (IOException | ParseException e) 
    	{

		}
    }
    
    @Test
    void SaveAndLoadFileTestWithCommandInovkerAndGameManager()
    {
    	CommandInvoker ci = new CommandInvoker();
    	GameManager gm = new GameManager();

        SaveAndLoadManager s = new SaveAndLoadManager(ci,gm,"../saves/");

        //create gameobjects arraylist to test against
        ArrayList<Saveable> saveObjects = new ArrayList<>();

    	Ball b1 = new Ball();
    	b1.setVelocity(new Point2D(40,50));
    	b1.setPosition(new Point2D(400,20));
    	Ball b2 = new Ball();
    	b2.setVelocity(new Point2D(50,40));
    	b2.setPosition(new Point2D(20,400));
    	Paddle p = new Paddle();
    	p.setPosition(new Point2D(1,2));
    	Brick br = new Brick();
    	br.setPosition(new Point2D(2,2));
    	
    	DigitalTimer dt = new DigitalTimer();
    	dt.setFinalTime(55.00);
    	
    	SpecialBrick sb = new SpecialBrick();
    	sb.setPosition(new Point2D(1,2));
    	
    	saveObjects.add(b1);
    	saveObjects.add(b2);
    	saveObjects.add(p);
    	saveObjects.add(br);
    	saveObjects.add(dt);
    	saveObjects.add(sb);
    	
    	s.addSaveObjects(saveObjects);
    }
}
