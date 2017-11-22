package pokemon;

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
	private Image bgImg;
	private BattleView battleView;
	private GraphicsContext gc;
	
	
	public PokeBattleAnimation(BattleView battleView, String bgPath, String standbyPath, String firstAttackPath) {
		
		this.battleView = battleView;
		gc = battleView.getGraphicsContext2D();
		bgImg = new Image(bgPath, false);
		constructTimelines(standbyPath, firstAttackPath);
	}
	
	private void constructTimelines(String standbyPath, String firstAttackPath) {
		
		//Standby Timeline
		standby = new Timeline(new KeyFrame(Duration.millis(50), new AnimateStandby(standbyPath)));
		standby.setCycleCount(Animation.INDEFINITE);
		
		//First Attack Timeline
		firstAttack = new Timeline(new KeyFrame(Duration.millis(50), new AnimateFirstAttack(firstAttackPath)));
		firstAttack.setCycleCount(Animation.INDEFINITE);
		
		
	}
	
	public void animateStandby() {
		standby.play();
	}
	
	public void animateFirstAttack() {
		firstAttack.play();
	}
	
	
	private class AnimateStandby implements EventHandler<ActionEvent> {
		int tic;
		double sx, sy, sw, sh, dx, dy, dw, dh;
		private Image spritesheet;

		public AnimateStandby(String standbyPath) {
			
			spritesheet = new Image(standbyPath, false);
			tic = 0;
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

			tic++;
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
		int tic;
		double sx, sy, sw, sh, dx, dy, dw, dh;
		private Image spritesheet;

		public AnimateFirstAttack(String firstAttackPath) {
			
			spritesheet = new Image(firstAttackPath, false);
			tic = 0;
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

			tic++;
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
	
	
	
	
	
	
	
	
}
