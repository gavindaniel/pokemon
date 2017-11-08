package controller;

import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observer;

import javafx.application.Application;
import javafx.concurrent.Task;
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
import model.PokemonGame;
import views.LoginView;
import views.TextView;


public class PokemonMain extends Application {

	//private Pokemon theGame;
        private GameLoader gameLoader; 	
	private MenuBar menuBar;
	private Observer currentView;
	private Observer textView;
	private Observer graphicView; // to be implemented after textView
        private Observer loginView;
	
	private BorderPane window;
	public static final int height = 400;
	public static final int width = 600;
	
	char keyPressed;


        private Socket socket;
        private ObjectOutputStream outputToServer;
        private ObjectInputStream inputFromServer;
        private static final String Address = "localhost";
        public Point altPlayer;
        FileManager man = new FileManager();




	public static void main(String[] args) {
		   launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {

                // sets up server connnection
      //          openConnection();

		primaryStage.setTitle("Pokemon");
		keyPressed = 'z';
		window = new BorderPane();
		Scene scene = new Scene(window, width, height);


		setupMenus();
		window.setTop(menuBar);
		initializeGameForTheFirstTime();
		
		
		scene.setOnKeyReleased(new moveListener());
		
		//Setup the views
		textView = new TextView(gameLoader.getPokemon());
                loginView = new LoginView(man,gameLoader,textView);

		//graphicView = new GraphicView(theGame);
		gameLoader.getPokemon().addObserver(textView);

//		setViewTo(loginView); 
		setViewTo(textView); //change to graphicView once graphicView is implemented
		
		primaryStage.setScene(scene);


		primaryStage.show();
		
	}
	
	
	private void setViewTo(Observer newView) {
	    window.setCenter(null);
	    currentView = newView;
	    window.setCenter((Node) currentView);
	}
	
	
	public void initializeGameForTheFirstTime() {
	    gameLoader = new GameLoader( new PokemonGame());
	}
	
	
	private void setupMenus() {
	    MenuItem textV = new MenuItem("Text");
	//    MenuItem graphicV = new MenuItem("Graphics");

	    Menu views = new Menu("Views");
	    views.getItems().addAll(textV); //, graphicV

            Menu user = new Menu("User");
	    MenuItem signIn = new MenuItem("Sign In");
            MenuItem signOut = new MenuItem("Sign Out");
            user.getItems().addAll(signIn, signOut);  
           	
	    MenuItem newGame = new MenuItem("New Game");
	    Menu options = new Menu("Options");
	    options.getItems().addAll(user, newGame, views);
	    
	    menuBar = new MenuBar();
	    menuBar.getMenus().addAll(options);
	 
	    // Add the same listener to all menu items requiring action
	    MenuItemListener menuListener = new MenuItemListener();
	    newGame.setOnAction(menuListener);
	    textV.setOnAction(menuListener);
            signIn.setOnAction(menuListener); 
            signOut.setOnAction(menuListener); 


//	    graphicV.setOnAction(menuListener);
	}
	
	
	public class moveListener implements EventHandler<KeyEvent> {

		@Override
		public void handle(KeyEvent event) {
				
                                if(event.getCode() == KeyCode.UP){
					keyPressed = 'U';
				gameLoader.getPokemon().movePlayer(keyPressed);
				}
				else if(event.getCode() == KeyCode.DOWN){
					keyPressed = 'D';
   		                gameLoader.getPokemon().movePlayer(keyPressed);
		

	}
				else if(event.getCode() == KeyCode.LEFT){
					keyPressed = 'L';
	                        gameLoader.getPokemon().movePlayer(keyPressed);

}
				else if(event.getCode() == KeyCode.RIGHT){
					keyPressed = 'R';
	                        gameLoader.getPokemon().movePlayer(keyPressed);

	}



//////////////// in progress 

/*
           try {

// send point to server ie comment if needed

                   outputToServer.writeObject(gameLoader.getPokemon().getTrainerLoc());

//System.out.println("PkMain: "+gameLoader.getPokemon().getTrainerLoc().getX()+" "+gameLoader.getPokemon().getTrainerLoc().getY());
	



         	} catch (IOException e) {
			e.printStackTrace();
		}

*/
                
        
		}
		
	}
	
	private class MenuItemListener implements EventHandler<ActionEvent> {

	    @Override
	    public void handle(ActionEvent e) {
	      // Find out the text of the JMenuItem that was just clicked
	      String text = ((MenuItem) e.getSource()).getText();
	      if (text.equals("New Game")){
	        gameLoader.getPokemon().startNewGame(); // The computer player has been set and should not change.
                setViewTo(textView);

	      }else if (text.equals("Text")){
	        setViewTo(textView);
//	      else if (text.equals("Graphics"))
//			  setViewTo(graphicView);
	      }else if (text.equals("Sign In")){
  		setViewTo(loginView);

	      }else if (text.equals("Sign Out")){
    
                // FileManager will push User data
                man.pushUserData();  
  
                // new Pokemon and textView
                gameLoader.setPokemon(null); 
                gameLoader.setPokemon(new PokemonGame());
                textView = null; 
                textView = new TextView(gameLoader.getPokemon()); 
                loginView = new LoginView(man,gameLoader,textView);
  		setViewTo(textView);
	
              }
	    }
	}

  
//////////////




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

System.out.println("C: "+altPlayer.getX()+" "+altPlayer.getY());         

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

