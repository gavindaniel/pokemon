package pokemon;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represents the rare, fire pokemon 'Dragonite'.
 * @author Abdullah Asaad
 *
 */
public class Dragonite extends Pokemon {

	String standByPath,runAwayPath,capturePath;
	public Dragonite() {
		super("Dragonite", PokeType.FIRE, PokeType.NORMAL, OccurrenceRate.RARE);
		initializeStats(330, 330, 265, 260, 220);
		initializeAttacks();
		initializeBattleAnimations();

	}
	
	@Override
	public ArrayList<Attack> initializeAttacks() {

		ArrayList<Attack> listOfAttacks = this.getAttackList();

		listOfAttacks.add(new ActiveAttack("Fire Blast", 110, PokeType.FIRE, 80));
		listOfAttacks.add(new ActiveAttack("Body Slam", 90, PokeType.NORMAL, 90));
		listOfAttacks.add(new ActiveAttack("Thunder Punch", 75, PokeType.ELECTRIC, 100));
		listOfAttacks.add(new PassiveStatBuff("Defense Curl", PokeType.NORMAL, 100, 1, 0, 1.5, 1, 1));

		return listOfAttacks;
	}
	
	@Override
	public void initializeBattleAnimations() {
		List<String> spritePaths = new ArrayList<>(5);
		spritePaths.add("file:images/battle/Dragonite/dragonitestandby.png");
		spritePaths.add("file:images/battle/Dragonite/dragonitefireblast.png");
		spritePaths.add("file:images/battle/Dragonite/dragonitefireblast.png");
		spritePaths.add("file:images/battle/Dragonite/dragonitefireblast.png");
		spritePaths.add("file:images/battle/Dragonite/dragonitestandby.png");
		spritePaths.add("file:images/battle/Dragonite/dragoniteback.png");
		
		//coordinates columns: sx, sy, sw, sh, dx, dy, dw, dh, sx shift, # of frames. 									 
		 															 //Rows
		int[][] coordinates = {{0,0, 80,100,520,195,135,175, 85, 44}, //standBy
				{0,0, 90,110,520,195,135,175, 104, 41},				 //First Attack
				{0,0, 90,110,520,195,135,175, 104, 41},				 //Second Attack
				{0,0, 90,110,520,195,135,175, 104, 41},				 //Third Attack
				{0,0, 80,100,520,195,135,175, 85, 44},				 //Fourth Attack
				{0,0,110,98,50,360,200,180, 110, 38},				 //Back Standby
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

}
