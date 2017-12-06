package pokemon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class represents the common, grass pokemon 'Caterpie'.
 * @author Abdullah Asaad
 *
 */
public class Vaporeon extends Pokemon {

	String standByPath,runAwayPath,capturePath;
	public Vaporeon() {
		super("Vaporeon", PokeType.WATER, PokeType.WATER, OccurrenceRate.UNCOMMON);
		initializeStats(50, 52, 48, 65, 50);
		initializeAttacks();
		initializeBattleAnimations();
		this.standByPath="file:images/battle/Vaporeon/vaporeonstand-by.png";
		this.runAwayPath="file:images/battle/Vaporeon/vaporeonwatergun.png";
		this.capturePath="file:images/battle/Vaporeon/vaporeonstand-by.png";
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
		int[][] coordinates = {{0,0,100,72,565,240,100,100,102, 33}, //standBy
		{0,0,98,101,560,245,110,110, 105, 40},				 //escape/hydropump
		
		};
		return coordinates;
	}
	
}
