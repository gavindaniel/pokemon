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
	private Image bgImg;
	private BattleView battleView;
	private GraphicsContext gc;
	
	
	public PokeBattleAnimation(BattleView battleView, List<String> paths) {
		
		this.battleView = battleView;
		gc = battleView.getGraphicsContext2D();
		bgImg = new Image(paths.get(0), false);
		constructTimelines(paths);
	}
	
	private void constructTimelines(List<String> paths) {

		//Standby Timeline
		standby = new Timeline(new KeyFrame(Duration.millis(50), new AnimateStandby(paths.get(0))));
		standby.setCycleCount(Animation.INDEFINITE);

		//First Attack Timeline
		firstAttack = new Timeline(new KeyFrame(Duration.millis(50), new AnimateFirstAttack(paths.get(1))));
		firstAttack.setCycleCount(Animation.INDEFINITE);

		//secondAttack Timeline
		secondAttack = new Timeline(new KeyFrame(Duration.millis(50), new AnimateSecondAttack(paths.get(2))));
		secondAttack.setCycleCount(Animation.INDEFINITE);

		//Third Attack Timeline
		thirdAttack = new Timeline(new KeyFrame(Duration.millis(50), new AnimateThirdAttack(paths.get(3))));
		thirdAttack.setCycleCount(Animation.INDEFINITE);
		
		//Fourth Attack Timeline
		fourthAttack = new Timeline(new KeyFrame(Duration.millis(50), new AnimateFourthAttack(paths.get(4))));
		fourthAttack.setCycleCount(Animation.INDEFINITE);

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

			gc.drawImage(bgImg, 0, 0, battleView.getWidth(), battleView.getHeight());
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

			// TODO 2: Draw the walker, update the proper variables, stop animation
			gc.drawImage(spritesheet, sx, sy, sw, sh, dx, dy, dw, dh);

			sx += 60;
			sx %= (60*33);
		}
	}
	
	private class AnimateFirstAttack implements EventHandler<ActionEvent> {
		double sx, sy, sw, sh, dx, dy, dw, dh;
		private Image spritesheet;

		public AnimateFirstAttack(String firstAttackPath) {
			
			spritesheet = new Image(firstAttackPath, false);
			sx = 65;
			sy = 70;
			sw = 100;
			sh = 100;
			dx = 555;
			dy = 190;
			dw = 150;
			dh = 150;
		}

		@Override
		public void handle(ActionEvent e) {

			gc.drawImage(bgImg, 0, 0, battleView.getWidth(), battleView.getHeight());
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

			// TODO 2: Draw the walker, update the proper variables, stop animation
			gc.drawImage(spritesheet, sx, sy, sw, sh, dx, dy, dw, dh);

			sx += 192;
			
			if (sx >= 192*56) {
				firstAttack.stop();
				animateStandby();
			}
		}
	}
	
	private class AnimateSecondAttack implements EventHandler<ActionEvent> {
		double sx, sy, sw, sh, dx, dy, dw, dh;
		private Image spritesheet;

		public AnimateSecondAttack(String secondAttackPath) {
			
			spritesheet = new Image(secondAttackPath, false);
			sx = 65;
			sy = 70;
			sw = 100;
			sh = 100;
			dx = 555;
			dy = 190;
			dw = 150;
			dh = 150;
		}

		@Override
		public void handle(ActionEvent e) {

			gc.drawImage(bgImg, 0, 0, battleView.getWidth(), battleView.getHeight());
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

			// TODO 2: Draw the walker, update the proper variables, stop animation
			gc.drawImage(spritesheet, sx, sy, sw, sh, dx, dy, dw, dh);

			sx += 192;
			
			if (sx >= 192*56) {
				secondAttack.stop();
				animateStandby();
			}
		}
	}
	
	private class AnimateThirdAttack implements EventHandler<ActionEvent> {
		double sx, sy, sw, sh, dx, dy, dw, dh;
		private Image spritesheet;

		public AnimateThirdAttack(String thirdAttackPath) {
			
			spritesheet = new Image(thirdAttackPath, false);
			sx = 65;
			sy = 70;
			sw = 100;
			sh = 100;
			dx = 555;
			dy = 190;
			dw = 150;
			dh = 150;
		}

		@Override
		public void handle(ActionEvent e) {

			gc.drawImage(bgImg, 0, 0, battleView.getWidth(), battleView.getHeight());
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

			// TODO 2: Draw the walker, update the proper variables, stop animation
			gc.drawImage(spritesheet, sx, sy, sw, sh, dx, dy, dw, dh);

			sx += 192;
			
			if (sx >= 192*56) {
				thirdAttack.stop();
				animateStandby();
			}
		}
	}
	
	private class AnimateFourthAttack implements EventHandler<ActionEvent> {
		double sx, sy, sw, sh, dx, dy, dw, dh;
		private Image spritesheet;

		public AnimateFourthAttack(String fourthAttackPath) {
			
			spritesheet = new Image(fourthAttackPath, false);
			sx = 65;
			sy = 70;
			sw = 100;
			sh = 100;
			dx = 555;
			dy = 190;
			dw = 150;
			dh = 150;
		}

		@Override
		public void handle(ActionEvent e) {

			gc.drawImage(bgImg, 0, 0, battleView.getWidth(), battleView.getHeight());
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

			// TODO 2: Draw the walker, update the proper variables, stop animation
			gc.drawImage(spritesheet, sx, sy, sw, sh, dx, dy, dw, dh);

			sx += 192;
			
			if (sx >= 192*56) {
				fourthAttack.stop();
				animateStandby();
			}
		}
	}
	
	
	
	
	
	
}
