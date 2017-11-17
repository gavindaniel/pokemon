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
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import map.Tile;
import model.SafariZone;

public class GraphicView extends Canvas implements Observer {

	private SafariZone theGame;
	private GraphicsContext gc;
	private Image spritesheet;
	private Timeline timeline;
	private char direction;
	private boolean done;
	private int tic;

	private static double imageSize, displaySize;	//  16px by 16px	 ,  32px by 32px 
	private static int lowerBound, upperBound;	//	bounds for display
	
	private double sx, sy, sw, sh, dx, dy, dw, dh;
	
	/**
	 * @param instance of the game 'PokemonGame'
	 */
	public GraphicView(SafariZone PokemonGame) {
		theGame = PokemonGame;
		lowerBound = theGame.getSettings().getLowerBound("graphic");
		upperBound = theGame.getSettings().getUpperBound("graphic");
		imageSize = theGame.getSettings().getImageSize("original");
		displaySize = theGame.getSettings().getImageSize("display");
		this.setWidth(theGame.getSettings().getWidth("scene"));
		this.setHeight(theGame.getSettings().getHeight("scene"));	
		spritesheet = new Image("file:images/sheets/trainer.png", false);
		timeline = new Timeline(new KeyFrame(Duration.millis(200), new AnimateStarter()));
//		timeline = new Timeline(new KeyFrame(Duration.millis(500), new AnimateStarter2()));
		timeline.setCycleCount(Animation.INDEFINITE);
		sx = theGame.getSettings().getX("sprite");
		sy = theGame.getSettings().getY("sprite");
		sw = theGame.getSettings().getWidth("source");
		sh = theGame.getSettings().getHeight("source");
		dx = theGame.getSettings().getX("trainer");
		dy = theGame.getSettings().getY("trainer");
		dw = theGame.getSettings().getWidth("display");
		dh = theGame.getSettings().getHeight("display");
		initializePane();
	}
	
	// initialize Pane for the first time
	private void initializePane() {
		gc = this.getGraphicsContext2D();
		drawViewableArea();
		drawTrainer();
	}

	public void drawViewableArea() {

		int pc = (int) theGame.getMap().getTrainer().getCurrentLocation().getX();
		int pr = (int) theGame.getMap().getTrainer().getCurrentLocation().getY();
		double rc = 0;
		double cc = 0;

		Tile temp = new Tile();
		Image img;
		String path = "";

		for (int r = lowerBound; r <= upperBound; r++) {
			for (int c = lowerBound; c <= upperBound; c++) {
				try {
					temp = theGame.getMap().getTile(pr + r, pc + c);
					path = temp.getImagePath();
					img = new Image("file:" + path);
					gc.drawImage(img, 0, 0, imageSize, imageSize, (cc * displaySize), (rc * displaySize), displaySize, displaySize);
					cc++;
				} catch (NullPointerException npe) {
					// needed just in case some weird shit happens
				}
			}
			cc = 0;
			rc++;
		}
	}
	
	public void drawTrainer() {
		gc.drawImage(spritesheet, sx, sy, sw, sh, dx, dy, dw, dh);
	}

	@Override
	public void update(Observable o, Object arg) {
		theGame = (SafariZone) o;
	}
	
	
	// Animation related functions
	public void animateTrainer(char c, boolean d) {
		timeline.play();
		direction = c;
		done = d;
	}


	// TODO 2: handle the TimeLine calls until tic gets too big
	private class AnimateStarter implements EventHandler<ActionEvent> {

//		double sx, sy, sw, sh, dx, dy, dw, dh;

		public AnimateStarter() {
			tic = 0;
		}

