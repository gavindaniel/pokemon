package model;

import java.awt.Point;
import java.util.List;

import pokemon.Pokemon;

public class Trainer {

	private String name;
	private List<Pokemon> pokemonList;
//	private List<Item> itemList;
	private Point position;

//	private TrainerAnimation trainerAnimation;

	public Trainer(String name) {

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
	 * @return the pokemonList
	 */
	public List<Pokemon> getPokemonList() {
		return pokemonList;
	}

	/**
	 * @param pokemonList the pokemonList to set
	 */
	public void setPokemonList(List<Pokemon> pokemonList) {
		this.pokemonList = pokemonList;
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

	public boolean addPokemon(Pokemon p) {
		return this.pokemonList.add(p);
	}

	public boolean removePokemon(Pokemon p) {
		return this.pokemonList.remove(p);
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
