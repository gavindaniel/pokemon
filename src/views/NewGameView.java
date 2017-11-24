package views;

import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.SafariZone;

public class NewGameView extends Canvas implements Observer {

	private SafariZone theGame;
	private GraphicsContext gc;
	private Timeline timeline;
	private Vector<String> profOakLines;
	private Scene origin_scene, game_scene, name_scene;
	private Stage theStage, nameStage;
	private boolean enter_pressed, done_printing;
	private TextArea text_input;
	private String name_input;
	private GridPane gp;
	private Button submit;
	
	@Override
	public void update(Observable o, Object arg) {
		theGame = (SafariZone) o;
	}
	
	/**
	 * @param instance of the game 'PokemonGame'
	 */
	public NewGameView(SafariZone PokemonGame, Scene origin, Scene game, Stage stage) {
		theGame = PokemonGame;
		origin_scene = origin;
		game_scene = game;
		theStage = stage;
		profOakLines = new Vector<String>();
		
		text_input = new TextArea();
		text_input.setPrefSize(theGame.getSettings().getWidth("text"), theGame.getSettings().getHeight("text")); // width, height
		gp = new GridPane();
		gp.setPrefSize(theGame.getSettings().getWidth("input"), theGame.getSettings().getHeight("input"));
		GridPane.setConstraints(text_input, 0, 0);
		submit = new Button("Submit");
		submit.setPrefSize(theGame.getSettings().getWidth("text"), theGame.getSettings().getHeight("text"));
		submit.setOnAction(new ButtonListener());
		GridPane.setConstraints(submit, 0, 1);
		gp.getChildren().addAll(text_input, submit);
		name_scene = new Scene(gp, theGame.getSettings().getWidth("input"), theGame.getSettings().getHeight("input"));
		nameStage = new Stage();
		nameStage.setTitle("Name Input");
		nameStage.setScene(name_scene);
		nameStage.hide();
		
		this.setWidth(theGame.getSettings().getWidth("scene"));
		this.setHeight(theGame.getSettings().getHeight("scene"));	
		timeline = new Timeline(new KeyFrame(Duration.millis(theGame.getSettings().getTimelineDuration(3)), new AnimateStarter()));
		timeline.setCycleCount(Animation.INDEFINITE);
		enter_pressed = false; 
		done_printing = false;
		initializePane();
	}
	
	/**
	 *	initializes the canvas and draws the zone and trainer 
	 */
	private void initializePane() {
		origin_scene.setOnKeyPressed(new KeyListener());
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
		int i, index; // tic, 
		boolean stage_show;
		
		public AnimateStarter() {
//			tic = 0;
			text = "";
			x = 100; 
			y = 500; 
			maxWidth = 600;
			index = 0;
			i = 0;
			stage_show = false;
		}
		@Override
		public void handle(ActionEvent event) {
//			tic++;
			if (index == 0)
				line = profOakLines.get(index);
			
			if (i == line.length()) {
				done_printing = true;
				// check if user needs to enter their name
				if (line == "What is your name?") {
					if (!stage_show) {
						stage_show = true;
						nameStage.show();
					}
					
				}
					
				// check if the user has pressed 'ENTER' to continue
				if (enter_pressed) {
					gc.clearRect(0, 480, theGame.getSettings().getWidth("scene"), 30);
					text = "";
					enter_pressed = false;	// reset the flag
					i = 0;
//					tic = 0;
					index++;
					// if we're at the last line for Prof. Oak stop animation	
					if (index == profOakLines.size()) { 
						timeline.stop();
						theStage.setScene(game_scene);
					}
					else 
						if (line == "What is your name?")
							text = theGame.getMap().getTrainer().getName();
						line = profOakLines.get(index);
				}				
			}
			else {
				done_printing = false;
				text += line.charAt(i);
				gc.strokeText(text, x, y, maxWidth);
				i++;
			}
			
		}
	}
	
	public void setupProfOakLines() {
		profOakLines.add("Hello! I'm Professor Oak.");
		profOakLines.add("Welcome to the world of Pokemon!");
		profOakLines.add("What is your name?");
		// user enter text
		profOakLines.add(" you say? Great to meet you!");
//		profOakLines.add("Would you like a few tips before you get started?");
		// user selects Yes or No
		// add text for Yes
		profOakLines.add("Let's get started!");	
	}
	
	private class KeyListener implements EventHandler<KeyEvent> {
		@Override
		public void handle(KeyEvent event) {
			if (event.getCode() == KeyCode.ENTER) {
				if (done_printing) {
					enter_pressed = true;
				}
			}
		}
	}
	
	private class ButtonListener implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			if (event.getSource() == submit) {
				if (text_input.getText().length() > 0) {
					name_input = text_input.getText();
					enter_pressed = true;
					theGame.getMap().getTrainer().setName(name_input);
					nameStage.hide();
				}
			}
		}
		
	}
}