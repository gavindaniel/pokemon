package capture;

import java.util.Random;

import items.Bait;
import items.Item;
import items.Rock;
import items.SafariBall;
/**
 * author Ian O'Heir
 */
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
/**
 * Make a working gui for a capture sequence
 * 
 * @author Rick Mercer
 * 
 */
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Trainer;
import pokemon.Bulbasaur;
import pokemon.Charmander;
import pokemon.Pikachu;
import pokemon.Pokemon;
import pokemon.Squirtle;

public class CaptureView extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  private Button startAnimationButton;
  Button pikachuButton;
  Button bulbasaurButton;
  Button charmanderButton;
  Button squirtleButton;
  Button throwBallButton,throwRockButton,throwBaitButton;
  Pokemon currentPokemon;
  GridPane pokemonButtons;
  Item currentItem;
  private Canvas canvas;
  private Image ship, backGround,characterSheet,pikachu,bulbasaur,charmander,squirtle,currentPokemonImage,currentItemImage;
  BorderPane pane;
  private double sx, sy,itemsx,itemsy;
  private GraphicsContext g2;
  private Timeline timeline,timeline2,retreatTimeline,captureTimeline,emoteTimeline;
  Trainer currentTrainer;
  Capture currentCapture;

  /**
   * Layout the GUI and initialize everything (which is too much)
   */
  @Override
  public void start(Stage stage) throws Exception {
    pane = new BorderPane();
    pikachuButton = new Button("Pikachu");
    bulbasaurButton = new Button("Bulbasaur");
    charmanderButton = new Button("Charmander");
    squirtleButton = new Button("Squirtle");
    
    //Get images for the pokemon
    pikachu = new Image("file:C:\\Classes\\CSC 335\\gitrepos\\pokemon-teammoltres\\images\\pokemon\\pikachu.png", false);
    bulbasaur = new Image("file:C:\\Classes\\CSC 335\\gitrepos\\pokemon-teammoltres\\images\\pokemon\\bulbasaur.png", false);
    squirtle = new Image("file:C:\\Classes\\CSC 335\\gitrepos\\pokemon-teammoltres\\images\\pokemon\\squirtle.png", false);
    charmander = new Image("file:C:\\Classes\\CSC 335\\gitrepos\\pokemon-teammoltres\\images\\pokemon\\charmander.png", false);
    pokemonButtons=new GridPane();
    pokemonButtons.add(pikachuButton, 0, 0);
    pokemonButtons.add(bulbasaurButton, 1, 0);
    pokemonButtons.add(charmanderButton, 2, 0);
    pokemonButtons.add(squirtleButton, 3, 0);
    pokemonButtons.setAlignment(Pos.CENTER);
    pane.setTop(pokemonButtons);
    BorderPane.setAlignment(pokemonButtons, Pos.BOTTOM_CENTER);
    StackPane holder = new StackPane();
    canvas = new Canvas(450, 300);
    holder.getChildren().add(canvas);
    holder.setStyle("-fx-background-color: black");
    pane.setCenter(holder);
    g2 = canvas.getGraphicsContext2D();
    backGround = new Image("file:C:\\Classes\\CSC 335\\gitrepos\\pokemon-teammoltres\\images\\capture\\battle_background.png", false);
    characterSheet = new Image("file:C:\\Classes\\CSC 335\\gitrepos\\pokemon-teammoltres\\images\\sheets\\Game Boy Advance - Pokemon FireRed LeafGreen - Trainers Backs-Transparent.png", false);
    g2.drawImage(backGround, 0, 0,450,300);
    g2.drawImage(characterSheet, 8, 8, 70, 70, 20, 165, 150, 150);
    sx=8;
    sy=8;
    itemsx=50;
    itemsy=170;
    pikachuButton.setOnAction(new PokemonButtonListener());
    bulbasaurButton.setOnAction(new PokemonButtonListener());
    squirtleButton.setOnAction(new PokemonButtonListener());
    charmanderButton.setOnAction(new PokemonButtonListener());
    //Give trainer 30 safari balls
    currentTrainer=new Trainer("Player 1");
    for (int i=0;i<30;i++) {
		currentTrainer.itemList.add(new SafariBall());
		}
    Scene scene = new Scene(pane, 500, 370);
    stage.setScene(scene);
    stage.show();

    // TODO 1: Create a TimeLine that call AnimateStarter.handle every 100ms
    timeline=new Timeline(new KeyFrame(Duration.millis(150), new AnimateStarter()));
    timeline.setCycleCount(Animation.INDEFINITE);
  
  }

  
