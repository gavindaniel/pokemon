package controller;

public class Settings {
	
	private static int starting_zone;
	private static int zone_size, tree_line;
	private static double scene_height, scene_width;
	private static double map_height, map_width;
	private static double info_height, info_width;
	private static double input_height, input_width;
	private static double input_display_height, input_display_width;
	private static double tile_source_size, tile_display_size;
	private static double trainer_source_width, trainer_source_height;
	private static double trainer_display_width, trainer_display_height;
	private static int graphic_display_lowerbound, graphic_display_upperbound;
	private static int text_display_lowerbound, text_display_upperbound;
	private static double trainer_x, trainer_y;
	private static double sprite_x, sprite_y;
	private static double timeline_1_duration, timeline_2_duration;
	
	public Settings() {
		starting_zone = 1;					//  zone # that the trainer starts in (this will be changed once the safari zone building is done)
		zone_size = 85;						//  number of tiles that make up the height and width of the zone map
		tree_line = 18;						//	number of trees that will border the zone 
		scene_height = 550;					//	the display height of the Game View
		scene_width = 800;					//	the display width of the Game View
		map_height = 500;					//	the display height of the entire Map View
		map_width = 800;						//	the display width of the entire Map View
		info_height = 400;					//	the display height of the trainer / bag / pokemon view
		info_width = 200;					//	the display height of the trainer / bag / pokemon view
		input_height = 50;					//	the display height of the info text input 
		input_width = 200;					//	the display width of the info text input 
		input_display_height = 100;			//	the display height of the info input stage
		input_display_width = 200;			//	the display width of the info input stage
		tile_source_size = 16;				//	original background image size:	16px by 16px
		tile_display_size = 32;				//	desired display background image size:	32px by 32px
		trainer_source_width = 19;			//	width of the source image of the trainer on the spritesheet
		trainer_source_height = 25;			//	height of the source image of the trainer on the spritesheet
		trainer_display_width = 32;			// 	display width of the trainer image in graphic view
		trainer_display_height = 44;			//	display height of the trainer image in graphic view
		graphic_display_lowerbound = -12;	//	prints 12 images to the left of the trainer in the viewable area
		graphic_display_upperbound = 12;		//	prints 12 images to the right of the trainer in the viewable area
		text_display_lowerbound = -7;		//	prints 4 chars to the left of the trainer in the viewable area
		text_display_upperbound = 7;			//	prints 4 chars to the right of the trainer in the viewable area 
		trainer_x = 385;						//	trainer x location to be displayed in the game
		trainer_y = 368;						//	trainer y location to be displayed in the game
		sprite_x = -2;						//	trainer x location on the sprite sheet
		sprite_y = 0;						//	trainer y location on the sprite sheet
		timeline_1_duration = 150;			//	duration of timeline 1 to animate Trainer movement (in miliseconds)
		timeline_2_duration = 10;			//	duration of timeline 2 to animate the speech of professor Oak
	}
	public int getStartingZone() {
		return starting_zone;
	}
	public int getZoneSize() {
		return zone_size;
	}
	public int getTreeLine() {
		return tree_line;
	}
	public double getX(String key) {
		if (key == "trainer")
			return trainer_x;
		else //if (key == "sprite")
			return sprite_x;
	}
	public double getY(String key) {
		if (key == "trainer")
			return trainer_y;
		else //if (key == "sprite")
			return sprite_y;
	}
	public double getHeight(String key) {
		if (key == "scene")
			return scene_height;
		else if (key == "map")
			return map_height;
		else if (key == "source")
			return trainer_source_height;
		else if (key == "info")
			return info_height;
		else if (key == "text")
			return input_height;
		else if (key == "input")
			return input_display_height;
		else //if(key == "display")
			return trainer_display_height;
	}
	public double getWidth(String key) {
		if (key == "scene")
			return scene_width;
		else if (key == "map")
			return map_width;
		else if (key == "source")
			return trainer_source_width;
		else if (key == "info")
			return info_width;
		else if (key == "text")
			return input_width;
		else if (key == "input")
			return input_display_width;
		else //if(key == "display")
			return trainer_display_width;
	}
	public double getImageSize(String key) {
		if (key == "original")
			return tile_source_size;
		else  //if (key == "display")
			return tile_display_size;
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
	public double getTimelineDuration(int key) {
		if (key == 1)
			return timeline_1_duration;
		else //if (key == 2)
			return timeline_2_duration;
	}
}