package views;

import java.util.Observable;
import java.util.Observer;

import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import model.SafariZone;

public class TextView extends BorderPane implements Observer {

	private SafariZone theGame;
	private GridPane gp1;

	private TextArea gameDisplay;

	private static final double height = 400;
	private static final double width = 600;
	
	private static final int lb = -4;	// lower bound of viewable area 
	private static final int ub = 5;		// upper bound of viewable area

	// constructor
	public TextView(SafariZone PokemonGame) {
		theGame = PokemonGame;
		gp1 = new GridPane();
		initializePane();
	}

	private void initializePane() {
		gameDisplay = new TextArea();
		gameDisplay.setFont(new Font("Courier", 34));
		gameDisplay.setEditable(false);
		gp1.setPrefWidth(width);
		gp1.setPrefHeight(height);
		gameDisplay.setPrefWidth(width);
		gameDisplay.setPrefHeight(height);
		gameDisplay.setStyle("-fx-font-alignment: center");

		gp1.getChildren().addAll(gameDisplay);
		this.setCenter(gp1);
		updateTextArea();
	}

	@Override
	public void update(Observable o, Object arg) {
		theGame = (SafariZone) o;
		updateTextArea();
	}

	
	public void updateTextArea() {
		String result = "";
		int pc = (int) theGame.getMap().getTrainer().getCurrentLocation().getX();
		int pr = (int) theGame.getMap().getTrainer().getCurrentLocation().getY();

		for (int r = lb; r < ub; r++) {
			for (int c = lb; c < ub; c++) {
				if (r == 0 && c == 0) {
					result += " P ";
				} else {
					if (theGame.getMap().getBoard()[pr + r][pc + c] == '_') {
						result += "   ";
					} else {
						result += " " + theGame.getMap().getBoard()[pr + r][pc + c] + " ";
					}
				}
			}
			if (r < 4)
				result += "\n";
		}

		gameDisplay.setText(result);
	}

}
