package capture;




import javax.sound.sampled.UnsupportedAudioFileException;

import controller.SoundClip;
import items.Bait;
import items.Item;
import items.MasterBall;
import items.SafariBall;
import items.UltraBall;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import pokemon.Pokemon;


public class CaptureAnimations {

	private Timeline standby,itemThrow,runAway,capture,emotion;
	private Image bgImg;
	private CaptureView captureView;
	private GraphicsContext gc;
	private String runAwayPath,capturePath;
	private int[][] coordinates;
	private Capture currentCapture;
	private Item itemCurrent;
	
	
	public CaptureAnimations(CaptureView captureView, String bgPath, String standbyPath,String runAwayPath,String capturePath,int [][] coordinates,Capture currentCapture) throws UnsupportedAudioFileException {
		
		this.captureView = captureView;
		gc = captureView.getGraphicsContext2D();
		bgImg = new Image(bgPath, false);
		this.runAwayPath=runAwayPath;
		this.capturePath=capturePath;
		this.coordinates=coordinates;
		this.currentCapture=currentCapture;
		constructTimelines(standbyPath);
		
	}
	
	private void constructTimelines(String standbyPath) {
		
		//Standby Timeline
		standby = new Timeline(new KeyFrame(Duration.millis(50), new AnimateStandby(standbyPath)));
		standby.setCycleCount(Animation.INDEFINITE);
		//Item Throw Timeline
		
		
	}
	
	//Animate the pokemon standing by
	public void animateStandby() {
		standby.play();
	}
	
	//Animate the pokemon running away
		public void animateRunAway() {
			if(capture==null) {
			runAway = new Timeline(new KeyFrame(Duration.millis(50), new AnimatePokemonRun(runAwayPath)));
			runAway.setCycleCount(Animation.INDEFINITE);
			standby.stop();
			runAway.play();
			}
		}
	
	
	//Set up timeframe for animation throw
	public void animateItemThrow(Item currentItem) {
		
		itemThrow = new Timeline(new KeyFrame(Duration.millis(50), new AnimateItemThrow(currentItem)));
		itemThrow.setCycleCount(Animation.INDEFINITE);
		itemThrow.play();
	}
	
	//Animate the pokemon running away
			public void animateCapture() {
				
				capture = new Timeline(new KeyFrame(Duration.millis(50), new AnimatePokemonCapture(capturePath)));
				capture.setCycleCount(Animation.INDEFINITE);
				standby.stop();
				capture.play();
			}
		//Animate the pokemons emotion
		public void animateEmotion(String item) {
			
			emotion = new Timeline(new KeyFrame(Duration.millis(50), new AnimatePokemonEmotion(item)));
			emotion.setCycleCount(Animation.INDEFINITE);
			emotion.play();
		}
		
		//Draw 'health' bars
		public void drawBars() {
			gc.setFill(Color.BLACK);
			gc.fillRoundRect(560, 177, 105, 25,20,20);
			gc.fillRoundRect(560, 147, 105, 25,20,20);
			gc.setFill(Color.RED);
			gc.fillRoundRect(563, 180, 100, 20,20,20);
			gc.fillRoundRect(563, 150, 100, 20,20,20);
			gc.setFill(Color.GREEN);
			gc.fillRoundRect(563, 180, 100-currentCapture.getCurrentCatchRate()+5, 20,20,20);
			gc.fillRoundRect(563, 150, currentCapture.getCurrentRunRate()+5, 20,20,20);
			gc.setFill(Color.BLACK);
			gc.fillText("Chance to run", 574, 164, 300);
			gc.fillText("Chance to catch", 570, 194, 300);
		}
	
			
			
			
	//Check if the timer is done to use another item
	public boolean canUseItem() {
		if(itemThrow==null && emotion==null && runAway==null && capture==null) {
			return true;
		}
		if(capture!=null || runAway!=null) {
			return false;
		}
		if(itemThrow.getStatus()==Animation.Status.STOPPED && emotion==null) {
			return true;
		}
		if(itemThrow.getStatus()==Animation.Status.STOPPED && emotion.getStatus()==Animation.Status.STOPPED) {
			return true;
		}
		return false;
		
	}
	
	
	
	
	/*
	 * This handler will take care of animating a Pokemons emotion
	 */
	private class AnimatePokemonEmotion implements EventHandler<ActionEvent> {
		double sx, sy, sw, sh, dx, dy, dw, dh;
		private Image spritesheet,anger,heart,itemImage;
		int tic;
		
		public AnimatePokemonEmotion(String item) {
			
			spritesheet = new Image(capturePath, false);
			if(item=="Rock") {
			itemImage=new Image("file:images/items/anger.png", false);
			}
			else {
			itemImage=new Image("file:images/items/heart.png", false);
			}
			tic=0;
		}

		@Override
		public void handle(ActionEvent e) {
			emotion.jumpTo(standby.getCurrentTime());
			if(tic>20 && tic<26 || tic>40 && tic<46) {
				
			}
			else {
			gc.drawImage(itemImage, 530, 230, 35, 35);
			gc.drawImage(itemImage, 650, 230, 35, 35);
			}
			if(tic==60) {
			emotion.stop();
			captureView.attemptRetreat();
			}
			tic++;
			}
		
		
		
		
	}
	
	
	
	
	
	/*
	 * This handler will take care of animating a Pokemon getting captured
	 */
	private class AnimatePokemonCapture implements EventHandler<ActionEvent> {
		double sx, sy, sw, sh, dx, dy, dw, dh;
		private Image spritesheet,ball;

