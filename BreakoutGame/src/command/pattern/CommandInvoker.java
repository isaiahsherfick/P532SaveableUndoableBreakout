/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 4, 2021
 * @editors: Aditi Shivaji Pednekar, Isaiah Sherfick
 * Last modified on: 14 Sep 2021
 * Last modified by: Isaiah Sherfick
 * Changes: Added comments
 **/
package command.pattern;

import java.util.Stack;

//Invoker for our commands
public class CommandInvoker implements CommandListener {
	
	// Each MacroCommand contains all commands for a given tick/update in the game engine
	private MacroCommand currentTickCommandList;
	private Stack<MacroCommand> undoCommandList;
	private Stack<MacroCommand> redoCommandList;
	
	public CommandInvoker() {
		currentTickCommandList = new MacroCommand();
		undoCommandList = new Stack<MacroCommand>();
		redoCommandList = new Stack<MacroCommand>();
	}
	
	public void restart() {
		currentTickCommandList = new MacroCommand();
		undoCommandList.clear();
		undoCommandList = null;
		undoCommandList = new Stack<MacroCommand>();
		redoCommandList.clear();
		redoCommandList = null;
		redoCommandList = new Stack<MacroCommand>();
	}
	
	@Override
	public void receiveCommand(Command command) {
		currentTickCommandList.addCommand(command);
	}
	
	/*
	 * Normal game engine execution
	 */
	public void executeCurrentTickCommands() {
		currentTickCommandList.execute();
		pushToUndoCommandList(currentTickCommandList);
		currentTickCommandList = new MacroCommand();
		System.out.println("ECTC Undo Count: " + undoCommandList.size());
	}
	
	/*
	 * When undoing commands
	 * Either undo step or rewind
	 */
	public void undoCurrentTickCommands() {
		MacroCommand undoMacroCommand = popFromUndoCommandList();
		pushToRedoCommandList(undoMacroCommand);
		undoMacroCommand.undo();
		System.out.println("UCTC Undo Count: " + undoCommandList.size());
		System.out.println("UCTC Redo Count: " + redoCommandList.size());

	}
	
    //Redo
	public void redoCurrentTickCommands() {
		MacroCommand redoMacroCommand = popFromRedoCommandList();
		pushToUndoCommandList(redoMacroCommand);
		redoMacroCommand.redo();
		System.out.println("RCTC Undo Count: " + undoCommandList.size());
		System.out.println("RCTC Redo Count: " + redoCommandList.size());
	}
	
	public void undosToRedos(){
		while(undosAvailable()){
			undoCurrentTickCommands();
		}
	}
	
	public boolean undosAvailable() {
		return !undoCommandList.empty();
	}
	
	public boolean redosAvailable() {
		return !redoCommandList.isEmpty();
	}
	
	private void pushToUndoCommandList(MacroCommand macroCommand) {
		undoCommandList.push(macroCommand);
	}
	
	private MacroCommand popFromUndoCommandList() {
		return undoCommandList.pop();
	}
	
	private void pushToRedoCommandList(MacroCommand macroCommand) {
		redoCommandList.push(macroCommand);
	}
	
	private MacroCommand popFromRedoCommandList() {
		return redoCommandList.pop();
	}
}
