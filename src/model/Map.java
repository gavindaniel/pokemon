package model;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Observable;
import java.util.Scanner;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

import controller.Settings;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import map.Tile;
import map.Zone;
import pokemon.Bulbasaur;
import pokemon.Charmander;
import pokemon.Pikachu;
import pokemon.Pokemon;

public class Map extends Observable {

	private int size;
	private Trainer trainer;
	private Vector<Trainer> trainers;
	private Vector<Zone> zones;
	private static final int numPokemon = 10;
	private static Settings settings;

	public Map() {
		settings = new Settings();
		size = settings.getZoneSize(); // # columns, rows
		trainer = new Trainer("Player1");
		zones = new Vector<Zone>();
		generateZones();
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
	public Zone getZone(int zoneNum) {
		return zones.get(zoneNum - 1);
	}
	
	public void generateZones() {
		for (int i = 1; i <= 4; i++) {
			Zone temp = new Zone(i);
			zones.add(temp);
		}
	}
	public void clearBoard() {
		for (int z = 0; z < zones.size(); z++) 
			for (int r = 0; r < size; r++)
				for (int c = 0; c < size; c++)
					zones.get(z).setTile(new Tile('T'),r,c);
	}

	public void ReadMapFromFile() {
		
		boolean trainer_already_found = false;
		
		for (int z = 1; z <= 4; z++) {
			File file = new File("./files/zone"+z+".txt");
			Zone tempZone = zones.get(z-1);
			Tile temp = new Tile();
			int r = settings.getTreeLine();
			int c = settings.getTreeLine();
			int row_count = 0;
			int col_count = 0;
			try {
				Scanner sc = new Scanner(file);
				while (sc.hasNextLine()) {
					String line = sc.nextLine();
					for (int i = 0; i < line.length(); i++) {
						if (line.charAt(i) != ' ') {
							temp = new Tile(line.charAt(i));
							tempZone.setTile(temp,r,c);
							if (line.charAt(i) == 'P') {
								if (!trainer_already_found) {
									trainer_already_found = true;
									trainer.setCurrentLocation(new Point(c, r));
								}
							}
							c++;
							col_count++;
						}
					}
					if (row_count == 0)
						tempZone.setColumnSize(col_count);
					c = settings.getTreeLine();
					r++;
					row_count++;
				}
				sc.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			tempZone.setRowSize(row_count);
			zones.set(z-1,tempZone);
		}
	}
	public void clearPokemon() {
		for (int z = 0; z < zones.size(); z++)
			for (int r = 0; r < zones.get(z).getRowSize(); r++)
				for (int c = 0; c < zones.get(z).getColumnSize(); c++)
					zones.get(z).getTile(r,c).setPokemonHere(false);
	}
	public void spawnPokemon() {
		int min = 0;
		int max = size;
		int num = 1;
		int random_r;
		int random_c;
		clearPokemon(); //clears the zones of any pokemon
		for (int z = 0; z < zones.size(); z++) {
//			if (zones.get(z).getColumnSize() > zones.get(z).getRowSize())
//				max = zones.get(z).getRowSize();
//			else if (zones.get(z).getColumnSize() <= zones.get(z).getRowSize())
//				max = zones.get(z).getColumnSize();
			while (num <= numPokemon) {
				random_r = ThreadLocalRandom.current().nextInt(min, max); // removed '+ 1' to not include size (200)
				random_c = ThreadLocalRandom.current().nextInt(min, max); // removed '+ 1' to not include size (200)
			
				if (zones.get(z).getTile(random_r, random_c).getPokemonHere() == false)
					if (zones.get(z).getTile(random_r, random_c).getType() == "grass") {
						zones.get(z).getTile(random_r, random_c).setPokemonHere(true);
						num++;
					}
			}
			num = 1;
		}
	}
	
	
	public String updateTrainerLocation(Point oldLoc, Point newLoc) {
		Point newP = newLoc;
		String response = "nothing";
		if (checkCanMoveHere(trainer.getZone()-1, oldLoc, newP)) {
			if (checkZoneChange(newP)) {
				newP = changeZone(trainer, oldLoc);
				System.out.println("trying to swtich");
			}
			trainer.setCurrentLocation(newP);
			trainer.setNumSteps(trainer.getNumSteps() - 1);
			Pokemon temp = checkForPokemon(trainer.getZone()-1, newLoc);
			if (temp != null) {
				response = "pokemon-" + temp.getName();
			}
			if (checkForTrainer(trainer.getZone()-1, newLoc)) {
				response = "trainer";
			}
			clearPokemon();
			spawnPokemon();
		}
		return response;
	}

	public boolean checkCanMoveHere(int zoneNum, Point oldPosition, Point newPosition) {
		int new_c = (int) newPosition.getX();
		int new_r = (int) newPosition.getY();
		int old_c = (int) oldPosition.getX();
		int old_r = (int) oldPosition.getY();
		Tile oldTile = zones.get(zoneNum).getTile(old_r, old_c);
		Tile newTile = zones.get(zoneNum).getTile(new_r, new_c);
		try {
			if (oldTile.getType() == "hill") {
				if (newTile.getSourceChar() == 'm' || newTile.getSourceChar() == 't' || newTile.getType() == "stairs") return true;
				else return false;
			}
			if (newTile.getType() == "ground") return true;
			else if ((newTile.getType() == "grass") || (newTile.getSourceChar() == 'm') || (newTile.getType() == "stairs") || (newTile.getType() == "exit")) return true;
			else if (newTile.getTrainerHere()) return false;
			else return false;
		} 
		
		catch (ArrayIndexOutOfBoundsException aiobe) {
//			System.out.println(aiobe.toString() + "\tr: " + new_r + "\tc: " + new_c);
			return false;
		} 
		
	}
	
	public Pokemon checkForPokemon(int zoneNum, Point newPosition) {
		int c = (int) newPosition.getX();
		int r = (int) newPosition.getY();
		try {
			if (zones.get(zoneNum).getTile(r,c).getPokemonHere() == true) {
				
//				List<Pokemon> temp = trainer.getOwnedPokemonList();
//				trainer.setOwnedPokemonList(temp);
				return getRandomPokemon();
			}
		} catch (ArrayIndexOutOfBoundsException aiobe) {
			System.out.println(aiobe.toString());
		} 
		return null;
	}

	public Pokemon getRandomPokemon() {
		int random = ThreadLocalRandom.current().nextInt(0, 3); //9
		if (random == 0)
			return new Pikachu();
		if (random == 1)
			return new Charmander();
//		if (random == 2)
			return new Bulbasaur();
//		if (random == 3)
//			return new Pikachu();
//		if (random == 4)
//			return new Pikachu();
//		if (random == 5)
//			return new Pikachu();
//		if (random == 6)
//			return new Pikachu();
//		if (random == 7)
//			return new Pikachu();
	}

	public boolean checkForTrainer(int zoneNum, Point newPosition) {
		int c = (int) newPosition.getX();
		int r = (int) newPosition.getY();
		try {
			if (zones.get(zoneNum).getTile(r,c).getTrainerHere() == true) {
				return true;
			}
		} catch (ArrayIndexOutOfBoundsException aiobe) {
			System.out.println(aiobe.toString());
		} 
		return false;
	}
	
	public boolean checkZoneChange(Point newPosition) {
		int new_c = (int) newPosition.getX();
		int new_r = (int) newPosition.getY();
		int zoneNum = trainer.getZone()-1;
		Tile newTile = zones.get(zoneNum).getTile(new_r, new_c);
		try {
			if (newTile.getType() == "exit") {
				System.out.println("trying to switch zones");
				return true;
				
			}
			else return false;
		} 
		
		catch (ArrayIndexOutOfBoundsException aiobe) {
	//		System.out.println(aiobe.toString() + "\tr: " + new_r + "\tc: " + new_c);
			return false;
		} 
	}
	// if check for zone change was true, switch the zone so the view updates
	public Point changeZone(Trainer player, Point oldPosition) {
		int c = (int) oldPosition.getX();
		int r = (int) oldPosition.getY();
		
		int prevZone = player.getZone();
		System.out.println("prev zone: " + prevZone);
//		System.out.println(x);
		if (prevZone == 1) {
			if (c == 61) {
				r += 8;
				c = 24;
				player.setZone(2); // might need to be 1 
			}
			else if (r == 21) {
				r = 47;
				c += 10;
				player.setZone(3);
			}
			else if (c == 21) {
				// r = 36 (top) or 37 (bottom)
				r += 8;
				c = 61;
				player.setZone(4);
			}
			return new Point(c,r);
		}
		else if (prevZone == 2) {
			if (r == 41 || r == 42 || r == 43) {
				r -= 8;
				c = 61; 
				player.setZone(1); // might need to be 1 
				return new Point(c,r);
			}
			else if (r == 24 || r == 25) {
				r += 17;
				c = 71;
				player.setZone(3);
			}
			return new Point(c,r); //don't change 
		}
		else if (prevZone == 3) {
			if (r == 41 || r == 42) {
				r -= 17;
				c = 24;
				player.setZone(2);
			}
			else if (c == 50 || c == 51 || c == 52) {
				r = 21;
				c -= 10;
				player.setZone(1);
			}
			else if (c == 28 || c == 29) { // r = 47
				r = 21;
				c += 28;
				player.setZone(4);
			}
			return new Point(c,r); //don't change 
		}
		else if (prevZone == 4) {
			if (c == 61) { // r = 44 (top) or 45 (bottom)
				r -= 8;
				c = 21;
				player.setZone(1);
			}
			else if (c == 56 || c == 57) { // top-right exit	: r = 21
				r = 47;
				c -= 28;
				player.setZone(3);
			}
			return new Point(c,r);
		}
		else return new Point(c,r); //don't change 
	}
	/**
	 * Proved a textual version of the game Map
	 */
	public String drawGameMap(int zoneNum) {
		String result = "";
		int tree_line = settings.getTreeLine();
		for (int r = tree_line; r < (size - tree_line); r++) {
			for (int c = tree_line; c < (size - tree_line); c++)
				if (r == trainer.getCurrentLocation().getY() && c == trainer.getCurrentLocation().getX())
					result += " P ";
				else
					if  (zones.get(zoneNum).getTile(r,c).getType() == "ground")	result += "   ";
					else	  result += " " + zones.get(zoneNum).getTile(r,c).getSourceChar() + " ";
			if (r < size - 1)  result += "\n";
		}
		return result;
	}
	
} // end of Class 'Map'