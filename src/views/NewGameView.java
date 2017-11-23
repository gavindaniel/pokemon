package views;

import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.util.Duration;
import map.Tile;
import model.SafariZone;

public class NewGameView extends Canvas implements Observer {

	private SafariZone theGame;
	private GraphicsContext gc;
	private Image spritesheet;
	private Timeline timeline, timeline2;
	private char direction;
	private boolean done;
	private int tic;
	private Label label;
	private Vector<String> profOakLines;

	private static double imageSize, displaySize;	//  16px by 16px	 ,  32px by 32px 
	private static int lowerBound, upperBound;		//	bounds for display
	
	private double sx, sy, sw, sh, dx, dy, dw, dh;
	
	
	@Override
	public void update(Observable o, Object arg) {
		theGame = (SafariZone) o;
	}
	
	/**
	 * @param instance of the game 'PokemonGame'
	 */
	public NewGameView(SafariZone PokemonGame) {
		theGame = PokemonGame;
		label = new Label();
		profOakLines = new Vector<String>();
//		lowerBound = theGame.getSettings().getLowerBound("graphic");
//		upperBound = theGame.getSettings().getUpperBound("graphic");
//		imageSize = theGame.getSettings().getImageSize("original");
//		displaySize = theGame.getSettings().getImageSize("display");
		this.setWidth(theGame.getSettings().getWidth("scene"));
		this.setHeight(theGame.getSettings().getHeight("scene"));	
		timeline = new Timeline(new KeyFrame(Duration.millis(theGame.getSettings().getTimelineDuration(3)), new AnimateStarter()));
//		timeline2 = new Timeline(new KeyFrame(Duration.millis(theGame.getSettings().getTimelineDuration(2)), new AnimateStarter2()));
		timeline.setCycleCount(Animation.INDEFINITE);
//		timeline2.setCycleCount(Animation.INDEFINITE);
//		spritesheet = new Image("file:images/sheets/trainer.png", false);
//		resetTrainer();
		initializePane();
	}
	
	/**
	 *	initializes the canvas and draws the zone and trainer 
	 */
	private void initializePane() {
		gc = this.getGraphicsContext2D();
		
		setupProfOakLines();
		drawOak();
		animateOak();
	}
	
	public void drawOak() {
		Image img = new Image("file:images/newgame/prof-oak.png");
		gc.drawImage(img, 0, 0, 188, 342, 100, 100, 188, 342);
	}	
	
	/**
	 *	starts the trainer timeline for animation & stores direction the trainer is moving 
	 */
	public void animateOak() {
		timeline.play();
	}
	/**
	 *	Animation class relating to trainer animation
	 */
	private class AnimateStarter implements EventHandler<ActionEvent> {
		
		String text, line;
		double x, y, maxWidth;
		int i, index;
		
		public AnimateStarter() {
			tic = 0;
			text = "";
			x = 100; 
			y = 500; 
			maxWidth = 600;
			index = 0;
//			System.out.println(profOakLines.size());
			i = -1;
		}
		@Override
		public void handle(ActionEvent event) {
			tic++;
			i++;
			
			line = profOakLines.get(index);
			
			if (i == line.length()) {
				i = -1;
				tic = 0;
				index++;
				if (index == profOakLines.size()) 
					timeline.stop();
				else {
					line = profOakLines.get(index);
					text = "";
				}
				gc.clearRect(0, 480, theGame.getSettings().getWidth("scene"), 30);
				
			}
			else {
				text += line.charAt(i);
				gc.strokeText(text, x, y, maxWidth);
			}
			
		}
	}
	
	public void setupProfOakLines() {
		
		String text = "";
		
//		profOakLines.add("");
		profOakLines.add("Hello! I'm Professor Oak.");
		profOakLines.add("Welcome to the world of Pokemon!");
		profOakLines.add("What is your name?");
		// user enter text
		profOakLines.add("____ you say? Great to meet you!");
		profOakLines.add("Would you like a few tips and tricks before you get started?");
		// user selects Yes or No
		// add text for Yes
		profOakLines.add("Great! Let's get started!");
		
	}
}