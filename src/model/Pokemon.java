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
	  
	  public Map getMap() {	return theMap;	}
	  
	  
	// Move Player 
		public void movePlayer(char direction) {
		
			Point oldLoc = theMap.getTrainerLocation();
			Point newLoc = oldLoc;
			int newC = (int) newLoc.getX();
			int newR = (int) newLoc.getY();
		    
//	    		System.out.println("Moving player...");
	    		
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
	    		// hi
	    		newLoc = new Point(newC, newR);
//	    		System.out.println("old: (" + (int)oldLoc.getX() + "," + (int)oldLoc.getY() + ")    new: (" + (int)newLoc.getX() + "," + (int)newLoc.getY() + ")");
	    		
	    		theMap.updatePlayerLocation(oldLoc, newLoc);
				
		    setChanged();
		    notifyObservers();
		}
		
		
		
}
