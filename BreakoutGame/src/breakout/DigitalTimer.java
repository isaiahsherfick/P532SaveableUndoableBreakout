/**
 * @author: Pratyush Duklan
 * @CreationDate: Sep 4, 2021
 * @editors: Ethan Taylor Behar, Snehal Patare, Aditi Pednekar, Isaiah Sherfick
 * Last modified on: 18 Sep 2021
 * Last modified by: Isaiah Sherfick
 * Changes: added save/load functionality
 **/
package breakout;

import org.json.simple.JSONObject;

import command.pattern.DigitalTimerUpdateCommand;
import game.engine.Drawable;
import javafx.scene.paint.Color;
import rendering.DrawText;
import save_and_load.Saveable;
import userinterface.Text;

public class DigitalTimer extends Text {

	private double finalTime = 0;
	
	public double getFinalTime()
	{
		return finalTime;
	}
	
	public void setFinalTime(double ft)
	{
		this.finalTime = ft;
	}
	
	public DigitalTimer() {
		super();
		this.drawBehaviour = new DrawText();
	}
	
	public DigitalTimer(Drawable drawBehaviour, Color color, int locationX, int locationY, String font, int fontSize, String label) {
		super(drawBehaviour, color, locationX, locationY, font, fontSize, label);
	}

	@Override
    //Called on every tick
	public void update(double timeDelta) {	
		commandListener.receiveCommand(new DigitalTimerUpdateCommand(this, timeDelta));
	}
	
    //Update the time of the clock
	public void performTimeUpdate(double timeDelta) {
        //Add the change to the time
		finalTime += timeDelta;
        //Do the math necessary to get it in human readable format
		int finalMins = (int) (finalTime / 60);
		int finalSecs = (int) (finalTime % 60);

        //Display it to the humans
		label = String.format("Time: %02d:%02d", (finalMins), (finalSecs));	
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject save()
	{
		JSONObject obj = new JSONObject();
		obj.put("type","DigitalTimer");
		obj.put("fontName",fontName);
		obj.put("fontSize",fontSize);
		obj.put("color",Saveable.saveColor(color));
		obj.put("position",Saveable.savePoint2D(position));
		obj.put("dimensions",Saveable.savePoint2D(dimensions));
		obj.put("drawBehaviour",drawBehaviour.save());
		obj.put("font",Saveable.saveFont(font));
		obj.put("label",label);
		obj.put("finalTime",finalTime);
		return obj;
	}
	
	public void load(JSONObject data)
	{
		fontName = (String)data.get("fontName");
		fontSize = (int)data.get("fontSize");
		position = Saveable.loadPoint2D((JSONObject)data.get("position"));
		dimensions = Saveable.loadPoint2D((JSONObject)data.get("dimensions"));
		color = Saveable.loadColor((JSONObject)data.get("color"));

		JSONObject drawBehaviourObj = (JSONObject)data.get("drawBehaviour");
		drawBehaviour = Saveable.getDrawBehaviour(drawBehaviourObj);
		
		font = Saveable.loadFont((JSONObject)data.get("font"));
		label = (String)data.get("label");
		finalTime = (double)data.get("finalTime");
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (o instanceof DigitalTimer)
		{
			DigitalTimer dt = (DigitalTimer) o;
			return finalTime == dt.getFinalTime() && position.equals(dt.getPosition());
		}
		return false;
	}
}
