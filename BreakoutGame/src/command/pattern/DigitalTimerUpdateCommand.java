/**
 * @author: Snehal Patare, Aditi Pednekar
 * @CreationDate: Sep 4, 2021
 * @editors: Ethan Taylor Behar, Isaiah Sherfick
 * Last modified on: 14 Sep 2021
 * Last modified by: Isaiah Sherfick
 * Changes: Added comments
 **/

package command.pattern;

import breakout.DigitalTimer;

//Command for telling the digital timer to update
public class DigitalTimerUpdateCommand implements Command {
	
	private double timeDelta;
	private DigitalTimer digitalTimer;
	private String undoLabel;
	private String redoLabel;
	
	public DigitalTimerUpdateCommand(DigitalTimer digitalTimer, double timeDelta) {
		this.digitalTimer = digitalTimer;
		this.timeDelta = timeDelta;
	}

	@Override
    //Step the clock forward
	public void execute() {
		undoLabel = digitalTimer.getLabel();
		
		digitalTimer.performTimeUpdate(timeDelta);
		
		redoLabel = digitalTimer.getLabel();
	}

	@Override
    //Walk it back
	public void undo() {
		digitalTimer.setLabel(undoLabel);
	}

	@Override
    //Walk it back forward
	public void redo() {
		digitalTimer.setLabel(redoLabel);
	}

	@Override
	public void store() {
		// TODO Auto-generated method stub
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub
	}
	

}