		@Override
		// This handle method gets called every 100 ms to draw the ship on a new
		// location
		public void handle(ActionEvent event) {
			tic++;
			drawViewableArea();
			
			if (direction == 'R') {
				if (tic == 1) {
					sx = 89;
					sy = 30;
				}
				if (tic == 2) {
					sy -= 30;
				}
			}
			if (direction == 'L') {
				if (tic == 1) {
					sx = 28;
					sy = 30;
				}
				if (tic == 2) 
					sy -= 30;
			}
			if (direction == 'U') {
				if (tic == 1) {
					sx = 58;
					sy = 30;
				}
				if (tic == 2) 
					sy -= 30;
 			}
			if (direction == 'D') {
				if (tic == 1) {
					sx = -2;
					sy = 30;
				}
				if (tic == 2) 
					sy -= 30;
			}
//			gc.drawImage(spritesheet, sx, sy, sw, sh, dx, dy, dw, dh);
			drawTrainer();
				
			if (tic > 1) {
				tic = 0;
				if (done)
					timeline.stop();
			}
			

		}
	}
	
	
	public void animateMap(char c, boolean d) {
		timeline.play();
		direction = c;
		done = d;
	}


	// TODO 2: handle the TimeLine calls until tic gets too big
	private class AnimateStarter2 implements EventHandler<ActionEvent> {

		double sx, sy, sw, sh, dx, dy, dw, dh;
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
			sx = -2;
			sy = 0;
			sw = 19;
			sh = 25;
			dx = 289;//304
			dy = 272;//297
			dw = 32; //38
			dh = 44; //50
			
			xShift = 0;
			yShift = 0;
		}

		@Override
		// This handle method gets called every 100 ms to draw the ship on a new
		// location
		public void handle(ActionEvent event) {
			tic++;
	//		drawViewableArea();
			
			if (tic == 1) {
			pc = (int) theGame.getMap().getTrainer().getCurrentLocation().getX();
			pr = (int) theGame.getMap().getTrainer().getCurrentLocation().getY();
			}
			
			if (direction == 'R') {
	//			xShift += 16;
				yShift = 0;
				if (tic == 1) {
					sx = 89;
					sy = 30;
					xShift = 16;
	//				yShift = 0;
				}
				if (tic == 2) {
					sy -= 30;
					xShift = 32;
	//				yShift = 0;
				}
			}
			if (direction == 'L') {
				xShift -= 16;
				yShift = 0;
				if (tic == 1) {
					sx = 28;
					sy = 30;
	//				xShift = -16;
	//				yShift = 0;
				}
				if (tic == 2) {
					sy -= 30;
	//				xShift = 0;
	//				yShift = 0;
				}
			}
			if (direction == 'U') {
				xShift = 0;
				yShift -= 16;
				if (tic == 1) {
					sx = 58;
					sy = 30;
	//				xShift = 0;
	//				yShift = -16;
				}
				if (tic == 2) {
					sy -= 30;
	//				xShift = 0;
	//				yShift = 0;
				}
 			}
			if (direction == 'D') {
				xShift = 0;
				yShift += 16;
				if (tic == 1) {
					sx = -2;
					sy = 30;
	//				xShift = 0;
	//				yShift = 16;
				}
				if (tic == 2) { 
					sy -= 30;
	//				xShift = 0;
	//				yShift = 0;
				}
			}
			
	
			System.out.println("x-shift: " + xShift + "\ty-shift: " + yShift);
			

			for (int r = lowerBound; r <= upperBound; r++) {
				for (int c = lowerBound; c <= upperBound; c++) {
					try {
						temp = theGame.getMap().getTile(pr + r, pc + c);
						path = temp.getImagePath();
						img = new Image("file:" + path);
						gc.drawImage(img, 0, 0, imageSize, imageSize, (cc * displaySize) + xShift, (rc * displaySize) + yShift, displaySize, displaySize);
						cc++;
					} catch (NullPointerException npe) {
						// needed just in case some weird shit happens
					}
				}
				cc = 0;
				rc++;
			}
			
			
			
			gc.drawImage(spritesheet, sx, sy, sw, sh, dx, dy, dw, dh);
				
			if (tic > 1) {
				tic = 0;
				if (done)
					timeline.stop();
			}
			

		}
	}
	
	
}
