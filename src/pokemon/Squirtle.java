package pokemon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Squirtle extends Pokemon {

	String standByPath,runAwayPath,capturePath;
	public Squirtle() {
		super("Squirtle", PokeType.WATER, null, OccurrenceRate.COMMON);
		initializeStats(260, 163, 195, 167, 153);
		initializeAttacks();
		initializeBattleAnimations();
		this.standByPath="file:images/battle/Squirtle/squirtle-standby.png";
		this.runAwayPath="file:images/battle/Squirtle/squirtle-bubblebeam.png";
		this.capturePath="file:images/battle/Squirtle/squirtle-standby.png";
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
		int[][] coordinates = {{0,0,53,55,565,235,100,100, 53, 28}, //standBy
		{0,0,76,67,555,230,115,115, 75, 65},				 //Escape/Bubblebeam
		};
		return coordinates;
	}
}