package model;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Observable;
import java.util.Scanner;



public class Map extends Observable {

	private char[][] board;
	private int size;
	private Trainer trainer;

	public Map() {
		size = 120; // playable map size: 30, total Map size (with trees): 50x50, to allow for 9 sections to visit on the map, 10 squares of trees padding 
		board = new char[size][size];
		trainer = new Trainer();
		ReadMapFromFile();
	}
	
	public int getSize() {	return size;		}
	public char[][] getBoard() {		return board;	}
	public Point getTrainerLocation() { return trainer.getCurrentLocation(); } 
	
	public void updatePlayerLocation(Point oldLoc, Point newLoc) {
		if (checkCanMoveHere(newLoc)) {
			trainer.setCurrentLocation(newLoc);
		}
	}
	
	public boolean checkCanMoveHere(Point newPosition) {
		int c = (int) newPosition.getX();
		int r = (int) newPosition.getY();
		
		try {
			if (board[r][c] == '_') {
				return true; // nothing 
			}
			else if ((board[r][c] == 'G') || (board[r][c] == 'm') || (board[r][c] == 's')) {
				return true; // grass
			}
			else 		
				return false; //not a valid spot to move to
		}
		catch (ArrayIndexOutOfBoundsException aiobe) {
			System.out.println(aiobe.toString() + "\tr: " + r + "\tc: " + c);
			return false;
		}
	}

	
	public String getViewableArea() {
		String result = "";
	    int pc = (int) trainer.getCurrentLocation().getX();
	    int pr = (int) trainer.getCurrentLocation().getY();
	    
	    for (int r = -4; r < 5; r++) {
	    		for (int c = -4; c < 5; c++) {
	    			if (r == 0 && c == 0) {
		    			result += " P ";
		    		}
	    			else {
		    			if (getBoard()[pr+r][pc+c] == '_'){
			    			  result += "   ";
			    		}
			    		else {
			    			result += " " + getBoard()[pr+r][pc+c] + " ";
			    		}
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
	    			if (r == trainer.getCurrentLocation().getY() && c == trainer.getCurrentLocation().getX()) {
	    				result += " P ";
	    			}
	    			else {
			    		if (board[r][c] == '_'){
			    			  result += "   ";
			    		}
			    		else {
			    			result += " " + board[r][c] + " ";
			    		}
	    			}
	    		}
	    		if (r < size-1)
	    			result += "\n";
	    }
	    
	    return result;
	  }
	  
	  public void ReadMapFromFile() {
		  
		  File file = new File("/Users/gavindaniel/Documents/Zona/cs335/gitrepos/pokemon-teammoltres/maps/zone1.txt");

		  int r = 0;
		  int c = 0;
		  
		    try {

		        Scanner sc = new Scanner(file);

		        while (sc.hasNextLine()) {
		            String line = sc.nextLine();
		            for (int i = 0; i < line.length(); i++) {
		            		if (line.charAt(i) != ' ') {
			            		board[r][c] = line.charAt(i);
			            		if (line.charAt(i) == 'P') {
			            			trainer.setCurrentLocation(new Point(c,r));
			            			board[r][c] = '_';
			            		}
			            		c++;
		            		}
		            }
		            c = 0;
		            r++;
		        }
		        sc.close();
		    } 
		    catch (FileNotFoundException e) {
		        e.printStackTrace();
		    }
	  }
}
