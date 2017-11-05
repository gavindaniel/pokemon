package model;

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
			int count = 0;
			char spot = 'z';
			int pos = 0;
			boolean flag = false;
			
		    for (int r = 0; r < theMap.getSize(); r++) {
		    		for (int c = 0; c < theMap.getSize(); c++) {
			    	  	count++;
				    	if (count == theMap.getPlayerLocation()){
				    		if (direction == 'U'){
				    			  
				    		}
				    		else if (direction == 'L'){
				    			  
				    		}
				    		else if (direction == 'R'){
				    			  	    			  
				    		}
				    		else if (direction == 'D'){
				    			    
				    		}
				    		  
				    		flag = true;
				    	}
				    	if (flag){
			    		  break;
				    	}
		    		}
		    		if (flag){
		    			break;
		    		}
		    }
		    setChanged();
		    notifyObservers();
		}
}
