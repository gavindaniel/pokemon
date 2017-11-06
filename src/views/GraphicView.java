package views;

import java.util.Observable;
import java.util.Observer;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import model.Pokemon;

public class GraphicView extends BorderPane implements Observer {

	private Pokemon theGame;
	private Canvas canvas;
	private GraphicsContext gc;
	private GridPane gp1;
	
	private Image tree;
	private Image trainer;
	private Image grass;
	private Image ground;
	private Image water;
	
	private static final double height = 400;
	private static final double width = 600;
	private static final double imageSize = 16; // 16px by 16px
	
	// contructor 
	public GraphicView(Pokemon PokemonGame) {
		theGame = PokemonGame;
		canvas = new Canvas(width, height);
		gp1 = new GridPane();
		
		initializePane();
	}
	
	// initialize Pane for the first time
	private void initializePane() {
		gc = canvas.getGraphicsContext2D();
		generateImages();
		gp1.setPrefHeight(height);
		gp1.setPrefWidth(width);
		gp1.getChildren().addAll(canvas);
		this.setCenter(gp1);
		updateImages();
	}
	
	private void generateImages() {
		trainer = new Image("/Users/gavindaniel/Documents/Zona/cs335/gitrepos/pokemon-teammoltres/images/trainer/trainer1.png");
		ground = new Image("/Users/gavindaniel/Documents/Zona/cs335/gitrepos/pokemon-teammoltres/images/shrubs/ground-g.bmp");
		tree = new Image("/Users/gavindaniel/Documents/Zona/cs335/gitrepos/pokemon-teammoltres/images/shrubs/tree1.png");
		grass = new Image("/Users/gavindaniel/Documents/Zona/cs335/gitrepos/pokemon-teammoltres/images/shrubs/grass.bmp");	
		water = new Image("/Users/gavindaniel/Documents/Zona/cs335/gitrepos/pokemon-teammoltres/images/water/water-c.bmp");
	}
	
	public void updateImages() {
		char[][] board = theGame.getMap().getBoard();
		
		for (int r = 0; r < theGame.getMap().getSize(); r++) {
			for (int c = 0; c < theGame.getMap().getSize(); c++) {
				if (board[r][c] == '_') {
					gc.setGlobalAlpha(100);
					gc.drawImage(ground, (c*imageSize), (r*imageSize));
				}
				else if (board[r][c] == 'G') {
					gc.setGlobalAlpha(100);
					gc.drawImage(grass, (c*imageSize), (r*imageSize));
				}
				else if (board[r][c] == 'W') {
					gc.setGlobalAlpha(100);
					gc.drawImage(water, (c*imageSize), (r*imageSize));
				}
				else if (board[r][c] == 'P') {
					gc.setGlobalAlpha(100);
					gc.drawImage(trainer, (c*imageSize), (r*imageSize));
				}
			}
		}
	}
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		gc.clearRect(0, 0, width, height);
		theGame = (Pokemon) o;
		updateImages();
	}

	
	
}
