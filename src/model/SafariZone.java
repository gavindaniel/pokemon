package model;

import java.awt.Point;
import java.util.Observable;

import controller.Settings;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;

public class SafariZone extends Observable {

	private Map theMap;
	private Settings settings; 
	
	public SafariZone() {
		theMap = new Map();
		settings = new Settings();
		startNewGame();
	}

	/**
	 * Start a new game and tell all observers to draw an new game with the string
	 * message startNewGame()
	 */
	public void startNewGame() {
		theMap = new Map();
		setChanged();
		notifyObservers("startNewGame()");
	}

	public Map getMap() {
		return theMap;
	}

	public Settings getSettings() {
		return settings;
	}
	
	// Move Player
	public String movePlayer(KeyCode direction) {
		String response;
		if (!gameOver()) {
			Point oldLoc = theMap.getTrainer().getCurrentLocation();
			Point newLoc = oldLoc;
			int newC = (int) newLoc.getX();
			int newR = (int) newLoc.getY();

			if (direction == KeyCode.UP)
				newR -= 1;
			else if (direction == KeyCode.LEFT)
				newC -= 1;
			else if (direction == KeyCode.RIGHT)
				newC += 1;
			else if (direction == KeyCode.DOWN)
				newR += 1;

			newLoc = new Point(newC, newR);

			response = theMap.updateTrainerLocation(oldLoc, newLoc);

			setChanged();
			notifyObservers();
		} else {
//			Alert alert = new Alert(AlertType.INFORMATION);
//			alert.setTitle("Game Over");
//			alert.setHeaderText("You ran out of steps!");
//			alert.setContentText("You caught " + theMap.getTrainer().getOwnedPokemonList().size() + " Pokemon");
//			alert.showAndWait();
			response = "game over";
		}
		return response;
	}

	public boolean gameOver() {
		if (theMap.getTrainer().getNumSteps() == 0)
			return true;
		else
			return false;
	}
}
