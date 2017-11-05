package model;

import java.awt.Point;
import java.util.Observable;

public class Pokemon extends Observable {

	private Map theMap;
	
	
	public Pokemon() {
		theMap = new Map();
		startNewGame();
	}
	
	/**
	   * Start a new game and tell all observers to draw an new game
	   * with the string message startNewGame()
	   */
	  public void startNewGame() {
//		clearBoard();
		theMap = new Map();
	    // The state of this model just changed so tell any observer to update themselves
	    setChanged();
	    notifyObservers("startNewGame()");
	  }
	  
	  
	  
	  
	// Move Player 
		public void movePlayer(char direction) {
		
			Point oldLoc = theMap.getPlayerLocation();
			Point newLoc = oldLoc;
			int newC = (int) newLoc.getX();
			int newR = (int) newLoc.getY();
		    
	    		System.out.println("Moving player...");
	    		
	    		if (direction == 'U'){
	    			  newR -= 1;
	    		}
	    		else if (direction == 'L'){
	    			  newC -= 1;
	    		}
	    		else if (direction == 'R'){
	    			  newC += 1;	
	    		}
	    		else if (direction == 'D'){
	    			  newR += 1;
	    		}
	    		
	    		newLoc = new Point(newC, newR);
	    		System.out.println("old: (" + (int)oldLoc.getX() + "," + (int)oldLoc.getY() + ")    new: (" + (int)newLoc.getX() + "," + (int)newLoc.getY() + ")");
	    		
	    		theMap.updatePlayerLocation(oldLoc, newLoc);
				
		    setChanged();
		    notifyObservers();
		}
		
		
		
		/**
		   * Proved a textual version of this tic tac toe board.
		   */
		  @Override
		  public String toString() {
		    String result = "";
		    int pc = (int) theMap.getPlayerLocation().getX();
		    int pr = (int) theMap.getPlayerLocation().getY();
		    
		    for (int r = -4; r < 5; r++) {
		    		for (int c = -4; c < 5; c++) {
			    		if (theMap.getBoard()[pr+r][pc+c] == '_'){
			    			  result += " _ ";
			    		}
			    		else {
			    			result += " " + theMap.getBoard()[pr+r][pc+c] + " ";
			    		}
		    		}
		    		if (r < 4)
		    			result += "\n";
		    }
		    
		    return result;
		  }
}
