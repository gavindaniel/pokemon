package model;

import java.awt.Point;
import java.util.Observable;



public class Map extends Observable {

	private char[][] board;
	private int size;
	private Point playerLocation;

	public Map() {
		size = 40; // playable map size: 30, total Map size (with trees): 40x40, to allow for 9 sections to visit on the map, 5 squares of trees padding 
		
		playerLocation = new Point((size / 2), (size / 2)); // starts in the middle of the map
		clearMap();
		generateTrees();
		generatePlayer();
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
	public Point getPlayerLocation() {	return playerLocation;	}
	public char[][] getBoard() {		return board;	}
	
	
	public void updatePlayerLocation(Point oldLoc, Point newLoc) {
		if (checkCanMoveHere(newLoc)) {
			int oldC = (int) oldLoc.getX();
			int oldR = (int) oldLoc.getY();
			int newC = (int) newLoc.getX();
			int newR = (int) newLoc.getY();
			
			board[oldR][oldC] = '_';
			board[newR][newC] = 'P';
			playerLocation = newLoc;
		}
	}
	
	public boolean checkCanMoveHere(Point newPosition) {
		int c = (int) newPosition.getX();
		int r = (int) newPosition.getY();
		
		if (board[r][c] == '_') {
			return true; // no tree in the way
		}
		else if (board[r][c] == 'T') {
			System.out.println("Tree in the way");
			return false; //tree in the way
		}
		else 		
			return false; //not a valid spot to move to
	}

	public void generatePlayer() {
		int c = (int) playerLocation.getX();
		int r = (int) playerLocation.getY();
		
		board[r][c] = 'P';
	}
	
	
	public void generateTrees() {
		
		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {
				
				// check if on the edges, if so, place a 'T' to resemble a tree on the map
				if (r < 5) {
					board[r][c] = 'T';
				}
				else if (r > size - 4) {
					board[r][c] = 'T';
				}
				else if (c < 5) {
					board[r][c] = 'T';
				}
				else if (c > size - 4) {
					board [r][c] = 'T';
				}
			}
		}
		
	}
}
