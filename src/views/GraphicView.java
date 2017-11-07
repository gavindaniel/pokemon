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
		drawViewableArea();
	}
	
	private void generateImages() {
		trainer = new Image("file:Users/gavindaniel/Documents/Zona/cs335/gitrepos/pokemon-teammoltres/images/trainer/trainer1.png");
		ground = new Image("file:Users/gavindaniel/Documents/Zona/cs335/gitrepos/pokemon-teammoltres/images/shrubs/ground-g.bmp");
		tree = new Image("file:Users/gavindaniel/Documents/Zona/cs335/gitrepos/pokemon-teammoltres/images/shrubs/tree1.png");
		grass = new Image("file:Users/gavindaniel/Documents/Zona/cs335/gitrepos/pokemon-teammoltres/images/shrubs/grass.bmp");	
		water = new Image("file:Users/gavindaniel/Documents/Zona/cs335/gitrepos/pokemon-teammoltres/images/water/water-c.bmp");
	}
	
	public void drawViewableArea() {
	
		char[][] board = theGame.getMap().getBoard();
		int pc = (int) theGame.getMap().getTrainerLocation().getX();
		int pr = (int) theGame.getMap().getTrainerLocation().getY();
		
		int cc = 0;
		int rc = 0;
		
		for (int r = -4; r < 5; r++) {
			for (int c = -4; c < 5; c++) {
				if (r == 0 && c == 0) {
					gc.setGlobalAlpha(100);
					gc.drawImage(trainer, ((cc)*imageSize), ((rc)*imageSize));
					System.out.println("Drawing trainer...");
				} else {
					if (board[pr + r][pc + c] == '_') {
						gc.setGlobalAlpha(100);
						gc.drawImage(ground, ((cc)*imageSize), ((rc)*imageSize));
					} else if (board[pr + r][pc + c] == 'G') {
						gc.setGlobalAlpha(100);
						gc.drawImage(grass, ((cc)*imageSize), ((rc)*imageSize));
						System.out.println("Drawing grass...");
					}
					else if (board[pr + r][pc + c] == 'W') {
						gc.setGlobalAlpha(100);
						gc.drawImage(water, ((cc)*imageSize), ((rc)*imageSize));
					}
				}
//				System.out.println("Drawing image at... (" + cc + "," + rc + ")");
				cc++;
			}
			cc = 0;
			rc++;
		}
	}
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		gc.clearRect(0, 0, width, height);
		theGame = (Pokemon) o;
		drawViewableArea();
	}

	
	
}
