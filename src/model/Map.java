package model;

public class Map {

	private char[][] board;
	private int size;
	private int playerLocation;
	


	public Map() {
		size = 30; // total Map size: 30x30, to allow for 9 sections to visit on the map
		playerLocation = 0;
	}
	
	// Blank Map Generator
	private void clearMap() {
	    board = new char[size][size];
	//    visited = new char[size][size];
	    for (int r = 0; r < size; r++) {
	      for (int c = 0; c < size; c++) {
	    		  board[r][c] = '_'; 
	//    		  visited[r][c] = 'N';
	      }
	    }
	}
	
	public int getSize() {	return size;		}
	public int getPlayerLocation() {	return playerLocation;	}
}
