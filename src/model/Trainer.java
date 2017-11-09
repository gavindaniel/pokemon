package model;

import java.awt.Point;
import java.util.List;

import pokemon.Pokemon;

public class Trainer {

	private String name;
	private List<Pokemon> ownedPokemonList;		//List of all owned pokemon
	private List<Pokemon> battlePokemonList;		//List of 3 pokemon to be used in battle
	private Pokemon activeBattlePokemon;			//Active pokemon in battle
//	private List<Item> itemList;
	private Point position;		//Position on map

//	private TrainerAnimation trainerAnimation;

	public Trainer(String name) {
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
	 * @return the ownedPokemonList
	 */
	public List<Pokemon> getOwnedPokemonList() {
		return this.ownedPokemonList;
	}

	/**
	 * @param pokemonList the pokemonList to set
	 */
	public void setPokemonList(List<Pokemon> pokemonList) {
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
	 * @return the position
	 */
	public Point getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Point position) {
		this.position = position;
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

//	public boolean addItem() {
//		return false;
//
//	}
//
//	public boolean removeItem() {
//		return false;
//
//	}
}
