package pokemon;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represents the uncommon, electric pokemon 'Luxio'.
=======
import java.util.Arrays;
import java.util.List;

/**
 * Class represents the common, grass pokemon 'Caterpie'.
>>>>>>> ian
 * @author Abdullah Asaad
 *
 */
public class Luxio extends Pokemon {

	String standByPath,runAwayPath,capturePath;
	public Luxio() {
		super("Luxio", PokeType.ELECTRIC, PokeType.NORMAL, OccurrenceRate.UNCOMMON);
		initializeStats(220, 180, 120, 130, 130);
		initializeAttacks();
		initializeBattleAnimations();
		this.standByPath="file:images/battle/Luxio/luxiostandby.png";
		this.runAwayPath="file:images/battle/Luxio/luxiothunder.png";
		this.capturePath="file:images/battle/Luxio/luxiostandby.png";
	}

	@Override
	public ArrayList<Attack> initializeAttacks() {
		
		ArrayList<Attack> listOfAttacks = this.getAttackList();

		listOfAttacks.add(new ActiveAttack("Spark", 75, PokeType.ELECTRIC, 95));
		listOfAttacks.add(new ActiveAttack("Crunch", 85, PokeType.NORMAL, 100));
		listOfAttacks.add(new PassiveStatBuff("Double Team", PokeType.NORMAL, 100, 1, 0, 1, 1.75, 1));
		listOfAttacks.add(new ActiveAttack("Swift", 60, PokeType.NORMAL, 100));

		return listOfAttacks;
	}

	@Override
	public void initializeBattleAnimations() {
		List<String> spritePaths = new ArrayList<>(5);
		spritePaths.add("file:images/battle/Luxio/luxiostandby.png");
		spritePaths.add("file:images/battle/Luxio/luxiothunder.png");
		spritePaths.add("file:images/battle/Luxio/luxiosandattack.png");
		spritePaths.add("file:images/battle/Luxio/luxiostandby.png");
		spritePaths.add("file:images/battle/Luxio/luxiosandattack.png");
		spritePaths.add("file:images/battle/Luxio/luxioback.png");
				
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
