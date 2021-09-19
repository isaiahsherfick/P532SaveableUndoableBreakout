package custom.layout;

/**
 * @author: Abhishek Tiwari
 * @CreationDate: Sep 19, 2021
 * @editors: Abhishek Tiwari
 * Last modified on: 19 Sep 2021
 * Last modified by: Abhishek Tiwari
 **/

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;

public class CustomLayout extends GridPane {
	
	private List<GridPane> gridPanes = new ArrayList<>();
	
	public CustomLayout() {
		super.setHgap(10);
		super.setVgap(10);
		super.setPadding(new Insets(10, 10, 10, 10));
		super.setStyle("-fx-border-color: blue");
	}
	
	public boolean addNewChild(GridPane pane, int column, int row) {
		super.add(pane, column, row);
		return gridPanes.add(pane);
	}

	public boolean removeChild(GridPane pane) {
		return gridPanes.remove(pane);
	}

}
