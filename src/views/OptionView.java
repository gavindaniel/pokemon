package views;

import java.util.Observable;
import java.util.Observer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.SafariZone;

public class OptionView extends GridPane implements Observer {
	
	private Stage main_stage;
	private Scene game_scene, option_scene;
	private SafariZone theGame;
	private Button gs_slow, gs_normal, gs_fast;
	private Button ts_slow, ts_normal, ts_fast;
	private Label game_speed_label, text_speed_label;
	
	
	
	@Override
	public void update(Observable o, Object arg) {
		theGame = (SafariZone) o;
	}
	
	/**
	 * @param instance of the game 'PokemonGame'
	 */
	public OptionView(SafariZone PokemonGame, Stage primaryStage, Scene gameScene, Scene optionScene) {
		main_stage = primaryStage;
		game_scene = gameScene;
		option_scene = optionScene;
		theGame = PokemonGame;
		
		
		this.setWidth(theGame.getSettings().getWidth("scene"));
		this.setHeight(theGame.getSettings().getHeight("scene"));	
		this.setHgap(20);
		this.setVgap(10);
		
		game_speed_label = new Label("Game Speed: ");
		game_speed_label.setFont(new Font("Calibri", theGame.getSettings().getFontSize("option label")));
		text_speed_label = new Label("Text Speed: ");
		text_speed_label.setFont(new Font("Calibri", theGame.getSettings().getFontSize("option label")));
		gs_slow = new Button("Slow");
		gs_slow.setMinSize(theGame.getSettings().getWidth("option button"), theGame.getSettings().getHeight("option button"));
		gs_slow.setFont(new Font("Calibri", theGame.getSettings().getFontSize("option button")));
		gs_normal = new Button("Normal");
		gs_normal.setMinSize(theGame.getSettings().getWidth("option button"), theGame.getSettings().getHeight("option button"));
		gs_normal.setFont(new Font("Calibri", theGame.getSettings().getFontSize("option button")));
		gs_fast = new Button("Fast");
		gs_fast.setMinSize(theGame.getSettings().getWidth("option button"), theGame.getSettings().getHeight("option button"));
		gs_fast.setFont(new Font("Calibri", theGame.getSettings().getFontSize("option button")));
		ts_slow = new Button("Slow");
		ts_slow.setMinSize(theGame.getSettings().getWidth("option button"), theGame.getSettings().getHeight("option button"));
		ts_slow.setFont(new Font("Calibri", theGame.getSettings().getFontSize("option button")));
		ts_normal = new Button("Normal");
		ts_normal.setMinSize(theGame.getSettings().getWidth("option button"), theGame.getSettings().getHeight("option button"));
		ts_normal.setFont(new Font("Calibri", theGame.getSettings().getFontSize("option button")));
		ts_fast = new Button("Fast");
		ts_fast.setMinSize(theGame.getSettings().getWidth("option button"), theGame.getSettings().getHeight("option button"));
		ts_fast.setFont(new Font("Calibri", theGame.getSettings().getFontSize("option button")));
		
		gs_slow.setOnAction(new ButtonListener());
		gs_normal.setOnAction(new ButtonListener());
		gs_fast.setOnAction(new ButtonListener());
		ts_slow.setOnAction(new ButtonListener());
		ts_normal.setOnAction(new ButtonListener());
		ts_fast.setOnAction(new ButtonListener());
		
		initializePane();
	}
	
	/**
	 *	initializes the canvas and draws the zone and trainer 
	 */
	private void initializePane() {
		GridPane.setConstraints(game_speed_label, 1, 1);
		GridPane.setConstraints(gs_slow, 2, 1);
		GridPane.setConstraints(gs_normal, 3, 1);
		GridPane.setConstraints(gs_fast, 4, 1);
		GridPane.setConstraints(text_speed_label, 1, 2);
		GridPane.setConstraints(ts_slow, 2, 2);
		GridPane.setConstraints(ts_normal, 3, 2);
		GridPane.setConstraints(ts_fast, 4, 2);
		this.getChildren().addAll(game_speed_label, gs_slow, gs_normal, gs_fast, text_speed_label, ts_slow, ts_normal, ts_fast);
	}
	
	
	private class ButtonListener implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			if (event.getSource() == gs_slow)
				theGame.getSettings().setTimelineDuration(1, 300);
			if (event.getSource() == gs_normal)
				theGame.getSettings().setTimelineDuration(1, 150);
			if (event.getSource() == gs_fast)
				theGame.getSettings().setTimelineDuration(1, 75);
			if (event.getSource() == ts_slow)
				theGame.getSettings().setTimelineDuration(2, 100);
			if (event.getSource() == ts_normal)
				theGame.getSettings().setTimelineDuration(2, 50);
			if (event.getSource() == ts_fast)
				theGame.getSettings().setTimelineDuration(2, 10);
		}
	}
}