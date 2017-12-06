package pokemon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class represents the common, grass pokemon 'Caterpie'.
 * @author Abdullah Asaad
 *
 */
public class Psyduck extends Pokemon {

	String standByPath,runAwayPath,capturePath;
	public Psyduck() {
		super("Psyduck", PokeType.WATER, PokeType.WATER, OccurrenceRate.UNCOMMON);
		initializeStats(50, 52, 48, 65, 50);
		initializeAttacks();
		initializeBattleAnimations();
		this.standByPath="file:images/battle/Psyduck/psyduckstandby.png";
		this.runAwayPath="file:images/battle/Psyduck/psyduckpsychic.png";
		this.capturePath="file:images/battle/Psyduck/psyduckstandby.png";
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
		int[][] coordinates = {{0,0,52,53,565,240,100,100,51, 48}, //standBy
		{0,0,65,65,560,235,110,110, 64, 40},				 //escape/psychic
		
		};
		return coordinates;
	}
	
}
