package views;

import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import java.util.Vector;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.SafariZone;
import network.User;
import persistence.FileManager;
import persistence.GameLoader;

public class LoadGameView extends BorderPane implements Observer {

	private Stage mainStage;
	private Scene newgame_scene, game_scene, menu_scene;
	private SafariZone theGame;
	// --------------------------------------------------------
	private MenuBar mainMenu;
	private Menu userMenuBar, adminMenuBar;
	private TextField acctT;
	private PasswordField pswdT;
	private Button loginB, playB, pauseB, stopB, createU;
	private GridPane acctGrid, loginGrid, buttonGrid;
	private Label acctLabl, pswdLabl, loginMsg, userMsg;
	private FileManager man;
	// private Pokemon theGame;
	private Observer textView;
	private GameLoader gameLoader;
	//--------------------------------------------------------
	private int loggedIn;
	private Button loginButton;
	private Button exitLgn;

	// name input variables

	@Override
	public void update(Observable o, Object arg) {
		theGame = (SafariZone) o;
	}

	/**
	 * @param instance
	 *            of the game 'PokemonGame'
	 */
	public LoadGameView(SafariZone PokemonGame, Stage stage, Scene newgame, Scene menu, Scene game, FileManager man, GameLoader gameLoader) {
		mainStage = stage;
		newgame_scene = newgame;
		game_scene = game;
		menu_scene = menu;
		theGame = PokemonGame;

		this.man = man;
		this.gameLoader = gameLoader;
		acctGrid = new GridPane();
		loginGrid = new GridPane();
		mainMenu = new MenuBar();

		initializePane();
	}

	/**
	 * initializes the canvas and draws the zone and trainer
	 */
	private void initializePane() {
		acctGrid = new GridPane();
		loginGrid = new GridPane();
		
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

		this.setAlignment(loginMsg, Pos.CENTER);
		this.setMargin(loginMsg, new Insets(10, 10, 10, 10));
		this.setTop(loginMsg);

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

		this.setAlignment(acctGrid, Pos.CENTER);
		this.setMargin(acctGrid, new Insets(10, 10, 10, 10));
		this.setCenter(acctGrid);

		this.setAlignment(loginGrid, Pos.CENTER);
		this.setMargin(loginGrid, new Insets(10, 10, 100, 125));
		this.setBottom(loginGrid);

		loginGrid.setHgap(5);
		loginGrid.setVgap(5);
		
		loginGrid.add(loginB, 1, 1);
		loginGrid.add(exitLgn, 1, 10);

		createU = new Button("Create");
		this.setAlignment(createU, Pos.CENTER);
		this.setMargin(createU, new Insets(10, 10, 100, 125));

		LoginButtonListener handler1 = new LoginButtonListener();

		loginB.setOnAction(handler1);
		exitLgn.setOnAction(handler1);
		createU.setOnAction(handler1);
	}

	public void setUserMsg(String val) {
		this.setCenter(null);
		this.setTop(null);
		this.setBottom(null);
		userMsg = new Label(val);
		userMsg.setFont(new Font("Arial", 24));
		this.setCenter(userMsg);
	}

	private void setViewToAdmin(Observer newView) {
		this.setCenter(null);
		this.setBottom(null);
		this.setCenter((Node) newView);
	}

	private void setViewTo(Observer newView) {
		this.setCenter(null);
		this.setTop(null);
		this.setBottom(null);
		this.setCenter((Node) newView);
	}

	// has adminOptions menu
	public void setUpAdminMenus() {

		AdminMenuItemListener adminMenuListener = new AdminMenuItemListener();
		MenuItem createUser = new MenuItem("Create User");
		Menu usr = new Menu("Users");

		for (User usu : man.getUserVector()) {
			MenuItem val = new MenuItem(usu.name);
			val.setOnAction(adminMenuListener);
			usr.getItems().addAll(val);
		}

		Menu adminOptions = new Menu("Admin");
		adminOptions.getItems().addAll(usr, createUser);
		createUser.setOnAction(adminMenuListener);
		mainMenu.getMenus().addAll(adminOptions);

		this.setTop(mainMenu);
	}

	// menuListener for Admin
	private class AdminMenuItemListener implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent e) {
			// Find out the text of the adminOptions that was just clicked

			String text = ((MenuItem) e.getSource()).getText();
			// creates new User

			if (text.equals("Create User")) {

			}

		}
	}

	private class LoginButtonListener implements EventHandler<ActionEvent> {
		
				@Override
				public void handle(ActionEvent arg0) {
					if (exitLgn == (Button) arg0.getSource())
						mainStage.setScene(menu_scene);
		
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
		
//									graphicView = null;
//									graphicView = new GraphicView(gameLoader.getSafariZone());
//									gameLoader.getSafariZone().addObserver(graphicView);
//									setViewTo(graphicView);
									mainStage.setScene(game_scene);
		
								} else {
									System.out.println("Set SafariZone current for ret User");
									// set User SafariZone to current game
		
									temp.setSafariZone(gameLoader.getSafariZone());
//									graphicView = null;
		
//									graphicView = new GraphicView(gameLoader.getSafariZone());
//									gameLoader.getSafariZone().addObserver(graphicView);
		
//									setViewTo(graphicView);
		
									mainStage.setScene(game_scene);
		
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
		
//								setViewTo(graphicView);
								mainStage.setScene(game_scene);
		
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
}