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
		int[][] coordinates = {{2, 0,70,58,550,255,90,80, 72, 45}, //standBy
				{2, 0,70,58,550,255,90,80, 80, 46},				 //First Attack
				{2, 0,70,58,550,255,90,80, 80, 46},				 //Second Attack
				{2, 0,70,58,550,255,90,80, 72, 46},				 //Third Attack
				{2, 0,70,58,550,255,90,80, 72, 45},				 //Fourth Attack
				{2,0,70,58,110,430,120,100, 73, 45},				 //Back Standby
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
		int[][] coordinates = {{0,0,72,59,555,240,100,100,72, 45}, //standBy
		{0,0,72,59,555,245,100,100, 72, 44},				 //escape/electrode attack
		
		};
		return coordinates;
	}

	
}
