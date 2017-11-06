package model;

import java.awt.Point;
import java.util.Observable;



public class Map extends Observable {

	private char[][] board;
	private int size;
	private Point playerLocation;
	private Trainer trainer;

	public Map() {
		size = 50; // playable map size: 30, total Map size (with trees): 50x50, to allow for 9 sections to visit on the map, 10 squares of trees padding 
		
//		playerLocation = new Point((size / 2), (size / 2)+15); // starts in the middle of the map
		trainer = new Trainer();
		clearMap();
		generateTrees();
		generatePlayer();
		generateBushes();
		generateWater();
	}
	
	// Blank Map Generator
	private void clearMap() {
	    board = new char[size][size];
	    for (int r = 0; r < size; r++) {
	      for (int c = 0; c < size; c++) {
	    		  board[r][c] = '_'; 
	      }
	    }
	}
	
	public int getSize() {	return size;		}
	public char[][] getBoard() {		return board;	}
	public Point getTrainerLocation() { return trainer.getCurrentLocation(); } 
	
	public void updatePlayerLocation(Point oldLoc, Point newLoc) {
		if (checkCanMoveHere(newLoc)) {
			int oldC = (int) oldLoc.getX();
			int oldR = (int) oldLoc.getY();
			int newC = (int) newLoc.getX();
			int newR = (int) newLoc.getY();
			
			board[oldR][oldC] = '_';
			board[newR][newC] = 'P';
			trainer.setCurrentLocation(newLoc);
		}
	}
	
	public boolean checkCanMoveHere(Point newPosition) {
		int c = (int) newPosition.getX();
		int r = (int) newPosition.getY();
		
		if (board[r][c] == '_') {
			return true; // no tree in the way
		}
		else if (board[r][c] == 'T') {
			return false; //tree in the way
		}
		else 		
			return false; //not a valid spot to move to
	}

	public void generatePlayer() {
		int c = (int) trainer.getCurrentLocation().getX();
		int r = (int) trainer.getCurrentLocation().getY();
		
		board[r][c] = 'P';
	}
	
	
	public void generateTrees() {
		
		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {
				
				// check if on the edges, if so, place a 'T' to resemble a tree on the map
				if (r < 10) {
					board[r][c] = 'T';
				}
				else if (r > size - 9) {
					board[r][c] = 'T';
				}
				else if (c < 10) {
					board[r][c] = 'T';
				}
				else if (c > size - 9) {
					board [r][c] = 'T';
				}
			}
		}
	}
	
	public void generateBushes() {
		
		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {
				if (r == 30 && (c >= 10 && c < 20)) {
					board[r][c] = 'B';
				}
				if (r == 29 && (c >= 20 && c < 33)) {
					board[r][c] = 'B';
				}
				if (r == 28 && (c >= 31 && c < 33)) {
					board[r][c] = 'B';
				}
				if (r == 22 && (c >= 35 && c <= 41)) {
					board[r][c] = 'B';
				}
				if ((r >= 10 && r < 21) && c == 25) {
					board[r][c] = 'B';
				}
//				if (r == 29)
			}
		}
	}
	
	public void generateWater() {
		
		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {
				if ((r > 20 && r < 28) && (c >= 20 && c < 35)) {
					board[r][c] = 'W';
				}
			}
		}
	}
	
	public String getViewableArea() {
		String result = "";
	    int pc = (int) trainer.getCurrentLocation().getX();
	    int pr = (int) trainer.getCurrentLocation().getY();
	    
	    for (int r = -4; r < 5; r++) {
	    		for (int c = -4; c < 5; c++) {
		    		if (getBoard()[pr+r][pc+c] == '_'){
		    			  result += "   ";
		    		}
		    		else {
		    			result += " " + getBoard()[pr+r][pc+c] + " ";
		    		}
	    		}
	    		if (r < 4)
	    			result += "\n";
	    }
	    
	    return result;
	}
	/**
	   * Proved a textual version of the game Map
	   */
	  @Override
	  public String toString() {
	    String result = "";
	    
	    for (int r = 0; r < size; r++) {
	    		for (int c = 0; c < size; c++) {
		    		if (board[r][c] == '_'){
		    			  result += "   ";
		    		}
		    		else {
		    			result += " " + board[r][c] + " ";
		    		}
	    		}
	    		if (r < size-1)
	    			result += "\n";
	    }
	    
	    return result;
	  }
}
