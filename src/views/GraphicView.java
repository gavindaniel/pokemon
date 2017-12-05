package views;

import java.util.Observable;
import java.util.Observer;

import battle.BattleGUI;
import capture.Capture;
import capture.CaptureView;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import map.Tile;
import model.SafariZone;
import model.Trainer;
import pokemon.Pikachu;

public class GraphicView extends Canvas implements Observer {

	private SafariZone theGame;
	private GraphicsContext gc;
	private Image spritesheet;
	private Timeline timeline;
	private KeyCode direction;
	private boolean done;
	private int tic;

	private static double imageSize, displaySize;
	private static int row_lowerBound, col_lowerBound, col_upperBound, row_upperBound;
	
	private double sx, sy, sw, sh, dx, dy, dw, dh;
	
	private double xshift, yshift;
	private char lastStep;
	
	private static Image ground;
	private static Image grass;
	private static Image bush;
	private static Image tree;
	private static Image fence;
	private static Image stairs;
	private static Image hill_bottom;
	private static Image hill_top;
	private static Image hill_middle;
	private static Image hill_left;
	private static Image hill_right;
	private static Image hill_topleft;
	private static Image hill_topright;
	private static Image hill_bottomleft;
	private static Image hill_bottomright;
	private static Image water_bottom;
	private static Image water_top;
	private static Image water_middle;
	private static Image water_left;
	private static Image water_right;
	private static Image water_topleft;
	private static Image water_topright;
	private static Image water_bottomleft;
	private static Image water_bottomright;
	
	private double duration;
	
	private Stage mainStage;
	private Scene game_scene, capture_scene, battle_scene;
	
	private BorderPane battle_window;
	
	private BorderPane capture_window;
	private Capture capture;
	private CaptureView captureView;
	private String response;
	
	
	@Override
	public void update(Observable o, Object arg) {
		theGame = (SafariZone) o;
		if (theGame.getSettings().getTimelineDuration(1) != duration) {
			timeline.stop();
			duration = theGame.getSettings().getTimelineDuration(1);
			timeline = new Timeline(new KeyFrame(Duration.millis(duration), new AnimateStarter()));
			timeline.setCycleCount(Animation.INDEFINITE);
		}
	}
	
	/**
	 * @param instance of the game 'PokemonGame'
	 */
	public GraphicView(SafariZone PokemonGame, Stage main, Scene game) {
		theGame = PokemonGame;
		mainStage = main;
		game_scene = game;
		
		response = "nothing";
		//----- Capture -----------
		capture_window = new BorderPane();
		capture_scene = new Scene(capture_window, theGame.getSettings().getWidth("scene"), theGame.getSettings().getHeight("scene")); //new
		capture = new Capture(new Pikachu(), theGame.getMap().getTrainer());
		//captureView = new CaptureView(capture, theGame.getSettings().getWidth("scene"), theGame.getSettings().getHeight("scene"), mainStage, game_scene, capture_scene);
		capture_window.setCenter(captureView);		
		//----- Battle ------------
		battle_window = new BorderPane();
		battle_scene = new Scene(battle_window, theGame.getSettings().getWidth("scene"), theGame.getSettings().getHeight("scene")); //new 
		//----- Animation ---------
		xshift = 0; 
		yshift = 0;
		tic = 0;
		direction = KeyCode.ENTER;
		lastStep = 'L';
		//-----------------------
		row_lowerBound = theGame.getSettings().getLowerBound("graphic");
		col_lowerBound = theGame.getSettings().getLowerBound("graphic");
		row_upperBound = theGame.getSettings().getUpperBound("graphic");
		col_upperBound = theGame.getSettings().getUpperBound("graphic");
		imageSize = theGame.getSettings().getImageSize("original");
		displaySize = theGame.getSettings().getImageSize("display");
		this.setWidth(theGame.getSettings().getWidth("scene"));
		this.setHeight(theGame.getSettings().getHeight("scene"));	
		duration = theGame.getSettings().getTimelineDuration(1);
		timeline = new Timeline(new KeyFrame(Duration.millis(duration), new AnimateStarter()));
		timeline.setCycleCount(Animation.INDEFINITE);
		spritesheet = new Image("file:images/sheets/trainer.png", false);
		resetTrainer();
		initializePane();
	}
	
