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
		
		return null;
	}

	@Override
	public void initializeBattleAnimations() {
		
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
		{0,0,70,55,535,220,115,115, 67, 53},				 //escape/caterpieattack
		
		};
		return coordinates;
	}
	
}
