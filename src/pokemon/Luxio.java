package pokemon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class represents the common, grass pokemon 'Caterpie'.
 * @author Abdullah Asaad
 *
 */
public class Luxio extends Pokemon {

	String standByPath,runAwayPath,capturePath;
	public Luxio() {
		super("Luxio", PokeType.ELECTRIC, PokeType.POISON, OccurrenceRate.UNCOMMON);
		initializeStats(60, 85, 49, 60, 49);
		initializeAttacks();
		initializeBattleAnimations();
		this.standByPath="file:images/battle/Luxio/luxiostandby.png";
		this.runAwayPath="file:images/battle/Luxio/luxiothunder.png";
		this.capturePath="file:images/battle/Luxio/luxiostandby.png";
	}

	@Override
	public ArrayList<Attack> initializeAttacks() {
		
		ArrayList<Attack> listOfAttacks = this.getAttackList();

		listOfAttacks.add(new ActiveAttack("Spark", 40, PokeType.ELECTRIC, 100));
		listOfAttacks.add(new ActiveAttack("Thunder Fang",65,PokeType.ELECTRIC, 95));
		listOfAttacks.add(new ActiveAttack("Discharge", 80, PokeType.ELECTRIC, 100));
		listOfAttacks.add(new PassiveStatBuff("Recover", PokeType.NORMAL, 100, 1, 181, 1, 1, 1));

		return listOfAttacks;
	}

	@Override
	public void initializeBattleAnimations() {
		List<String> spritePaths = new ArrayList<>(5);
		spritePaths.add("file:images/battle/Luxio/luxiostandby.png");
		spritePaths.add("file:images/battle/Luxio/luxiothunder.png");
		spritePaths.add("file:images/battle/Luxio/Luxiofireblast.png");
		spritePaths.add("file:images/battle/Luxio/Luxioattack.png");
		spritePaths.add("file:images/battle/Luxio/Luxiostandby.png");
		spritePaths.add("file:images/battle/Luxio/Luxioback.png");
				
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
		//coordinates columns: sx, sy, sw, sh, dx, dy, dw, dh, sx shift, # of frames. 									 
		 //Rows		
		int[][] coordinates = {{0,0,70,76,565,240,100,100,70, 49}, //standBy
		{0,0,70,90,555,240,100,100, 71, 35},				 //escape/thunder
		
		};
		return coordinates;
	}
	
}
