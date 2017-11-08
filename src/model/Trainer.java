package model;

import java.awt.Point;
import java.util.Vector;

public class Trainer {

	private Point currentLocation;
	private int numSteps;
	private int numPokeBalls;
	private Vector<Pokemon> pokemon;

	public Trainer() {
		currentLocation = new Point((50 / 2), (50 / 2) + 15);
		numSteps = 500;
		numPokeBalls = 30;
		pokemon = new Vector<Pokemon>();
	}

	public Point getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(Point c) {
		currentLocation = c;
	}

	public int getNumSteps() {
		return numSteps;
	}

	public void setNumSteps(int n) {
		numSteps = n;
	}

	public int getNumPokeballs() {
		return numPokeBalls;
	}

	public void setNumPokeballs(int np) {
		numPokeBalls = np;
	}

	public Vector<Pokemon> getPokemon() {
		return pokemon;
	}

	public void setPokemon(Vector<Pokemon> p) {
		pokemon = p;
	}
}
