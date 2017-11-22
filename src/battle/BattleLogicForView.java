package battle;

import java.util.List;
import java.util.Observable;
import java.util.Random;
import java.util.Scanner;

import model.Trainer;
import pokemon.ActiveAttack;
import pokemon.Attack;
import pokemon.PassiveAttackBuff;
import pokemon.PassiveStatBuff;
import pokemon.PokeType;
import pokemon.Pokemon;

public class BattleLogicForView extends Observable {

	private Trainer trainer1;
	private Trainer trainer2;
	private Trainer activeTrainer;
	private double[][] effectLookupTable; // Lookup table with values determining elemental attack effectiveness

	public BattleLogicForView(Trainer trainer1, Trainer trainer2) {

		this.trainer1 = trainer1;
		this.trainer2 = trainer2;
		this.activeTrainer = null;

		generateEffectLookupTable();
	}

	/**
	 * @return the trainer1
	 */
	public Trainer getTrainer1() {
		return trainer1;
	}

	/**
	 * @param trainer1
	 *            the trainer1 to set
	 */
	public void setTrainer1(Trainer trainer1) {
		this.trainer1 = trainer1;
	}

	/**
	 * @return the trainer2
	 */
	public Trainer getTrainer2() {
		return trainer2;
	}

	/**
	 * @param trainer2
	 *            the trainer2 to set
	 */
	public void setTrainer2(Trainer trainer2) {
		this.trainer2 = trainer2;
	}

	/**
	 * @return the activeTrainer
	 */
	public Trainer getActiveTrainer() {
		return activeTrainer;
	}

	/**
	 * @param activeTrainer the activeTrainer to set
	 */
	public void setActiveTrainer(Trainer activeTrainer) {
		this.activeTrainer = activeTrainer;
	}

	/**
	 * Top level function to handle battle sequence.
	 */
//	public void runBattle() {
//		
//		initializeActiveBattlePokemon();
//
//		activeTrainer = determineWhoStarts();
//
//		boolean isPokemonDrained = false;
//
//		while (!isBattleOver()) {
//
//			if (isPokemonDrained) {
//				activeTrainer = determineWhoStarts();
//			}
//
//			if (activeTrainer == trainer1)
//				isPokemonDrained = playRound(trainer1, trainer2);
//			else
//				isPokemonDrained = playRound(trainer2, trainer1);
//		}
//
//		System.out.println("\nBattle is over.");
//
//		if (areAllPokemonDrained(trainer1.getBattlePokemonList())) {
//			System.out.println(trainer2.getName() + " has defeated " + trainer1.getName());
//		} else {
//			System.out.println(trainer1.getName() + " has defeated " + trainer2.getName());
//		}
//	}
	
	/**
	 * First pokemon selected automatically starts in battle.
	 */
	public void initializeActiveBattlePokemon() {

		trainer1.setActiveBattlePokemon(trainer1.getBattlePokemonList().get(0));
		trainer2.setActiveBattlePokemon(trainer2.getBattlePokemonList().get(0));
		setChanged();
		notifyObservers();
	}

	/**
	 * Determines which trainer starts based on chosen pokemon's speed. If speed for
	 * both pokemon is tied, order is determined by 'coin toss'.
	 * 
	 * @return trainer1 if trainer 1 goes first, trainer2 if trainer 2 goes first.
	 */
	public Trainer determineWhoStarts() {

		Pokemon p1 = trainer1.getActiveBattlePokemon();
		Pokemon p2 = trainer2.getActiveBattlePokemon();

		if (p1.getSpeed() > p2.getSpeed())
			return trainer1;
		else if (p1.getSpeed() < p2.getSpeed())
			return trainer2;
		else {// Perform 'coin toss'
			int num = (new Random()).nextInt(10);
			if (num <= 4)
				return trainer1;
			else
				return trainer2;
		}
	}

	/**
	 * Play a round of battle (i.e. each pokemon gets a turn to attack, unless one
	 * dies in middle of round)
	 * 
	 * @param starter
	 *            Pokemon to go first
	 * @param finisher
	 *            Pokemon to go second
	 * 
	 * @return true if a pokemon has been depleted during the round, false otherwise
	 */
//	private boolean playRound(Trainer starter, Trainer finisher) {
//		// First Turn
//		playTurn(starter, finisher);
//		if (isPokemonDrained(finisher.getActiveBattlePokemon())) {
//			System.out.println();
//			System.out.println(finisher.getActiveBattlePokemon().getName() + " has fainted.");
//			if (isBattleOver())
//				return true;
//			switchPokemon(finisher);
//			return true;
//		}
//
//		// Second Turn
//		playTurn(finisher, starter);
//		if (isPokemonDrained(starter.getActiveBattlePokemon())) {
//			System.out.println();
//			System.out.println(starter.getActiveBattlePokemon().getName() + " has fainted.");
//			if (isBattleOver())
//				return true;
//			switchPokemon(starter);
//			return true;
//		}
//
//		return false;
//	}

