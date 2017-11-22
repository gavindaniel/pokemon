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
import views.MapView;
import views.TextView;
import network.User;

/**************************************************************/

import javafx.scene.control.Alert.AlertType;

import javafx.event.*;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.geometry.*;


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
import views.LoginView;
import persistence.FileManager;
import persistence.GameLoader;


/**************************************************************/

public class SafariZoneMain extends Application {

	private Stage stage;
	private Scene scene1, scene2, scene3, scene4;
	private BorderPane window1, window2, window3, window4;
        private int loggedIn;

	
//	private SafariZone gameLoader.getSafariZone();
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
//      openConnection();
/***************************************/
                acctGrid = new GridPane();

                loginGrid = new GridPane();
                loggedIn=0;

                man = new FileManager();

		primaryStage.setTitle("Pokemon: Safari Zone");
		stage = primaryStage;
		keyPressed = 'z';
		settings = new Settings();
		
		initializeGameForTheFirstTime();
//	        loginView = new LoginView(man,gameLoader,graphicView);
		setupScenes();
		setupMainMenu();
		setupStartScreen();
		setupGameMenus();
		
                setUpLoginGrids();
		window2.setTop(menuBar);

		scene2.setOnKeyPressed(new KeyPressListener());
		scene2.setOnKeyReleased(new KeyReleaseListener());

		scene3.setOnMouseClicked(new MouseClickListener());
		scene3.setOnKeyPressed(new KeyPressListener2());

//		scene4.setOnKeyPressed(new KeyPressListener3());



		// Setup the views
		textView = new TextView(gameLoader.getSafariZone());
		graphicView = new GraphicView(gameLoader.getSafariZone());
		mapView = new MapView(gameLoader.getSafariZone());
		
/***************************************/
//		textView = new TextView(gameLoader.getSafariZone());
//        loginView = new LoginView(man,gameLoader,textView);
/***************************************/
		
