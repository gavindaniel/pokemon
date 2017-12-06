package pokemon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class represents the common, elcetric pokemon 'Pikachu'.
 * @author Abdullah Asaad
 *
 */
public class Pikachu extends Pokemon {

	String standByPath,runAwayPath,capturePath;
	public Pikachu() {
		super("Pikachu", PokeType.ELECTRIC, null, OccurrenceRate.COMMON);
		initializeStats(242, 177, 125, 167, 247);
		initializeAttacks();
		initializeBattleAnimations();
		this.standByPath="file:images/battle/Pikachu2/pikachu-standby.png";
		this.runAwayPath="file:images/battle/Pikachu2/pikachu-quickattack.png";
		this.capturePath="file:images/battle/Pikachu2/pikachu-standby.png";
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
		int[][] coordinates = {{0,0,60,60,570,240,100,100, 60, 33}, //standBy
				{10,10,80,105,530,245,120,120, 96, 60},				 //escape/quickattack
		};
		return coordinates;
	}
}
