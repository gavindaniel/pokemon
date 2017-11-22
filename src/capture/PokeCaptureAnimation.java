package capture;


import items.Bait;
import items.Item;
import items.SafariBall;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.util.Duration;


public class PokeCaptureAnimation {

	private Timeline standby,itemThrow,runAway,capture,emotion;
	private Image bgImg;
	private CaptureView2 captureView;
	private GraphicsContext gc;
	private String runAwayPath,capturePath;
	
	
	public PokeCaptureAnimation(CaptureView2 captureView, String bgPath, String standbyPath,String runAwayPath,String capturePath) {
		
		this.captureView = captureView;
		gc = captureView.getGraphicsContext2D();
		bgImg = new Image(bgPath, false);
		this.runAwayPath=runAwayPath;
		this.capturePath=capturePath;
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
			
			runAway = new Timeline(new KeyFrame(Duration.millis(50), new AnimatePokemonRun(runAwayPath)));
			runAway.setCycleCount(Animation.INDEFINITE);
			standby.stop();
			runAway.play();
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
	
			
			
			
	//Check if the timer is done to use another item
	public boolean canUseItem() {
		if(itemThrow==null) {
			return true;
		}
		if(itemThrow.getStatus()==Animation.Status.STOPPED) {
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
	 * This handler will take care of animating a Pokemon getting capture
	 */
	private class AnimatePokemonCapture implements EventHandler<ActionEvent> {
		double sx, sy, sw, sh, dx, dy, dw, dh;
		private Image spritesheet,ball;

		public AnimatePokemonCapture(String capturePath) {
			
			spritesheet = new Image(capturePath, false);
			ball=new Image("file:images/items/safariBall.png", false);
			sx = 0;
			sy = 0;
			sw = 60;
			sh = 60;
			dx = 575;
			dy = 230;
			dw = 100;
			dh = 100;
		}

		@Override
		public void handle(ActionEvent e) {

			gc.drawImage(bgImg, 0, 0, captureView.getWidth(), captureView.getHeight());

			// TODO 2: Draw the walker, update the proper variables, stop animation
			gc.drawImage(spritesheet, sx, sy, sw, sh, dx, dy, dw, dh);
			gc.drawImage(ball, 560, 279, 35, 35);

			sx += 60;
			sx %= (60*33);
			dw-=5;
			dh-=5;
			dx-=.5;
			dy+=3;
			if(dw==0) {
				gc.drawImage(bgImg, 0, 0, captureView.getWidth(), captureView.getHeight());
				capture.stop();
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
			sx = 0;
			sy = 80;
			sw = 200;
			sh = 190;
			dx = 460;
			dy = 200;
			dw = 300;
			dh = 300;
		}

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
		
		@Override
		public void handle(ActionEvent e) {
			gc.drawImage(bgImg, 0, 0, captureView.getWidth(), captureView.getHeight());
			gc.drawImage(spritesheet, sx, sy, sw, sh, dx, dy, dw, dh);
			sx += 192;
	}
	}
	
	
	
	
	
	
	/*
	 * This handler will take care of animating an item being thrown
	 */
	private class AnimateItemThrow implements EventHandler<ActionEvent> {
		int tic;
		double x,y,w,h;
		private Image item;
		Item itemCurrent;

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
				if(itemCurrent.getClass()==SafariBall.class) {
					captureView.attemptCapture();
					captureView.attemptRetreat();	
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
			sx = 0;
			sy = 0;
			sw = 60;
			sh = 60;
			dx = 575;
			dy = 230;
			dw = 100;
			dh = 100;
		}

		@Override
		public void handle(ActionEvent e) {

			gc.drawImage(bgImg, 0, 0, captureView.getWidth(), captureView.getHeight());

			// TODO 2: Draw the walker, update the proper variables, stop animation
			gc.drawImage(spritesheet, sx, sy, sw, sh, dx, dy, dw, dh);

			sx += 60;
			sx %= (60*33);
		}
	}
	
	}
