package pokemon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Squirtle extends Pokemon {

	public Squirtle() {
		super("Squirtle", PokeType.WATER, null, OccurrenceRate.COMMON);
		initializeStats(260, 163, 195, 167, 153);
		initializeAttacks();
		initializeBattleAnimations();
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
	
	@Override
	public void initializeBattleAnimations() {
		List<String> spritePaths = new ArrayList<>(5);
		spritePaths.add("file:images/battle/Squirtle/squirtle-standby.png");
		spritePaths.add("file:images/battle/Squirtle/squirtle-bubblebeam.png");
		spritePaths.add("file:images/battle/Squirtle/squirtle-bubblebeam.png");
		spritePaths.add("file:images/battle/Squirtle/squirtle-tackle.png");
		spritePaths.add("file:images/battle/Squirtle/squirtle-standby.png");
		spritePaths.add("file:images/battle/Squirtle/squirtle-back.png");
		
		String bgPath = "file:images/battle/battle-background.png";
		
		//coordinates columns: sx, sy, sw, sh, dx, dy, dw, dh, sx shift, # of frames. 									 
		 															 //Rows
		int[][] coordinates = {{0,0,60,60,575,230,100,100, 60, 33}, //standBy
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