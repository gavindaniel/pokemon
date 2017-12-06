package pokemon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class represents the common, grass pokemon 'Caterpie'.
 * @author Abdullah Asaad
 *
 */
public class Eevee extends Pokemon {

	String standByPath,runAwayPath,capturePath;
	public Eevee() {
		super("Eevee", PokeType.NORMAL, PokeType.NORMAL, OccurrenceRate.COMMON);
		initializeStats(55, 55, 50, 45, 65);
		initializeAttacks();
		initializeBattleAnimations();
		this.standByPath="file:images/battle/Eevee/eeveestandby.png";
		this.runAwayPath="file:images/battle/Eevee/eeveeember.png";
		this.capturePath="file:images/battle/Eevee/eeveestandby.png";
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
		int[][] coordinates = {{0,0,63,55,555,240,100,100, 64, 25}, //standBy
		{0,0,74,67,550,245,100,100, 78, 39},				 //escape/ember
		
		};
		return coordinates;
	}
	
}
