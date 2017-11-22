package battle;

import java.util.Observable;
import java.util.Observer;

import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import pokemon.PokeBattleAnimation;

public class BattleView extends Canvas implements Observer {
	
	private BattleLogicForView battle;
	private GraphicsContext gc;
	private Image battleGround;
	private PokeBattleAnimation pokeAnimation;
	private ParallelTransition pTransition;
	
	public BattleView(BattleLogicForView battle, double width, double height) {
		
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
				
		pTransition = new ParallelTransition();
		pTransition.getChildren().add(battle.getTrainer1().getActiveBattlePokemon().getBattleAnimation().getStandby());
		pTransition.getChildren().add(battle.getTrainer2().getActiveBattlePokemon().getBattleAnimation().getBackStandby());
		pTransition.setCycleCount(Timeline.INDEFINITE);
		pTransition.play();
	}
	
	

}
