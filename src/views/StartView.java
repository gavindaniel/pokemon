package views;

import java.util.Observable;
import java.util.Observer;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.SafariZone;

public class StartView extends Canvas implements Observer {
	
	private Stage mainStage;
	private Scene start_scene, menu_scene;
	private SafariZone theGame;
	private GraphicsContext gc;
	
	private Image startScreen;
	
	@Override
	public void update(Observable o, Object arg) {
		theGame = (SafariZone) o;
	}
	
	/**
	 * @param instance of the game 'PokemonGame'
	 */
	public StartView(SafariZone PokemonGame, Stage primaryStage, Scene startScene, Scene menuScene) {
		mainStage = primaryStage;
		start_scene = startScene;
		menu_scene = menuScene;
		theGame = PokemonGame;
		startScreen = new Image("file:images/misc/start-screen.jpg");
		
		this.setWidth(theGame.getSettings().getWidth("scene"));
		this.setHeight(theGame.getSettings().getHeight("scene"));	
 
		initializePane();
	}
	
	/**
	 *	initializes the canvas and draws the zone and trainer 
	 */
	private void initializePane() {
		gc = this.getGraphicsContext2D();
		start_scene.setOnMouseClicked(new MouseClickListener());
		start_scene.setOnKeyPressed(new KeyListener());
		gc.drawImage(startScreen, 0, 0);
	}

	
	// start menu listeners
	private class MouseClickListener implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent event) {
			mainStage.setScene(menu_scene);
		}
	}

	private class KeyListener implements EventHandler<KeyEvent> {
		@Override
		public void handle(KeyEvent event) {
			if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.SPACE)
				mainStage.setScene(menu_scene);
		}
	}
}