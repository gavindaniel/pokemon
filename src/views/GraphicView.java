package views;

import java.util.Observable;
import java.util.Observer;

import javafx.scene.canvas.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import model.SafariZone;

public class GraphicView extends BorderPane implements Observer {

	private SafariZone theGame;
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

	private Image hill_m;
	private Image hill_l;
	private Image hill_r;
	private Image hill_t;
	private Image hill_b;
	private Image hill_tl;
	private Image hill_tr;
	private Image hill_bl;
	private Image hill_br;
	
	private Image water_m;
	private Image water_t;
	private Image water_b;
	private Image water_l;
	private Image water_r;
	private Image water_tl;
	private Image water_tr;
	private Image water_bl;
	private Image water_br;

	private static final double height = 400;
	private static final double width = 600;
	private static final double imageSize = 16; // 16px by 16px

	// contructor
	public GraphicView(SafariZone PokemonGame) {
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

		hill_m = new Image("/images/hills/hill-middle.bmp");
		hill_l = new Image("/images/hills/hill-left.bmp");
		hill_r = new Image("/images/hills/hill-right.bmp");
		hill_t = new Image("/images/hills/hill-top.bmp");
		hill_b = new Image("/images/hills/hill-bottom.bmp");
		hill_tl = new Image("/images/hills/hill-topleft.bmp");
		hill_tr = new Image("/images/hills/hill-topright.bmp");
		hill_bl = new Image("/images/hills/hill-bottomleft.bmp");
		hill_br = new Image("/images/hills/hill-bottomright.bmp");
		
		water_m = new Image("/images/water/water-c.bmp");
		water_t = new Image("/images/water/water-tc.bmp");
		water_b = new Image("/images/water/water-bc.bmp");
		water_l = new Image("/images/water/water-lc.bmp");
		water_r = new Image("/images/water/water-rc.bmp");
		water_tl = new Image("/images/water/water-tl.bmp");
		water_tr = new Image("/images/water/water-tr.bmp");
		water_bl = new Image("/images/water/water-bl.bmp");
		water_br = new Image("/images/water/water-br.bmp");
	}

	public void drawViewableArea() {

		char[][] board = theGame.getMap().getBoard();
		int pc = (int) theGame.getMap().getTrainer().getCurrentLocation().getX();
		int pr = (int) theGame.getMap().getTrainer().getCurrentLocation().getY();

		int cc = 0;
		int rc = 0;

		int lowerBound = -19;
		int upperBound = 20;

		for (int r = lowerBound; r < upperBound; r++) {
			for (int c = lowerBound; c < upperBound; c++) {
				if (r == 0 && c == 0) gc.drawImage(trainer, ((cc) * imageSize), ((rc) * imageSize));
				
				else {
					if (board[pr + r][pc + c] == '_') gc.drawImage(ground, ((cc) * imageSize), ((rc) * imageSize));
					else if (board[pr + r][pc + c] == 'G') gc.drawImage(grass, ((cc) * imageSize), ((rc) * imageSize));
					else if (board[pr + r][pc + c] == 'W') gc.drawImage(water, ((cc) * imageSize), ((rc) * imageSize));
					else if (board[pr + r][pc + c] == 'B') gc.drawImage(bush, ((cc) * imageSize), ((rc) * imageSize));
					else if (board[pr + r][pc + c] == 'T') gc.drawImage(tree, ((cc) * imageSize), ((rc) * imageSize));
					else if (board[pr + r][pc + c] == 'E') gc.drawImage(fence, ((cc) * imageSize), ((rc) * imageSize));
					else if (board[pr + r][pc + c] == 's') gc.drawImage(stairs, ((cc) * imageSize), ((rc) * imageSize));
					

					else if (board[pr + r][pc + c] == 'b') gc.drawImage(hill_b, ((cc) * imageSize), ((rc) * imageSize));
					else if (board[pr + r][pc + c] == 't') gc.drawImage(hill_t, ((cc) * imageSize), ((rc) * imageSize));
					else if (board[pr + r][pc + c] == 'm') gc.drawImage(hill_m, ((cc) * imageSize), ((rc) * imageSize));
					else if (board[pr + r][pc + c] == 'l') gc.drawImage(hill_l, ((cc) * imageSize), ((rc) * imageSize));
					else if (board[pr + r][pc + c] == 'r') gc.drawImage(hill_r, ((cc) * imageSize), ((rc) * imageSize));
					else if (board[pr + r][pc + c] == 'q') gc.drawImage(hill_tl, ((cc) * imageSize), ((rc) * imageSize));
					else if (board[pr + r][pc + c] == 'o') gc.drawImage(hill_tr, ((cc) * imageSize), ((rc) * imageSize));
					else if (board[pr + r][pc + c] == 'z') gc.drawImage(hill_bl, ((cc) * imageSize), ((rc) * imageSize));
					else if (board[pr + r][pc + c] == 'n') gc.drawImage(hill_br, ((cc) * imageSize), ((rc) * imageSize));
					
					else if (board[pr + r][pc + c] == '~') gc.drawImage(water_bl, ((cc) * imageSize), ((rc) * imageSize));
					else if (board[pr + r][pc + c] == '!') gc.drawImage(water_l, ((cc) * imageSize), ((rc) * imageSize));
					else if (board[pr + r][pc + c] == '@') gc.drawImage(water_tl, ((cc) * imageSize), ((rc) * imageSize));
					else if (board[pr + r][pc + c] == '#') gc.drawImage(water_t, ((cc) * imageSize), ((rc) * imageSize));
					else if (board[pr + r][pc + c] == '$') gc.drawImage(water_tr, ((cc) * imageSize), ((rc) * imageSize));
					else if (board[pr + r][pc + c] == '%') gc.drawImage(water_r, ((cc) * imageSize), ((rc) * imageSize));
					else if (board[pr + r][pc + c] == '^') gc.drawImage(water_br, ((cc) * imageSize), ((rc) * imageSize));
					else if (board[pr + r][pc + c] == '&') gc.drawImage(water_b, ((cc) * imageSize), ((rc) * imageSize));
					
					// new stuff
//					else if (board[pr + r][pc + c] == 'W' && board[(pr + r)+1][pc + c] == 'W') gc.drawImage(water_bl, ((cc) * imageSize), ((rc) * imageSize));
//					else if (board[pr + r][pc + c] == 'W') gc.drawImage(water_l, ((cc) * imageSize), ((rc) * imageSize));
//					else if (board[pr + r][pc + c] == 'W') gc.drawImage(water_tl, ((cc) * imageSize), ((rc) * imageSize));
//					else if (board[pr + r][pc + c] == 'W') gc.drawImage(water_t, ((cc) * imageSize), ((rc) * imageSize));
//					else if (board[pr + r][pc + c] == 'W') gc.drawImage(water_tr, ((cc) * imageSize), ((rc) * imageSize));
//					else if (board[pr + r][pc + c] == 'W') gc.drawImage(water_r, ((cc) * imageSize), ((rc) * imageSize));
//					else if (board[pr + r][pc + c] == 'W') gc.drawImage(water_br, ((cc) * imageSize), ((rc) * imageSize));
//					else if (board[pr + r][pc + c] == 'W') gc.drawImage(water_b, ((cc) * imageSize), ((rc) * imageSize));
						
				}
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
		theGame = (SafariZone) o;
		drawViewableArea();
	}

}
