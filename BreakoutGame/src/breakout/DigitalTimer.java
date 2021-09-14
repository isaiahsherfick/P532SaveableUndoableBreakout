/**
 * @author: Pratyush Duklan
 * @CreationDate: Sep 4, 2021
 * @editors: Ethan Taylor Behar, Snehal Patare, Aditi Pednekar
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
	public void update(double timeDelta) {	
		commandListener.receiveCommand(new DigitalTimerUpdateCommand(this, timeDelta));
	}
	
	public void performTimeUpdate(double timeDelta) {
		finalTime += timeDelta;
		int finalMins = (int) (finalTime / 60);
		int finalSecs = (int) (finalTime % 60);

		label = String.format("Time: %02d:%02d", (finalMins), (finalSecs));	
	}
}
