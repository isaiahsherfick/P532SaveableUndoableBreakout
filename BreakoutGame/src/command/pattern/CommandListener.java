/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 4, 2021
 * @editors: Isaiah Sherfick
 * Last modified on: 16 Sep 2021
 * Last modified by: Isaiah Sherfick
 * Changes: Extended Saveable for save/load
 **/
package command.pattern;

import save_and_load.Saveable;

//Interface for commandlistener
public interface CommandListener extends Saveable {
    //Behavior upon receipt of command
	public void receiveCommand(Command command);
}
