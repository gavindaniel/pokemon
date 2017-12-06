package pokemon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class represents the common, grass pokemon 'Caterpie'.
 * @author Abdullah Asaad
 *
 */
public class Flareon extends Pokemon {

	String standByPath,runAwayPath,capturePath;
	public Flareon() {
		super("Flareon", PokeType.FIRE, PokeType.FIRE, OccurrenceRate.UNCOMMON);
		initializeStats(65, 130, 60, 95, 110);
		initializeAttacks();
		initializeBattleAnimations();
		this.standByPath="file:images/battle/Flareon/flareonstandby.png";
		this.runAwayPath="file:images/battle/Flareon/flareonfireblast.png";
		this.capturePath="file:images/battle/Flareon/flareonstandby.png";
	}

	@Override
	public ArrayList<Attack> initializeAttacks() {
		
		ArrayList<Attack> listOfAttacks = this.getAttackList();

		listOfAttacks.add(new ActiveAttack("Ember", 40, PokeType.FIRE, 100));
		listOfAttacks.add(new ActiveAttack("Fire Fang",65,PokeType.FIRE, 95));
		listOfAttacks.add(new ActiveAttack("Fire Spin", 60, PokeType.FIRE, 85));
		listOfAttacks.add(new PassiveStatBuff("Recover", PokeType.NORMAL, 100, 1, 181, 1, 1, 1));

		return listOfAttacks;
	}

	@Override
	public void initializeBattleAnimations() {
		List<String> spritePaths = new ArrayList<>(5);
		spritePaths.add("file:images/battle/Flareon/flareonstandby.png");
		spritePaths.add("file:images/battle/Flareon/flareonquickattack.png");
		spritePaths.add("file:images/battle/Flareon/flareonfireblast.png");
		spritePaths.add("file:images/battle/Flareon/flareonattack.png");
		spritePaths.add("file:images/battle/Flareon/flareonstandby.png");
		spritePaths.add("file:images/battle/Flareon/flareonback.png");
				
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
		int[][] coordinates = {{0,0,58,95,555,240,100,100,59, 28}, //standBy
		{0,0,95,112,525,215,130,130, 94, 35},				 //escape/fireblast
		
		};
		return coordinates;
	}
	
}
