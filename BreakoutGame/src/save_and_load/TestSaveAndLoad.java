package save_and_load;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import breakout.Ball;
import game.engine.GameObject;

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
        SaveAndLoadManager s = new SaveAndLoadManager();
        DrawObject d = new DrawObject
        s.addGameObject(b1);
        //save to saveTest.brkout
        s.save("saveTest.brkout");
        //Open the file
        File saveFile = File.open("saveTest.brkout","r");
        //Assert its contents and the ball's save string are the same
        assertEquals(saveFile.read(),b1.save());
    }
}
