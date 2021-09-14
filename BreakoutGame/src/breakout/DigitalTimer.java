/**
 * @author: Pratyush Duklan
 * @CreationDate: Sep 4, 2021
 * @editors: Ethan Taylor Behar, Snehal Patare, Aditi Pednekar, Isaiah Sherfick
 * Last modified on: 14 Sep 2021
 * Last modified by: Isaiah Sherfick
 * Changes: Added comments
 **/
package breakout;

import command.pattern.DigitalTimerUpdateCommand;
import game.engine.Drawable;
import javafx.scene.paint.Color;
import userinterface.Text;

public class DigitalTimer extends Text {

	private double finalTime = 0;
	
	public DigitalTimer() {
		super();
	}
	
	public DigitalTimer(Drawable drawBehaviour, Color color, int locationX, int locationY, String font, int fontSize, String label) {
		super(drawBehaviour, color, locationX, locationY, font, fontSize, label);
	}

	@Override
    //Called on every tick
	public void update(double timeDelta) {	
		commandListener.receiveCommand(new DigitalTimerUpdateCommand(this, timeDelta));
	}
	
    //Update the time of the clock
	public void performTimeUpdate(double timeDelta) {
        //Add the change to the time
		finalTime += timeDelta;
        //Do the math necessary to get it in human readable format
		int finalMins = (int) (finalTime / 60);
		int finalSecs = (int) (finalTime % 60);

        //Display it to the humans
		label = String.format("Time: %02d:%02d", (finalMins), (finalSecs));	
	}
}
