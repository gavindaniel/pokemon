package pokemon;

import java.util.ArrayList;

/**
 * Class represents the common, elcetric pokemon 'Pikachu'.
 * @author Abdullah Asaad
 *
 */
public class Pikachu extends Pokemon {

	String standByPath,runAwayPath,capturePath;
	public Pikachu() {
		super("Pikachu", PokeType.ELECTRIC, null, OccurrenceRate.COMMON);
		initializeStats(242, 177, 125, 167, 247);
		initializeAttacks();
		this.standByPath="file:images/battle/Pikachu/pikachu-standby.png";
		this.runAwayPath="file:images/battle/Pikachu/pikachu-quickattack.png";
		this.capturePath="file:images/battle/Pikachu/pikachu-standby.png";
	}

	@Override
	public ArrayList<Attack> initializeAttacks() {

		ArrayList<Attack> listOfAttacks = this.getAttackList();

		listOfAttacks.add(new ActiveAttack("Thunderbolt", 90, PokeType.ELECTRIC, 75));
		listOfAttacks.add(new ActiveAttack("Swift", 60, PokeType.NORMAL, 80));
		listOfAttacks.add(new ActiveAttack("Quick Attack", 40, PokeType.NORMAL, 100));
		listOfAttacks.add(new PassiveAttackBuff("Charge", PokeType.ELECTRIC, 100, 1, 2, 1.15));

		return listOfAttacks;
	}
	
	@Override
	public String getStandByPath() {
		// TODO Auto-generated method stub
		return standByPath;
	}

	@Override
	public String getRunAwayPath() {
		// TODO Auto-generated method stub
		return runAwayPath;
	}

	@Override
	public String getCapturePath() {
		// TODO Auto-generated method stub
		return capturePath;
	}
}
