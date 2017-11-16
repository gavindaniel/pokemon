package controller;

import java.util.Observer;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import map.GraphicView;
import map.MapView;
import map.PokemonView;
import map.TextView;
import menus.LoginView;
import menus.MainMenu;
import model.SafariZone;

/**************************************************************/
import java.net.*;
import java.io.*;
import javafx.concurrent.Task;
import javafx.collections.ObservableList;
import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Optional;
//import controller.SafariZoneMain.ServerListener;
import javafx.concurrent.Task;
//import views.LoginView;
/**************************************************************/

public class SafariZoneMain extends Application {

	private SafariZone theGame;
	private MenuBar menuBar;
	private Observer currentView;
//	private Observer textView;
//	private Observer graphicView; // to be implemented after textView
	private GraphicView graphicView;
	private TextView textView;
	private BorderPane window;
	private Settings settings;
	char keyPressed;
	boolean surfEnabled;
	
	private MainMenu mainView;

/***************************************/
//		private GameLoader gameLoader;
		private Socket socket;
        private ObjectOutputStream outputToServer;
        private ObjectInputStream inputFromServer;
        private static final String Address = "localhost";
        public Point altPlayer;
 //       FileManager man = new FileManager();
/***************************************/
        
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
/***************************************/
//      openConnection();
/***************************************/
		primaryStage.setTitle("Pokemon: Safari Zone");
		keyPressed = 'z';
		settings = new Settings();
		window = new BorderPane();
		Scene scene = new Scene(window, settings.getWidth("scene"), settings.getHeight("scene"));

		setupMenus();
//		window.setTop(menuBar);
		initializeGameForTheFirstTime();

		scene.setOnKeyPressed(new KeyPressListener());
		scene.setOnKeyReleased(new KeyReleaseListener());

		// Setup the views
		textView = new TextView(theGame);
		graphicView = new GraphicView(theGame);
		
		mainView = new MainMenu();
		
/***************************************/
//		textView = new TextView(gameLoader.getSafariZone());
//        loginView = new LoginView(man,gameLoader,textView);
/***************************************/
		
		theGame.addObserver(textView);
		theGame.addObserver(graphicView);

/***********************/
		//gameLoader.getPokemon().addObserver(textView);
/***********************/
		
		setViewTo(mainView); //setViewTo(graphicView);

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
		

		// game menu options
		MenuItem pokemon = new MenuItem("Pokemon");
		MenuItem bag = new MenuItem("Bag");
		MenuItem map = new MenuItem("Map");
		MenuItem trainer = new MenuItem("Trainer");
		MenuItem save = new MenuItem("Save");
		MenuItem option = new MenuItem("Option");
		MenuItem exit = new MenuItem("Exit");
		// add the options
		Menu menu = new Menu("Menu");
		menu.getItems().addAll(pokemon, bag, map, trainer, save, option, exit);
		
		// view menu options
		MenuItem textV = new MenuItem("Text");
		MenuItem graphicV = new MenuItem("Graphics");
		// add the options
		Menu views = new Menu ("View");
		views.getItems().addAll(textV, graphicV);
		
//		MenuItem newGame = new MenuItem("New Game");
		
/***************************************/
//		Menu user = new Menu("User");
//	    MenuItem signIn = new MenuItem("Sign In");
//      MenuItem signOut = new MenuItem("Sign Out");
/***************************************/
            
