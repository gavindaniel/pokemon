package pokemon;

import java.util.ArrayList;

/**
 * Class represents the common, grass pokemon 'Bulbasaur'.
 * @author Abdullah Asaad
 *
 */
public class Bulbasaur extends Pokemon {

	public Bulbasaur() {
		super("Bulbasaur", PokeType.GRASS, PokeType.POISON, OccurrenceRate.COMMON);
		initializeStats(262, 165, 163, 197, 157);
	}

	@Override
	public ArrayList<Attack> initializeAttacks() {
		
		ArrayList<Attack> listOfAttacks = this.getAttackList();

		listOfAttacks.add(new ActiveAttack("Razor Leaf", 55, PokeType.GRASS, 95));
		listOfAttacks.add(new ActiveAttack("Body Slam", 85, PokeType.NORMAL, 80));
		listOfAttacks.add(new ActiveAttack("Leech Life", 40, PokeType.GRASS, 100));
		listOfAttacks.add(new PassiveStatBuff("Recover", PokeType.NORMAL, 100, 1, 181, 1, 1, 1));

		return listOfAttacks;
	}

	
	
}
