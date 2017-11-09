package pokemon;

import java.util.ArrayList;

/**
 * Class represents the common, fire pokemon 'Charmander'.
 * @author Abdullah Asaad
 *
 */
public class Charmander extends Pokemon {

	public Charmander() {
		super("Charmander", PokeType.FIRE, null, OccurrenceRate.COMMON);
		initializeStats(250, 171, 151, 167, 219);
		initializeAttacks();
	}

	@Override
	public ArrayList<Attack> initializeAttacks() {
		
		ArrayList<Attack> listOfAttacks = this.getAttackList();

		listOfAttacks.add(new ActiveAttack("Flamethrower", 90, PokeType.FIRE, 75));
		listOfAttacks.add(new ActiveAttack("Body Slam", 85, PokeType.NORMAL, 80));
		listOfAttacks.add(new ActiveAttack("Sand Attack", 60, PokeType.NORMAL, 85));
		listOfAttacks.add(new PassiveStatBuff("Defense Curl", PokeType.NORMAL, 100, 1, 0, 1, 1.5, 1));

		return listOfAttacks;
	}

}
