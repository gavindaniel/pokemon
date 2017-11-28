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
	private Timeline timeline, timeline2;
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
		timeline2 = new Timeline(new KeyFrame(Duration.millis(theGame.getSettings().getTimelineDuration(2)), new AnimateStarter2()));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline2.setCycleCount(Animation.INDEFINITE);
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
		
//		System.out.println("x: " + pc + "\ty: " + pr);
		
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
	
	/**
	 *	starts the timeline for shifting the background behind the trainer
	 */
	public void animateMap(KeyCode key, boolean d) {
		timeline2.play();
		direction = key;
		done = d;
	}
	/**
	 *	animation class relating to animation of the background
	 */
	private class AnimateStarter2 implements EventHandler<ActionEvent> {
		Tile temp = new Tile();
		Image img;
		String path = "";
		int pc = (int) theGame.getMap().getTrainer().getCurrentLocation().getX();
		int pr = (int) theGame.getMap().getTrainer().getCurrentLocation().getY();
		double rc = 0;
		double cc = 0;
		int xShift, yShift;
		
		public AnimateStarter2() {
			tic = 0;
			
			xShift = 0;
			yShift = 0;
		}

		@Override
		// This handle method gets called every 100 ms to draw the ship on a new
		// location
		public void handle(ActionEvent event) {
			tic++;
	//		drawViewableArea();
			
	//		if (tic == 1) {
				pc = (int) theGame.getMap().getTrainer().getCurrentLocation().getX();
				pr = (int) theGame.getMap().getTrainer().getCurrentLocation().getY();
	//		}
			
			if (direction == KeyCode.RIGHT) {
	//			xShift += 16;
				yShift = 0;
				if (tic == 1) {
					sx = 89;
					sy = 30;
					xShift = -16;
	//				yShift = 0;
				}
				if (tic == 2) {
					sy = 0;
					xShift = -32;
	//				yShift = 0;
				}
			}
			if (direction == KeyCode.LEFT) {
	//			xShift -= 16;
				yShift = 0;
				if (tic == 1) {
					sx = 28;
					sy = 30;
					xShift = -16;
	//				yShift = 0;
				}
				if (tic == 2) {
					sy = 0;
					xShift = 0;
	//				yShift = 0;
				}
			}
			if (direction == KeyCode.UP) {
				xShift = 0;
	//			yShift -= 16;
				if (tic == 1) {
					sx = 58;
					sy = 30;
	//				xShift = 0;
					yShift = -16;
				}
				if (tic == 2) {
					sy -= 30;
	//				xShift = 0;
					yShift = 0;
				}
 			}
			if (direction == KeyCode.DOWN) {
				xShift = 0;
	//			yShift += 16;
				if (tic == 1) {
					sx = -2;
					sy = 30;
	//				xShift = 0;
					yShift = 16;
				}
				if (tic == 2) { 
					sy -= 30;
	//				xShift = 0;
					yShift = 0;
				}
			}
			
	
			System.out.println("x-shift: " + xShift + "\ty-shift: " + yShift);
			

			
//			for (int r = lowerBound; r <= upperBound; r++) {
//				for (int c = lowerBound; c <= upperBound; c++) {
//					try {
//						int zoneNum = theGame.getMap().getTrainer().getZone();
//						temp = theGame.getMap().getZone(zoneNum).getTile(pr + r, pc + c);
//						path = temp.getImagePath();
//						img = new Image("file:" + path);
//						gc.drawImage(img, 0, 0, imageSize, imageSize, (cc * displaySize) + xShift, (rc * displaySize) + yShift, displaySize, displaySize);
//						cc++;
//					} catch (NullPointerException npe) {
//						// needed just in case some weird shit happens
//					}
//				}
//				cc = 0;
//				rc++;
//			}
			for (int r = lowerBound; r <= upperBound; r++) {
				for (int c = lowerBound; c <= upperBound; c++) {
					try {
						int zoneNum = theGame.getMap().getTrainer().getZone();
						temp = theGame.getMap().getZone(zoneNum).getTile(pr + r, pc + c);
						path = temp.getImagePath();
						img = new Image("file:" + path);
						System.out.println("x: " + ((cc*displaySize) + xShift) + "\t\ty: " + ((rc*displaySize) + yShift));
						gc.drawImage(img, 0, 0, imageSize, imageSize, (cc * displaySize) + xShift, (rc * displaySize) + yShift, displaySize, displaySize);
						cc++;
					} catch (NullPointerException npe) {
						// needed just in case some weird shit happens
					}
				}
				cc = 0;
				rc++;
			}
			
			drawTrainer();
//			gc.drawImage(spritesheet, sx, sy, sw, sh, dx, dy, dw, dh);
				
			if (tic > 1) {
				tic = 0;
				System.out.println("done -> "+ done);
				if (done)
					System.out.println("stopping timeline...");
					timeline2.stop();
			}
			

		}
	}
	
	
}