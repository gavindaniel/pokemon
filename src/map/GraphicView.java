package map;

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
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import model.SafariZone;

public class GraphicView extends BorderPane implements Observer {

	private SafariZone theGame;
	private Canvas canvas;
	private GraphicsContext gc;
	private GridPane gp1;

	private Image trainer;
	private Image spritesheet;
	private Timeline timeline;
	private char direction;
	private boolean done;
	private int tic;

	private static final double height = 400;
	private static final double width = 600;
	private static final double imageSize = 16; // 16px by 16px

	/**
	 * @param instance of the game 'PokemonGame'
	 */
	public GraphicView(SafariZone PokemonGame) {
		theGame = PokemonGame;
		canvas = new Canvas(width, height);
		gp1 = new GridPane();
		trainer = new Image("file:./images/trainer1.png", false);
		
		spritesheet = new Image("file:images/sheets/trainer.png", false);
		timeline = new Timeline(new KeyFrame(Duration.millis(100), new AnimateStarter()));
		timeline.setCycleCount(Animation.INDEFINITE);
		
		initializePane();
	}
	
	public void animateTrainer(char c, boolean d) {
		timeline.play();
		direction = c;
		done = d;
	}


	// TODO 2: handle the TimeLine calls until tic gets too big
	private class AnimateStarter implements EventHandler<ActionEvent> {
//		int tic;
		double sx, sy, sw, sh, dx, dy, dw, dh;

		public AnimateStarter() {
			tic = 0;

			sx = 0;
			sy = 0;
			sw = 19;
			sh = 25;
			dx = 288;
			dy = 297;
			dw = 19;
			dh = 25;
		}

		@Override
		// This handle method gets called every 100 ms to draw the ship on a new
		// location
		public void handle(ActionEvent event) {
			tic++;
//			g2.drawImage(dirt, 0, 0);
			drawViewableArea();
			System.out.println(direction + " -> " + tic);
			
			if (direction == 'R') {
//				dx += 5;
				if (tic == 1) {
					sx = 89;
					sy = 30;
				}
				if (tic == 2) {
					sy -= 30;
				}
			}
			if (direction == 'L') {
//				dx -= 5;
				if (tic == 1) {
					sx = 28;
					sy = 30;
				}
				if (tic == 2) 
					sy -= 30;
			}
			if (direction == 'U') {
//				dy -= 5;
				if (tic == 1) {
					sx = 58;
					sy = 30;
				}
				if (tic == 2) 
					sy -= 30;
 			}
			if (direction == 'D') {
//				dy += 5;
				if (tic == 1) {
					sx = -2;
					sy = 30;
				}
				if (tic == 2) 
					sy -= 30;
			}
			gc.drawImage(spritesheet, sx, sy, sw, sh, dx, dy, dw, dh);
				
			if (tic > 1) {
				tic = 0;
				if (done)
					timeline.stop();
			}
			

		}
	}

	// initialize Pane for the first time
	private void initializePane() {
		gc = canvas.getGraphicsContext2D();
		gp1.setPrefHeight(height);
		gp1.setPrefWidth(width);
		gp1.getChildren().addAll(canvas);
		this.setCenter(gp1);
		drawViewableArea();
	}

	public void drawViewableArea() {

		int pc = (int) theGame.getMap().getTrainer().getCurrentLocation().getX();
		int pr = (int) theGame.getMap().getTrainer().getCurrentLocation().getY();
		int cc = 0;
		int rc = 0;
		int lowerBound = -19;
		int upperBound = 20;

		Tile temp = new Tile();
		Image img;
		String path = "";

		for (int r = lowerBound; r < upperBound; r++) {
			for (int c = lowerBound; c < upperBound; c++) {
				try {
					temp = theGame.getMap().getTile(pr + r, pc + c);
					path = temp.getImagePath();
					img = new Image("file:" + path);
					gc.drawImage(img, (cc * imageSize), (rc * imageSize));
//					if (r == 0 && c == 0)
//						gc.drawImage(spritesheet, 0, 0, 19, 25, (cc * imageSize), (rc * imageSize), 19, 25);
//						gc.drawImage(trainer, (cc * imageSize), (rc * imageSize));
					// else gc.drawImage(temp.getImage(), (cc * imageSize), (rc * imageSize));
//					else
//						gc.drawImage(img, (cc * imageSize), (rc * imageSize));
					cc++;
				} catch (NullPointerException npe) {
					System.out.println("(r,c) = [" + r + "," + c + "]");
					System.out.println("Tile -> " + "[" + (pr + r) + "," + (pc + c) + "]");
					System.out.println();
					// System.out.println("Not found -> " + temp.getImagePath()); //idk
				}
			}
			cc = 0;
			rc++;
		}

	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
//		gc.clearRect(0, 0, width, height);
		theGame = (SafariZone) o;
		drawViewableArea();
	}

}
