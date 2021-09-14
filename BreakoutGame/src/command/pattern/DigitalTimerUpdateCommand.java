/**
 * @author: Snehal Patare, Aditi Pednekar
 * @CreationDate: Sep 4, 2021
 * @editors: Ethan Taylor Behar, 
 **/

package command.pattern;

import breakout.DigitalTimer;

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
	public void execute() {
		undoLabel = digitalTimer.getLabel();
		
		digitalTimer.performTimeUpdate(timeDelta);
		
		redoLabel = digitalTimer.getLabel();
	}

	@Override
	public void undo() {
		digitalTimer.setLabel(undoLabel);
	}

	@Override
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
