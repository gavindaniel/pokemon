package model;

import java.awt.Point;
import java.util.Vector;

public class Pokemon {

	private String type; // change once Type enum is defined
	private String rarity; // Common, Uncommon, Rare
	private int total_HP;	// static?
	private int current_HP;
	private Vector<String> moves; // change once Move class is defined
	private Point location;
	
	public Pokemon() {
		type = "FIXME";
		rarity = "Common";
		total_HP = -99999;
		current_HP = -99999;
		moves = new Vector<String>();
		location = new Point(0,0);
	}
	
	public Point getLocation() {
		return location;
	}
	public void setLocation(Point p) {
		location = p;
	}
}