		menuBar = new MenuBar();
		menuBar.getMenus().addAll(menu,views);
		
/***************************************/
//		menuBar.getMenus().addAll(options, user);
/***************************************/
		
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
		

/***************************************/
//		signIn.setOnAction(menuListener); 
//        signOut.setOnAction(menuListener);
/***************************************/
		
	}

	public class KeyPressListener implements EventHandler<KeyEvent> {
		@Override
		public void handle(KeyEvent event) {
			if (event.getCode() == KeyCode.UP) {
//				graphicView.animateMap('U', false);
				graphicView.animateTrainer('U', false);
				theGame.movePlayer('U');	//keyPressed = 'U';
			} else if (event.getCode() == KeyCode.DOWN) {
//				graphicView.animateMap('D', false);
				graphicView.animateTrainer('D', false);
				theGame.movePlayer('D');//keyPressed = 'D';
			} else if (event.getCode() == KeyCode.LEFT) {
//				graphicView.animateMap('L', false);
				graphicView.animateTrainer('L', false);
				theGame.movePlayer('L');//keyPressed = 'L';
			} else if (event.getCode() == KeyCode.RIGHT) {
//				graphicView.animateMap('R', false);
				graphicView.animateTrainer('R', false);
				theGame.movePlayer('R');//keyPressed = 'R';
			}
			
			
			
			if (event.getCode() == KeyCode.M) {
				Stage stage = new Stage();
				stage.setTitle("Menu View");
//				stage.setScene(new Scene(m, theGame.getSettings().getWidth("map"), theGame.getSettings().getHeight("map")));
				stage.show();
			}
		}

	}
	public class KeyReleaseListener implements EventHandler<KeyEvent> {
		@Override
		public void handle(KeyEvent event) {
			if (event.getCode() == KeyCode.UP) {
//				graphicView.animateTrainer('U', true);
				graphicView.animateMap('U', true);
			} else if (event.getCode() == KeyCode.DOWN) {
//				graphicView.animateTrainer('D', true);
				graphicView.animateMap('D', true);
			} else if (event.getCode() == KeyCode.LEFT) {
//				graphicView.animateTrainer('L', true);
				graphicView.animateMap('L', true);
			} else if (event.getCode() == KeyCode.RIGHT) {
//				graphicView.animateTrainer('R', true);
				graphicView.animateMap('R', true);
			}
		}

	}

	private class MenuItemListener implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent e) {
			// Find out the text of the JMenuItem that was just clicked
			String text = ((MenuItem) e.getSource()).getText();
			if (text.equals("New Game"))
				theGame.startNewGame(); 
			else if (text.equals("Text"))
				setViewTo(textView);
			else if (text.equals("Graphics"))
				setViewTo(graphicView);
			else if (text.equals("Map")) {
				Stage stage = new Stage();
				MapView mv = new MapView(theGame);
				stage.setTitle("Map View");
				stage.setScene(new Scene(mv, theGame.getSettings().getWidth("map"), theGame.getSettings().getHeight("map")));
				stage.show();
			} else if (text.equals("Pokemon")) {
				Stage stage = new Stage();
				PokemonView pv = new PokemonView(theGame);
				stage.setTitle("Pokemon View");
				stage.setScene(new Scene(pv, theGame.getSettings().getWidth("map"), theGame.getSettings().getHeight("map")));
				stage.show();
			} 
		
/**************************************************************/
//			else if (text.equals("Sign In")){
//		  		setViewTo(loginView);
//
//		      }else if (text.equals("Sign Out")){
//	    
//	                // FileManager will push User data
//	  
//	                man.pushUserData();  
//	                // new SafariZone and textView
//	                gameLoader.setSafariZone(null); 
//	                gameLoader.setSafariZone(new SafariZone());
//	                textView = null; 
//	                textView = new TextView(gameLoader.getSafariZone()); 
//	                loginView = new LoginView(man,gameLoader,textView);
//	                gameLoader.getSafariZone().addObserver(textView); 
//	                setViewTo(textView);
//		
//	              }
//		    }
/**************************************************************/
		}
		
	}
	
	private class ButtonListener implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			// TODO Auto-generated method stub
			window.setTop(menuBar);
		}
		
	}
	
	
	
	
	
////////////////work in progress




	private void openConnection() {
	// Our server is on our computer, but make sure to use the same port.
	try {
	socket = new Socket(Address, 4001);
	outputToServer = new ObjectOutputStream(socket.getOutputStream());
	inputFromServer = new ObjectInputStream(socket.getInputStream());
	
	altPlayer = new Point();
	
	
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
	public void run()  {
	
	
	while(true){
	
	try {
	    altPlayer = (Point)inputFromServer.readObject();
	
	System.out.println("C: altPlayer: "+altPlayer.getX()+" "+altPlayer.getY());         
	
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
