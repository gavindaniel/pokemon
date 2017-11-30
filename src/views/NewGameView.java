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
	
	private Stage mainStage, inputStage;
	private Scene newgame_scene, game_scene, gender_scene, name_scene, tips_scene;
	private SafariZone theGame;
	private GraphicsContext gc;
	private Timeline timeline, timeline2;
	private Vector<String> profOakLines;
	private boolean enter_pressed, done_printing, stage_show;
	// gender input variables
	private GridPane gp_gender;
	private Button male_button, female_button;
	private char gender_pressed;
	// name input variables
	private TextArea text_input;
	private String name_input;
	private GridPane gp_name;
	private Button submit;
	// walkthrough input variables
	private GridPane gp_tips;
	private Button yes_button, no_button;
	
	@Override
	public void update(Observable o, Object arg) {
		theGame = (SafariZone) o;
		stage_show = false;
		text_input.clear();
		animateOak();
	}
	
	/**
	 * @param instance of the game 'PokemonGame'
	 */
	public NewGameView(SafariZone PokemonGame, Stage stage, Scene newgame, Scene game) {
		mainStage = stage;
		newgame_scene = newgame;
		game_scene = game;
		theGame = PokemonGame;
		theGame.startNewGame();
		profOakLines = new Vector<String>();
		// walkthrough window setup
		gp_tips = new GridPane();
		gp_tips.setPrefSize(theGame.getSettings().getWidth("input"), theGame.getSettings().getHeight("input"));
		yes_button = new Button("Yes");
		yes_button.setPrefSize(theGame.getSettings().getWidth("text"), theGame.getSettings().getHeight("text"));
		yes_button.setOnAction(new ButtonListener());
		no_button = new Button("No");
		no_button.setPrefSize(theGame.getSettings().getWidth("text"), theGame.getSettings().getHeight("text"));
		no_button.setOnAction(new ButtonListener());
		GridPane.setConstraints(yes_button, 0, 0);
		GridPane.setConstraints(no_button, 0, 1);
		gp_tips.getChildren().addAll(yes_button, no_button);
		tips_scene = new Scene(gp_tips, theGame.getSettings().getWidth("input"), theGame.getSettings().getHeight("input"));
		// gender window setup
		gp_gender = new GridPane();
		gp_gender.setPrefSize(theGame.getSettings().getWidth("input"), theGame.getSettings().getHeight("input"));
		male_button = new Button("Male");
		male_button.setPrefSize(theGame.getSettings().getWidth("text"), theGame.getSettings().getHeight("text"));
		male_button.setOnAction(new ButtonListener());
		female_button = new Button("Female");
		female_button.setPrefSize(theGame.getSettings().getWidth("text"), theGame.getSettings().getHeight("text"));
		female_button.setOnAction(new ButtonListener());
		GridPane.setConstraints(male_button, 0, 0);
		GridPane.setConstraints(female_button, 0, 1);
		gp_gender.getChildren().addAll(male_button,female_button);
		gender_scene = new Scene(gp_gender, theGame.getSettings().getWidth("input"), theGame.getSettings().getHeight("input"));
		//name window setup
		name_input = "";
		gp_name = new GridPane();
		gp_name.setPrefSize(theGame.getSettings().getWidth("input"), theGame.getSettings().getHeight("input"));
		text_input = new TextArea();
		text_input.setPrefSize(theGame.getSettings().getWidth("text"), theGame.getSettings().getHeight("text")); // width, height
		submit = new Button("Submit");
		submit.setPrefSize(theGame.getSettings().getWidth("text"), theGame.getSettings().getHeight("text"));
		submit.setOnAction(new ButtonListener());
		GridPane.setConstraints(text_input, 0, 0);
		GridPane.setConstraints(submit, 0, 1);
		gp_name.getChildren().addAll(text_input, submit);
		name_scene = new Scene(gp_name, theGame.getSettings().getWidth("input"), theGame.getSettings().getHeight("input"));
		inputStage = new Stage();
		inputStage.setTitle("Name Input");
		inputStage.setScene(name_scene);
		inputStage.hide();
		
		this.setWidth(theGame.getSettings().getWidth("scene"));
		this.setHeight(theGame.getSettings().getHeight("scene"));	
		timeline = new Timeline(new KeyFrame(Duration.millis(theGame.getSettings().getTimelineDuration(2)), new AnimateStarter()));
		timeline.setCycleCount(Animation.INDEFINITE);
		
		timeline2 = new Timeline(new KeyFrame(Duration.millis(3000), new AnimateStarter2()));
		timeline2.setCycleCount(Animation.INDEFINITE);
		
		enter_pressed = false; 
		done_printing = false;
		initializePane();
	}
	
	/**
	 *	initializes the canvas and draws the zone and trainer 
	 */
	private void initializePane() {
		newgame_scene.setOnKeyPressed(new KeyListener());
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
	 *	starts the scene timeline to fade out the scene 
	 */
	public void fadeOutScene() {
		timeline2.play();
	}
	/**
	 *	Animation class relating to trainer animation
	 */
	private class AnimateStarter implements EventHandler<ActionEvent> {
		
		String text, line;
		double x, y, maxWidth;
		int i, index; // tic, 
		
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
			if (index == 0)
				line = profOakLines.get(index);
			
			if (i == line.length()) {
				done_printing = true;
				// check if user needs to enter their name
				if (line == "What is your name?")
					if (!stage_show && name_input == "") {
						stage_show = true;
						inputStage.setTitle("Name Input");
						inputStage.setScene(name_scene);
						inputStage.show();
					}
				if (line == "Would you like a quick walkthrough before you get started?")
					if (!stage_show) {
						stage_show = true;
						inputStage.setTitle("Walkthrough");
						inputStage.setScene(tips_scene);
						inputStage.show();
					}
				if (line == "Use the arrow keys to move around...") {
					Image arrow_keys = new Image("file:images/misc/arrowKeys.png");
					gc.drawImage(arrow_keys, 350, 100);
				}
				if (line == "You can check things like your pokemon from the Menu in-game...") {
					Image arrow_keys = new Image("file:images/misc/menu.png");
					gc.drawImage(arrow_keys, 0, 0);
				}
				// check if the user has pressed 'ENTER' to continue
				if (enter_pressed) {
					if (line == "Use the arrow keys to move around...") {
						gc.clearRect(350, 100, 332, 227);
					}
					if (line == "You can check things like your pokemon from the Menu in-game...") {
						gc.clearRect(0, 0, 130, 56);
					}
					gc.clearRect(0, 480, theGame.getSettings().getWidth("scene"), 30);
					text = "";
					enter_pressed = false;	// reset the flag
					i = 0;
					index++;
					// if we're at the last line for Prof. Oak stop animation	
					if (index == profOakLines.size()) { 
						index = 0; // i should already = 0
						timeline.stop();
						mainStage.setScene(game_scene);
					}
					else {
						if (line == "What is your name?")
							text = theGame.getMap().getTrainer().getName();
						line = profOakLines.get(index);
					}
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
//		profOakLines.add("Welcome to the world of Pokemon!");
		profOakLines.add("Welcome to the Safari Zone!");
		profOakLines.add("What is your name?");
		profOakLines.add(" you say? Great to meet you!");
		profOakLines.add("Would you like a quick walkthrough before you get started?");
//		profOakLines.add("Let's get started!");	
	}
	
	private class KeyListener implements EventHandler<KeyEvent> {
		@Override
		public void handle(KeyEvent event) {
			if (event.getCode() == KeyCode.ENTER)
				if (done_printing)
					enter_pressed = true;
		}
	}
	
	private class ButtonListener implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			if (event.getSource() == submit)
				if (text_input.getText().length() > 0) {
					name_input = text_input.getText();			
					theGame.getMap().getTrainer().setName(name_input);
					inputStage.setTitle("Gender Input");
					inputStage.setScene(gender_scene);
				}
			if (event.getSource() == male_button) {
				gender_pressed = 'M';
				enter_pressed = true;
				stage_show = false;
				inputStage.hide();
			}
			if (event.getSource() == female_button) {
				gender_pressed = 'F';
				enter_pressed = true;
				stage_show = false;
				inputStage.hide();
			}
			if (event.getSource() == yes_button || event.getSource() == no_button) {
				if (event.getSource() == yes_button) {
					profOakLines.add("Great!");
					profOakLines.add("This is the Safari Zone!");
					profOakLines.add("Here, some of the rarest Pokemon in the Hoenn region roam...");
					profOakLines.add("Use the arrow keys to move around...");
					profOakLines.add("You can check things like your pokemon from the Menu in-game...");
					profOakLines.add("You only get 500 steps so make them count...");
					profOakLines.add("You also only get 30 safari balls...");
					profOakLines.add("And that's it!");
					profOakLines.add("Let's get started!");
				}
				else if (event.getSource() == no_button) {
					profOakLines.add("Let's get started!");
				}
				enter_pressed = true;
				inputStage.hide();
			}
		}
		
	}
	
	
	
	
	
	
	
	
	/**
	 *	Animation class relating to trainer animation
	 */
	private class AnimateStarter2 implements EventHandler<ActionEvent> {

		int tic; 
		
		public AnimateStarter2() {
			tic = 0;
		}
		
		@Override
		public void handle(ActionEvent event) {
			
			tic++;
			
			
		}
	}
}