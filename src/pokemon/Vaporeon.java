package pokemon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class represents the common, grass pokemon 'Caterpie'.
 * @author Abdullah Asaad
 *
 */
public class Vaporeon extends Pokemon {

	String standByPath,runAwayPath,capturePath;
	public Vaporeon() {
		super("Vaporeon", PokeType.WATER, PokeType.WATER, OccurrenceRate.UNCOMMON);
		initializeStats(50, 52, 48, 65, 50);
		initializeAttacks();
		initializeBattleAnimations();
		this.standByPath="file:images/battle/Vaporeon/vaporeonstand-by.png";
		this.runAwayPath="file:images/battle/Vaporeon/vaporeonwatergun.png";
		this.capturePath="file:images/battle/Vaporeon/vaporeonstand-by.png";
	}

	@Override
	public ArrayList<Attack> initializeAttacks() {
		
		ArrayList<Attack> listOfAttacks = this.getAttackList();

		listOfAttacks.add(new ActiveAttack("Water Gun", 40, PokeType.WATER, 100));
		listOfAttacks.add(new ActiveAttack("Water Pulse",65,PokeType.WATER, 95));
		listOfAttacks.add(new ActiveAttack("Aqua Tail", 80, PokeType.WATER, 100));
		listOfAttacks.add(new PassiveStatBuff("Recover", PokeType.NORMAL, 100, 1, 181, 1, 1, 1));

		return listOfAttacks;
	}

	@Override
	public void initializeBattleAnimations() {
		List<String> spritePaths = new ArrayList<>(5);
		spritePaths.add("file:images/battle/Vaporeon/vaporeonstand-by.png");
		spritePaths.add("file:images/battle/Vaporeon/vaporeonwatergun.png");
		spritePaths.add("file:images/battle/Vaporeon/Vaporeonfireblast.png");
		spritePaths.add("file:images/battle/Vaporeon/Vaporeonattack.png");
		spritePaths.add("file:images/battle/Vaporeon/Vaporeonstandby.png");
		spritePaths.add("file:images/battle/Vaporeon/Vaporeonback.png");
				
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
		int[][] coordinates = {{0,0,100,72,565,240,100,100,102, 33}, //standBy
		{0,0,98,101,560,245,110,110, 105, 40},				 //escape/hydropump
		
		};
		return coordinates;
	}
	
}
