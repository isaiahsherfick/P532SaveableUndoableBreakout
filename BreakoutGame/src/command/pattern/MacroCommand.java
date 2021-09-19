/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 4, 2021
 * @editors: Isaiah Sherfick
 * Last modified on: 14 Sep 2021
 * Last modified by: Isaiah Sherfick
 * Changes: Added comments
 **/
package command.pattern;

import java.util.LinkedList;
import java.util.Queue;

import save_and_load.Saveable;

//Data structure to store a bunch of commands
public class MacroCommand implements Saveable{
	
	private Queue<Command> commands;
	
	public MacroCommand() {
		commands = new LinkedList<Command>();
	}
	
	public MacroCommand(LinkedList<Command> commands) {
		this.commands = commands;
	}
	
    //Add a command to the queue
	public void addCommand(Command command) {
		commands.add(command);
	}
	
    //Execute each command in the queue
	public void execute() {
		for(Command command: commands) {
			command.execute();
		}
	}
	
    //undo each command in the queue
	public void undo() {
		for(Command command: commands) {
			command.undo();
		}
	}
	
    //redo each command in the queue
	public void redo() {
		for(Command command: commands) {
			command.redo();
		}
	}
}
