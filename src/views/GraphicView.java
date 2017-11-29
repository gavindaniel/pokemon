package views;

import java.util.Observable;
import java.util.Observer;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import map.Tile;
import model.SafariZone;

public class GraphicView extends Canvas implements Observer {

	private SafariZone theGame;
	private GraphicsContext gc;
	private Image spritesheet;
	private Timeline timeline;
	private KeyCode direction;
	private boolean done;
	private int tic;

	private static double imageSize, displaySize;	//  16px by 16px	 ,  32px by 32px 
	private static int lowerBound, upperBound;		//	bounds for display
	
	private double sx, sy, sw, sh, dx, dy, dw, dh;
	
	private int print_count;
	private double xshift, yshift;
	@Override
	public void update(Observable o, Object arg) {
		theGame = (SafariZone) o;
//		drawViewableArea();
	}
	
	/**
	 * @param instance of the game 'PokemonGame'
	 */
	public GraphicView(SafariZone PokemonGame) {
		print_count = 0;
		xshift = 0; yshift = 0;
		tic = 0;
		//-----------------------
		theGame = PokemonGame;
		lowerBound = theGame.getSettings().getLowerBound("graphic");
		upperBound = theGame.getSettings().getUpperBound("graphic");
		imageSize = theGame.getSettings().getImageSize("original");
		displaySize = theGame.getSettings().getImageSize("display");
		this.setWidth(theGame.getSettings().getWidth("scene"));
		this.setHeight(theGame.getSettings().getHeight("scene"));	
		timeline = new Timeline(new KeyFrame(Duration.millis(theGame.getSettings().getTimelineDuration(1)), new AnimateStarter()));
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
		drawViewableArea();
		drawTrainer();
	}
	/**
	 *	draws the viewable area around the trainer based on his/her location
	 */
	public void drawViewableArea() {
		print_count++;
		System.out.println("printing map..." + print_count);
		
		int pc = (int) theGame.getMap().getTrainer().getCurrentLocation().getX();	//	trainer location (column)
		int pr = (int) theGame.getMap().getTrainer().getCurrentLocation().getY();	//	trainer location (row)
		int zoneNum = theGame.getMap().getTrainer().getZone();	//	trainer location (zone number)
		double rc = 0, cc = 0; //row_index, col_index;
		Tile temp;
		Image img;
		for (int r = lowerBound; r <= upperBound; r++) {
			for (int c = lowerBound; c <= upperBound; c++) {
				try {
					temp = theGame.getMap().getZone(zoneNum).getTile(pr + r, pc + c);
					img = new Image("file:" + temp.getImagePath());
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
		direction = key;
		done = d;
	}
	/**
	 *	Animation class relating to trainer animation
	 */
	private class AnimateStarter implements EventHandler<ActionEvent> {
//		public AnimateStarter() {
//			
//		}
		@Override
		public void handle(ActionEvent event) {
			tic++;
//			drawViewableArea();
			if (direction == KeyCode.RIGHT) {
				if (tic == 1) {
					sx = 89;
					sy = 30;
					xshift = -16;
				}
				if (tic == 2) {
					sy -= 30;
					xshift = 0;
				}
			}
			if (direction == KeyCode.LEFT) {
				if (tic == 1) {
					sx = 28;
					sy = 30;
					xshift = 16;
				}
				if (tic == 2) { 
					sy -= 30;
					xshift = 0;
				}
			}
			if (direction == KeyCode.UP) {
				if (tic == 1) {
					sx = 58;
					sy = 30;
					yshift = +16;
				}
				if (tic == 2) {
					sy -= 30;
					yshift = 0;
				}
 			}
			if (direction == KeyCode.DOWN) {
				if (tic == 1) {
					sx = -2;
					sy = 30;
					yshift = -16;
				}
				if (tic == 2) {
					sy -= 30;
					yshift = 0;
				}
			}
			drawViewableArea();
			drawTrainer();		
			if (tic > 1) {
				tic = 0;
				if (done)
					timeline.stop();
			}
			if (tic == 1)
				theGame.movePlayer(direction);
		}
	}
}