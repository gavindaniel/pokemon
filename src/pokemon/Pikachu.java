package pokemon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class represents the common, elcetric pokemon 'Pikachu'.
 * @author Abdullah Asaad
 *
 */
public class Pikachu extends Pokemon {

	public Pikachu() {
		super("Pikachu", PokeType.ELECTRIC, null, OccurrenceRate.COMMON);
		initializeStats(242, 177, 125, 167, 247);
		initializeAttacks();
		initializeBattleAnimations();
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
	public void initializeBattleAnimations() {
		List<String> spritePaths = new ArrayList<>(5);
		spritePaths.add("file:images/battle/Pikachu/pikachu-standby.png");
		spritePaths.add("file:images/battle/Pikachu/pikachu-thnderbolt.png");
		spritePaths.add("file:images/battle/Pikachu/pikachu-quickattack.png");
		spritePaths.add("file:images/battle/Pikachu/pikachu-swift.png");
		spritePaths.add("file:images/battle/Pikachu/pikachu-charge.png");
		spritePaths.add("file:images/battle/Pikachu/pikachu-back.png");
		
		String bgPath = "file:images/battle/battle-background.png";
		
		//coordinates columns: sx, sy, sw, sh, dx, dy, dw, dh, sx shift, # of frames. 									 
		 															 //Rows
		int[][] coordinates = {{0,0,60,60,570,240,100,100, 60, 33}, //standBy
				{0,0,60,60,575,230,100,100, 60, 33},				 //First Attack
				{0,0,60,60,575,230,100,100, 60, 33},				 //Second Attack
				{0,0,60,60,575,230,100,100, 60, 33},				 //Third Attack
				{0,0,60,60,575,230,100,100, 60, 33},				 //Fourth Attack
				{0,0,60,60,575,230,100,100, 60, 33},				 //Back Standby
		};
		
		PokeBattleAnimation pba = new PokeBattleAnimation(bgPath, spritePaths, coordinates);
		this.setBattleAnimation(pba);
	}
}
