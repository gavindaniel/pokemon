package pokemon;

import java.util.List;

import battle.BattleView;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.util.Duration;

/**
 * Class handles all pokemon animations during battle.
 * 
 * @author Abdullah Asaad
 *
 */
public class PokeBattleAnimation {

	private Timeline standby;
	private Timeline firstAttack;
	private Timeline secondAttack;
	private Timeline thirdAttack;
	private Timeline fourthAttack;
	private Timeline backStandby;
	private Timeline backAttack;
	private BattleView battleView;
	private GraphicsContext gc;
	private int[][] coordinates;
	
	
	public PokeBattleAnimation(String bgPath, List<String> paths, int[][] coordinates) {
		
		this.coordinates = coordinates;
		constructTimelines(paths);
	}
	
	

	/**
	 * @return the standby
	 */
	public Timeline getStandby() {
		return standby;
	}



	/**
	 * @param standby the standby to set
	 */
	public void setStandby(Timeline standby) {
		this.standby = standby;
	}



	/**
	 * @return the firstAttack
	 */
	public Timeline getFirstAttack() {
		return firstAttack;
	}



	/**
	 * @param firstAttack the firstAttack to set
	 */
	public void setFirstAttack(Timeline firstAttack) {
		this.firstAttack = firstAttack;
	}



	/**
	 * @return the secondAttack
	 */
	public Timeline getSecondAttack() {
		return secondAttack;
	}



	/**
	 * @param secondAttack the secondAttack to set
	 */
	public void setSecondAttack(Timeline secondAttack) {
		this.secondAttack = secondAttack;
	}



	/**
	 * @return the thirdAttack
	 */
	public Timeline getThirdAttack() {
		return thirdAttack;
	}



	/**
	 * @param thirdAttack the thirdAttack to set
	 */
	public void setThirdAttack(Timeline thirdAttack) {
		this.thirdAttack = thirdAttack;
	}



	/**
	 * @return the fourthAttack
	 */
	public Timeline getFourthAttack() {
		return fourthAttack;
	}



	/**
	 * @param fourthAttack the fourthAttack to set
	 */
	public void setFourthAttack(Timeline fourthAttack) {
		this.fourthAttack = fourthAttack;
	}



	/**
	 * @return the backStandby
	 */
	public Timeline getBackStandby() {
		return backStandby;
	}



	/**
	 * @param backStandby the backStandby to set
	 */
	public void setBackStandby(Timeline backStandby) {
		this.backStandby = backStandby;
	}
	
	
	/**
	 * @return the backAttack
	 */
	public Timeline getBackAttack() {
		return backAttack;
	}


	/**
	 * @param backAttack the backAttack to set
	 */
	public void setBackAttack(Timeline backAttack) {
		this.backAttack = backAttack;
	}



	public void setBattleView(BattleView battleView) {
		this.battleView = battleView;
		gc = battleView.getGraphicsContext2D();
	}
	
	private void constructTimelines(List<String> paths) {

		//Standby Timeline
		standby = new Timeline(new KeyFrame(Duration.millis(50), new AnimateStandby(paths.get(0))));
		standby.setCycleCount(Animation.INDEFINITE);

		//First Attack Timeline
		firstAttack = new Timeline(new KeyFrame(Duration.millis(50), new AnimateFirstAttack(paths.get(1))));
		firstAttack.setCycleCount(coordinates[1][9]);

		//secondAttack Timeline
		secondAttack = new Timeline(new KeyFrame(Duration.millis(50), new AnimateSecondAttack(paths.get(2))));
		secondAttack.setCycleCount(coordinates[2][9]);

		//Third Attack Timeline
		thirdAttack = new Timeline(new KeyFrame(Duration.millis(50), new AnimateThirdAttack(paths.get(3))));
		thirdAttack.setCycleCount(coordinates[3][9]);
		
		//Fourth Attack Timeline
		fourthAttack = new Timeline(new KeyFrame(Duration.millis(50), new AnimateFourthAttack(paths.get(4))));
		fourthAttack.setCycleCount(coordinates[4][9]);
		
		//Back Standby Timeline
		backStandby = new Timeline(new KeyFrame(Duration.millis(50), new AnimateBackStandby(paths.get(5))));
		backStandby.setCycleCount(Animation.INDEFINITE);
		
		//Back Attack Timeline
		backAttack = new Timeline(new KeyFrame(Duration.millis(50), new AnimateBackAttack(paths.get(5))));
		backAttack.setCycleCount(10);	//Must change if "midpoint" from event handler is changed
	}
	
	public void animateStandby() {
		standby.play();
	}
	
	public void animateFirstAttack() {
		firstAttack.play();
	}
	
	public void animateSecondAttack() {
		secondAttack.play();
	}
	
	public void animateThirdAttack() {
		thirdAttack.play();
	}
	
	public void animateFourthAttack() {
		fourthAttack.play();
	}
	
	public void animateBackStandby() {
		backStandby.play();
	}
	
	public void animateBackAttack() {
		backAttack.play();
	}
	
	
	private class AnimateStandby implements EventHandler<ActionEvent> {
		double sx, sy, sw, sh, dx, dy, dw, dh;
		private Image spritesheet;

		public AnimateStandby(String spritePath) {
			
			spritesheet = new Image(spritePath, false);
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
			
			battleView.drawBackgroundAndMenus();
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

			sx += coordinates[0][8];
			sx %= (coordinates[0][8]*coordinates[0][9]);
		}
	}
	
	private class AnimateFirstAttack implements EventHandler<ActionEvent> {
		double sx, sy, sw, sh, dx, dy, dw, dh;
		private Image spritesheet;

