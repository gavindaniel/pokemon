package pokemon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class represents the common, grass pokemon 'Bulbasaur'.
 * @author Abdullah Asaad
 *
 */
public class Bulbasaur extends Pokemon {

	String standByPath,runAwayPath,capturePath;
	public Bulbasaur() {
		super("Bulbasaur", PokeType.GRASS, PokeType.POISON, OccurrenceRate.COMMON);
		initializeStats(262, 165, 163, 197, 157);
		initializeAttacks();
		initializeBattleAnimations();
		this.standByPath="file:images/battle/Bulbasaur/bulbasaur-standby.png";
		this.runAwayPath="file:images/battle/Bulbasaur/bulbasaur-attack.png";
		this.capturePath="file:images/battle/Bulbasaur/bulbasaur-standby.png";
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
		int[][] coordinates = {{0,0,46,49,565,240,100,100, 45, 40}, //standBy
		{0,0,65,65,555,230,120,120, 62, 63},				 //escape
		
		};
		return coordinates;
	}
	
}
