package model;

import java.awt.Point;

public class Trainer {
	
	private Point currentLocation;
	
	
	public Trainer() {
		currentLocation = new Point((50 / 2), (50 / 2)+15);
	}
	
	public Point getCurrentLocation() {
		return currentLocation;
	}
	public void setCurrentLocation(Point c) {
		currentLocation = c;
	}
}
