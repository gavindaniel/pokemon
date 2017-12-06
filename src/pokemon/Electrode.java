package pokemon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class represents the common, grass pokemon 'Caterpie'.
 * @author Abdullah Asaad
 *
 */
public class Electrode extends Pokemon {

	String standByPath,runAwayPath,capturePath;
	public Electrode() {
		super("Electrode", PokeType.ELECTRIC, PokeType.NORMAL, OccurrenceRate.COMMON);
		initializeStats(60, 50, 70, 80, 80);
		initializeAttacks();
		initializeBattleAnimations();
		this.standByPath="file:images/battle/Electrode/electrodestandby.png";
		this.runAwayPath="file:images/battle/Electrode/electrodeattack.png";
		this.capturePath="file:images/battle/Electrode/electrodestandby.png";
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
		int[][] coordinates = {{0,0,72,59,555,240,100,100,72, 45}, //standBy
		{0,0,72,59,555,245,100,100, 72, 44},				 //escape/electrode attack
		
		};
		return coordinates;
	}
	
}
