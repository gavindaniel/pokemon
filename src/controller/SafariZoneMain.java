package controller;

import java.util.Observer;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.SafariZone;
import model.Trainer;
import views.*;
import network.User;
import network.SerailizableTrainer;

/**************************************************************/
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.geometry.*;
import javafx.concurrent.Task;
import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Optional;
//import controller.SafariZoneMain.ServerListener;
import persistence.FileManager;
import persistence.GameLoader;
/**************************************************************/
public class SafariZoneMain extends Application {

	private Stage stage, info_stage;
	private Settings settings;
	private BorderPane start_window, menu_window, newgame_window, loadgame_window, game_window, info_window;
	private Scene start_scene, menu_scene, newgame_scene, loadgame_scene, game_scene, info_scene;

	private MenuBar menuBar;
	private Observer currentView;
	private GraphicView graphicView;
	private TextView textView;
	private StartView startView;
	private MenuView menuView;
	private NewGameView newGameView;
	private LoadGameView loadGameView;
	private PokemonView pokemonView;
	private BagView bagView;
	private TrainerView trainerView;
	
	//------ NEW ---------------
	private BorderPane option_window;
	private Scene option_scene;
	private OptionView optionView;
	/***************************************/
	private int loggedIn;
	public FileManager man;
	private GameLoader gameLoader;
	/////////////////////
	private Socket socket;
	private ObjectOutputStream outputToServer;
	private ObjectInputStream inputFromServer;
	private static final String Address = "localhost";
//	public Trainer altPlayer;
	public SerializableTrainer altPlayer;



	/***************************************/

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		/***************************************/
		 openConnection();
		/***************************************/
		man = new FileManager();
		primaryStage.setTitle("Pokemon: Safari Zone");
		stage = primaryStage;
		settings = new Settings();

		initializeGameForTheFirstTime();
		setupGameMenus();
		setupScenes();
		setupViews();
		
		game_window.setTop(menuBar);
		
