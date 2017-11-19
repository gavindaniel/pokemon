package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import items.Item;
import pokemon.Pokemon;

public class Trainer {

	private String name;
	private Point currentLocation;
	private int zone;
	private int numSteps;
	private int numPokeBalls;
	private List<Pokemon> ownedPokemonList;		//List of all owned pokemon
	private List<Pokemon> battlePokemonList;		//List of 3 pokemon to be used in battle
	public List<Item> itemList;
	private Pokemon activeBattlePokemon;			//Active pokemon in battle
	
//	private TrainerAnimation trainerAnimation;
	
	public Trainer(String name) {
		currentLocation = new Point((50 / 2), (50 / 2) + 15);
		zone = 1;
		numSteps = 500;
		numPokeBalls = 30;
//		pokemon = new Vector<Pokemon>();
		ownedPokemonList = new ArrayList<Pokemon>();
		battlePokemonList = new ArrayList<Pokemon>(3);
		itemList = new ArrayList<Item>();
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the zone the trainer is in (default is 1 the starting zone)
	 */
	public int getZone() {
		return zone;
	}
	/**
	 * @param the zone the trainer is in (default is 1 the starting zone)
	 */
	public void setZone(int z) {
		zone = z;
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
	/**
	 * @return the number of pokeballs the trainer has
	 */
	public int getNumPokeballs() {
		return numPokeBalls;
	}
	/**
	 * @param the number of pokeballs the trainer has
	 */
	public void setNumPokeballs(int np) {
		numPokeBalls = np;
	}
	
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
	 * @param pokemonList the pokemonList to set
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
	 * @param battlePokemonList the battlePokemonList to set
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
	 * @param activeBattlePokemon the activeBattlePokemon to set
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
	 * @param itemList the itemList to set
	 */
	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}
	
	/**
	 * @param add to item list
	 */
	public void addItem(Item item) {
		this.itemList.add(item);
	}
	
	/**
	 * @param remove from item list
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
	 * @param position the position to set
	 */
	public void setCurrentLocation(Point p) {
		currentLocation = p;
	}
	
//	public void move() {
//
//	}
	/**
	 * Append pokemon to owned list.
	 * @param p pokemon to add
	 * @return true if successful
	 */
	public boolean addPokemonToOwned(Pokemon p) {
		return this.ownedPokemonList.add(p);
	}

	/**
	 * Delete pokemon from owned list
	 * @param p pokemon to delete
	 * @return ture if successful
	 */
	public boolean removePokemonFromOwned(Pokemon p) {
		return this.ownedPokemonList.remove(p);
	}
}
