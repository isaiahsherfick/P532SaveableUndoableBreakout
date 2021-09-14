/**
 * @author: Ethan Taylor Behar
 * @CreationDate: Sep 4, 2021
 * @editors:
 **/
package userinterface;

public interface Clickable {
	// If I was pressed
	public void onPress();
	// If I was already pressed and then released
	public void onRelease();
	// If I was pressed and released
	public void onClick();
}
