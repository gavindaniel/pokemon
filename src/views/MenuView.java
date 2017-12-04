package views;

import java.util.Observable;
import java.util.Observer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.SafariZone;

public class MenuView extends GridPane implements Observer {
	
	private Stage mainStage;
	private Scene newgame_scene, loadgame_scene;
	private SafariZone theGame;
	private Button newGameButton, loadGameButton;
	
	
	
	@Override
	public void update(Observable o, Object arg) {
		theGame = (SafariZone) o;
	}
	
	/**
	 * @param instance of the game 'PokemonGame'
	 */
	public MenuView(SafariZone PokemonGame, Stage primaryStage, Scene newGameScene, Scene loadGameScene) {
		mainStage = primaryStage;
		newgame_scene = newGameScene;
		loadgame_scene = loadGameScene;
		theGame = PokemonGame;
		
		this.setWidth(theGame.getSettings().getWidth("scene"));
		this.setHeight(theGame.getSettings().getHeight("scene"));	
		this.setHgap(10);
		this.setVgap(10);
		
		newGameButton = new Button("New Game");
		newGameButton.setMinSize(300, 100);
		newGameButton.setOnAction(new ButtonListener());
		loadGameButton = new Button("Load Game...");
		loadGameButton.setMinSize(300, 100);
		loadGameButton.setOnAction(new ButtonListener());
		
		initializePane();
	}
	
	/**
	 *	initializes the canvas and draws the zone and trainer 
	 */
	private void initializePane() {
		newGameButton.setOnAction(new ButtonListener());
		loadGameButton.setOnAction(new ButtonListener());
		GridPane.setConstraints(newGameButton, 1, 1);
		GridPane.setConstraints(loadGameButton, 1, 2);
		this.getChildren().addAll(newGameButton, loadGameButton);
	}
	
	
	private class ButtonListener implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			if (event.getSource() == newGameButton) {
				theGame.startNewGame();
				mainStage.setScene(newgame_scene);
			} 
			else if (event.getSource() == loadGameButton) {
				mainStage.setScene(loadgame_scene);
			}
		}
	}
}