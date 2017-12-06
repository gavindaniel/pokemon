package pokemon;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represents the common, electric pokemon 'Electrode'.
 * @author Abdullah Asaad
 *
 */
public class Electrode extends Pokemon {

	String standByPath,runAwayPath,capturePath;
	public Electrode() {
		super("Electrode", PokeType.ELECTRIC, null, OccurrenceRate.COMMON);
		initializeStats(160, 95, 120, 130, 220);
		initializeAttacks();
		initializeBattleAnimations();
		this.standByPath="file:images/battle/Electrode/electrodestandby.png";
		this.runAwayPath="file:images/battle/Electrode/electrodeattack.png";
		this.capturePath="file:images/battle/Electrode/electrodestandby.png";
	}

	@Override
	public ArrayList<Attack> initializeAttacks() {
		
		ArrayList<Attack> listOfAttacks = this.getAttackList();

		listOfAttacks.add(new ActiveAttack("Thunderbolt", 90, PokeType.ELECTRIC, 75));
		listOfAttacks.add(new ActiveAttack("Swift", 60, PokeType.NORMAL, 80));
		listOfAttacks.add(new PassiveAttackBuff("Charge", PokeType.ELECTRIC, 100, 1, 2, 1.15));
		listOfAttacks.add(new PassiveStatBuff("Defense Curl", PokeType.NORMAL, 100, 1, 0, 1, 1.5, 1));

		return listOfAttacks;
	}

	@Override
	public void initializeBattleAnimations() {
		List<String> spritePaths = new ArrayList<>(5);
		spritePaths.add("file:images/battle/Electrode/electrodestandby.png");
		spritePaths.add("file:images/battle/Electrode/electrodeattack.png");
		spritePaths.add("file:images/battle/Electrode/electrodeattack.png");
		spritePaths.add("file:images/battle/Electrode/electrodeattack.png");
		spritePaths.add("file:images/battle/Electrode/electrodestandby.png");
		spritePaths.add("file:images/battle/Electrode/electrodeback.png");
				
		//coordinates columns: sx, sy, sw, sh, dx, dy, dw, dh, sx shift, # of frames. 									 
		 															 //Rows		
		int[][] coordinates = {{0,0,63,55,555,240,100,100, 64, 25}, //standBy
				{0,0,74,67,515,245,140,110, 78, 39},				 //First Attack
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
		return null;
	}
	
}
