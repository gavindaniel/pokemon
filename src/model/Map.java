package model;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Observable;
import java.util.Scanner;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Map extends Observable {

	private char[][] board;
	private char[][] pokemonLocations; // change later to a 2D array of Pokemon
	private int size;
	private Trainer trainer;

	private static final int numPokemon = 50;

	public Map() {
		size = 200; // playable map size: 30, total Map size (with trees): 50x50, to allow for 9
					// sections to visit on the map, 10 squares of trees padding
		board = new char[size][size];
		pokemonLocations = new char[size][size];
		trainer = new Trainer();
		clearBoard();
		ReadMapFromFile();
		spawnPokemon();
	}

	public int getSize() {
		return size;
	}

	public char[][] getBoard() {
		return board;
	}

	public char[][] getPokemonLocations() {
		return pokemonLocations;
	}

	public Trainer getTrainer() {
		return trainer;
	}

	public void setTrainer(Trainer t) {
		trainer = t;
	}

	public void updateTrainerLocation(Point oldLoc, Point newLoc) {
		if (checkCanMoveHere(newLoc)) {
			trainer.setCurrentLocation(newLoc);
			trainer.setNumSteps(trainer.getNumSteps() - 1);
			System.out.println("Steps left: " + (trainer.getNumSteps()));
			checkForPokemon(newLoc);
			clearPokemon();
			spawnPokemon();
		}
	}

	public boolean checkCanMoveHere(Point newPosition) {
		int c = (int) newPosition.getX();
		int r = (int) newPosition.getY();

		try {
			if (board[r][c] == '_') {
				return true; // nothing
			} else if ((board[r][c] == 'G') || (board[r][c] == 'm') || (board[r][c] == 's')) {
				return true; // grass
			} else
				return false; // not a valid spot to move to
		} catch (ArrayIndexOutOfBoundsException aiobe) {
			System.out.println(aiobe.toString() + "\tr: " + r + "\tc: " + c);
			return false;
		}
	}

	public void checkForPokemon(Point newPosition) {
		int c = (int) newPosition.getX();
		int r = (int) newPosition.getY();

		try {
			if (pokemonLocations[r][c] == 'Y') {
				Vector<Pokemon> temp = trainer.getPokemon();
				temp.add(new Pokemon());
				trainer.setPokemon(temp);
				// System.out.println("# Pokemon = " + trainer.getPokemon().size());
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Pokemon!");
				alert.setHeaderText("You found a Pokemon!");
				alert.showAndWait();
			}
		} catch (ArrayIndexOutOfBoundsException aiobe) {
			System.out.println(aiobe.toString());
		}
	}

	public void ReadMapFromFile() {

		File file = new File("/Users/gavindaniel/Documents/Zona/cs335/gitrepos/pokemon-teammoltres/maps/zone1.txt");

		int r = 0;
		int c = 0;

		try {

			Scanner sc = new Scanner(file);

			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				for (int i = 0; i < line.length(); i++) {
					if (line.charAt(i) != ' ') {
						board[r][c] = line.charAt(i);
						if (line.charAt(i) == 'P') {
							trainer.setCurrentLocation(new Point(c, r));
							board[r][c] = '_';
						}
						c++;
					}
				}
				c = 0;
				r++;
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void spawnPokemon() {
		clearPokemon();
		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		int min = 0;
		int max = size;

		int random_r = -1;
		int random_c = -1;

		int n = 1;

		while (n <= numPokemon) {
			random_r = ThreadLocalRandom.current().nextInt(min, max); // removed '+ 1'
			random_c = ThreadLocalRandom.current().nextInt(min, max); // removed '+ 1'

			if (pokemonLocations[random_r][random_c] == 'N') {
				if (board[random_r][random_c] == 'G') {
					pokemonLocations[random_r][random_c] = 'Y';
					// System.out.println( "Pokemon " + n + " @ [" + random_r + "][" + random_c +
					// "]" );
					n++;
				}
			}
		}
	}

	public void clearPokemon() {
		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {
				pokemonLocations[r][c] = 'N';
			}
		}
	}

	public void clearBoard() {
		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {
				board[r][c] = 'T';
			}
		}
	}

	/**
	 * Proved a textual version of the game Map
	 */
	public String toString(char[][] _board) {
		String result = "";

		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {
				if (r == trainer.getCurrentLocation().getY() && c == trainer.getCurrentLocation().getX()) {
					result += " P ";
				} else {
					if (_board[r][c] == '_' || _board[r][c] == 'N') {
						result += "   ";
					} else {
						result += " " + _board[r][c] + " ";
					}
				}
			}
			if (r < size - 1)
				result += "\n";
		}

		return result;
	}
}