	public void printCurrentBattleStatus() {

		System.out.println();
		System.out.println(trainer1.getName() + ": " + trainer1.getActiveBattlePokemon().getName() + " "
				+ trainer1.getActiveBattlePokemon().getCurrHP());
		System.out.println(trainer2.getName() + ": " + trainer2.getActiveBattlePokemon().getName() + " "
				+ trainer2.getActiveBattlePokemon().getCurrHP());
		System.out.println();
	}

	/**
	 * Effects from an attack are applied.
	 * 
	 * @param attackPokemon
	 *            pokemon performing the attack
	 * @param defendPokemon
	 *            pokemon defending against attack
	 * @param attack
	 *            the attack to be triggered
	 */
	public void applyAttack(Attack attack, Pokemon attackPokemon, Pokemon defendPokemon) {

		if (attack instanceof ActiveAttack) {
			int damage = calculateDamage((ActiveAttack) attack, attackPokemon, defendPokemon);
			defendPokemon.takeDamage(damage);
			System.out
					.println(attackPokemon.getName() + " used " + attack.getName() + " on " + defendPokemon.getName());
			System.out.println(defendPokemon.getName() + " lost " + damage + " HP.");
			setChanged();
			notifyObservers(attack);
		}

		else if (attack instanceof PassiveAttackBuff) {
			((PassiveAttackBuff) attack).activate(attackPokemon.getAttackList());
			System.out.println(attackPokemon.getName() + " increased effectiveness of " + attackPokemon.getPrimaryType()
					+ " attacks.");
			setChanged();
			notifyObservers(attack);
		}

		else {
			((PassiveStatBuff) attack).activate(attackPokemon);
			System.out.println(attackPokemon.getName() + " increased base stats.");
			setChanged();
			notifyObservers(attack);
		}
	}

	public int calculateDamage(ActiveAttack attack, Pokemon attackPokemon, Pokemon defendPokemon) {
		// Calculating multiplier from elemental effectiveness, randomness, and same
		// type bonus.
		// Effectiveness multiplier
		double multiplier = effectLookupTable[attack.getType().getIndex()][defendPokemon.getPrimaryType().getIndex()];
		// Randomness of multiplier ( * [.85-1])
		multiplier *= Math.random() * (1 - .85) + .85;
		// 50% damage increase if attack and attacking pokemon are of the same type
		if (attack.getType() == attackPokemon.getPrimaryType()
				|| attack.getType() == attackPokemon.getSecondaryType()) {
			multiplier *= 1.5;
		}
		double damage = .7 * ((double) attackPokemon.getAttack() / (double) defendPokemon.getDefense())
				* attack.getDamage() * multiplier;
		return (int) damage;
	}

	private void switchPokemon(Trainer trainer) {

		printSwitchMenu(trainer);

		Pokemon chosenPoke = getSwitchChoice(trainer);

		trainer.setActiveBattlePokemon(chosenPoke);
	}

	private void printSwitchMenu(Trainer trainer) {

		System.out.println(trainer.getName() + ", Select new pokemon to enter battlefield:");

		List<Pokemon> pokeList = trainer.getBattlePokemonList();

		for (int i = 0; i < pokeList.size(); i++) {
			System.out.println((i) + ": " + pokeList.get(i).getName());
		}
	}

	private Pokemon getSwitchChoice(Trainer trainer) {

		List<Pokemon> pokeList = trainer.getBattlePokemonList();
		Pokemon currPoke = trainer.getActiveBattlePokemon();

		Scanner in = new Scanner(System.in);
		int choice;

		while (true) {

			if (in.hasNextInt()) {

				choice = in.nextInt();

				if (choice >= 0 && choice < 3) {

					if (pokeList.get(choice).getCurrHP() <= 0) {
						System.out.println(
								pokeList.get(choice).getName() + " is completely drained. Choose another pokemon");
					} else
						break;
				} else
					System.out.println("Invalid Entry. Try Again");
			} else
				in.next();
		}
		return pokeList.get(choice);
	}

	/**
	 * Battle is over when all battle pokemon HP are zero for either trainer.
	 * 
	 * @return true if one player has no HP left for any of battle pokemon, false
	 *         otherwise.
	 */
	public boolean isBattleOver() {
//		return areAllPokemonDrained(trainer1.getBattlePokemonList())
//				|| areAllPokemonDrained(trainer2.getBattlePokemonList());
		return isPokemonDrained(trainer1.getActiveBattlePokemon()) || isPokemonDrained(trainer2.getActiveBattlePokemon());
	}

