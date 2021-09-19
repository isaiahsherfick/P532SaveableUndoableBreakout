/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 4, 2021
 * @editors: Isaiah Sherfick
 * Last modified on: 19 Sep 2021
 * Last modified by: Isaiah Sherfick
 * Changes: Removed Saveable extension
 **/
package command.pattern;


//Interface for commandlistener
public interface CommandListener{
    //Behavior upon receipt of command
	public void receiveCommand(Command command);
}
