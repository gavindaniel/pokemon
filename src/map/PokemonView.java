package map;

import java.util.Observable;
import java.util.Observer;

import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import model.SafariZone;

public class PokemonView extends BorderPane implements Observer {

	private SafariZone theGame;
	private GridPane gp1;

	private TextArea gameDisplay;

	private static final double height = 650;
	private static final double width = 1100;

	// constructor
	public PokemonView(SafariZone PokemonGame) {
		theGame = PokemonGame;
		gp1 = new GridPane();
		initializePane();
	}

	private void initializePane() {
		gameDisplay = new TextArea();
		gameDisplay.setFont(new Font("Courier", 12));
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
//		gameDisplay.setText(theGame.getMap().toString(theGame.getMap().getPokemonLocations()));
		gameDisplay.setText(theGame.getMap().drawPokemonMap());
	}

}
