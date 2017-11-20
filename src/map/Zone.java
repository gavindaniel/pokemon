package map;

import controller.Settings;

public class Zone {

	private int zone_number;
	private Tile[][] tiles;
	private Settings settings;
	private int size;
	
	public Zone(int num) {
		zone_number = num;
		settings = new Settings();
		size = settings.getZoneSize();
		tiles = new Tile[size][size];
	}
	
	public Tile[][] getTiles() {
		return tiles;
	}
	public void setTiles(Tile[][] t) {
		tiles = t;
	}
	public Tile getTile(int r, int c) {
		return tiles[r][c];
	}
	public void setTile(Tile t, int r, int c) {
		tiles[r][c] = t;
	}
}