//Pokemon emotion animator
  private class EmoteAnimateStarter implements EventHandler<ActionEvent> {
    int tic = 0;
    @Override
    // This handle method gets called every 100 ms to draw the ship on a new location
    public void handle(ActionEvent event) {
    	g2.drawImage(backGround, 0, 0,450,300);
    	g2.drawImage(characterSheet, 8, 8, 70, 70, 20, 165, 150, 150);
        g2.drawImage(currentPokemonImage, 270, 70, 125, 125);
    	
      
      // TODO 2: handle the TimeLine calls until tic gets too big 
      System.out.println(tic);
      
      
      
      
    }
  } 
  
  
  
//Pokemon retreat animator
  private class RetreatAnimateStarter implements EventHandler<ActionEvent> {
    int tic = 0;
    int x=270;
    @Override
    // This handle method gets called every 100 ms to draw the ship on a new location
    public void handle(ActionEvent event) {
    	g2.drawImage(backGround, 0, 0,450,300);
    	g2.drawImage(characterSheet, 8, 8, 70, 70, 20, 165, 150, 150);
        g2.drawImage(currentPokemonImage, x, 70, 125, 125);
        x+=5;
        tic++;
        if(tic==40) {
        	retreatTimeline.stop();
        	Platform.exit();
        }
    	
      
      // TODO 2: handle the TimeLine calls until tic gets too big 
      System.out.println(tic);
      
      
      
      
    }
  } 
  
  
  
  //Character throw animator
  private class AnimateStarter implements EventHandler<ActionEvent> {
    int tic = 0;

    @Override
    // This handle method gets called every 100 ms to draw the ship on a new location
    public void handle(ActionEvent event) {
    	g2.drawImage(backGround, 0, 0,450,300);
        sx+=72;
        g2.drawImage(characterSheet, sx, 8, 70, 70, 20, 165, 150, 150);
        g2.drawImage(currentPokemonImage, 270, 70, 125, 125);
        tic++;
    	if(tic==4) {
    		g2.drawImage(backGround, 0, 0,450,300);
    		g2.drawImage(characterSheet, 8, 8, 70, 70, 20, 165, 150, 150);
    		g2.drawImage(currentPokemonImage, 270, 70, 125, 125);
    		sx=8;
    	    sy=8;
    		tic=0;
    		if(itemsx==50) {
    		timeline2=new Timeline(new KeyFrame(Duration.millis(50), new ItemAnimateStarter()));
    	    timeline2.setCycleCount(Animation.INDEFINITE);
    	    timeline2.play();
    		}
    		timeline.stop();
    		
    	}
      
      // TODO 2: handle the TimeLine calls until tic gets too big 
      System.out.println(tic);
      
      
      
      
    }
  }

  
  //Item animator
  private class ItemAnimateStarter implements EventHandler<ActionEvent> {
	    int tic2 = 0;
	    @Override
	    // This handle method gets called every 100 ms to draw the ship on a new location
	    public void handle(ActionEvent event) {
	    	if(currentItem.getClass()==new SafariBall().getClass()) {
	    		currentItemImage=new Image("file:C:\\Classes\\CSC 335\\gitrepos\\pokemon-teammoltres\\images\\items\\safariBall.png", false);
	    		if(tic2==0) {
	    		currentCapture.throwBall(new Random().nextInt(100));
	    		}
	    	}
	    	else if(currentItem.getClass()==new Rock().getClass()) {
	    		currentItemImage=new Image("file:C:\\Classes\\CSC 335\\gitrepos\\pokemon-teammoltres\\images\\items\\rock.png", false);
	    		if(tic2==0) {
	    		currentCapture.throwRock();
	    		}
	    	}
	    	else if(currentItem.getClass()==new Bait().getClass()) {
	    		currentItemImage=new Image("file:C:\\Classes\\CSC 335\\gitrepos\\pokemon-teammoltres\\images\\items\\bait.png", false);
	    		if(tic2==0) {
	    		currentCapture.throwBait();
	    		}
	    	}
	    	g2.drawImage(backGround, 0, 0,450,300);
	    	g2.drawImage(currentItemImage, itemsx, itemsy, 30, 30);
	    	g2.drawImage(currentPokemonImage, 270, 70, 125, 125);
    		g2.drawImage(characterSheet, 8, 8, 70, 70, 20, 165, 150, 150);
    		if(tic2<16) {
    		itemsx+=10;
    		itemsy-=10;
    		}
    		else {
    			itemsx+=5;
    			itemsy+=5;
    		}
    		if(tic2==30) {
	    	System.out.println("Threw " +currentItem.toString());
	    	timeline2.stop();
	        itemsx=50;
	        itemsy=170;
	        g2.drawImage(backGround, 0, 0,450,300);
	    	g2.drawImage(currentPokemonImage, 270, 70, 125, 125);
    		g2.drawImage(characterSheet, 8, 8, 70, 70, 20, 165, 150, 150);
    		if(currentCapture.retreat(new Random().nextInt(100))) {
    			System.out.println(currentPokemon.getName()+" retreated");
    			retreatTimeline=new Timeline(new KeyFrame(Duration.millis(50), new RetreatAnimateStarter()));
    		    retreatTimeline.setCycleCount(Animation.INDEFINITE);
    		    retreatTimeline.play();
    		}
    		emoteTimeline=new Timeline(new KeyFrame(Duration.millis(50), new EmoteAnimateStarter()));
    		emoteTimeline.setCycleCount(Animation.INDEFINITE);
    		emoteTimeline.play();
    		}
    		tic2++;
	    }
	    
  }

  // This event handler will start the Timeline animation 
  private class StartTimerButtonListener implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent event) {
      // The play method begins Timeline calling the handle method
      // to draw the background and the ship.
      // TODO 3: Start the Timeline
    	if(itemsx==50) {
    	if(event.getSource()==throwBallButton) {
    		if(currentCapture.checkSafariBalls()) {
    		currentItem=new SafariBall();
    		}
    		else {
    			System.out.println("No safari balls left");
    		}
    	}
    	if(event.getSource()==throwBaitButton) {
    		currentItem=new Bait();
    	}
    	if(event.getSource()==throwRockButton) {
    		currentItem=new Rock();
    	}
    	
    	
    	timeline.play();
    	}

    }
  }
  
