package views;

import java.util.Observable;
import java.util.Observer;

import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

import model.SafariZone;

public class MapView extends BorderPane implements Observer {

	private SafariZone theGame;
	private GridPane gp1;
	private TextArea gameDisplay;

	// constructor
	public MapView(SafariZone PokemonGame) {
		theGame = PokemonGame;
		gp1 = new GridPane();
		initializePane();
	}

	private void initializePane() {
		gameDisplay = new TextArea();
		gameDisplay.setFont(new Font("Courier", 7));
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
//		gameDisplay.setText(theGame.getMap().toString(theGame.getMap().getBoard()));
		gameDisplay.setText(theGame.getMap().drawGameMap(theGame.getMap().getTrainer().getZone()));
	}

}
