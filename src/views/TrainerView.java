package views;

import java.util.Observable;
import java.util.Observer;

import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import model.SafariZone;

public class TrainerView extends BorderPane implements Observer {

	private SafariZone theGame;
	private GridPane gp1;
	private TextArea gameDisplay;

	private static int lowerBound;	// lower bound of viewable area 
	private static int upperBound;	// upper bound of viewable area

	// constructor
	public TrainerView(SafariZone PokemonGame) {
		theGame = PokemonGame;
		lowerBound = theGame.getSettings().getLowerBound("text");
		upperBound = theGame.getSettings().getUpperBound("text");
		gp1 = new GridPane();
		initializePane();
	}

	private void initializePane() {
		gameDisplay = new TextArea();
		gameDisplay.setFont(new Font("Calibri", 16));
		gameDisplay.setEditable(false);
		gp1.setPrefWidth(theGame.getSettings().getWidth("info"));
		gp1.setPrefHeight(theGame.getSettings().getHeight("info"));
		gameDisplay.setPrefWidth(theGame.getSettings().getWidth("info"));
		gameDisplay.setPrefHeight(theGame.getSettings().getHeight("info"));
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
		
		result += "*********************\n";
		result += "****Trainer Profile****\n";
		result += "*********************\n\n";
		result += "Name: " + theGame.getMap().getTrainer().getName() + "\n";
		result += "No. steps left: " + theGame.getMap().getTrainer().getNumSteps() + "\n";

		gameDisplay.setText(result);
	}

}