//This event handler will start the Timeline animation 
 private class PokemonButtonListener implements EventHandler<ActionEvent> {
   @Override
   public void handle(ActionEvent event) {
     // The play method begins Timeline calling the handle method
     // to draw the background and the ship.
     // TODO 3: Start the Timeline
	   
	   if(event.getSource()==pikachuButton) {
		   currentPokemonImage=pikachu;
		   currentPokemon=new Pikachu();
		   System.out.println("Pikachu");
		   
	   }
	   if(event.getSource()==charmanderButton) {
		   currentPokemonImage=charmander;
		   currentPokemon=new Charmander();
		   System.out.println("Charmander");
	   }
	   if(event.getSource()==bulbasaurButton) {
		   currentPokemonImage=bulbasaur;
		   currentPokemon=new Bulbasaur();
		   System.out.println("Bulbasaur");
	   }
	   if(event.getSource()==squirtleButton) {
		   currentPokemon=new Squirtle();
		   currentPokemonImage=squirtle;
		   System.out.println("Squirtle");
	   }
	   g2.drawImage(currentPokemonImage, 270, 70, 125, 125);
	   setUpCapture(currentPokemon);
	   
	   

   }
 }
 
 public void setUpCapture(Pokemon currentPokemon) {
	 GridPane captureButtons=new GridPane();
	 throwBallButton = new Button("Throw Ball");
	 throwBaitButton = new Button("Throw Bait");
	 throwRockButton = new Button("Throw Rock");
	 captureButtons.add(throwBallButton, 0, 0);
	 captureButtons.add(throwBaitButton, 1, 0);
	 captureButtons.add(throwRockButton, 2, 0);
	 captureButtons.setAlignment(Pos.CENTER);
	 pane.setTop(captureButtons);
	 BorderPane.setAlignment(captureButtons, Pos.BOTTOM_CENTER);
	 throwBallButton.setOnAction(new StartTimerButtonListener());
	 throwBaitButton.setOnAction(new StartTimerButtonListener());
	 throwRockButton.setOnAction(new StartTimerButtonListener());
	 currentCapture=new Capture(currentPokemon,currentTrainer);
	 
	 
	 
 }
}