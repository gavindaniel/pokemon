package views;

import java.util.Observable;
import java.util.Observer;

import javafx.scene.canvas.*;
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
	private Image bush;
	private Image fence;
	private Image stairs;
	
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
		trainer = new Image("/images/trainer1.png");
		ground = new Image("/images/shrubs/ground-g.bmp");
		tree = new Image("/images/shrubs/tree.bmp");
		grass = new Image("/images/shrubs/grass.bmp");	
		water = new Image("/images/water/water-c.bmp");
		bush = new Image("/images/shrubs/bush.bmp");
		fence = new Image("/images/shrubs/fence.bmp");
		stairs = new Image("/images/misc/stair-right.bmp");
	}
	
	public void drawViewableArea() {
	
		char[][] board = theGame.getMap().getBoard();
		int pc = (int) theGame.getMap().getTrainerLocation().getX();
		int pr = (int) theGame.getMap().getTrainerLocation().getY();
		
		int cc = 0;
		int rc = 0;
		
		int lowerBound = -19;
		int upperBound = 20;
		
		for (int r = lowerBound; r < upperBound; r++) {
			for (int c = lowerBound; c < upperBound; c++) {
				if (r == 0 && c == 0) {
					gc.setGlobalAlpha(100);
					gc.drawImage(trainer, ((cc)*imageSize), ((rc)*imageSize));
//					System.out.println("Drawing trainer...");
				} else {
					if (board[pr + r][pc + c] == '_') {
						gc.setGlobalAlpha(100);
						gc.drawImage(ground, ((cc)*imageSize), ((rc)*imageSize));
					} else if (board[pr + r][pc + c] == 'G') {
						gc.setGlobalAlpha(100);
						gc.drawImage(grass, ((cc)*imageSize), ((rc)*imageSize));
//						System.out.println("Drawing grass...");
					}
					else if (board[pr + r][pc + c] == 'W') {
						gc.setGlobalAlpha(100);
						gc.drawImage(water, ((cc)*imageSize), ((rc)*imageSize));
					}
					else if (board[pr + r][pc + c] == 'B') {
						gc.setGlobalAlpha(100);
						gc.drawImage(bush, ((cc)*imageSize), ((rc)*imageSize));
					}
					else if (board[pr + r][pc + c] == 'T') {
						gc.setGlobalAlpha(100);
						gc.drawImage(tree, ((cc)*imageSize), ((rc)*imageSize));
					}
					else if (board[pr + r][pc + c] == 'E') {
						gc.setGlobalAlpha(100);
						gc.drawImage(fence, ((cc)*imageSize), ((rc)*imageSize));
					}
					else if (board[pr + r][pc + c] == 's') {
						gc.setGlobalAlpha(100);
						gc.drawImage(stairs, ((cc)*imageSize), ((rc)*imageSize));
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