	/**
	 * Checks if all pokemon in a list are 'incapacitated' (i.e. currHP = 0)
	 * 
	 * @param chosenPokeList
	 *            Pokemon list to iterate through
	 * @return true if all pokemon are incapacitated, false otherwise
	 */
	public boolean areAllPokemonDrained(List<Pokemon> chosenPokeList) {

		for (Pokemon p : chosenPokeList) {
			if (!isPokemonDrained(p)) {
				return false;
			}
		}
		return true;
	}

	public boolean isPokemonDrained(Pokemon p) {
		return (p.getCurrHP() <= 0);
	}

	/**
	 * Creates a table with appropriate values for effectiveness based on elemental
	 * types. e.g. Fire attacks Grass, gives a modifier of 2 (super-effective).
	 */
	private void generateEffectLookupTable() {

		double[][] table = new double[6][6];

		// Fire Attack
		table[PokeType.FIRE.getIndex()][PokeType.FIRE.getIndex()] = .5;
		table[PokeType.FIRE.getIndex()][PokeType.WATER.getIndex()] = .5;
		table[PokeType.FIRE.getIndex()][PokeType.GRASS.getIndex()] = 2;
		table[PokeType.FIRE.getIndex()][PokeType.ELECTRIC.getIndex()] = 1;
		table[PokeType.FIRE.getIndex()][PokeType.NORMAL.getIndex()] = 1;
		table[PokeType.FIRE.getIndex()][PokeType.POISON.getIndex()] = 1;

		// Water Attack
		table[PokeType.WATER.getIndex()][PokeType.FIRE.getIndex()] = 2;
		table[PokeType.WATER.getIndex()][PokeType.WATER.getIndex()] = .5;
		table[PokeType.WATER.getIndex()][PokeType.GRASS.getIndex()] = .5;
		table[PokeType.WATER.getIndex()][PokeType.ELECTRIC.getIndex()] = 1;
		table[PokeType.WATER.getIndex()][PokeType.NORMAL.getIndex()] = 1;
		table[PokeType.WATER.getIndex()][PokeType.POISON.getIndex()] = 1;

		// Grass Attack
		table[PokeType.GRASS.getIndex()][PokeType.FIRE.getIndex()] = .5;
		table[PokeType.GRASS.getIndex()][PokeType.WATER.getIndex()] = 2;
		table[PokeType.GRASS.getIndex()][PokeType.GRASS.getIndex()] = .5;
		table[PokeType.GRASS.getIndex()][PokeType.ELECTRIC.getIndex()] = 1;
		table[PokeType.GRASS.getIndex()][PokeType.NORMAL.getIndex()] = 1;
		table[PokeType.GRASS.getIndex()][PokeType.POISON.getIndex()] = .5;

		// Electric Attack
		table[PokeType.ELECTRIC.getIndex()][PokeType.FIRE.getIndex()] = 1;
		table[PokeType.ELECTRIC.getIndex()][PokeType.WATER.getIndex()] = 2;
		table[PokeType.ELECTRIC.getIndex()][PokeType.GRASS.getIndex()] = .5;
		table[PokeType.ELECTRIC.getIndex()][PokeType.ELECTRIC.getIndex()] = .5;
		table[PokeType.ELECTRIC.getIndex()][PokeType.NORMAL.getIndex()] = 1;
		table[PokeType.ELECTRIC.getIndex()][PokeType.POISON.getIndex()] = 1;

		// Normal Attack
		table[PokeType.NORMAL.getIndex()][PokeType.FIRE.getIndex()] = 1;
		table[PokeType.NORMAL.getIndex()][PokeType.WATER.getIndex()] = 1;
		table[PokeType.NORMAL.getIndex()][PokeType.GRASS.getIndex()] = 1;
		table[PokeType.NORMAL.getIndex()][PokeType.ELECTRIC.getIndex()] = 1;
		table[PokeType.NORMAL.getIndex()][PokeType.NORMAL.getIndex()] = 1;
		table[PokeType.NORMAL.getIndex()][PokeType.POISON.getIndex()] = 1;

		// Poison Attack
		table[PokeType.POISON.getIndex()][PokeType.FIRE.getIndex()] = 1;
		table[PokeType.POISON.getIndex()][PokeType.WATER.getIndex()] = 1;
		table[PokeType.POISON.getIndex()][PokeType.GRASS.getIndex()] = 2;
		table[PokeType.POISON.getIndex()][PokeType.ELECTRIC.getIndex()] = 1;
		table[PokeType.POISON.getIndex()][PokeType.NORMAL.getIndex()] = 1;
		table[PokeType.POISON.getIndex()][PokeType.POISON.getIndex()] = .5;

		this.effectLookupTable = table;
	}

}