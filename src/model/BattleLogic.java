package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Random;
import java.util.Scanner;

import pokemon.ActiveAttack;
import pokemon.Attack;
import pokemon.PassiveAttackBuff;
import pokemon.PassiveStatBuff;
import pokemon.PokeType;
import pokemon.Pokemon;

public class BattleLogic extends Observable {
	
	private Trainer trainer1;
	private Trainer trainer2;
	private List<Pokemon> chosenPokemonList1;	//First trainer's 3 chosen pokemon
	private List<Pokemon> chosenPokemonList2;	//Second trainer's 3 chosen pokemon
	private Pokemon currPoke1;	//Pokemon starting the round
	private Pokemon currPoke2;	//Pokemon ending the round
	private double[][] effectLookupTable;	//Lookup table with values determining elemental attack effectiveness
	
	public BattleLogic(Trainer trainer1, Trainer trainer2) {
		
		this.setTrainer1(trainer1);
		this.setTrainer2(trainer2);
		
		this.chosenPokemonList1 = null;
		this.chosenPokemonList2 = null;
		this.currPoke1 = null;
		this.currPoke2 = null;
		
		generateEffectLookupTable();
	}

	/**
	 * @return the trainer1
	 */
	public Trainer getTrainer1() {
		return trainer1;
	}

	/**
	 * @param trainer1 the trainer1 to set
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
	 * @param trainer2 the trainer2 to set
	 */
	public void setTrainer2(Trainer trainer2) {
		this.trainer2 = trainer2;
	}

	/**
	 * @return the chosenPokemonList1
	 */
	public List<Pokemon> getChosenPokemonList1() {
		return chosenPokemonList1;
	}

	/**
	 * @param chosenPokemonList1 the chosenPokemonList1 to set
	 */
	public void setChosenPokemonList1(List<Pokemon> chosenPokemonList1) {
		this.chosenPokemonList1 = chosenPokemonList1;
	}

	/**
	 * @return the chosenPokemonList2
	 */
	public List<Pokemon> getChosenPokemonList2() {
		return chosenPokemonList2;
	}

	/**
	 * @param chosenPokemonList2 the chosenPokemonList2 to set
	 */
	public void setChosenPokemonList2(List<Pokemon> chosenPokemonList2) {
		this.chosenPokemonList2 = chosenPokemonList2;
	}


	
	/**
	 * @return the current pokemon chosen by trainer 1
	 */
	public Pokemon getCurrPoke1() {
		return currPoke1;
	}

	/**
	 * @param currPoke1 set the pokemon chosen by trainer 1
	 */
	public void setCurrPoke1(Pokemon currPoke1) {
		this.currPoke1 = currPoke1;
	}

	/**
	 * @return the current pokemon chosen by trainer 2
	 */
	public Pokemon getCurrPoke2() {
		return currPoke2;
	}

	/**
	 * @param currPoke2 set the pokemon chosen by trainer 2
	 */
	public void setCurrPoke2(Pokemon currPoke2) {
		this.currPoke2 = currPoke2;
	}

	/**
	 * Top level function to handle battle sequence.
	 */
	public void runBattle() {
		
		// Each trainer selects pokemon.
		chosenPokemonList1 = trainerSelectsPokemon(trainer1);
		chosenPokemonList2 = trainerSelectsPokemon(trainer2);
		
		//Head of list is automatically selected at start.
		currPoke1 = chosenPokemonList1.get(0);
		currPoke2 = chosenPokemonList2.get(0);
		
		int faster = determineWhoStarts(currPoke1, currPoke2);
		
		while(!isBattleOver()) {
			
			if (faster == 1) playRound(trainer1, trainer2);
			else playRound(trainer2, trainer1);
		
		}
		
	}
	
	/**
	 * 3 pokemon are selected for use in battle.
	 */
	private List<Pokemon> trainerSelectsPokemon(Trainer trainer) {
		
		List<Pokemon> pokeList = new ArrayList<>(3);
		
		Pokemon chosenPoke;
		
		while (pokeList.size() < 3) {
			
			printPokeListChooser(trainer);
			
			int index = getPokeChoiceFromUser(trainer);
			
			chosenPoke = trainer.getPokemonList().get(index);
			
			if (!pokeList.contains(chosenPoke)) {
				pokeList.add(chosenPoke);
				System.out.println(chosenPoke.getName() + " Successfully added.");
			}
			else System.out.println("Pokemon already chosen. Pick another one.");
		}
		
		return pokeList;
		
	}
	
	/**
	 * Prints list of available pokemon to console for trainer to choose from.
	 */
	private void printPokeListChooser(Trainer trainer) {
		
		List<Pokemon> pokeList = trainer.getPokemonList();
		
		System.out.println(trainer.getName() + ", Choose from the following pokemon: ");
		
		for (int i = 0; i < pokeList.size(); i++) {
			System.out.println((i) + ": " + pokeList.get(i).getName());
		}
	}
	
