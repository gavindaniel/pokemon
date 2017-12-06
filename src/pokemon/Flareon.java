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
		int[][] coordinates = {{0,0,58,95,555,240,100,100,59, 28}, //standBy
		{0,0,95,112,525,215,130,130, 94, 35},				 //escape/fireblast
		
		};
		return coordinates;
	}
	
}