		addGameObservers();
		setViewTo(graphicView);
		primaryStage.setScene(start_scene); //replacement scene 
		primaryStage.show();
	}

	private void setViewTo(Observer newView) {
		game_window.setCenter(null);
		currentView = newView;
		game_window.setCenter((Node) currentView);
	}
	
	private void setInfoViewTo(Observer currentView) {
		info_window.setCenter(null); // i think this clears it
		info_window.setCenter((Node) currentView);
		info_stage.show();
	}
	
	public void initializeGameForTheFirstTime() {
		gameLoader = new GameLoader(new SafariZone());
	}

	private void setupScenes() {
		// window initialization
		start_window = new BorderPane();	
		menu_window = new BorderPane();	
		newgame_window = new BorderPane();
		loadgame_window = new BorderPane();
		game_window = new BorderPane();
		// scene initialization 
		start_scene = new Scene(start_window, settings.getWidth("scene"), settings.getHeight("scene"));
		menu_scene = new Scene(menu_window, settings.getWidth("scene"), settings.getHeight("scene"));
		newgame_scene = new Scene(newgame_window, settings.getWidth("scene"), settings.getHeight("scene"));
		loadgame_scene = new Scene(loadgame_window, settings.getWidth("scene"), settings.getHeight("scene"));
		game_scene = new Scene(game_window, settings.getWidth("scene"), settings.getHeight("scene"));
		// adding listeners
		game_scene.setOnKeyPressed(new KeyPressListener());
		game_scene.setOnKeyReleased(new KeyReleaseListener());
	}

	private void setupViews() {
		// in-game views
		textView = new TextView(gameLoader.getSafariZone());
		graphicView = new GraphicView(gameLoader.getSafariZone());
		// other views
		startView = new StartView(gameLoader.getSafariZone(), stage, start_scene, menu_scene);
		start_window.setCenter(startView);
		menuView = new MenuView(gameLoader.getSafariZone(), stage, newgame_scene, loadgame_scene);
		menu_window.setCenter(menuView);
		newGameView = new NewGameView(gameLoader.getSafariZone(), stage, newgame_scene, game_scene);
		newgame_window.setCenter(newGameView);
		loadGameView = new LoadGameView(gameLoader.getSafariZone(), stage, loadgame_scene, menu_scene, game_scene, man, gameLoader);
		loadgame_window.setCenter(loadGameView);
		// trainer information views
		info_stage = new Stage();
		info_window = new BorderPane();
		info_scene = new Scene(info_window, settings.getWidth("info"), settings.getHeight("info"));
		info_stage.setScene(info_scene);
		pokemonView = new PokemonView(gameLoader.getSafariZone());
		bagView = new BagView(gameLoader.getSafariZone());
		trainerView = new TrainerView(gameLoader.getSafariZone());
		// options view
		option_window = new BorderPane();
		option_scene = new Scene(option_window, settings.getWidth("scene"), settings.getHeight("scene"));
		optionView = new OptionView(gameLoader.getSafariZone(), stage, game_scene, option_scene);
	}
	
	private void addGameObservers() {
		gameLoader.getSafariZone().addObserver(textView);
		gameLoader.getSafariZone().addObserver(graphicView);
		gameLoader.getSafariZone().addObserver(pokemonView); 
		gameLoader.getSafariZone().addObserver(bagView);		
		gameLoader.getSafariZone().addObserver(trainerView); 
		gameLoader.getSafariZone().addObserver(newGameView); // new
		gameLoader.getSafariZone().addObserver(optionView);
	}
	
	private void setupGameMenus() {
		// game menu options
		MenuItem pokemon = new MenuItem("Pokemon");
		MenuItem bag = new MenuItem("Bag");
		MenuItem map = new MenuItem("Map");
		MenuItem trainer = new MenuItem("Trainer");
		MenuItem save = new MenuItem("Save");
		MenuItem option = new MenuItem("Options");
		MenuItem exit = new MenuItem("Exit");
		// add the options
		Menu menu = new Menu("Menu");
		menu.getItems().addAll(pokemon, bag, map, trainer, save, option, exit); //, option
		// view menu options
		MenuItem textV = new MenuItem("Text");
		MenuItem graphicV = new MenuItem("Graphics");
		// add the options
		Menu views = new Menu("View");
		views.getItems().addAll(textV, graphicV);
		// setup menu bar
		menuBar = new MenuBar();
		menuBar.getMenus().addAll(menu, views);
		// Add the same listener to all menu items requiring action
		MenuItemListener menuListener = new MenuItemListener();
		pokemon.setOnAction(menuListener);
		bag.setOnAction(menuListener);
		map.setOnAction(menuListener);
		trainer.setOnAction(menuListener);
		save.setOnAction(menuListener);
		option.setOnAction(menuListener);
		exit.setOnAction(menuListener);
		// view menu listeners
		textV.setOnAction(menuListener);
		graphicV.setOnAction(menuListener);
	}
	
	
	public class KeyPressListener implements EventHandler<KeyEvent> {
		@Override
		public void handle(KeyEvent event) {
			if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.RIGHT) {
//				gameLoader.getSafariZone().movePlayer(event.getCode());
				graphicView.animateTrainer(event.getCode(), false);
				
		           try {


		                  outputToServer.writeObject(new SerilizableTrainer(gameLoader.getSafariZone().getMap().getTrainer()));


		//System.out.println("PkMain: "+gameLoader.getSafariZone().getTrainerLoc().getX()+" "+gameLoader.getSafariZone().getTrainerLoc().getY());
			



		         	} catch (IOException e) {
					e.printStackTrace();
				}

		
			
			} 
		}
	}

	public class KeyReleaseListener implements EventHandler<KeyEvent> {
		@Override
		public void handle(KeyEvent event) {
			if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.RIGHT) {
				graphicView.animateTrainer(event.getCode(), true);
			} 
		}
	}


	private class MenuItemListener implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			String text = ((MenuItem) e.getSource()).getText();
			if (text.equals("Pokemon"))
				setInfoViewTo(pokemonView);
			else if (text.equals("Bag"))
				setInfoViewTo(bagView);
			else if (text.equals("Map")) {
				Stage stage = new Stage();
				stage.setTitle("Map View");
				stage.setScene(new Scene(new MapView(gameLoader.getSafariZone()), gameLoader.getSafariZone().getSettings().getWidth("map"), gameLoader.getSafariZone().getSettings().getHeight("map")));
				stage.show();
			}
			else if (text.equals("Trainer")) 
					setInfoViewTo(trainerView);
			else if (text.equals("Save")) {
				if (loggedIn == 0) {
					stage.setScene(null);
					stage.setScene(loadgame_scene);
				}
				// UD man.pushUserData();
			} else if (text.equals("Options")) {
				setViewTo(optionView);
			}
			else if (text.equals("Exit")) {
				// UD man.pushUserData();
				loggedIn = 0;
//				gameLoader.setSafariZone(null);
//				gameLoader.setSafariZone(new SafariZone());
//				graphicView = null;
//				graphicView = new GraphicView(gameLoader.getSafariZone());
//				gameLoader.getSafariZone().addObserver(graphicView);
				stage.setScene(menu_scene);			
			} 
			else if (text.equals("Text"))
				setViewTo(textView);
			else if (text.equals("Graphics"))
				setViewTo(graphicView);		
		}
	}

	//////////////// work in progress

	private void openConnection() {
		// Our server is on our computer, but make sure to use the same port.
		try {
			socket = new Socket(Address, 4001);
			outputToServer = new ObjectOutputStream(socket.getOutputStream());
			inputFromServer = new ObjectInputStream(socket.getInputStream());

			altPlayer = new SerializableTrainer();

			// SeverListener will have a while(true) loop
			ServerListener listener = new ServerListener();
			// Note: Need setDaemon when started with a JavaFX App, or it crashes.

			Thread thread = new Thread(listener);
			thread.setDaemon(true);
			thread.start();

		} catch (IOException e) {
		}
	}

	private class ServerListener extends Task<Object> {
		@Override
		public void run() {
			while (true) {
				try {
					altPlayer = (SerializableTrainer) inputFromServer.readObject();
					System.out.println("C: altPlayer: " + altPlayer.getCurrentLocation().getX() + " " + altPlayer.getCurrentLocation().getY());
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		@Override
		protected Object call() throws Exception {
			// Not using this call, but we need to override it to compile
			return null;
		}
	}


	/////////////////////////
}
