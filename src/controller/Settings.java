package controller;

public class Settings {
	
	private static double scene_height, scene_width;
	private static double map_height, map_width;
	private static double original_image_size, image_display_size;
	private static int graphic_display_lowerbound, graphic_display_upperbound;
	private static int text_display_lowerbound, text_display_upperbound;
	
	public Settings() {
		scene_height = 400;				//	the display height of the Game View
		scene_width = 600;				//	the display width of the Game View
		map_height = 650;				//	the display height of the entire Map View
		map_width = 1100;				//	the display width of the entire Map View
		original_image_size = 16;		//	original background image size:	16px by 16px
		image_display_size = 32;			//	desired display background image size:	32px by 32px
		graphic_display_lowerbound = -9;	//	prints 9 images to the left of the trainer in the viewable area
		graphic_display_upperbound = 9;	//	prints 9 images to the right of the trainer in the viewable area
		text_display_lowerbound = -4;	//	prints 4 chars to the left of the trainer in the viewable area
		text_display_upperbound = 4;		//	prints 4 chars to the right of the trainer in the viewable area 
	}
	
	public double getHeight(String key) {
		if (key == "scene")
			return scene_height;
		else // (key == "map")
			return map_height;
	}
	public double getWidth(String key) {
		if (key == "scene")
			return scene_width;
		else // (key == "map")
			return map_width;
	}
	public double getImageSize(String key) {
		if (key == "original")
			return original_image_size;
		else // if (key == "display")
			return image_display_size;
	}
	public int getLowerBound(String key) {
		if (key == "graphic")
			return graphic_display_lowerbound;
		else //if (key == "text")
			return text_display_lowerbound;
	}
	public int getUpperBound(String key) {
		if (key == "graphic")
			return graphic_display_upperbound;
		else //if (key == "text")
			return text_display_upperbound;
	}
}
