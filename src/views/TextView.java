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

	private static int lowerBound;	// lower bound of viewable area 
	private static int upperBound;	// upper bound of viewable area

	// constructor
	public TextView(SafariZone PokemonGame) {
		theGame = PokemonGame;
		lowerBound = theGame.getSettings().getLowerBound("text");
		upperBound = theGame.getSettings().getUpperBound("text");
		gp1 = new GridPane();
		initializePane();
	}

	private void initializePane() {
		gameDisplay = new TextArea();
		gameDisplay.setFont(new Font("Courier", 28));
		gameDisplay.setEditable(false);
		gp1.setPrefWidth(theGame.getSettings().getWidth("map"));
		gp1.setPrefHeight(theGame.getSettings().getHeight("map"));
		gameDisplay.setPrefWidth(theGame.getSettings().getWidth("map"));
		gameDisplay.setPrefHeight(theGame.getSettings().getHeight("map"));
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

		int zoneNum = theGame.getMap().getTrainer().getZone();
		for (int r = lowerBound; r <= upperBound+1; r++) {
			for (int c = lowerBound; c <= upperBound; c++) {
				if (r == 0 && c == 0) {
					result += " P ";
				} else {
					if (theGame.getMap().getZone(zoneNum).getTile(pr + r,pc + c).getType() == "ground") {
						result += "   ";
					} else {
						result += " " + theGame.getMap().getZone(zoneNum).getTile(pr + r,pc + c).getSourceChar() + " ";
					}
				}
			}
			if (r < upperBound+1)
				result += "\n";
		}

		gameDisplay.setText(result);
	}

}
