package pokemon;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represents the uncommon, grass pokemon 'Ivysaur'.
 * @author Abdullah Asaad
 *
 */
public class Ivysaur extends Pokemon {

	String standByPath,runAwayPath,capturePath;
	public Ivysaur() {
		super("Ivysaur", PokeType.GRASS, null, OccurrenceRate.UNCOMMON);
		initializeStats(225, 150, 150, 160, 130);
		initializeAttacks();
		initializeBattleAnimations();
	}

	@Override
	public ArrayList<Attack> initializeAttacks() {
		
		ArrayList<Attack> listOfAttacks = this.getAttackList();

		listOfAttacks.add(new ActiveAttack("Razor Leaf", 55, PokeType.GRASS, 95));
		listOfAttacks.add(new ActiveAttack("Solar Beam", 120, PokeType.GRASS, 100));
		listOfAttacks.add(new PassiveStatBuff("Harden", PokeType.NORMAL, 100, 1, 0, 1, 1.5, 1));
		listOfAttacks.add(new ActiveAttack("Bite", 70, PokeType.NORMAL, 85));

		return listOfAttacks;
	}

	@Override
	public void initializeBattleAnimations() {
		List<String> spritePaths = new ArrayList<>(5);
		spritePaths.add("file:images/battle/Ivysaur/ivysaurstandby.png");
		spritePaths.add("file:images/battle/Ivysaur/ivysaursolarbeam.png");
		spritePaths.add("file:images/battle/Ivysaur/ivysaursolarbeam.png");
		spritePaths.add("file:images/battle/Ivysaur/ivysaurstandby.png");
		spritePaths.add("file:images/battle/Ivysaur/ivysaursandattack.png");
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
		
		return null;
	}
	
}
