package pokemon;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represents the uncommon, fire pokemon 'Flareon'.
 * @author Abdullah Asaad
 *
 */
public class Flareon extends Pokemon {

	String standByPath,runAwayPath,capturePath;
	public Flareon() {
		super("Flareon", PokeType.FIRE, null, OccurrenceRate.UNCOMMON);
		initializeStats(230, 250, 130, 190, 150);
		initializeAttacks();
		initializeBattleAnimations();
	}

	@Override
	public ArrayList<Attack> initializeAttacks() {
		
		ArrayList<Attack> listOfAttacks = this.getAttackList();

		listOfAttacks.add(new ActiveAttack("Fire Blast", 110, PokeType.FIRE, 75));
		listOfAttacks.add(new ActiveAttack("Quick Attack", 40, PokeType.NORMAL, 100));
		listOfAttacks.add(new PassiveAttackBuff("Flame Charge", PokeType.FIRE, 100, 1, 2, 1.15));
		listOfAttacks.add(new ActiveAttack("Sand Attack", 60, PokeType.NORMAL, 85));

		return listOfAttacks;
	}

	@Override
	public void initializeBattleAnimations() {
		List<String> spritePaths = new ArrayList<>(5);
		spritePaths.add("file:images/battle/Flareon/flareonstandby.png");
		spritePaths.add("file:images/battle/Flareon/flareonfireblast.png");
		spritePaths.add("file:images/battle/Flareon/flareonquickattack.png");
		spritePaths.add("file:images/battle/Flareon/flareonstandby.png");
		spritePaths.add("file:images/battle/Flareon/flareonquickattack.png");
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
		
		return null;
	}
	
}