		public AnimatePokemonCapture(String capturePath) {
			
			spritesheet = new Image(capturePath, false);
			
			if(itemCurrent.getClass()==SafariBall.class) {
			ball=new Image("file:images/items/safariball2.png", false);
			}
			else if(itemCurrent.getClass()==UltraBall.class) {
				ball=new Image("file:images/items/ultraball.png", false);
				}
			if(itemCurrent.getClass()==MasterBall.class) {
				ball=new Image("file:images/items/masterball.png", false);
				}
			sx = coordinates[0][0];
			sy = coordinates[0][1];
			sw = coordinates[0][2];
			sh = coordinates[0][3];
			dx = coordinates[0][4];
			dy = coordinates[0][5];
			dw = coordinates[0][6];
			dh = coordinates[0][7];
		}

		@Override
		public void handle(ActionEvent e) {

			gc.drawImage(bgImg, 0, 0, captureView.getWidth(), captureView.getHeight());
			drawBars();

			// TODO 2: Draw the walker, update the proper variables, stop animation
			gc.drawImage(spritesheet, sx, sy, sw, sh, dx, dy, dw, dh);
			gc.drawImage(ball, 560, 279, 35, 35);

			//sx += 60;
			//sx %= (60*33);
			dw-=5;
			dh-=5;
			dx-=.000001;
			dy+=3;
			if(dw==0) {
				gc.drawImage(bgImg, 0, 0, captureView.getWidth(), captureView.getHeight());
				capture.stop();
				captureView.endCapture();
			}
		}
	}
	

	
	
	
	
	
	/*
	 * This handler will take care of animating a Pokemon running away
	 */
	private class AnimatePokemonRun implements EventHandler<ActionEvent> {
		int tic;
		double sx, sy, sw, sh, dx, dy, dw, dh;
		private Image spritesheet;
		public AnimatePokemonRun(String runAwayPath) {
			spritesheet = new Image(runAwayPath, false);
			sx = coordinates[1][0];
			sy = coordinates[1][1];
			sw = coordinates[1][2];
			sh = coordinates[1][3];
			dx = coordinates[1][4];
			dy = coordinates[1][5];
			dw = coordinates[1][6];
			dh = coordinates[1][7];
		}

		/*
		 * sx the source rectangle's X coordinate position. 
		 * sy the source rectangle's Y
		 * coordinate position. 
		 * sw the source rectangle's width. 
		 * sh the source rectangle's height. 
		 * dx the destination rectangle's X coordinate position. 
		 * dy the destination rectangle's Y coordinate position. 
		 * dw the destination rectangle's width. 
		 * dh the destination rectangle's height.
		 */
		
		@Override
		public void handle(ActionEvent e) {
			gc.drawImage(bgImg, 0, 0, captureView.getWidth(), captureView.getHeight());
			/*
			 * sx the source rectangle's X coordinate position. 
			 * sy the source rectangle's Y
			 * coordinate position. 
			 * sw the source rectangle's width. 
			 * sh the source
			 * rectangle's height. 
			 * dx the destination rectangle's X coordinate position. 
			 * dy the destination rectangle's Y coordinate position. 
			 * dw the destination rectangle's width. 
			 * dh the destination rectangle's height.
			 */

			gc.drawImage(spritesheet, sx, sy, sw, sh, dx, dy, dw, dh);

			sx += coordinates[1][8];
			
			
	}
	}
	
	
	
	
	
	
	/*
	 * This handler will take care of animating an item being thrown
	 */
	private class AnimateItemThrow implements EventHandler<ActionEvent> {
		int tic;
		double x,y,w,h;
		private Image item;

		public AnimateItemThrow(Item currentItem) {
			String ItemPath=currentItem.getImagePath();
			itemCurrent=currentItem;
			item = new Image(ItemPath, false);
			tic = 0;
			x=-100;
			y=550;
			w=100;
			h=100;
			
		}

		@Override
		public void handle(ActionEvent e) {
			itemThrow.jumpTo(standby.getCurrentTime());
			tic++;
			// Draw the item moving across the background
			gc.drawImage(item, x, y, w, h);
			w-=1;
			h-=1;
			x+=10;
			if(tic>45) {
				y=y*1.05;
			}
			else {
				y-=10;
			}
			if(tic>66) {
				itemThrow.stop();
				if(itemCurrent.getClass()==SafariBall.class ) {
					captureView.attemptCapture();
					captureView.attemptRetreat();	
				}
				else if(itemCurrent.getClass()==UltraBall.class) {
					captureView.attemptUltraCapture();
					captureView.attemptRetreat();
				}
				else if(itemCurrent.getClass()==MasterBall.class) {
					captureView.attemptMasterCapture();
				}
				else {
					if (itemCurrent.getClass()==Bait.class) {
					animateEmotion("Bait");	
				}
					else {
						animateEmotion("Rock");
					}
				
			}
		}
	}
	}
	
	
	
	
	
	
	
	
	/*
	 * This handler will take care of animating a pokemon standing still
	 */
	private class AnimateStandby implements EventHandler<ActionEvent> {
		double sx, sy, sw, sh, dx, dy, dw, dh;
		private Image spritesheet;

		public AnimateStandby(String standbyPath) {
			
			spritesheet = new Image(standbyPath, false);
			sx = coordinates[0][0];
			sy = coordinates[0][1];
			sw = coordinates[0][2];
			sh = coordinates[0][3];
			dx = coordinates[0][4];
			dy = coordinates[0][5];
			dw = coordinates[0][6];
			dh = coordinates[0][7];
		}

		@Override
		public void handle(ActionEvent e) {
			gc.drawImage(bgImg, 0, 0, captureView.getWidth(), captureView.getHeight());
			drawBars();

			// TODO 2: Draw the walker, update the proper variables, stop animation
			gc.drawImage(spritesheet, sx, sy, sw, sh, dx, dy, dw, dh);


			sx += coordinates[0][8];
			sx %= (coordinates[0][8]*coordinates[0][9]);
		}
	}
}