	/**
	 *	initializes the canvas and draws the zone and trainer 
	 */
	private void initializePane() {
		gc = this.getGraphicsContext2D();
		createImages();
		drawViewableArea();
		drawTrainer();
	}
	/**
	 *	draws the viewable area around the trainer based on his/her location
	 */
	public void drawViewableArea() {
		int pc = (int) theGame.getMap().getTrainer().getCurrentLocation().getX();	//	trainer location (column)
		int pr = (int) theGame.getMap().getTrainer().getCurrentLocation().getY();	//	trainer location (row)
		int zoneNum = theGame.getMap().getTrainer().getZone();	//	trainer location (zone number)
		double rc = 0, cc = 0; //row_index, col_index;
		Tile temp;
		Image img;
		for (int r = row_lowerBound; r <= row_upperBound; r++) {
			for (int c = col_lowerBound; c <= col_upperBound; c++) {
				try {
					temp = theGame.getMap().getZone(zoneNum).getTile(pr + r, pc + c);
					img = getImage(temp.getSourceChar());
					gc.drawImage(img, 0, 0, imageSize, imageSize, (cc * displaySize)+xshift, (rc * displaySize)+yshift, displaySize, displaySize);
					cc++;
				} catch (NullPointerException npe) {
					// needed just in case some weird shit happens
				}
			}
			cc = 0; rc++;
		}
	}
	/**
	 *	clears the canvas 
	 */
	public void clearCanvas() {
		gc.clearRect(0, 0, theGame.getSettings().getWidth("scene"), theGame.getSettings().getHeight("scene"));
	}
	/**
	 *	resets the variables for drawing trainer location
	 */
	public void resetTrainer() {
		sx = theGame.getSettings().getX("sprite");
		sy = theGame.getSettings().getY("sprite");
		sw = theGame.getSettings().getWidth("source");
		sh = theGame.getSettings().getHeight("source");
		dx = theGame.getSettings().getX("trainer");
		dy = theGame.getSettings().getY("trainer");
		dw = theGame.getSettings().getWidth("display");
		dh = theGame.getSettings().getHeight("display");
	}
	/**
	 *	draws the trainer based on a range of values 
	 */
	public void drawTrainer() {
		gc.drawImage(spritesheet, sx, sy, sw, sh, dx, dy, dw, dh);
	}	
	
