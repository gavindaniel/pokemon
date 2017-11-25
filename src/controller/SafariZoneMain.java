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
import views.*;
import network.User;

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

	private Stage stage;
	private BorderPane start_window, menu_window, newgame_window, loadgame_window, game_window;
	private Scene start_scene, menu_scene, newgame_scene, loadgame_scene, game_scene;

	private MenuBar menuBar;
	private Observer currentView;
	private GraphicView graphicView;
	private TextView textView;
	private StartView startView;
	private MenuView menuView;
	private NewGameView newGameView;
	private LoadGameView loadGameView;

	private Settings settings;
	char keyPressed;

	/***************************************/

	private int loggedIn;
	private Button loginButton;
	public FileManager man;
	private GameLoader gameLoader;
	private Menu userMenuBar, adminMenuBar;
	private TextField acctT;
	private PasswordField pswdT;
	private Button loginB, exitLgn, createU;
	private GridPane acctGrid, loginGrid, buttonGrid;
	private Label acctLabl, pswdLabl, loginMsg, userMsg;
	/////////////////////
	private Socket socket;
	private ObjectOutputStream outputToServer;
	private ObjectInputStream inputFromServer;
	private static final String Address = "localhost";
	public Point altPlayer;

	/***************************************/

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		/***************************************/
		// openConnection();
		/***************************************/
		acctGrid = new GridPane();
		loginGrid = new GridPane();
		loggedIn = 0;
		man = new FileManager();

		primaryStage.setTitle("Pokemon: Safari Zone");
		stage = primaryStage;
		keyPressed = 'z';
		settings = new Settings();

		initializeGameForTheFirstTime();
		// loginView = new LoginView(man,gameLoader,graphicView);
		setupScenes();
		setupStartScreen();
		setupMainMenu();
		setupGameMenus();
		setupNewGame();
		setupLoadGame();

