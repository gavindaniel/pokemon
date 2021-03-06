package views;

import java.util.Observable;
import java.util.Observer;

import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import model.SafariZone;
import pokemon.Pokemon;

public class PokemonView extends BorderPane implements Observer {

	private SafariZone theGame;
	private GridPane gp1;
	private TextArea gameDisplay;

	private static int lowerBound;	// lower bound of viewable area 
	private static int upperBound;	// upper bound of viewable area

	// constructor
	public PokemonView(SafariZone PokemonGame) {
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
		result += "** Trainer Pokemon **\n";
		result += "*********************\n\n";
		for (Pokemon pokemon : theGame.getMap().getTrainer().getOwnedPokemonList()) {
			result += pokemon.getName() + "\t";
			result += pokemon.getCurrHP() + "/";
			result += pokemon.getMaxHP() + "\n";
		}
			

		gameDisplay.setText(result);
	}

}
