package views;

import java.util.Observable;
import java.util.Observer;

import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import model.Pokemon;

public class TextView extends BorderPane implements Observer {

	private Pokemon theGame;
	private GridPane gp1;

	private TextArea gameDisplay;

	private static final double height = 400;
	private static final double width = 600;

	// constructor
	public TextView(Pokemon PokemonGame) {
		theGame = PokemonGame;
		gp1 = new GridPane();
		initializePane();
	}

	private void initializePane() {
		gameDisplay = new TextArea(theGame.getMap().getViewableArea());
		gameDisplay.setFont(new Font("Courier", 34));
		gameDisplay.setEditable(false);
		gp1.setPrefWidth(width);
		gp1.setPrefHeight(height);
		gameDisplay.setPrefWidth(width);
		gameDisplay.setPrefHeight(height);
		gameDisplay.setStyle("-fx-font-alignment: center");

		gp1.getChildren().addAll(gameDisplay);
		this.setCenter(gp1);
	}

	@Override
	public void update(Observable o, Object arg) {
		theGame = (Pokemon) o;
		updateTextArea();
	}

	public void updateTextArea() {
		gameDisplay.setText(theGame.getMap().getViewableArea());
	}

}