//		setupLoginGrids();
		game_window.setTop(menuBar);

		// Setup the views
		textView = new TextView(gameLoader.getSafariZone());
		graphicView = new GraphicView(gameLoader.getSafariZone());
		
		/***************************************/
		// textView = new TextView(gameLoader.getSafariZone());
		// loginView = new LoginView(man,gameLoader,textView);
		/***************************************/

		gameLoader.getSafariZone().addObserver(textView);
		gameLoader.getSafariZone().addObserver(graphicView);
		// gameLoader.getSafariZone().addObserver(loginView);

		/***********************/
		// gameLoader.getPokemon().addObserver(textView);
		/***********************/

		setViewTo(graphicView);

		primaryStage.setScene(start_scene); //replacement scene 
		primaryStage.show();

	}

	private void setViewTo(Observer newView) {
		game_window.setCenter(null);
		currentView = newView;
		game_window.setCenter((Node) currentView);
	}

	public void initializeGameForTheFirstTime() {
		gameLoader = new GameLoader(new SafariZone());
	}

	private void setupScenes() {
		start_window = new BorderPane();	
		menu_window = new BorderPane();	
		newgame_window = new BorderPane();
		loadgame_window = new BorderPane();
		game_window = new BorderPane();

		start_scene = new Scene(start_window, settings.getWidth("scene"), settings.getHeight("scene"));
		menu_scene = new Scene(menu_window, settings.getWidth("scene"), settings.getHeight("scene"));
		newgame_scene = new Scene(newgame_window, settings.getWidth("scene"), settings.getHeight("scene"));
		loadgame_scene = new Scene(loadgame_window, settings.getWidth("scene"), settings.getHeight("scene"));
		game_scene = new Scene(game_window, settings.getWidth("scene"), settings.getHeight("scene"));
		
		game_scene.setOnKeyPressed(new KeyPressListener());
		game_scene.setOnKeyReleased(new KeyReleaseListener());
	}

	private void setupStartScreen() {
		startView = new StartView(gameLoader.getSafariZone(), stage, start_scene, menu_scene);
		start_window.setCenter(startView);
	}
	private void setupMainMenu() {
		menuView = new MenuView(gameLoader.getSafariZone(), stage, newgame_scene, loadgame_scene);
		menu_window.setCenter(menuView);
	}
	private void setupNewGame() {
		newGameView = new NewGameView(gameLoader.getSafariZone(), stage, newgame_scene, game_scene);
		newgame_window.setCenter(newGameView);
	}
	private void setupLoadGame() {
		loadGameView = new LoadGameView(gameLoader.getSafariZone(), stage, loadgame_scene, game_scene);
		loadgame_window.setCenter(loadGameView);
	}
	
	private void setupGameMenus() {
		// game menu options
		MenuItem pokemon = new MenuItem("Pokemon");
		MenuItem bag = new MenuItem("Bag");
		MenuItem map = new MenuItem("Map");
		MenuItem trainer = new MenuItem("Trainer");
		MenuItem save = new MenuItem("Save");
//		MenuItem option = new MenuItem("Option");
		MenuItem exit = new MenuItem("Exit");
		// add the options
		Menu menu = new Menu("Menu");
		menu.getItems().addAll(pokemon, bag, map, trainer, save, exit); //, option
		// view menu options
		MenuItem textV = new MenuItem("Text");
		MenuItem graphicV = new MenuItem("Graphics");
		// add the options
		Menu views = new Menu("View");
		views.getItems().addAll(textV, graphicV);

		menuBar = new MenuBar();
		menuBar.getMenus().addAll(menu, views);

		// Add the same listener to all menu items requiring action
		MenuItemListener menuListener = new MenuItemListener();
		pokemon.setOnAction(menuListener);
		bag.setOnAction(menuListener);
		map.setOnAction(menuListener);
		trainer.setOnAction(menuListener);
		save.setOnAction(menuListener);
//		option.setOnAction(menuListener);
		exit.setOnAction(menuListener);
		// view menu listeners
		textV.setOnAction(menuListener);
		graphicV.setOnAction(menuListener);
	}
	
	
	public class KeyPressListener implements EventHandler<KeyEvent> {
		@Override
		public void handle(KeyEvent event) {
			if (event.getCode() == KeyCode.UP) {
				graphicView.animateTrainer('U', false);
				gameLoader.getSafariZone().movePlayer('U');
			} else if (event.getCode() == KeyCode.DOWN) {
				graphicView.animateTrainer('D', false);
				gameLoader.getSafariZone().movePlayer('D');
			} else if (event.getCode() == KeyCode.LEFT) {
				graphicView.animateTrainer('L', false);
				gameLoader.getSafariZone().movePlayer('L');
			} else if (event.getCode() == KeyCode.RIGHT) {
				graphicView.animateTrainer('R', false);
				gameLoader.getSafariZone().movePlayer('R');
			}
		}
	}

	public class KeyReleaseListener implements EventHandler<KeyEvent> {
		@Override
		public void handle(KeyEvent event) {
			if (event.getCode() == KeyCode.UP) {
//				graphicView.animateMap('U', true);
				graphicView.animateTrainer('U', true);
			} else if (event.getCode() == KeyCode.DOWN) {
//				graphicView.animateMap('D', true);
				graphicView.animateTrainer('D', true);
			} else if (event.getCode() == KeyCode.LEFT) {
//				graphicView.animateMap('L', true);
				graphicView.animateTrainer('L', true);
			} else if (event.getCode() == KeyCode.RIGHT) {
//				graphicView.animateMap('R', true);
				graphicView.animateTrainer('R', true);
			}
		}
	}


	private class MenuItemListener implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			String text = ((MenuItem) e.getSource()).getText();
			if (text.equals("Pokemon")) {
				Stage stage = new Stage();
				stage.setTitle("Pokemon View");
				stage.setScene(new Scene(new PokemonView(gameLoader.getSafariZone()), gameLoader.getSafariZone().getSettings().getWidth("info"), gameLoader.getSafariZone().getSettings().getHeight("info")));
				stage.show();
			} else if (text.equals("Bag")) {
				Stage stage = new Stage();
				stage.setTitle("Bag View");
				stage.setScene(new Scene(new BagView(gameLoader.getSafariZone()), gameLoader.getSafariZone().getSettings().getWidth("info"), gameLoader.getSafariZone().getSettings().getHeight("info")));
				stage.show();
			}
			else if (text.equals("Save")) {
				if (loggedIn == 0) {
					stage.setScene(null);
					stage.setScene(loadgame_scene);
				}
				// UD man.pushUserData();
			} else if (text.equals("Map")) {
				Stage stage = new Stage();
				stage.setTitle("Map View");
				stage.setScene(new Scene(new MapView(gameLoader.getSafariZone()), gameLoader.getSafariZone().getSettings().getWidth("map"), gameLoader.getSafariZone().getSettings().getHeight("map")));
				stage.show();
			} else if (text.equals("Trainer")) { 
				Stage stage = new Stage();
				stage.setTitle("Trainer View");
				stage.setScene(new Scene(new TrainerView(gameLoader.getSafariZone()), gameLoader.getSafariZone().getSettings().getWidth("info"), gameLoader.getSafariZone().getSettings().getHeight("info")));
				stage.show();
			}
			else if (text.equals("Exit")) {
				// UD man.pushUserData();
				loggedIn = 0;
//				gameLoader.setSafariZone(null);
//				gameLoader.setSafariZone(new SafariZone());
//				graphicView = null;
//
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


	public void setupLoginGrids() {

		loginB = new Button("Login");
		loginB.setFont(new Font("Arial", 14));
		exitLgn = new Button("Exit");
		exitLgn.setFont(new Font("Arial", 14));

		acctT = new TextField();
		pswdT = new PasswordField();
		acctLabl = new Label("Account Name");
		pswdLabl = new Label("Password");
		loginMsg = new Label("Sign In");
		acctLabl.setFont(new Font("Arial", 18));
		pswdLabl.setFont(new Font("Arial", 18));
		loginMsg.setFont(new Font("Arial", 24));

		loadgame_window.setAlignment(loginMsg, Pos.CENTER);
		loadgame_window.setMargin(loginMsg, new Insets(10, 10, 10, 10));
		loadgame_window.setTop(loginMsg);

		acctT.setMaxHeight(15.0);
		pswdT.setMaxHeight(15.0);
		acctT.setMaxWidth(125.0);
		pswdT.setMaxWidth(125.0);

		acctGrid.add(acctLabl, 1, 1);
		acctGrid.add(pswdLabl, 1, 2);
		acctGrid.add(acctT, 2, 1);
		acctGrid.add(pswdT, 2, 2);
		acctGrid.setHgap(5);
		acctGrid.setVgap(5);

		loadgame_window.setAlignment(acctGrid, Pos.CENTER);
		loadgame_window.setMargin(acctGrid, new Insets(10, 10, 10, 10));
		loadgame_window.setCenter(acctGrid);

		loadgame_window.setAlignment(loginGrid, Pos.CENTER);
		loadgame_window.setMargin(loginGrid, new Insets(10, 10, 100, 125));
		loadgame_window.setBottom(loginGrid);

		loginGrid.setHgap(5);
		loginGrid.setVgap(5);

		loginGrid.add(loginB, 1, 1);
		loginGrid.add(exitLgn, 1, 10);

		createU = new Button("Create");
		loadgame_window.setAlignment(createU, Pos.CENTER);
		loadgame_window.setMargin(createU, new Insets(10, 10, 100, 125));

		LoginButtonListener handler1 = new LoginButtonListener();

		loginB.setOnAction(handler1);
		exitLgn.setOnAction(handler1);

		createU.setOnAction(handler1);

	}
	//////////////// work in progress

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
		public void run() {
			while (true) {
				try {
					altPlayer = (Point) inputFromServer.readObject();
					System.out.println("C: altPlayer: " + altPlayer.getX() + " " + altPlayer.getY());
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

	private class LoginButtonListener implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {
			if (exitLgn == (Button) arg0.getSource())
				stage.setScene(menu_scene);

			if (loginB == (Button) arg0.getSource()) {
				// make one call to check user....
				if (acctT.getText().equals("") || pswdT.getText().equals(""))
					System.out.println("View:User Error1");
				else {
					loggedIn = 1;

					User temp = man.getUser(acctT.getText(), pswdT.getText());

					if (temp != null) {
						System.out.println("View:Returning User");
						// have confirm to load last game
						Alert alert = new Alert(AlertType.CONFIRMATION);
						alert.setHeaderText("Load Previous Game?");
						Optional<ButtonType> result = alert.showAndWait();

						if (result.get() == ButtonType.OK) {
							System.out.println("Set SafariZone previous for ret User");
							// check player loc in loaded game
							// System.out.println("Player Previous:
							// "+temp.getSafariZone().getTrainerLoc().getX()+"
							// "+temp.getSafariZone().getTrainerLoc().getY());
							// load User prev game
							gameLoader.setSafariZone(null);
							gameLoader.setSafariZone(temp.getSafariZone());

							graphicView = null;
							graphicView = new GraphicView(gameLoader.getSafariZone());
							gameLoader.getSafariZone().addObserver(graphicView);
							setViewTo(graphicView);
							stage.setScene(game_scene);

						} else {
							System.out.println("Set SafariZone current for ret User");
							// set User SafariZone to current game

							temp.setSafariZone(gameLoader.getSafariZone());
							graphicView = null;

							graphicView = new GraphicView(gameLoader.getSafariZone());
							gameLoader.getSafariZone().addObserver(graphicView);

							setViewTo(graphicView);

							stage.setScene(game_scene);

							// UD man.pushUserData();
						}
						// set up admin User menu and game view
						if (temp.name.equals("Merlin") && temp.password.equals("7777777")) {
							System.out.println("View:Admin");
							// setUpAdminMenus();
							// setViewToAdmin(textView);
						} else {
							// set up current User game view
							// setViewTo(textView);
						}
						// setUserMsg("Welcome Back User");

					} else {
						man.setUser(acctT.getText(), pswdT.getText(), gameLoader.getSafariZone());
						System.out.println("View:New User");
						System.out.println("Set SafariZone current for new");
						// get user and set SafariZone to current game

						// man.getUser(acctT.getText(),pswdT.getText()).setSafariZone(gameLoader.getSafariZone());

						// UD man.pushUserData();

						setViewTo(graphicView);
						stage.setScene(game_scene);

					}
				}
				acctT.setText("");
				pswdT.setText("");
			}

			if (createU == (Button) arg0.getSource()) {

				System.out.println("Got Create");

				System.out.println("View:Admin Create New User");
				man.setUser(acctT.getText(), pswdT.getText(), new SafariZone());
				// set created user new SafariZone
				man.getUser(acctT.getText(), pswdT.getText()).setSafariZone(new SafariZone());

				// UD man.pushUserData();
				// adminHelper();
				// setUpAdminMenus();
				// setViewToAdmin(textView);
				acctT.setText("");
				pswdT.setText("");

			}

		}

	}
	/////////////////////////
}
