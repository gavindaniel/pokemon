package pokemon;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represents the rare, electric pokemon 'Zapdos'.
 * @author Abdullah Asaad
 *
 */
public class Zapdos extends Pokemon {

	String standByPath,runAwayPath,capturePath;
	public Zapdos() {
		super("Zapdos", PokeType.ELECTRIC, null, OccurrenceRate.RARE);
		initializeStats(360, 250, 240, 300, 280);
		initializeAttacks();
		initializeBattleAnimations();
	}

	@Override
	public ArrayList<Attack> initializeAttacks() {
		
		ArrayList<Attack> listOfAttacks = this.getAttackList();

		listOfAttacks.add(new ActiveAttack("Thunder", 125, PokeType.ELECTRIC, 80));
		listOfAttacks.add(new ActiveAttack("Ancient Power", 80, PokeType.NORMAL, 85));
		listOfAttacks.add(new ActiveAttack("Hyper Beam", 150, PokeType.NORMAL, 75));
		listOfAttacks.add(new PassiveStatBuff("Magnetic Flux", PokeType.NORMAL, 100, 1, 0, 1.3, 1.3, 1.3));

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
		
		return null;
	}
	
}
