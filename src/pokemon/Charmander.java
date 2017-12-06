package pokemon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class represents the common, fire pokemon 'Charmander'.
 * @author Abdullah Asaad
 *
 */
public class Charmander extends Pokemon {

	String standByPath,runAwayPath,capturePath;
	public Charmander() {
		super("Charmander", PokeType.FIRE, null, OccurrenceRate.COMMON);
		initializeStats(250, 171, 151, 167, 219);
		initializeAttacks();
		initializeBattleAnimations();
		this.standByPath="file:images/battle/Charmander/charmander-standby.png";
		this.runAwayPath="file:images/battle/Charmander/charmander-flamethrower.png";
		this.capturePath="file:images/battle/Charmander/charmander-standby.png";
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
		int[][] coordinates = {{0,0,47,60,555,255,90,90, 48, 33}, //standBy
		{0,0,60,80,545,250,100,100, 59, 59},				 //Escape
		};
		return coordinates;
	}
}
