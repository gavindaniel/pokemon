package map;

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

	private Image trainer;

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
		trainer = new Image("file:./images/trainer1.png");
		initializePane();
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
					img = new Image("file:." + path);
					if (r == 0 && c == 0)
						gc.drawImage(trainer, (cc * imageSize), (rc * imageSize));
					// else gc.drawImage(temp.getImage(), (cc * imageSize), (rc * imageSize));
					else
						gc.drawImage(img, (cc * imageSize), (rc * imageSize));
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
		gc.clearRect(0, 0, width, height);
		theGame = (SafariZone) o;
		drawViewableArea();
	}

}
