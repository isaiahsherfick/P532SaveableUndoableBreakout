package save_and_load;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import breakout.Ball;
import game.engine.DrawObject;
import game.engine.GameObject;
import javafx.geometry.Point2D;
import rendering.DrawText;
import save_and_load.BadSaveStringException;

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
    //TODO
    void saveBallTest() 
    {
    	//Create a new ball, change its velocity from the default
    	Ball b1 = new Ball();
    	b1.setVelocity(new Point2D(2,5));
    	b1.setPosition(new Point2D(69, 420));
    	
    	//Save it
    	JSONObject b1Save = b1.save();
    	StringWriter out = new StringWriter();
    	try {
			b1Save.writeJSONString(out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	String saveString = out.toString();
    	System.out.println(saveString);

    	Ball b2 = new Ball();
    	assertNotEquals(b1,b2);
		b2.load(saveString);
    	assertEquals(b1,b2);
    }
    
    //void savePoint2DTest()
    //{
    	////Create new p2d
    	//Point2D p = new Point2D(5,6);
    	//
    	////Save it
    	//JSONObject p1Save = p.save();
    	//StringWriter out = new StringWriter();
    	//try {
			//p1Save.writeJSONString(out);
		//} catch (IOException e) {
			//// TODO Auto-generated catch block
			//e.printStackTrace();
		//}
    	//String saveString = out.toString();
    	//System.out.println(saveString);
//
    	//Point2D p2 = new Point2D(1,1);
    	//assertNotEquals(p,p2);
    	//p2.load(saveString);
    	//assertEquals(p,p2);
    //}
}
