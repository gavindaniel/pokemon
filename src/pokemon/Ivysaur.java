package pokemon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class represents the common, grass pokemon 'Caterpie'.
 * @author Abdullah Asaad
 *
 */
public class Ivysaur extends Pokemon {

	String standByPath,runAwayPath,capturePath;
	public Ivysaur() {
		super("Ivysaur", PokeType.GRASS, PokeType.POISON, OccurrenceRate.UNCOMMON);
		initializeStats(60, 62, 63, 80, 80);
		initializeAttacks();
		initializeBattleAnimations();
		this.standByPath="file:images/battle/Ivysaur/ivysaurstandby.png";
		this.runAwayPath="file:images/battle/Ivysaur/ivysaursolarbeam.png";
		this.capturePath="file:images/battle/Ivysaur/ivysaurstandby.png";
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
		int[][] coordinates = {{0,0,83,65,555,240,100,100,84, 49}, //standBy
		{0,0,95,73,555,240,100,100, 97, 35},				 //escape/solarbeam
		
		};
		return coordinates;
	}
	
}
