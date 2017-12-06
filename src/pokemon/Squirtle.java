package pokemon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Squirtle extends Pokemon {

	String standByPath,runAwayPath,capturePath;
	public Squirtle() {
		super("Squirtle", PokeType.WATER, null, OccurrenceRate.COMMON);
		initializeStats(260, 163, 195, 167, 153);
		initializeAttacks();
		initializeBattleAnimations();
		this.standByPath="file:images/battle/Squirtle/squirtle-standby.png";
		this.runAwayPath="file:images/battle/Squirtle/squirtle-bubblebeam.png";
		this.capturePath="file:images/battle/Squirtle/squirtle-standby.png";
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
		int[][] coordinates = {{0,0,53,55,565,235,100,100, 53, 28}, //standBy
		{0,0,76,67,555,230,115,115, 75, 65},				 //Escape/Bubblebeam
		};
		return coordinates;
	}
}