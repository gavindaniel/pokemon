package battle;

import java.util.Observable;
import java.util.Observer;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import pokemon.PokeBattleAnimation;

public class BattleView extends Canvas implements Observer {
	
	private BattleLogic battle;
	private GraphicsContext gc;
	private Image battleGround;
	private PokeBattleAnimation pokeAnimation;
	
	public BattleView(BattleLogic battle, double width, double height) {
		
		this.setWidth(width);
		this.setHeight(height);
		
		this.battle = battle;
		
		gc = this.getGraphicsContext2D();
		
		battleGround = new Image("file:images/battle/battle-background.png", false);
		
		gc.drawImage(battleGround, 0, 0, this.getWidth(), this.getHeight());
		
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
	
	public void animate() {
		String bgPath = "file:images/battle/battle-background.png";
		String standbyPath = "file:images/battle/Pikachu/pikachu-standby.png";
		String firstAttackPath = "file:images/battle/Pikachu/pikachu-thunderbolt.png";
		
		pokeAnimation = new PokeBattleAnimation(this, bgPath, standbyPath, firstAttackPath);
//		pokeAnimation.animateStandby();
		pokeAnimation.animateFirstAttack();
	}
	
	

}