		public AnimateFirstAttack(String spritePath) {
			
			spritesheet = new Image(spritePath, false);
			sx = coordinates[1][0];
			sy = coordinates[1][1];
			sw = coordinates[1][2];
			sh = coordinates[1][3];
			dx = coordinates[1][4];
			dy = coordinates[1][5];
			dw = coordinates[1][6];
			dh = coordinates[1][7];
		}

		@Override
		public void handle(ActionEvent e) {
			
			battleView.drawBackgroundAndMenus();
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
			
			if (sx >= coordinates[1][8]*coordinates[1][9]) {
				sx = coordinates[1][0];
			}
		}
	}
	
	private class AnimateSecondAttack implements EventHandler<ActionEvent> {
		double sx, sy, sw, sh, dx, dy, dw, dh;
		private Image spritesheet;

		public AnimateSecondAttack(String spritePath) {
			
			spritesheet = new Image(spritePath, false);
			sx = coordinates[2][0];
			sy = coordinates[2][1];
			sw = coordinates[2][2];
			sh = coordinates[2][3];
			dx = coordinates[2][4];
			dy = coordinates[2][5];
			dw = coordinates[2][6];
			dh = coordinates[2][7];
		}

		@Override
		public void handle(ActionEvent e) {

			battleView.drawBackgroundAndMenus();
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

			sx += coordinates[2][8];

			if (sx >= coordinates[2][8]*coordinates[2][9]) {
				sx = coordinates[2][0];
			}
		}
	}
	
	private class AnimateThirdAttack implements EventHandler<ActionEvent> {
		double sx, sy, sw, sh, dx, dy, dw, dh;
		private Image spritesheet;

		public AnimateThirdAttack(String spritePath) {
			
			spritesheet = new Image(spritePath, false);
			sx = coordinates[3][0];
			sy = coordinates[3][1];
			sw = coordinates[3][2];
			sh = coordinates[3][3];
			dx = coordinates[3][4];
			dy = coordinates[3][5];
			dw = coordinates[3][6];
			dh = coordinates[3][7];
		}

		@Override
		public void handle(ActionEvent e) {

			battleView.drawBackgroundAndMenus();
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

			sx += coordinates[3][8];
			
			if (sx >= coordinates[3][8]*coordinates[3][9]) {
//				thirdAttack.stop();
				sx = coordinates[3][0];
			}
		}
	}
	
	private class AnimateFourthAttack implements EventHandler<ActionEvent> {
		double sx, sy, sw, sh, dx, dy, dw, dh;
		private Image spritesheet;

		public AnimateFourthAttack(String spritePath) {
			
			spritesheet = new Image(spritePath, false);
			sx = coordinates[4][0];
			sy = coordinates[4][1];
			sw = coordinates[4][2];
			sh = coordinates[4][3];
			dx = coordinates[4][4];
			dy = coordinates[4][5];
			dw = coordinates[4][6];
			dh = coordinates[4][7];
		}

		@Override
		public void handle(ActionEvent e) {

			battleView.drawBackgroundAndMenus();
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

			sx += coordinates[4][8];
			
			if (sx >= coordinates[4][8]*coordinates[4][9]) {
//				fourthAttack.stop();
				sx = coordinates[4][0];
			}
		}
	}
	
	private class AnimateBackStandby implements EventHandler<ActionEvent> {
		double sx, sy, sw, sh, dx, dy, dw, dh;
		private Image spritesheet;

		public AnimateBackStandby(String spritePath) {
			
			spritesheet = new Image(spritePath, false);
			sx = coordinates[5][0];
			sy = coordinates[5][1];
			sw = coordinates[5][2];
			sh = coordinates[5][3];
			dx = coordinates[5][4];
			dy = coordinates[5][5];
			dw = coordinates[5][6];
			dh = coordinates[5][7];
		}

		@Override
		public void handle(ActionEvent e) {
			
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

			sx += coordinates[5][8];
			sx %= (coordinates[5][8]*coordinates[5][9]);
		}
	}
	
	private class AnimateBackAttack implements EventHandler<ActionEvent> {
		double sx, sy, sw, sh, dx, dy, dw, dh;
		private Image spritesheet;
		private int upCounter;
		private int downCounter;
		
		//Determines length of animation by setting threshold for forward movement
		private static final int midpoint = 5;
		
		//Determines how far pokemon moves forward.
		private static final double multiplier = 3;

		public AnimateBackAttack(String spritePath) {
			
			spritesheet = new Image(spritePath, false);
			sx = coordinates[5][0];
			sy = coordinates[5][1];
			sw = coordinates[5][2];
			sh = coordinates[5][3];
			dx = coordinates[5][4];
			dy = coordinates[5][5];
			dw = coordinates[5][6];
			dh = coordinates[5][7];
			
			upCounter = 0;
			downCounter = midpoint;
		}

		@Override
		public void handle(ActionEvent e) {
			
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
			
			//Move forward
			if (upCounter < midpoint) {
				upCounter++;
				gc.drawImage(spritesheet, sx, sy, sw, sh, dx + multiplier*upCounter, dy - multiplier*upCounter, dw, dh);
			}
			
			//Move backward
			else {
				downCounter--;
				gc.drawImage(spritesheet, sx, sy, sw, sh, dx + multiplier*downCounter, dy - multiplier*downCounter, dw, dh);
			}
			
			//Resets animation
			if (upCounter == midpoint && downCounter == 0) {
				upCounter = 0;
				downCounter = midpoint;
			}
		}
	}

	
}