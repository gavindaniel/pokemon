package pokemon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class represents the common, grass pokemon 'Caterpie'.
 * @author Abdullah Asaad
 *
 */
public class Ivysaur extends Pokemon {

	String standByPath,runAwayPath,capturePath;
	public Ivysaur() {
		super("Ivysaur", PokeType.GRASS, PokeType.POISON, OccurrenceRate.UNCOMMON);
		initializeStats(60, 62, 63, 80, 80);
		initializeAttacks();
		initializeBattleAnimations();
		this.standByPath="file:images/battle/Ivysaur/ivysaurstandby.png";
		this.runAwayPath="file:images/battle/Ivysaur/ivysaursolarbeam.png";
		this.capturePath="file:images/battle/Ivysaur/ivysaurstandby.png";
	}

	@Override
	public ArrayList<Attack> initializeAttacks() {
		
		ArrayList<Attack> listOfAttacks = this.getAttackList();

		listOfAttacks.add(new ActiveAttack("Vine Whip", 45, PokeType.GRASS, 100));
		listOfAttacks.add(new ActiveAttack("Razor Leaf",55,PokeType.GRASS, 95));
		listOfAttacks.add(new ActiveAttack("Solar Beam", 120, PokeType.GRASS, 100));
		listOfAttacks.add(new PassiveStatBuff("Recover", PokeType.NORMAL, 100, 1, 181, 1, 1, 1));

		return listOfAttacks;
	}

	@Override
	public void initializeBattleAnimations() {
		List<String> spritePaths = new ArrayList<>(5);
		spritePaths.add("file:images/battle/Ivysaur/ivysaurstandby.png");
		spritePaths.add("file:images/battle/Ivysaur/ivysaursolarbeam.png");
		spritePaths.add("file:images/battle/Ivysaur/ivysaurfireblast.png");
		spritePaths.add("file:images/battle/Ivysaur/ivysaurattack.png");
		spritePaths.add("file:images/battle/Ivysaur/ivysaurstandby.png");
		spritePaths.add("file:images/battle/Ivysaur/ivysaurback.png");
				
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
		int[][] coordinates = {{0,0,83,65,555,240,100,100,84, 49}, //standBy
		{0,0,95,73,555,240,100,100, 97, 35},				 //escape/solarbeam
		
		};
		return coordinates;
	}
	
}
