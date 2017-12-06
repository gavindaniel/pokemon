package map;

import javafx.scene.image.Image;

public class Tile {

	private String type;
	private char sourceChar;
	private boolean pokemonHere;
	private boolean trainerHere;
	private String imgPath;
	
	
	public Tile() {
		type = "empty";
		sourceChar = '?';
		pokemonHere = false;
		trainerHere = false;
		
//		img = new Image("/images/shrubs/ground-g.bmp");
		imgPath = "images/shrubs/ground-g.bmp";
	}
	
	public Tile(char c) {
		
		if (c == 'i') {
			type = "item";
		}
		if (c == '_' || c == 'P') {
			type = "ground";
		}
		else if (c == 'G') {
			type = "grass";
		}
		else if (c == 'W') {
			type = "water";
		}
		else if (c == 'B') {
			type = "bush";
		}
		else if (c == 'T') {		// trees
			type = "tree";
		}
		else if (c == 'F') {		// fence marking the exits of zones
			type = "fence";
		}
		else if (c == 'E') {		//transition to other zone
			type = "exit";
		}
		else if (c == 's') {
			type = "stairs";
		}
		else if (c == 'b') {
			type = "hill";
		}
		else if (c == 't') {
			type = "hill";
		}
		else if (c == 'm') {
			type = "hill";
		}
		else if (c == 'l') {
			type = "hill";
		}
		else if (c == 'r') {
			type = "hill";
		}
		else if (c == 'q') {
			type = "hill";
		}
		else if (c == 'o') {
			type = "hill";
		}
		else if (c == 'z') {
			type = "hill";
		}
		else if (c == 'n') {
			type = "hill";
		}
		else if (c == '~') {
			type = "water";
		}
		else if (c == '!') {
			type = "water";
		}
		else if (c == '@') {
			type = "water";
		}
		else if (c == '#') {
			type = "water";
		}
		else if (c == '$') {
			type = "water";
		}
		else if (c == '%') {
			type = "water";
		}
		else if (c == '^') {
			type = "water";
		}
		else if (c == '&') {
			type = "water";
		}
		else if (c == '6') {
			type = "grass";
		}
		
		pokemonHere = false;
		
		if (c == 'P') {
			trainerHere = true;
			sourceChar = '_';
		}
		else {
			trainerHere = false;
			sourceChar = c;
		}	
		
		if (c == '6')
			trainerHere = true;
	}
	
	public String getType() {
		return type;
	}
//	public void setType(String t) {
//		type = t;
//	}
	public char getSourceChar() {
		return sourceChar;
	}
//	public void setSourceChar(char c) {
//		sourceChar = c;
//	}
	public boolean getPokemonHere() {
		return pokemonHere;
	}
	public void setPokemonHere(boolean flag) {
		pokemonHere = flag;
	}
	public boolean getTrainerHere() {
		return trainerHere;
	}
//	public void setTrainerHere(boolean flag) {
//		trainerHere = flag;
//	}
//	public Image getImage() {
//		return (new Image(imgPath));
//	}
	public String getImagePath() {
		return imgPath;
	}
	public void setImage(String s) {
		imgPath = s;
	}
//	public Image getImage() {
//		return img;
//	}
//	public void setImage(Image i) {
//		img = i;
//	}
}
