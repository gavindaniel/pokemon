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
import views.LoginView;
import views.MapView;
import views.PokemonView;
import views.TextView;
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
	private Observer textView;
	private Observer graphicView; // to be implemented after textView
	private BorderPane window;
	public static final int height = 400;
	public static final int width = 600;
	char keyPressed;
	boolean surfEnabled;

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
		window = new BorderPane();
		Scene scene = new Scene(window, width, height);

		setupMenus();
		window.setTop(menuBar);
		initializeGameForTheFirstTime();

		scene.setOnKeyReleased(new moveListener());

		// Setup the views
		textView = new TextView(theGame);
		graphicView = new GraphicView(theGame);
		
/***************************************/
//		textView = new TextView(gameLoader.getSafariZone());
//        loginView = new LoginView(man,gameLoader,textView);
/***************************************/
		
		theGame.addObserver(textView);
		theGame.addObserver(graphicView);

/***********************/
		//gameLoader.getPokemon().addObserver(textView);
/***********************/
		
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
		
/***************************************/
//		Menu user = new Menu("User");
//	    MenuItem signIn = new MenuItem("Sign In");
//      MenuItem signOut = new MenuItem("Sign Out");
/***************************************/
            
		menuBar = new MenuBar();
		menuBar.getMenus().addAll(options);
		
/***************************************/
//		menuBar.getMenus().addAll(options, user);
/***************************************/
		
		// Add the same listener to all menu items requiring action
		MenuItemListener menuListener = new MenuItemListener();
		newGame.setOnAction(menuListener);
		textV.setOnAction(menuListener);
		graphicV.setOnAction(menuListener);
		map.setOnAction(menuListener);
		pokemon.setOnAction(menuListener);

/***************************************/
//		signIn.setOnAction(menuListener); 
//        signOut.setOnAction(menuListener);
/***************************************/
		
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
