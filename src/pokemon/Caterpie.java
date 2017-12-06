package pokemon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class represents the common, grass pokemon 'Caterpie'.
 * @author Abdullah Asaad
 *
 */
public class Caterpie extends Pokemon {

	String standByPath,runAwayPath,capturePath;
	public Caterpie() {
		super("Caterpie", PokeType.GRASS, PokeType.NORMAL, OccurrenceRate.COMMON);
		initializeStats(45, 30, 35, 20, 20);
		initializeAttacks();
		initializeBattleAnimations();
		this.standByPath="file:images/battle/Caterpie/caterpiestandby.png";
		this.runAwayPath="file:images/battle/Caterpie/caterpieattack.png";
		this.capturePath="file:images/battle/Caterpie/caterpiestandby.png";
	}

	@Override
	public ArrayList<Attack> initializeAttacks() {
		
		ArrayList<Attack> listOfAttacks = this.getAttackList();

		listOfAttacks.add(new ActiveAttack("Tackle", 40, PokeType.NORMAL, 100));
		listOfAttacks.add(new PassiveStatBuff("String Shot",PokeType.GRASS, 100, 1, 181, 1, 1, 1));
		listOfAttacks.add(new ActiveAttack("Bug Bite", 60, PokeType.GRASS, 95));
		listOfAttacks.add(new PassiveStatBuff("Recover", PokeType.NORMAL, 100, 1, 181, 1, 1, 1));

		return listOfAttacks;
	}

	@Override
	public void initializeBattleAnimations() {
		List<String> spritePaths = new ArrayList<>(5);
		spritePaths.add("file:images/battle/Caterpie/caterpiestandby.png");
		spritePaths.add("file:images/battle/Caterpie/caterpieattack.png");
		spritePaths.add("file:images/battle/Caterpie/caterpieattack.png");
		spritePaths.add("file:images/battle/Caterpie/caterpieattack.png");
		spritePaths.add("file:images/battle/Caterpie/caterpiestandby.png");
		spritePaths.add("file:images/battle/Caterpie/caterpieback.png");
				
		//coordinates columns: sx, sy, sw, sh, dx, dy, dw, dh, sx shift, # of frames. 									 
		 															 //Rows		
		int[][] coordinates = {{0,0,45,45,535,240,100,100, 46, 46}, //standBy
				{0,0,70,55,535,240,110,100, 67, 53},				 //First Attack
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
		int[][] coordinates = {{0,0,45,45,555,240,100,100, 46, 46}, //standBy
		{0,0,70,55,535,240,110,100, 67, 53},				 //escape
		
		};
		return coordinates;
	}
	
}
