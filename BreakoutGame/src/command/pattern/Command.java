/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 4, 2021
 * @editors: Isaiah Sherfick
 * Last modified on: 14 Sep 2021
 * Last modified by: Isaiah Sherfick
 * Changes: Added comments
 **/
package command.pattern;

//Command interface
public interface Command {
	public void execute();
	public void undo();
	public void redo();
    //TODO: these store() and load() methods aren't used in any of the classes
    //maybe we can just delete them?
	public void store();
	public void load();
}
