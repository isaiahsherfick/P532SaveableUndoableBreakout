/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 4, 2021
 * @editors:
 **/
package command.pattern;

public interface CommandListener {
	public void receiveCommand(Command command);
}