		gameLoader.getSafariZone().addObserver(textView);
		gameLoader.getSafariZone().addObserver(graphicView);
//                gameLoader.getSafariZone().addObserver(loginView);


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
               gameLoader = new GameLoader( new SafariZone());

	}



    public void setUpLoginGrids(){

    loginB = new Button("Login");
    loginB.setFont( new Font("Arial", 14));
    exitLgn = new Button("Exit");
    exitLgn.setFont( new Font("Arial", 14));



    acctT = new TextField();
    pswdT = new PasswordField();
    acctLabl = new Label("Account Name");
    pswdLabl = new Label("Password");
    loginMsg = new Label("Sign In");
    acctLabl.setFont( new Font("Arial", 18));
    pswdLabl.setFont( new Font("Arial", 18));
    loginMsg.setFont( new Font("Arial", 24));


    window4.setAlignment(loginMsg, Pos.CENTER);
    window4.setMargin(loginMsg, new Insets(10,10,10,10));
    window4.setTop(loginMsg);


    acctT.setMaxHeight(15.0);
    pswdT.setMaxHeight(15.0);
    acctT.setMaxWidth(125.0);
    pswdT.setMaxWidth(125.0);

    acctGrid.add(acctLabl,1,1);
    acctGrid.add(pswdLabl,1,2);
    acctGrid.add(acctT,2,1);
    acctGrid.add(pswdT,2,2);
    acctGrid.setHgap(5);
    acctGrid.setVgap(5);

    window4.setAlignment(acctGrid, Pos.CENTER);
    window4.setMargin(acctGrid, new Insets(10,10,10,10));
    window4.setCenter(acctGrid);


    window4.setAlignment(loginGrid, Pos.CENTER);
    window4.setMargin(loginGrid, new Insets(10,10,100,125));
    window4.setBottom(loginGrid);

    loginGrid.setHgap(5);
    loginGrid.setVgap(5);


    loginGrid.add(loginB,1,1);
    loginGrid.add(exitLgn,1,10);

    createU = new Button("Create");
    window4.setAlignment(createU, Pos.CENTER);
    window4.setMargin(createU, new Insets(10,10,100,125));


    LoginButtonListener handler1 = new LoginButtonListener();

    loginB.setOnAction(handler1);
    exitLgn.setOnAction(handler1);

    createU.setOnAction(handler1);


    }




        private class LoginButtonListener implements EventHandler<ActionEvent>{

      @Override
      public void handle(ActionEvent arg0){
  
         if(exitLgn==(Button) arg0.getSource()){
 
               stage.setScene(scene1);
          }
 
        if(loginB==(Button) arg0.getSource()){

         // make one call to check user.... 
           if(acctT.getText().equals("") || pswdT.getText().equals("")){
System.out.println("View:User Error1");
           } else {
               loggedIn=1;

               User temp=man.getUser(acctT.getText(),pswdT.getText());
            
               if(temp!=null){
System.out.println("View:Returning User");              
                   // have confirm to load last game
                  Alert alert = new Alert(AlertType.CONFIRMATION);
                  alert.setHeaderText("Load Previous Game?");
                  Optional<ButtonType> result = alert.showAndWait();
   
                  if(result.get() == ButtonType.OK){
System.out.println("Set SafariZone previous for ret User"); 
                  // check player loc in loaded game
//System.out.println("Player Previous: "+temp.getSafariZone().getTrainerLoc().getX()+" "+temp.getSafariZone().getTrainerLoc().getY());   
                      // load User prev game
                      gameLoader.setSafariZone(null);
                      gameLoader.setSafariZone(temp.getSafariZone());

                      

                      graphicView = null;
                      graphicView = new GraphicView(gameLoader.getSafariZone());
                      gameLoader.getSafariZone().addObserver(graphicView);
                      setViewTo(graphicView);
                      stage.setScene(scene2);




                   } else {
System.out.println("Set SafariZone current for ret User");
                      // set User SafariZone to current game   


                      temp.setSafariZone(gameLoader.getSafariZone());
                      graphicView = null;

                      graphicView = new GraphicView(gameLoader.getSafariZone());
                      gameLoader.getSafariZone().addObserver(graphicView);

                      setViewTo(graphicView);
 
                      stage.setScene(scene2);


//UD                      man.pushUserData();
                   }
                      // set up admin User menu and game view
                   if(temp.name.equals("Merlin") && temp.password.equals("7777777")){
System.out.println("View:Admin");
 //                        setUpAdminMenus();
 //                        setViewToAdmin(textView);
                   } else {
                    // set up current User game view
      //                   setViewTo(textView);   
                    }
//                     setUserMsg("Welcome Back User");               

               } else {
                   man.setUser(acctT.getText(),pswdT.getText(),gameLoader.getSafariZone());
System.out.println("View:New User");
System.out.println("Set SafariZone current for new");
                   //get user and set SafariZone to current game 

//                    man.getUser(acctT.getText(),pswdT.getText()).setSafariZone(gameLoader.getSafariZone());

//UD                      man.pushUserData();


                    setViewTo(graphicView);
                    stage.setScene(scene2);

               }
           }
          acctT.setText("");
          pswdT.setText("");
        }
                      
         if(createU==(Button) arg0.getSource()){

    System.out.println("Got Create");


System.out.println("View:Admin Create New User");
                    man.setUser(acctT.getText(),pswdT.getText(),new SafariZone());
            // set created user new SafariZone 
                    man.getUser(acctT.getText(),pswdT.getText()).setSafariZone(new SafariZone());

//UD                         man.pushUserData();
              //      adminHelper();
              //      setUpAdminMenus();
              //      setViewToAdmin(textView);
                    acctT.setText("");
                    pswdT.setText("");


         }



      }

   
      }




	private void setupScenes() {
		window1 = new BorderPane(); // Main Menu
		window2 = new BorderPane();	// Game 
		window3 = new BorderPane(); // Start screen
       		window4 = new BorderPane(); // Login screen
       
		scene1 = new Scene(window1, settings.getWidth("scene"), settings.getHeight("scene"));
		scene2 = new Scene(window2, settings.getWidth("scene"), settings.getHeight("scene"));
		scene3 = new Scene(window3, settings.getWidth("scene"), settings.getHeight("scene"));
        	scene4 = new Scene(window4, settings.getWidth("scene"), settings.getHeight("scene"));
		

	}
	private void setupStartScreen() {
		Image startScreen = new Image("file:images/misc/start-screen.jpg");
		Canvas canvas = new Canvas(settings.getWidth("scene"), settings.getHeight("scene"));
		canvas.getGraphicsContext2D().drawImage(startScreen, 0, 0);
		window3.setCenter(canvas);
	}
	private void setupMainMenu() {
		newGameButton = new Button("New Game");
		newGameButton.setMinSize(300, 100);
		newGameButton.setOnAction(new ButtonListener());
		loadGameButton = new Button("Load Game...");
		loadGameButton.setMinSize(300, 100);
		loadGameButton.setOnAction(new ButtonListener());
		GridPane gp = new GridPane();
		gp.setPrefSize(settings.getWidth("scene"), settings.getHeight("scene"));
		GridPane.setConstraints(newGameButton, 1, 1);
		GridPane.setConstraints(loadGameButton, 1, 2);
		gp.setHgap(10);
		gp.setVgap(10);
		gp.getChildren().addAll(newGameButton, loadGameButton);
		window1.setCenter(gp);
	}


	private void setupGameMenus() {
		// game menu options
		MenuItem pokemon = new MenuItem("Pokemon");
		MenuItem bag = new MenuItem("Bag");
		MenuItem map = new MenuItem("Map");
		MenuItem trainer = new MenuItem("Trainer");
		MenuItem save = new MenuItem("Save");
		MenuItem option = new MenuItem("Option");
		MenuItem newGame = new MenuItem("New Game");


		MenuItem exit = new MenuItem("Exit");
		// add the options
		Menu menu = new Menu("Menu");
		menu.getItems().addAll(pokemon, bag, map, trainer, newGame, save, option, exit);		
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
		newGame.setOnAction(menuListener);


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
				graphicView.animateMap('U', true);
			} else if (event.getCode() == KeyCode.DOWN) {
				graphicView.animateMap('D', true);
			} else if (event.getCode() == KeyCode.LEFT) {
				graphicView.animateMap('L', true);
			} else if (event.getCode() == KeyCode.RIGHT) {
				graphicView.animateMap('R', true);
			}
		}

	}
	// start menu listeners
	private class MouseClickListener implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent event) {
			stage.setScene(scene1);
		}
	}
	private class KeyPressListener2 implements EventHandler<KeyEvent> {
		@Override
		public void handle(KeyEvent event) {
			if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.SPACE)
				stage.setScene(scene1);
		}
		
	}
	
	private class MenuItemListener implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			String text = ((MenuItem) e.getSource()).getText();
			if (text.equals("New Game")){
				gameLoader.getSafariZone().startNewGame(); 
                                graphicView=null;
                                graphicView = new GraphicView(gameLoader.getSafariZone());
                                setViewTo(graphicView);
                                stage.setScene(scene2);

            		}else if (text.equals("Text")){
				setViewTo(textView);
			}else if (text.equals("Save")){
                              if(loggedIn==0){	       
                 stage.setScene(scene4);
                              }
//UD                               man.pushUserData();
	        	}else if (text.equals("Graphics")){
				setViewTo(graphicView);
			}else if (text.equals("Map")) {
//				stage.setScene(scene3);			
				Stage stage = new Stage();
				stage.setTitle("Map View");
				stage.setScene(new Scene(mapView, gameLoader.getSafariZone().getSettings().getWidth("map"), gameLoader.getSafariZone().getSettings().getHeight("map")));
				stage.show();
			} else if (text.equals("Pokemon")) {
				Stage stage = new Stage();
				stage.setTitle("Pokemon View");
				stage.setScene(new Scene(new BorderPane(), gameLoader.getSafariZone().getSettings().getWidth("scene"), gameLoader.getSafariZone().getSettings().getHeight("scene")));
				stage.show();
			} 
			else if (text.equals("Exit")) {
//UD		                          man.pushUserData();		
                         loggedIn=0;
                gameLoader.setSafariZone(null);
                gameLoader.setSafariZone(new SafariZone());
                graphicView = null;



                graphicView = new GraphicView(gameLoader.getSafariZone());
//                window4 = new LoginView(man,gameLoader,graphicView);
                gameLoader.getSafariZone().addObserver(graphicView);



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
			if (event.getSource()==newGameButton) {
				gameLoader.getSafariZone().startNewGame();
				graphicView.drawViewableArea();
				graphicView.resetTrainer();
				graphicView.drawTrainer();
				setViewTo(graphicView);
				stage.setScene(scene2);
			}
			else if (event.getSource()==loadGameButton) {
                               // setViewTo(loginView);       
                         	stage.setScene(scene4);
                 
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
