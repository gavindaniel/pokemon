package controller;

import java.util.Observer;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.SafariZone;
import views.GraphicView;
import views.MapView;
import views.PokemonView;
import views.TextView;

public class SafariZoneMain extends Application {

	private SafariZone theGame;

	private MenuBar menuBar;
	private Observer currentView;
	private Observer textView;
	private Observer graphicView; // to be implemented after textView
	private BorderPane window;
	public static final int height = 400;
	public static final int width = 600;

	char keyPressed;
	boolean surfEnabled;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Pokemon: Safari Zone");
		keyPressed = 'z';
		window = new BorderPane();
		Scene scene = new Scene(window, width, height);

		setupMenus();
		window.setTop(menuBar);
		initializeGameForTheFirstTime();

		scene.setOnKeyReleased(new moveListener());

		// Setup the views
		textView = new TextView(theGame);
		graphicView = new GraphicView(theGame);
		theGame.addObserver(textView);
		theGame.addObserver(graphicView);

		setViewTo(graphicView);

		primaryStage.setScene(scene);
		primaryStage.show();

	}

	private void setViewTo(Observer newView) {
		window.setCenter(null);
		currentView = newView;
		window.setCenter((Node) currentView);
	}

	public void initializeGameForTheFirstTime() {
		theGame = new SafariZone();
	}

	private void setupMenus() {
		MenuItem textV = new MenuItem("Text");
		MenuItem graphicV = new MenuItem("Graphics");
		Menu views = new Menu("Views");
		views.getItems().addAll(textV, graphicV);

		MenuItem newGame = new MenuItem("New Game");
		MenuItem map = new MenuItem("Map");
		MenuItem pokemon = new MenuItem("Pokemon");
		Menu options = new Menu("Options");
		options.getItems().addAll(newGame, map, pokemon, views);

		menuBar = new MenuBar();
		menuBar.getMenus().addAll(options);

		// Add the same listener to all menu items requiring action
		MenuItemListener menuListener = new MenuItemListener();
		newGame.setOnAction(menuListener);
		textV.setOnAction(menuListener);
		graphicV.setOnAction(menuListener);
		map.setOnAction(menuListener);
		pokemon.setOnAction(menuListener);

	}

	public class moveListener implements EventHandler<KeyEvent> {
		@Override
		public void handle(KeyEvent event) {
			if (event.getCode() == KeyCode.UP) {
				theGame.movePlayer('U');	//keyPressed = 'U';
			} else if (event.getCode() == KeyCode.DOWN) {
				theGame.movePlayer('D');//keyPressed = 'D';
			} else if (event.getCode() == KeyCode.LEFT) {
				theGame.movePlayer('L');//keyPressed = 'L';
			} else if (event.getCode() == KeyCode.RIGHT) {
				theGame.movePlayer('R');//keyPressed = 'R';
			}
			
//			else if (event.getCode() == KeyCode.ENTER) {
//				int c = (int) theGame.getMap().getTrainer().getCurrentLocation().getX();
//				int r = (int) theGame.getMap().getTrainer().getCurrentLocation().getY();
//				
//				if (!surfEnabled) {
//					if (theGame.getMap().getBoard()[r][c-1] == '~' || theGame.getMap().getBoard()[r][c-1] == '!' || theGame.getMap().getBoard()[r][c-1] == '@' || theGame.getMap().getBoard()[r][c-1] == '#' || theGame.getMap().getBoard()[r][c-1] == '$' || theGame.getMap().getBoard()[r][c-1] == '%' || theGame.getMap().getBoard()[r][c-1] == '^' || theGame.getMap().getBoard()[r][c-1] == '&') {
//						surfEnabled = true;
//						theGame.movePlayer('L');
//					}
//					else if (theGame.getMap().getBoard()[r][c+1] == '~' || theGame.getMap().getBoard()[r][c+1] == '!' || theGame.getMap().getBoard()[r][c+1] == '@' || theGame.getMap().getBoard()[r][c+1] == '#' || theGame.getMap().getBoard()[r][c+1] == '$' || theGame.getMap().getBoard()[r][c+1] == '%' || theGame.getMap().getBoard()[r][c+1] == '^' || theGame.getMap().getBoard()[r][c+1] == '&') {
//						surfEnabled = true;
//						theGame.movePlayer('R');
//					}
//					else if (theGame.getMap().getBoard()[r+1][c] == '~' || theGame.getMap().getBoard()[r+1][c] == '!' || theGame.getMap().getBoard()[r+1][c] == '@' || theGame.getMap().getBoard()[r+1][c] == '#' || theGame.getMap().getBoard()[r+1][c] == '$' || theGame.getMap().getBoard()[r+1][c] == '%' || theGame.getMap().getBoard()[r+1][c] == '^' || theGame.getMap().getBoard()[r+1][c] == '&') {
//						surfEnabled = true;
//						theGame.movePlayer('D');
//					}
//					else if (theGame.getMap().getBoard()[r-1][c] == '~' || theGame.getMap().getBoard()[r-1][c] == '!' || theGame.getMap().getBoard()[r-1][c] == '@' || theGame.getMap().getBoard()[r-1][c] == '#' || theGame.getMap().getBoard()[r-1][c] == '$' || theGame.getMap().getBoard()[r-1][c] == '%' || theGame.getMap().getBoard()[r-1][c] == '^' || theGame.getMap().getBoard()[r-1][c] == '&') {
//						surfEnabled = true;
//						theGame.movePlayer('U');
//					}
//				}
//			}
		}

	}

	private class MenuItemListener implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent e) {
			// Find out the text of the JMenuItem that was just clicked
			String text = ((MenuItem) e.getSource()).getText();
			if (text.equals("New Game"))
				theGame.startNewGame(); // The computer player has been set and should not change.
			else if (text.equals("Text"))
				setViewTo(textView);
			else if (text.equals("Graphics"))
				setViewTo(graphicView);
			else if (text.equals("Map")) {
				Stage stage = new Stage();
				MapView mv = new MapView(theGame);
				stage.setTitle("Map View");
				stage.setScene(new Scene(mv, 1100, 650));
				stage.show();
			} else if (text.equals("Pokemon")) {
				Stage stage = new Stage();
				PokemonView pv = new PokemonView(theGame);
				stage.setTitle("Pokemon View");
				stage.setScene(new Scene(pv, 1100, 650));
				stage.show();
			}
		}
	}
}