	/**
	 * Obtains choice for pokemon from user.
	 * @return
	 */
	private int getPokeChoiceFromUser(Trainer trainer) {
		
		Scanner in = new Scanner(System.in);
		int choice;
		
		while(true) {
			
//			while (!in.hasNextInt()) in.next();
			
			if (in.hasNextInt()) {
				
				choice = in.nextInt();
				
				if (choice >= 0 && choice < trainer.getPokemonList().size()) break;
				else System.out.println("Invalid Choice. Try Again");
			}
			else in.next();
		}
		return choice;
	}
	
	/**
	 * Determines who starts based pokemon speed.
	 * @return
	 */
	private int determineWhoStarts(Pokemon p1, Pokemon p2) {
		
		if (p1.getSpeed() > p2.getSpeed()) return 1;
		else if (p1.getSpeed() < p2.getSpeed()) return 2;
		else {//If speed is tied, determine starting player by 'coin toss'
			int num = (new Random()).nextInt(10);
			if (num <= 4) return 1;
			else return 2;
		}
	}
	
	private void playRound(Trainer starter, Trainer finisher) {
		playTurn(starter);
		playTurn(finisher);
	}
	
	private void playTurn(Trainer trainer) {
		
		Pokemon attackPokemon;
		Pokemon	defendPokemon;
		
		printCurrentBattleStatus();
		
		if (trainer == trainer1) {
			attackPokemon = currPoke1;
			defendPokemon = currPoke2;
			
		}
		else {
			attackPokemon = currPoke2;
			defendPokemon = currPoke1;
		}
		
		int choice = MakeTurnChoice(trainer);
		
		if (choice == 1) {
			int attackChoice = chooseAttack(attackPokemon, defendPokemon);
			applyAttack(attackPokemon, defendPokemon, attackChoice);
		}
//		if (choice == 2) switchPokemon(trainer);
		
	}
	
	private void printCurrentBattleStatus() {
		
		System.out.println();
		System.out.println(trainer1.getName() + ": " + currPoke1.getName() + " " + currPoke1.getCurrHP());
		System.out.println(trainer2.getName() + ": " + currPoke2.getName() + " " + currPoke2.getCurrHP());
		System.out.println();
	}
	
	private int MakeTurnChoice(Trainer trainer) {
		
		System.out.println(trainer.getName() + ". What would you like to do?");
		System.out.print("1- Attack\n2- Switch Pokemon\n");
		
		Scanner in = new Scanner(System.in);
		int choice;
		
		while(true) {
			
			if (in.hasNextInt()) {
				
				choice = in.nextInt();
				
				if (choice == 1 || choice == 2) break;
				else System.out.println("Invalid Choice. Try Again");
			}
			else in.next();
		}
		return choice;
	}
	
	private int chooseAttack(Pokemon attackPokemon, Pokemon defendPokemon) {
		
		List<Attack> attackSet = attackPokemon.getAttackList();
		
		System.out.println("Choose an attack: ");
		for (int i = 0; i < attackSet.size(); i++) {
			System.out.println((i) + ": " + attackSet.get(i).getName());
		}
		
		Scanner in = new Scanner(System.in);
		int choice;
		
		while(true) {
			
			if (in.hasNextInt()) {
				
				choice = in.nextInt();
				
				if (choice >= 0 && choice <= 3) break;
				else System.out.println("Invalid Choice. Try Again");
			}
			else in.next();
		}
		return choice;
	}
	
	private void applyAttack(Pokemon attackPokemon, Pokemon defendPokemon, int attackIndex) {
		Attack attack = attackPokemon.getAttackList().get(attackIndex);
		
		if (attack instanceof ActiveAttack) {
			int damage = calculateDamage((ActiveAttack) attack, attackPokemon, defendPokemon);
			defendPokemon.takeDamage(damage);
			System.out.println(attackPokemon.getName() + " used " + attack.getName() + " on " + defendPokemon.getName());
			System.out.println(defendPokemon.getName() + " lost " + damage + " HP.");
		}
		
		else if (attack instanceof PassiveAttackBuff) {
			((PassiveAttackBuff) attack).activate(attackPokemon.getAttackList());
		}
		
		else {
			((PassiveStatBuff) attack).activate(attackPokemon);
		}
	}
	
	private int calculateDamage(ActiveAttack attack, Pokemon attackPokemon, Pokemon defendPokemon) {
		//Calculating multiplier from elemental effectiveness, randomness, and same type bonus.
		//Effectiveness multiplier
		double multiplier = effectLookupTable[attack.getType().getIndex()][defendPokemon.getPrimaryType().getIndex()];
		//Randomness of multiplier ( * [.85-1])
		multiplier *= Math.random() * (1 - .85) + .85;
		//50% damage increase if attack and attacking pokemon are of the same type
		if (attack.getType() == attackPokemon.getPrimaryType() || attack.getType() == attackPokemon.getSecondaryType() ) {
			multiplier *= 1.5;
		}
		double damage = .80*((double) attackPokemon.getAttack()/(double) defendPokemon.getDefense())*attack.getDamage()*multiplier;
		return (int) damage;
	}
	
	
	
	
	
	
	/**
	 * Battle is over when all battle pokemon HP are zero for either trainer.
	 * @return true if one player has no HP left for any of battle pokemon, false otherwise.
	 */
	private boolean isBattleOver() {
		return areAllPokemonDrained(chosenPokemonList1) || areAllPokemonDrained(chosenPokemonList2);
	}
	
