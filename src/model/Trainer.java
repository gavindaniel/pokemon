package model;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import controller.Settings;
import items.EasyCatch;
import items.EasyStay;
import items.Item;
import items.MasterBall;
import pokemon.Pikachu;
import items.SafariBall;
import items.UltraBall;
import pokemon.Pokemon;

public class Trainer implements Serializable{

	private String name;

	private Point currentLocation;
	private int zone_number;
	private int numSteps;
	private List<Pokemon> ownedPokemonList;		//List of all owned pokemon
	private List<Pokemon> battlePokemonList;		//List of 3 pokemon to be used in battle
	public List<Item> itemList;
	private Pokemon activeBattlePokemon;			//Active pokemon in battle
	private Settings settings;
	
//	private TrainerAnimation trainerAnimation;
	
	public Trainer(String _name) {
		name = _name;
		settings = new Settings();
		currentLocation = new Point((50 / 2), (50 / 2) + 15);
		zone_number = settings.getStartingZone();
		numSteps = 500;
		ownedPokemonList = new ArrayList<Pokemon>();
			ownedPokemonList.add(new Pikachu());
		itemList = new ArrayList<Item>();
		givePokeBalls();
		battlePokemonList = new ArrayList<Pokemon>(3);
		activeBattlePokemon = null;
	}

	//Give the trainer 30 pokeballs to start
	public void givePokeBalls() {
		for(int i=0;i<30;i++) {
			itemList.add(new SafariBall());
		}
		for(int i=0;i<3;i++) {
			itemList.add(new UltraBall());
			itemList.add(new EasyCatch());
			itemList.add(new EasyStay());
		}
		
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String n) {
		name = n;
	}
	/**
	 * @return the zone the trainer is in (default is 1 the starting zone)
	 */
	public int getZone() {
		return zone_number;
	}
	/**
	 * @param the zone the trainer is in (default is 1 the starting zone)
	 */
	public void setZone(int z) {
		zone_number = z;
	}
	/**
	 * @return the trainer's number of steps left
	 */
	public int getNumSteps() {
		return numSteps;
	}
	/**
	 * @param the number of steps the trainer has
	 */
	public void setNumSteps(int n) {
		numSteps = n;
	}

//	/**
//	 * @return the number of pokeballs the trainer has
//	 */
//	public int getNumPokeballs() {
//		return numPokeBalls;
//	}
//	/**
//	 * @param the number of pokeballs the trainer has
//	 */
//	public void setNumPokeballs(int np) {
//		numPokeBalls = np;
//	}
	
	public boolean addItem() {
		return false;
	}

	public boolean removeItem() {
		return false;
	}
	
	/**
	 * @return the ownedPokemonList
	 */
	public List<Pokemon> getOwnedPokemonList() {
		return this.ownedPokemonList;
	}

	/**
	 * @param pokemonList
	 *            the pokemonList to set
	 */
	public void setOwnedPokemonList(List<Pokemon> pokemonList) {
		this.ownedPokemonList = pokemonList;
	}

	/**
	 * @return the battlePokemonList
	 */
	public List<Pokemon> getBattlePokemonList() {
		return battlePokemonList;
	}

	/**
	 * @param battlePokemonList
	 *            the battlePokemonList to set
	 */
	public void setBattlePokemonList(List<Pokemon> battlePokemonList) {
		this.battlePokemonList = battlePokemonList;
	}

	/**
	 * @return the activeBattlePokemon
	 */
	public Pokemon getActiveBattlePokemon() {
		return activeBattlePokemon;
	}

	/**
	 * @param activeBattlePokemon
	 *            the activeBattlePokemon to set
	 */
	public void setActiveBattlePokemon(Pokemon activeBattlePokemon) {
		this.activeBattlePokemon = activeBattlePokemon;
	}

	/**
	 * @return the itemList
	 */
	public List<Item> getItemList() {
		return itemList;
	}

	/**
	 * @param itemList
	 *            the itemList to set
	 */
	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}

	/**
	 * @param add
	 *            to item list
	 */
	public void addItem(Item item) {
		this.itemList.add(item);
	}

	/**
	 * @param remove
	 *            from item list
	 */
	public void removeItem(Item item) {
		this.itemList.remove(item);

	}

	/**
	 * @return the position
	 */
	public Point getCurrentLocation() {
		return currentLocation;
	}

	/**
	 * @param position
	 *            the position to set
	 */
	public void setCurrentLocation(Point p) {
		currentLocation = p;
	}

	/**
	 * Append pokemon to owned list.
	 * 
	 * @param p
	 *            pokemon to add
	 * @return true if successful
	 */
	public boolean addPokemonToOwned(Pokemon p) {
		return this.ownedPokemonList.add(p);
	}

	/**
	 * Delete pokemon from owned list
	 * 
	 * @param p
	 *            pokemon to delete
	 * @return ture if successful
	 */
	public boolean removePokemonFromOwned(Pokemon p) {
		return this.ownedPokemonList.remove(p);
	}

	public boolean removeSafariBall() {
		for(Item item:itemList) {
			if(item.getClass()==SafariBall.class) {
				itemList.remove(item);
				return true;
			}
		}
		return false;
		
	}
	
	
	//Remove ultraball
	public boolean removeUltraBall() {
		for(Item item:itemList) {
			if(item.getClass()==UltraBall.class) {
				itemList.remove(item);
				return true;
			}
		}
		return false;
		
	}
	
	//Remove masterball
		public boolean removeMasterBall() {
			for(Item item:itemList) {
				if(item.getClass()==MasterBall.class) {
					itemList.remove(item);
					return true;
				}
			}
			return false;
			
		}
}
