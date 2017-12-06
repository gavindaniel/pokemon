package pokemon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class represents the common, grass pokemon 'Caterpie'.
 * @author Abdullah Asaad
 *
 */
public class Luxio extends Pokemon {

	String standByPath,runAwayPath,capturePath;
	public Luxio() {
		super("Luxio", PokeType.ELECTRIC, PokeType.POISON, OccurrenceRate.UNCOMMON);
		initializeStats(60, 85, 49, 60, 49);
		initializeAttacks();
		initializeBattleAnimations();
		this.standByPath="file:images/battle/Luxio/luxiostandby.png";
		this.runAwayPath="file:images/battle/Luxio/luxiothunder.png";
		this.capturePath="file:images/battle/Luxio/luxiostandby.png";
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
		int[][] coordinates = {{0,0,70,76,565,240,100,100,70, 49}, //standBy
		{0,0,70,90,555,240,100,100, 71, 35},				 //escape/thunder
		
		};
		return coordinates;
	}
	
}
