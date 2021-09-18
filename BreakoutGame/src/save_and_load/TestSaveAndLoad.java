package save_and_load;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import breakout.*;
import game.engine.GameObject;
import javafx.geometry.Point2D;
import movement.behaviors.SimpleMovement;
import rendering.DrawButton;
import rendering.DrawCircle;
import rendering.DrawSpecialBrick;
import rendering.DrawSquare;
import rendering.DrawText;

class TestSaveAndLoad 
{
	@Test
	void addGameObjectsTest()
	{
        //default constructor
        SaveAndLoadManager s = new SaveAndLoadManager();

        //create gameobjects arraylist to test against
        ArrayList<GameObject> gameObjects = new ArrayList<>();

        //balls for the gameobjects arraylist
        Ball b1 = new Ball();
        Ball b2 = new Ball();
        gameObjects.add(b1);
        gameObjects.add(b2);

        //pass the arraylist to the SaveAndLoadManager
        s.addGameObjects(gameObjects);
        for (int i = 0; i < s.getGameObjects().size(); i++)
        {
            //assert that it contains the same balls that we passed it
            assertEquals(gameObjects.get(i), s.getGameObjects().get(i));
        }
        //make another ball to test the addGameObject method
        Ball b3 = new Ball();
        s.addGameObject(b3);

        //assert that this method also works as expected
        assertEquals(b3,s.getGameObjects().get(2));
	}

    @Test
    void saveTest()
    {
        //SaveAndLoadManager s = new SaveAndLoadManager();
        ////save to saveTest.brkout
        //s.save("saveTest.brkout");
        ////Open the file
        //File saveFile = File.open("saveTest.brkout","r");
        ////Assert its contents and the ball's save string are the same
        //assertEquals(saveFile.read(),b1.save());
    }

    @Test
    //PASSES!!!!!!!!!!!!!!!!!!!!!!!!!!!!
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
    
}
