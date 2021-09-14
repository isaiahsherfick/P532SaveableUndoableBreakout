/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 4, 2021
 * @editors: Isaiah Sherfick
 * Last modified on: 14 Sep 2021
 * Last modified by: Isaiah Sherfick
 * Changes: Added comments
 **/
package command.pattern;

import breakout.Ball;
import javafx.scene.canvas.GraphicsContext;

//Command to draw the ball
public class BallDrawCommand implements Command {

	private Ball ball;
	private GraphicsContext context;
	
	public BallDrawCommand(Ball ball, GraphicsContext context) {
		this.ball = ball;
		this.context = context;
	}
	
    //Draw the ball
	@Override
	public void execute() {
		ball.performDraw(context);
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
	}

	@Override
	public void store() {
		// TODO Auto-generated method stub
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub
	}

	@Override
	public void redo() {
		// TODO Auto-generated method stub
	}
}