	/**
	 *	starts the trainer timeline for animation & stores direction the trainer is moving 
	 */
	public void animateTrainer(KeyCode key, boolean d) {
		timeline.play();
		if (direction == KeyCode.ENTER) // first
			direction = key;
		else if (direction != key)
			if (tic == 0)
				direction = key;
		done = d;
	}
	/**
	 *	Animation class relating to trainer animation
	 */
	private class AnimateStarter implements EventHandler<ActionEvent> {
//		public AnimateStarter() {
//		}
		@Override
		public void handle(ActionEvent event) {
			tic++;
			if (direction == KeyCode.RIGHT) {
				if (tic == 1) {
					sx = 89;
					sy = lastStep == 'L' ? 30 : 150;
					lastStep = lastStep == 'L' ? 'R' : 'L';
					xshift = -16;
					col_upperBound += 1;
				}
				if (tic == 2) {
					sy = 0;
					xshift = 0;
					col_upperBound -= 1;
				}
			}
			if (direction == KeyCode.LEFT) {
				if (tic == 1) {
					sx = 28;
					sy = lastStep == 'L' ? 30 : 150;
					lastStep = lastStep == 'L' ? 'R' : 'L';
					xshift = -16;
					col_lowerBound -= 1;
				}
				if (tic == 2) { 
					sy = 0;
					xshift = 0;
					col_lowerBound += 1;
				}
			}
			if (direction == KeyCode.UP) {
				if (tic == 1) {
					sx = 58;
					sy = lastStep == 'L' ? 30 : 150;
					lastStep = lastStep == 'L' ? 'R' : 'L';
					yshift = -16;
					row_lowerBound -= 1;
				}
				if (tic == 2) {
					sy = 0;
					yshift = 0;
					row_lowerBound += 1;
				}
 			}
			if (direction == KeyCode.DOWN) {
				if (tic == 1) {
					sx = -2;
					sy = lastStep == 'L' ? 30 : 150;
					lastStep = lastStep == 'L' ? 'R' : 'L';
					yshift = -16;
				}
				if (tic == 2) {
					sy = 0;
					yshift = 0;
				}
			}
			clearCanvas();
			drawViewableArea();
			drawTrainer();
			
			if (tic > 1) {
				tic = 0;
				if (done || response == "pokemon") {
					timeline.stop();	
					if (response == "pokemon")
						mainStage.setScene(capture_scene);
					else if (response == "trainer") {
						mainStage.hide();
						BattleGUI gui = new BattleGUI();
						gui.setMainStage(mainStage);
						Stage stage2 = new Stage();
		
						try {
							gui.start(stage2);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						//mainStage.setScene(battle_scene);
					}
				}
			}
			if (tic == 1)
					response = theGame.movePlayer(direction);
		}
	}

	
	public void createImages() {
		 ground = new Image("file:images/shrubs/ground-g.bmp");
		 grass = new Image("file:images/shrubs/grass.bmp");
		 bush = new Image("file:images/shrubs/bush.bmp");
		 tree = new Image("file:images/shrubs/tree.bmp");
		 fence = new Image("file:images/misc/fence.bmp");
		 stairs = new Image("file:images/misc/stairs.bmp");
		 hill_bottom = new Image("file:images/hills/hill-bottom.bmp");
		 hill_top = new Image("file:images/hills/hill-top.bmp");
		 hill_middle = new Image("file:images/hills/hill-middle.bmp");
		 hill_left = new Image("file:images/hills/hill-left.bmp");
		 hill_right = new Image("file:images/hills/hill-right.bmp");
		 hill_topleft = new Image("file:images/hills/hill-topleft.bmp");
		 hill_topright = new Image("file:images/hills/hill-topright.bmp");
		 hill_bottomleft = new Image("file:images/hills/hill-bottomleft.bmp");
		 hill_bottomright = new Image("file:images/hills/hill-bottomright.bmp");
		 water_bottom = new Image("file:images/water/water-bc.bmp");
		 water_top = new Image("file:images/water/water-tc.bmp");
		 water_middle = new Image("file:images/water/water-c.bmp");
		 water_left = new Image("file:images/water/water-lc.bmp");
		 water_right = new Image("file:images/water/water-rc.bmp");
		 water_topleft = new Image("file:images/water/water-tl.bmp");
		 water_topright = new Image("file:images/water/water-tr.bmp");
		 water_bottomleft = new Image("file:images/water/water-bl.bmp");
		 water_bottomright = new Image("file:images/water/water-br.bmp");
	}
	public Image getImage(char c) {
		if (c == '_' || c == 'P')
			return ground;
		else if (c == 'G')
			return grass;
		else if (c == 'B') 
			return bush;
		else if (c == 'T') 
			return tree;
		else if (c == 'F') 
			return fence;
		else if (c == 'E') 
			return tree;
		else if (c == 's') 
			return stairs;
		else if (c == 'b') 
			return hill_bottom;
		else if (c == 't') 
			return hill_top;
		else if (c == 'm') 
			return hill_middle;
		else if (c == 'l') 
			return hill_left;
		else if (c == 'r') 
			return hill_right;
		else if (c == 'q') 
			return hill_topleft;
		else if (c == 'o') 
			return hill_topright;
		else if (c == 'z') 
			return hill_bottomleft;
		else if (c == 'n') 
			return hill_bottomright;
		else if (c == '~') 
			return water_bottomleft;
		else if (c == '!') 
			return water_left;
		else if (c == '@') 
			return water_topleft;
		else if (c == '#') 
			return water_top;
		else if (c == 'W')
			return water_middle;
		else if (c == '$') 
			return water_topright;
		else if (c == '%') 
			return water_right;
		else if (c == '^') 
			return water_bottomright;
		else //if (c == '&') 
			return water_bottom;
	}
}