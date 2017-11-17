package controller;

import java.util.Observer;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.SafariZone;
import views.GraphicView;
import views.LoginView;
import views.MapView;
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

	private Stage stage;
	private Scene scene1, scene2, scene3;
	private BorderPane window1, window2, window3;
	
	private SafariZone theGame;
	private MenuBar menuBar;
	private Observer currentView;
	private GraphicView graphicView;
	private TextView textView;
	private MapView mapView;
	
	private Settings settings;
	char keyPressed;
	
	private Button newGameButton;
	private Button loadGameButton;

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
		stage = primaryStage;
		keyPressed = 'z';
		settings = new Settings();
		
		setupScenes();
		setupWindow1();
		setupWindow3();
		setupMenus();
		
		window2.setTop(menuBar);
		initializeGameForTheFirstTime();

		scene2.setOnKeyPressed(new KeyPressListener());
		scene2.setOnKeyReleased(new KeyReleaseListener());

		scene3.setOnMouseClicked(new MouseClickListener());
		// Setup the views
		textView = new TextView(theGame);
		graphicView = new GraphicView(theGame);
		mapView = new MapView(theGame);
		
		
/***************************************/
//		textView = new TextView(gameLoader.getSafariZone());
//        loginView = new LoginView(man,gameLoader,textView);
/***************************************/
		
		theGame.addObserver(textView);
		theGame.addObserver(graphicView);

/***********************/
		//gameLoader.getPokemon().addObserver(textView);
/***********************/
		
		setViewTo(graphicView); //setViewTo(graphicView);

		primaryStage.setScene(scene3);
		primaryStage.show();

	}

	private void setViewTo(Observer newView) {
		window2.setCenter(null);
		currentView = newView;
		window2.setCenter((Node) currentView);
	}

	public void initializeGameForTheFirstTime() {
		theGame = new SafariZone();
	}

	private void setupScenes() {
		window1 = new BorderPane(); // Main Menu
		window2 = new BorderPane();	// Game 
		window3 = new BorderPane(); // Start screen
		scene1 = new Scene(window1, settings.getWidth("scene"), settings.getHeight("scene"));
		scene2 = new Scene(window2, settings.getWidth("scene"), settings.getHeight("scene"));
		scene3 = new Scene(window3, settings.getWidth("scene"), settings.getHeight("scene"));
	}
	private void setupWindow1() {
		newGameButton = new Button("New Game");
		newGameButton.setMinSize(300, 100);
		newGameButton.setOnAction(new ButtonListener());
		loadGameButton = new Button("Load Game...");
		loadGameButton.setMinSize(300, 100);
		loadGameButton.setOnAction(new ButtonListener());
		GridPane gp = new GridPane();
		gp.setPrefHeight(settings.getHeight("scene"));
		gp.setPrefWidth(settings.getWidth("scene"));
		GridPane.setConstraints(newGameButton, 1, 1);
		GridPane.setConstraints(loadGameButton, 1, 2);
		gp.setHgap(10);
		gp.setVgap(10);
		gp.getChildren().addAll(newGameButton, loadGameButton);
		window1.setCenter(gp);
	}
	private void setupWindow3() {
		Image startScreen = new Image("file:images/misc/start-screen.jpg");
		Canvas canvas = new Canvas(settings.getWidth("scene"), settings.getHeight("scene"));
		canvas.getGraphicsContext2D().drawImage(startScreen, 0, 0);
		window3.setCenter(canvas);
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

	private class MouseClickListener implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent event) {
			stage.setScene(scene1);
		}
		
	}
	private class MenuItemListener implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			String text = ((MenuItem) e.getSource()).getText();
			if (text.equals("New Game"))
				theGame.startNewGame(); 
			else if (text.equals("Text"))
				setViewTo(textView);
			else if (text.equals("Graphics"))
				setViewTo(graphicView);
			else if (text.equals("Map")) {
//				stage.setScene(scene3);			
				Stage stage = new Stage();
				stage.setTitle("Map View");
				stage.setScene(new Scene(mapView, theGame.getSettings().getWidth("map"), theGame.getSettings().getHeight("map")));
				stage.show();
			} else if (text.equals("Pokemon")) {
				Stage stage = new Stage();
				stage.setTitle("Pokemon View");
				stage.setScene(new Scene(new BorderPane(), theGame.getSettings().getWidth("scene"), theGame.getSettings().getHeight("scene")));
				stage.show();
			} 
			else if (text.equals("Exit")) {
				stage.setScene(scene1);
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
			if (event.getSource()==newGameButton) {
				theGame.startNewGame();
				graphicView.drawViewableArea();
				graphicView.drawTrainer();
				setViewTo(graphicView);
				stage.setScene(scene2);
			}
			else if (event.getSource()==loadGameButton) {
				// TODO
				System.out.println("loading..");
			}
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
