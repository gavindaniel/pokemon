package map;

import javafx.scene.image.Image;

public class Tile {

	private String type;
	private char sourceChar;
	private boolean pokemonHere;
	private boolean trainerHere;
//	private Image img;
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
		
		if (c == '_' || c == 'P') {
			type = "ground";
//			img = new Image("/images/shrubs/ground-g.bmp");
			imgPath = "images/shrubs/ground-g.bmp";
		}
		else if (c == 'G') {
			type = "grass";
//			img = new Image("/images/shrubs/grass.bmp");
			imgPath = "images/shrubs/grass.bmp";
		}
		else if (c == 'W') {
			type = "water";
//			img = new Image("/images/water/water-c.bmp");
			imgPath = "images/water/water-c.bmp";
		}
		else if (c == 'B') {
			type = "bush";
//			img = new Image("/images/shrubs/bush.bmp");
			imgPath = "images/shrubs/bush.bmp";
		}
		else if (c == 'T') {
			type = "tree";
//			img = new Image("/images/shrubs/tree.bmp");
			imgPath = "images/shrubs/tree.bmp";
		}
		else if (c == 'E') {
			type = "fence";
//			img = new Image("/images/misc/fence.bmp");
			imgPath = "images/misc/fence.bmp";
		}
		//d
		else if (c == 's') {
			type = "stairs";
//			img = new Image("/images/misc/stairs.bmp");
			imgPath = "images/misc/stairs.bmp";
		}
		else if (c == 'b') {
			type = "hill";
//			img = new Image("/images/hills/hill-bottom.bmp");
			imgPath = "images/hills/hill-bottom.bmp";
		}
		else if (c == 't') {
			type = "hill";
//			img = new Image("/images/hills/hill-top.bmp");
			imgPath = "images/hills/hill-top.bmp";
		}
		else if (c == 'm') {
			type = "hill";
//			img = new Image("/images/hills/hill-middle.bmp");
			imgPath = "images/hills/hill-middle.bmp";
		}
		else if (c == 'l') {
			type = "hill";
//			img = new Image("/images/hills/hill-left.bmp");
			imgPath = "images/hills/hill-left.bmp";
		}
		else if (c == 'r') {
			type = "hill";
//			img = new Image("/images/hills/hill-right.bmp");
			imgPath = "images/hills/hill-right.bmp";
		}
		else if (c == 'q') {
			type = "hill";
//			img = new Image("/images/hills/hill-topleft.bmp");
			imgPath = "images/hills/hill-topleft.bmp";
		}
		else if (c == 'o') {
			type = "hill";
//			img = new Image("/images/hills/hill-topright.bmp");
			imgPath = "images/hills/hill-topright.bmp";
		}
		else if (c == 'z') {
			type = "hill";
//			img = new Image("/images/hills/hill-bottomleft.bmp");
			imgPath = "images/hills/hill-bottomleft.bmp";
		}
		else if (c == 'n') {
			type = "hill";
//			img = new Image("/images/hills/hill-bottomright.bmp");
			imgPath = "images/hills/hill-bottomright.bmp";
		}
		
		else if (c == '~') {
			type = "water";
//			sourceChar = c;
//			img = new Image("/images/water/water-bl.bmp");
			imgPath = "images/water/water-bl.bmp";
		}
		else if (c == '!') {
			type = "water";
//			img = new Image("/images/water/water-lc.bmp");
			imgPath = "images/water/water-lc.bmp";
		}
		else if (c == '@') {
			type = "water";
//			img = new Image("/images/water/water-tl.bmp");
			imgPath = "images/water/water-tl.bmp";
		}
		else if (c == '#') {
			type = "water";
//			img = new Image("/images/water/water-tc.bmp");
			imgPath = "images/water/water-tc.bmp";
		}
		else if (c == '$') {
			type = "water";
//			img = new Image("/images/water/water-tr.bmp");
			imgPath = "images/water/water-tr.bmp";
		}
		else if (c == '%') {
			type = "water";
//			img = new Image("/images/water/water-rc.bmp");
			imgPath = "images/water/water-rc.bmp";
		}
		else if (c == '^') {
			type = "water";
//			img = new Image("/images/water/water-br.bmp");
			imgPath = "images/water/water-br.bmp";
		}
		else if (c == '&') {
			type = "water";
//			img = new Image("/images/water/water-bc.bmp");
			imgPath = "images/water/water-bc.bmp";
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
	}
	
	public String getType() {
		return type;
	}
	public void setType(String t) {
		type = t;
	}
	public char getSourceChar() {
		return sourceChar;
	}
	public void setSourceChar(char c) {
		sourceChar = c;
	}
	public boolean getPokemonHere() {
		return pokemonHere;
	}
	public void setPokemonHere(boolean flag) {
		pokemonHere = flag;
	}
	public boolean getTrainerHere() {
		return trainerHere;
	}
	public void setTrainerHere(boolean flag) {
		trainerHere = flag;
	}
	public Image getImage() {
		return (new Image(imgPath));
	}
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
