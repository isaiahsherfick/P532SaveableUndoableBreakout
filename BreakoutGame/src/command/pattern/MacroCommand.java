/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 4, 2021
 * @editors:
 **/
package command.pattern;

import java.util.LinkedList;
import java.util.Queue;

public class MacroCommand {
	
	private Queue<Command> commands;
	
	public MacroCommand() {
		commands = new LinkedList<Command>();
	}
	
	public MacroCommand(LinkedList<Command> commands) {
		this.commands = commands;
	}
	
	public void addCommand(Command command) {
		commands.add(command);
	}
	
	public void execute() {
		for(Command command: commands) {
			command.execute();
		}
	}
	
	public void undo() {
		for(Command command: commands) {
			command.undo();
		}
	}
	
	public void redo() {
		for(Command command: commands) {
			command.redo();
		}
	}
}
