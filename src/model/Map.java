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

	private int size;
	private Trainer trainer;
	private Tile[][] tiles;
	private static final int numPokemon = 50;

	public Map() {
		size = 200; // # columns, rows
		trainer = new Trainer();
		tiles = new Tile[size][size];
		clearBoard();
		ReadMapFromFile();
		spawnPokemon();
	}

	public int getSize() {
		return size;
	}

	public Trainer getTrainer() {
		return trainer;
	}

	public void setTrainer(Trainer t) {
		trainer = t;
	}
	public Tile[][] getTiles() {
		return tiles;
	}
	public void setTiles(Tile[][] t) {
		tiles = t;
	}
	public void clearPokemon() {
		for (int r = 0; r < size; r++)
			for (int c = 0; c < size; c++)
				tiles[r][c].setPokemonHere(false);
	}
	public void clearBoard() {
		for (int r = 0; r < size; r++)
			for (int c = 0; c < size; c++)
				tiles[r][c] = new Tile();
	}
	
	public void ReadMapFromFile() {
		File file = new File("./src/files/zone1.txt");
		Tile temp = new Tile();
		int r = 0;
		int c = 0;
		try {
			Scanner sc = new Scanner(file);
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				for (int i = 0; i < line.length(); i++) {
					if (line.charAt(i) != ' ') {
						temp = new Tile(line.charAt(i));
						tiles[r][c] = temp;
						if (line.charAt(i) == 'P') trainer.setCurrentLocation(new Point(c, r));
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
		int min = 0;
		int max = size;
		int num = 1;
		clearPokemon(); //clears the map of any pokemon
		while (num <= numPokemon) {
			int random_r = ThreadLocalRandom.current().nextInt(min, max); // removed '+ 1' to not include size (200)
			int random_c = ThreadLocalRandom.current().nextInt(min, max); // removed '+ 1' to not include size (200)
			
			if (tiles[random_r][random_c].getPokemonHere() == false)
				if (tiles[random_r][random_c].getType() == "grass") {
					tiles[random_r][random_c].setPokemonHere(true);
					num++;
				}
		}
	}
	
	public void updateTrainerLocation(Point oldLoc, Point newLoc) {
		if (checkCanMoveHere(oldLoc, newLoc)) {
			trainer.setCurrentLocation(newLoc);
			trainer.setNumSteps(trainer.getNumSteps() - 1);
			System.out.println("Steps left: " + (trainer.getNumSteps()));
			checkForPokemon(newLoc);
			clearPokemon();
			spawnPokemon();
		}
	}

	public boolean checkCanMoveHere(Point oldPosition, Point newPosition) {
		int new_c = (int) newPosition.getX();
		int new_r = (int) newPosition.getY();
		int old_c = (int) oldPosition.getX();
		int old_r = (int) oldPosition.getY();
		Tile oldTile = tiles[old_r][old_c];
		Tile newTile = tiles[new_r][new_c];
		try {
			if (oldTile.getType() == "hill") {
				if (newTile.getSourceChar() == 'm' || newTile.getSourceChar() == 't' || newTile.getType() == "stairs") return true;
				else return false;
			}
			if (newTile.getType() == "ground") return true;
			else if ((newTile.getType() == "grass") || (newTile.getSourceChar() == 'm') || (newTile.getType() == "stairs")) return true;
			else return false;
		} 
		
		catch (ArrayIndexOutOfBoundsException aiobe) {
			System.out.println(aiobe.toString() + "\tr: " + new_r + "\tc: " + new_c);
			return false;
		} 
		
	}

	public void checkForPokemon(Point newPosition) {
		int c = (int) newPosition.getX();
		int r = (int) newPosition.getY();
		try {
			if (tiles[r][c].getPokemonHere() == true) {
				Vector<Pokemon> temp = trainer.getPokemon();
				temp.add(new Pokemon());
				trainer.setPokemon(temp);
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Pokemon!");
				alert.setHeaderText("You found a Pokemon!");
				alert.showAndWait();
			}
		} catch (ArrayIndexOutOfBoundsException aiobe) {
			System.out.println(aiobe.toString());
		}
	}

	

	/**
	 * Proved a textual version of the game Map
	 */
	public String drawGameMap() {
		String result = "";

		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++)
				if (r == trainer.getCurrentLocation().getY() && c == trainer.getCurrentLocation().getX())
					result += " P ";
				else
					if  (tiles[r][c].getType() == "ground")	result += "   ";
					else	  result += " " + tiles[r][c].getSourceChar() + " ";
			if (r < size - 1)  result += "\n";
		}
		return result;
	}
	
	/**
	 * Proved a textual version of the Pokemon locations
	 */
	public String drawPokemonMap() {
		String result = "";

		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++)
				if (r == trainer.getCurrentLocation().getY() && c == trainer.getCurrentLocation().getX())
					result += " P ";
				else
					if (tiles[r][c].getPokemonHere() == false)  result += "   ";
					else  result += " Y ";
		
			if (r < size - 1)
				result += "\n";
		}
		return result;
	}
	
} // end of Class 'Map'