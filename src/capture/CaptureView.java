package capture;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import items.Bait;
import items.MasterBall;
import items.Rock;
import items.SafariBall;
import items.UltraBall;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class CaptureView extends Canvas implements Observer {
	
	private Capture capture;
	private GraphicsContext gc;
	private Image battleGround;
	private BorderPane window;
	Button throwBallButton,throwUltraBallButton,throwMasterBallButton;
	 Button throwBaitButton;
	 Button throwRockButton;
	 Button runButton;
	 CaptureAnimations captureAnimation;
	
	
	public CaptureView(Capture capture, double width, double height,BorderPane window) {
		
		this.setWidth(width);
		this.setHeight(height);
		this.window=window;
		this.capture = capture;
		captureAnimation=new CaptureAnimations(this,"file:images/battle/battle-background.png",capture.currentPoke.getStandByPath(),capture.currentPoke.getRunAwayPath(),capture.currentPoke.getCapturePath(),this.capture.currentPoke.getCoordinates(),capture);
		gc = this.getGraphicsContext2D();
		
		battleGround = new Image("file:images/battle/battle-background.png", false);
		
		gc.drawImage(battleGround, 0, 0, this.getWidth(), this.getHeight());
		setUpCapture();
		setUpCaptureButtons();
		

		
	}
	
	
	public void setUpCapture() {
		
		captureAnimation.animateStandby();
	}
	
	
	@Override
	/**
	 * Called by the setChanged/notifyObserver methods in the observable.
	 */
	public void update(Observable arg0, Object arg1) {
		printBattleStage();
	}
	
	private void printBattleStage() {
		
		//Draw Background
		gc.drawImage(battleGround, 0, 0, this.getWidth(), this.getHeight());
	}
	
	
	
	
	
	// This event handler will handle the buttons for catching the Pokemon 
	  private class CaptureButtonListener implements EventHandler<ActionEvent> {
	    @Override
	    public void handle(ActionEvent event) {
	    	if(captureAnimation.canUseItem()) {
	    		
	    		//Throw safariball
	    	if(event.getSource()==throwBallButton) {
	    		if(capture.checkSafariBalls()) {
	    		captureAnimation.animateItemThrow(new SafariBall());
	    		capture.currentTrain.removeSafariBall();
	    		}
	    		else {
	    			System.out.println("Out of pokeballs");
	    		}
	    }
	    	
	    	//Throw ultraball
	    	if(event.getSource()==throwUltraBallButton) {
	    		if(capture.checkUltraBalls()) {
	    		captureAnimation.animateItemThrow(new UltraBall());
	    		capture.currentTrain.removeUltraBall();
	    		}
	    		else {
	    			System.out.println("Out of UltraBalls");
	    		}
	    }
	    	
	    	//Throw masterball
	    	if(event.getSource()==throwMasterBallButton) {
	    		if(capture.checkMasterBalls()) {
	    		captureAnimation.animateItemThrow(new MasterBall());
	    		capture.currentTrain.removeMasterBall();
	    		}
	    		else {
	    			System.out.println("Out of MasterBalls");
	    		}
	    }
	    	
	    	
	    	if(event.getSource()==throwBaitButton) {
	    		captureAnimation.animateItemThrow(new Bait());
	    		capture.throwBait();
	    }
	    	if(event.getSource()==throwRockButton) {
	    		captureAnimation.animateItemThrow(new Rock());
	    		capture.throwRock();
	    }
	    	if(event.getSource()==runButton) {
	    		System.exit(0);
	    }
	  }
	    	
	    }
	  }
	  
	  //Let the pokemon try to retreat
	  public void attemptRetreat() {
		  
	    	if(capture.retreat(new Random().nextInt(100)+1)) {
	    		captureAnimation.animateRunAway();
	    	}
	    	
	  }
	  
	  
	  //Try to capture the pokemon
	  public void attemptCapture() {
		  
		  if(capture.throwBall(new Random().nextInt(100)+1)) {
			  System.out.println(capture.currentTrain.getOwnedPokemonList());
			  captureAnimation.animateCapture();
		  }
			  
		  
	  }
	  
	//Try to capture the pokemon with ultraball
	  public void attemptUltraCapture() {
		  
		  if(capture.throwUltraBall(new Random().nextInt(100)+1)) {
			  System.out.println(capture.currentTrain.getOwnedPokemonList());
			  captureAnimation.animateCapture();
		  }
			  
		  
	  }
	  
	//Catch pokemon with masterball
	  public void attemptMasterCapture() {
		  
		  capture.throwMasterBall();
			  System.out.println(capture.currentTrain.getOwnedPokemonList());
			  captureAnimation.animateCapture();
		  
			  
		  
	  }
	  
	  
	  
	  
	  
	  
	//Set up capture buttons
	 public void setUpCaptureButtons() {
		 GridPane captureButtons=new GridPane();
		 throwBallButton = new Button("Throw SafariBall");
		 throwUltraBallButton = new Button("Throw UltraBall");
		 throwMasterBallButton = new Button("Throw MasterBall");
		 throwBaitButton = new Button("Throw Bait");
		 throwRockButton = new Button("Throw Rock");
		 runButton = new Button("Run Away");
		 captureButtons.add(throwBallButton, 0, 0);
		 captureButtons.add(throwBaitButton, 1, 0);
		 captureButtons.add(throwRockButton, 2, 0);
		 captureButtons.add(throwUltraBallButton, 3, 0);
		 captureButtons.add(throwMasterBallButton, 4, 0);
		 captureButtons.add(runButton, 5, 0);
		 captureButtons.setAlignment(Pos.CENTER);
		 window.setTop(captureButtons);
		 BorderPane.setAlignment(captureButtons, Pos.BOTTOM_CENTER);
		 throwBallButton.setOnAction(new CaptureButtonListener());
		 throwBaitButton.setOnAction(new CaptureButtonListener());
		 throwRockButton.setOnAction(new CaptureButtonListener());
		 throwUltraBallButton.setOnAction(new CaptureButtonListener());
		 throwMasterBallButton.setOnAction(new CaptureButtonListener());
		 runButton.setOnAction(new CaptureButtonListener());
	 }
	
	
	
	
	

}