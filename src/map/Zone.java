package map;

import controller.Settings;

public class Zone {

	private int zone_number;
	private Tile[][] tiles;
	private Settings settings;
	private int size;
	private int num_columns;
	private int num_rows;
	
	public Zone(int num) {
		zone_number = num;
		settings = new Settings();
		size = settings.getZoneSize();
		tiles = new Tile[size][size];
	}
	
//	public Tile[][] getTiles() {
//		return tiles;
//	}
//	public void setTiles(Tile[][] t) {
//		tiles = t;
//	}
	public Tile getTile(int r, int c) {
		return tiles[r][c];
	}
	public void setTile(Tile t, int r, int c) {
		tiles[r][c] = t;
	}
	
	public int getColumnSize() {
		return num_columns;
	}
	public int getRowSize() {
		return num_rows;
	}
	public void setColumnSize(int c) {
		num_columns = c;
	}
	public void setRowSize(int r) {
		num_rows = r;
	}
}