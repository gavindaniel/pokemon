package pokemon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class represents the common, grass pokemon 'Bulbasaur'.
 * @author Abdullah Asaad
 *
 */
public class Bulbasaur extends Pokemon {

	String standByPath,runAwayPath,capturePath;
	public Bulbasaur() {
		super("Bulbasaur", PokeType.GRASS, PokeType.POISON, OccurrenceRate.COMMON);
		initializeStats(262, 165, 163, 197, 157);
		initializeAttacks();
		initializeBattleAnimations();
		this.standByPath="file:images/battle/Bulbasaur/bulbasaur-standby.png";
		this.runAwayPath="file:images/battle/Bulbasaur/bulbasaur-attack.png";
		this.capturePath="file:images/battle/Bulbasaur/bulbasaur-standby.png";
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

	@Override
	public void initializeBattleAnimations() {
		List<String> spritePaths = new ArrayList<>(5);
		spritePaths.add("file:images/battle/Bulbasaur/bulbasaur-standby.png");
		spritePaths.add("file:images/battle/Bulbasaur/bulbasaur-attack.png");
		spritePaths.add("file:images/battle/Bulbasaur/bulbasaur-attack.png");
		spritePaths.add("file:images/battle/Bulbasaur/bulbasaur-attack.png");
		spritePaths.add("file:images/battle/Bulbasaur/bulbasaur-standby.png");
		spritePaths.add("file:images/battle/Bulbasaur/bulbasaur-back.png");
				
		//coordinates columns: sx, sy, sw, sh, dx, dy, dw, dh, sx shift, # of frames. 									 
		 															 //Rows		
		int[][] coordinates = {{0,0,60,60,575,230,100,100, 60, 33}, //standBy
				{0,0,60,60,575,230,100,100, 60, 33},				 //First Attack
				{0,0,60,60,575,230,100,100, 60, 33},				 //Second Attack
				{0,0,60,60,575,230,100,100, 60, 33},				 //Third Attack
				{0,0,60,60,575,230,100,100, 60, 33},				 //Fourth Attack
				{0,0,60,60,575,230,100,100, 60, 33},				 //Back Standby
		};
		
		PokeBattleAnimation pba = new PokeBattleAnimation(battleBgPath, spritePaths, coordinates);
		this.setBattleAnimation(pba);
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

	@Override
	public int[][] getCoordinates() {
		//coordinates columns: sx, sy, sw, sh, dx, dy, dw, dh, sx shift, # of frames. 									 
		 //Rows		
		int[][] coordinates = {{0,0,46,49,565,240,100,100, 45, 40}, //standBy
		{0,0,65,65,565,240,110,100, 62, 63},				 //escape
		
		};
		return coordinates;
	}
	
}
