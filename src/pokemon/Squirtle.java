package pokemon;

import java.util.ArrayList;

public class Squirtle extends Pokemon {

	public Squirtle() {
		super("Squirtle", PokeType.WATER, null, OccurrenceRate.COMMON);
		initializeStats(260, 163, 195, 167, 153);
		initializeAttacks();
	}
	
	@Override
	public ArrayList<Attack> initializeAttacks() {

		ArrayList<Attack> listOfAttacks = this.getAttackList();

		listOfAttacks.add(new ActiveAttack("Hydro Pump", 110, PokeType.WATER, 60));
		listOfAttacks.add(new ActiveAttack("Bubble Beam", 65, PokeType.WATER, 90));
		listOfAttacks.add(new ActiveAttack("Slash", 70, PokeType.NORMAL, 75));
		listOfAttacks.add(new PassiveStatBuff("Harden", PokeType.NORMAL, 100, 1, 0, 1, 1.5, 1));

		return listOfAttacks;
	}
}