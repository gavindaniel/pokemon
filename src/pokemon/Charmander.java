package pokemon;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represents the common, fire pokemon 'Charmander'.
 * @author Abdullah Asaad
 *
 */
public class Charmander extends Pokemon {

	String standByPath,runAwayPath,capturePath;
	public Charmander() {
		super("Charmander", PokeType.FIRE, null, OccurrenceRate.COMMON);
		initializeStats(150, 100, 90, 110, 120);
		initializeAttacks();
		initializeBattleAnimations();
		this.standByPath="file:images/battle/Charmander/charmander-standby.png";
		this.runAwayPath="file:images/battle/Charmander/charmander-flamethrower.png";
		this.capturePath="file:images/battle/Charmander/charmander-standby.png";
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

	@Override
	public void initializeBattleAnimations() {
		List<String> spritePaths = new ArrayList<>(5);
		spritePaths.add("file:images/battle/Charmander/charmander-standby.png");
		spritePaths.add("file:images/battle/Charmander/charmander-flamethrower.png");
		spritePaths.add("file:images/battle/Charmander/charmander-tackle.png");
		spritePaths.add("file:images/battle/Charmander/charmander-tackle.png");
		spritePaths.add("file:images/battle/Charmander/charmander-standby.png");
		spritePaths.add("file:images/battle/Charmander/charmander-back.png");
		
		//coordinates columns: sx, sy, sw, sh, dx, dy, dw, dh, sx shift, # of frames. 									 
																	 //Rows
		int[][] coordinates = {{0,0,50,60,555,250,90,90, 48, 33}, //standBy
				{0,10,60,70,545,250,100,100, 59, 59},				 //First Attack
				{5,0,70,70,545,245,100,100, 70, 56},				 //Second Attack
				{5,0,70,70,545,245,100,100, 70, 56},				 //Third Attack
				{0,0,50,60,555,250,90,90, 48, 60},				 //Fourth Attack
				{0,0,63,60,50,400,150,150, 64, 38}, //Back Standby
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
		return null;
	}
}
