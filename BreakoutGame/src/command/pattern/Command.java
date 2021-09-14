/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 4, 2021
 * @editors:
 **/
package command.pattern;

public interface Command {
	public void execute();
	public void undo();
	public void redo();
	public void store();
	public void load();
}