	private boolean areAllPokemonDrained(List<Pokemon> chosenPokeList) {
		
		for (Pokemon p : chosenPokeList) {
			if(p.getCurrHP() > 0) {
				return false;
			}
		}
		return true;
	}
	
	
	
	
	/**
	 * Creates a table with appropriate values for effectiveness based on elemental types.
	 * e.g. Fire attacks Grass, gives a modifier of 2 (super-effective).
	 */
	private void generateEffectLookupTable() {
		
		double[][] table = new double[6][6];
		
		//Fire Attack
		table[PokeType.FIRE.getIndex()][PokeType.FIRE.getIndex()] = .5;
		table[PokeType.FIRE.getIndex()][PokeType.WATER.getIndex()] = .5;
		table[PokeType.FIRE.getIndex()][PokeType.GRASS.getIndex()] = 2;
		table[PokeType.FIRE.getIndex()][PokeType.ELECTRIC.getIndex()] = 1;
		table[PokeType.FIRE.getIndex()][PokeType.NORMAL.getIndex()] = 1;
		table[PokeType.FIRE.getIndex()][PokeType.POISON.getIndex()] = 1;
		
		//Water Attack
		table[PokeType.WATER.getIndex()][PokeType.FIRE.getIndex()] = 2;
		table[PokeType.WATER.getIndex()][PokeType.WATER.getIndex()] = .5;
		table[PokeType.WATER.getIndex()][PokeType.GRASS.getIndex()] = .5;
		table[PokeType.WATER.getIndex()][PokeType.ELECTRIC.getIndex()] = 1;
		table[PokeType.WATER.getIndex()][PokeType.NORMAL.getIndex()] = 1;
		table[PokeType.WATER.getIndex()][PokeType.POISON.getIndex()] = 1;
		
		//Grass Attack
		table[PokeType.GRASS.getIndex()][PokeType.FIRE.getIndex()] = .5;
		table[PokeType.GRASS.getIndex()][PokeType.WATER.getIndex()] = 2;
		table[PokeType.GRASS.getIndex()][PokeType.GRASS.getIndex()] = .5;
		table[PokeType.GRASS.getIndex()][PokeType.ELECTRIC.getIndex()] = 1;
		table[PokeType.GRASS.getIndex()][PokeType.NORMAL.getIndex()] = 1;
		table[PokeType.GRASS.getIndex()][PokeType.POISON.getIndex()] = .5;
		
		//Electric Attack
		table[PokeType.ELECTRIC.getIndex()][PokeType.FIRE.getIndex()] = 1;
		table[PokeType.ELECTRIC.getIndex()][PokeType.WATER.getIndex()] = 2;
		table[PokeType.ELECTRIC.getIndex()][PokeType.GRASS.getIndex()] = .5;
		table[PokeType.ELECTRIC.getIndex()][PokeType.ELECTRIC.getIndex()] = .5;
		table[PokeType.ELECTRIC.getIndex()][PokeType.NORMAL.getIndex()] = 1;
		table[PokeType.ELECTRIC.getIndex()][PokeType.POISON.getIndex()] = 1;
		
		//Normal Attack
		table[PokeType.NORMAL.getIndex()][PokeType.FIRE.getIndex()] = 1;
		table[PokeType.NORMAL.getIndex()][PokeType.WATER.getIndex()] = 1;
		table[PokeType.NORMAL.getIndex()][PokeType.GRASS.getIndex()] = 1;
		table[PokeType.NORMAL.getIndex()][PokeType.ELECTRIC.getIndex()] = 1;
		table[PokeType.NORMAL.getIndex()][PokeType.NORMAL.getIndex()] = 1;
		table[PokeType.NORMAL.getIndex()][PokeType.POISON.getIndex()] = 1;
		
		//Poison Attack
		table[PokeType.POISON.getIndex()][PokeType.FIRE.getIndex()] = 1;
		table[PokeType.POISON.getIndex()][PokeType.WATER.getIndex()] = 1;
		table[PokeType.POISON.getIndex()][PokeType.GRASS.getIndex()] = 2;
		table[PokeType.POISON.getIndex()][PokeType.ELECTRIC.getIndex()] = 1;
		table[PokeType.POISON.getIndex()][PokeType.NORMAL.getIndex()] = 1;
		table[PokeType.POISON.getIndex()][PokeType.POISON.getIndex()] = .5;
		
		
		this.effectLookupTable = table;
	}
	
	

}
