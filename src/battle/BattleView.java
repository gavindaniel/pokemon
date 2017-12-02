package battle;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javafx.animation.Animation;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import model.Trainer;
import pokemon.Attack;
import pokemon.Pokemon;

public class BattleView extends Canvas implements Observer {
	
	private BattleLogicForView battle;
	private GraphicsContext gc;
	private Image battleGround;
	private ParallelTransition idleTimeline;
	private ParallelTransition attackTimeline;
	
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
	public void update(Observable battle, Object message) {
		
		this.battle = (BattleLogicForView) battle;
		
		//Draw Background
		gc.drawImage(battleGround, 0, 0, this.getWidth(), this.getHeight());
		printBattleStage(message);
	}
	
	private void printBattleStage(Object message) {
		
		if (message instanceof Attack) {
			startAttack((Attack) message);
		}
		
		else {
			startIdle();
		}		
	}
	
	private void startIdle() {
		idleTimeline = new ParallelTransition();
		idleTimeline.getChildren().add(battle.getTrainer1().getActiveBattlePokemon().getBattleAnimation().getStandby());
		idleTimeline.getChildren().add(battle.getTrainer2().getActiveBattlePokemon().getBattleAnimation().getBackStandby());
		idleTimeline.setCycleCount(Timeline.INDEFINITE);
		idleTimeline.play();
	}
	
	private void stopIdle() {
		idleTimeline.stop();
	}
	
	private void startAttack(Attack attack) {
		
		Pokemon activePoke = battle.getActiveTrainer().getActiveBattlePokemon();
		
		//Stop any active timelines.
		stopIdle();
		
		int attackNumber = findAttackNumber(activePoke, attack);
		
		attackTimeline = new ParallelTransition();
		
//		if (attackNumber == 1) activePoke.getBattleAnimation().animateFirstAttack();		
//		else if (attackNumber == 2) activePoke.getBattleAnimation().animateSecondAttack();		
//		else if (attackNumber == 3) activePoke.getBattleAnimation().animateThirdAttack();		
//		else if (attackNumber == 4) activePoke.getBattleAnimation().animateFourthAttack();
		
		if (attackNumber == 1) attackTimeline.getChildren().add(activePoke.getBattleAnimation().getFirstAttack());	
		else if (attackNumber == 2) attackTimeline.getChildren().add(activePoke.getBattleAnimation().getSecondAttack());		
		else if (attackNumber == 3) attackTimeline.getChildren().add(activePoke.getBattleAnimation().getThirdAttack());		
		else if (attackNumber == 4) attackTimeline.getChildren().add(activePoke.getBattleAnimation().getFourthAttack());
		
		
		Trainer defendTrainer = (battle.getActiveTrainer() == battle.getTrainer1()) 
				? battle.getTrainer2() : battle.getTrainer1();
		
		attackTimeline.getChildren().add(defendTrainer.getActiveBattlePokemon().getBattleAnimation().getBackStandby());
		attackTimeline.getChildren().get(1).setCycleCount(attackTimeline.getChildren().get(0).getCycleCount());
		
		attackTimeline.play();
		attackTimeline.getChildren().clear();
		
	}
	
	private void stopAttack() {
		attackTimeline.stop();
		attackTimeline.getChildren().clear();
	}
	
	private int findAttackNumber(Pokemon poke, Attack attack ) {
		
		List<Attack> attackList = poke.getAttackList();
		
		for (int i = 0; i < attackList.size(); i++) {
			if (attackList.get(i).getName().equals(attack.getName())) {
				return i + 1;
			}
		}
		return -1;
	}

}