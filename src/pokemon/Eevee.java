package pokemon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class represents the common, grass pokemon 'Caterpie'.
 * @author Abdullah Asaad
 *
 */
public class Eevee extends Pokemon {

	String standByPath,runAwayPath,capturePath;
	public Eevee() {
		super("Eevee", PokeType.NORMAL, PokeType.NORMAL, OccurrenceRate.COMMON);
		initializeStats(55, 55, 50, 45, 65);
		initializeAttacks();
		initializeBattleAnimations();
		this.standByPath="file:images/battle/Eevee/eeveestandby.png";
		this.runAwayPath="file:images/battle/Eevee/eeveeember.png";
		this.capturePath="file:images/battle/Eevee/eeveestandby.png";
	}

	@Override
	public ArrayList<Attack> initializeAttacks() {
		
		ArrayList<Attack> listOfAttacks = this.getAttackList();

		listOfAttacks.add(new ActiveAttack("Tackle", 40, PokeType.NORMAL, 100));
		listOfAttacks.add(new ActiveAttack("Quick Attack",40,PokeType.NORMAL, 100));
		listOfAttacks.add(new ActiveAttack("Swift", 60, PokeType.NORMAL, 100));
		listOfAttacks.add(new PassiveStatBuff("Recover", PokeType.NORMAL, 100, 1, 181, 1, 1, 1));

		return listOfAttacks;
	}

	@Override
	public void initializeBattleAnimations() {
		List<String> spritePaths = new ArrayList<>(5);
		spritePaths.add("file:images/battle/Eevee/eeveestandby.png");
		spritePaths.add("file:images/battle/Eevee/eeveeember.png");
		spritePaths.add("file:images/battle/Eevee/eeveeember.png");
		spritePaths.add("file:images/battle/Eevee/eeveeattack.png");
		spritePaths.add("file:images/battle/Eevee/eeveestandby.png");
		spritePaths.add("file:images/battle/Eevee/eeveeback.png");
				
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
		int[][] coordinates = {{0,0,63,55,555,240,100,100, 64, 25}, //standBy
		{0,0,74,67,515,245,140,110, 78, 39},				 //escape
		
		};
		return coordinates;
	}
	
}
