package pokemon;

import java.util.ArrayList;
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
		initializeStats(160, 100, 100, 110, 105);
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
		listOfAttacks.add(new ActiveAttack("Leech Life", 50, PokeType.GRASS, 90));
		listOfAttacks.add(new PassiveStatBuff("Recover", PokeType.NORMAL, 100, 1, getMaxHP()/3, 1, 1, 1));

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
		int[][] coordinates = {{0,0,46,50,550,255,85,85, 45, 40}, //standBy
				{0,0,65,65,548,243,100,100, 62, 66},				 //First Attack
				{0,0,65,65,548,243,100,100, 62, 66},				 //Second Attack
				{0,0,65,65,548,243,100,100, 62, 66},				 //Third Attack
				{0,0,46,50,550,255,85,85, 45, 40},				 //Fourth Attack
				{0,0,52,52,50,390,150,150, 52, 45},				 //Back Standby
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

	
}
