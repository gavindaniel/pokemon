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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.Trainer;
import pokemon.Attack;
import pokemon.Pokemon;

public class BattleView extends Canvas implements Observer {
	
	private BattleLogicForView battle;
	private GraphicsContext gc;
	private Timeline currentOppAttack;	//Keeps track of animation for opponent's chosen attack to start/stop animation.
	
	private static Image battleGround;
	private static Image battleMenus;
	
	public BattleView(BattleLogicForView battle, double width, double height) {
		
		this.setWidth(width);
		this.setHeight(height);
		
		this.battle = battle;
		
		gc = this.getGraphicsContext2D();
		
		battleGround = new Image("file:images/battle/battle-background.png", false);
		battleMenus = new Image ("file:images/battle/battle-menus.png", false);
		
		currentOppAttack = null;
		
		
		gc.drawImage(battleGround, 0, 0, this.getWidth(), this.getHeight());
	}
	
	
	@Override
	/**
	 * Called by the setChanged/notifyObserver methods in the observable.
	 */
	public void update(Observable battle, Object message) {
		
		this.battle = (BattleLogicForView) battle;
		
		//Draw Background
		drawBackgroundAndMenus();
		printBattleStage(message);
	}
	
	private void printBattleStage(Object message) {
		
		if (message instanceof Attack) {
			if (battle.getAttackTrainer() == battle.getActiveTrainer()) startAttack((Attack) message);
			
			else startDefend((Attack) message);
		}
		
		else {
			startIdle();
		}		
	}
	
	/**
	 * Draws HUD for user containing information about each pokemon, 
	 * and providing a selection menu for battle actions.
	 */
	public void drawBackgroundAndMenus() {
		gc.drawImage(battleGround, 0, 0, this.getWidth(), this.getHeight()-144);
		gc.drawImage(battleMenus, 119, 14, 92, 26, 470, 100, 92*3, 26*3); //Opponent Bar
		gc.drawImage(battleMenus, 128, 55, 92, 33, 0, 250, 92*3, 33*3); //Player Bar
		gc.drawImage(battleMenus, 16, 110, 240, 48, 0, this.getHeight() - 48*3, this.getWidth(), 48*3); //Text Bar
		gc.drawImage(battleMenus, 269, 10, 120, 48, this.getWidth() - 120*3, this.getHeight()-48*3, 120*3, 48*3); //Choice Menu
		
		fillBattleMenus();
	}
	
	/**
	 * Fills in pokemon names, battle lines, and health bars for battle menus.
	 */
	private void fillBattleMenus() {
		
		//Setting fonts and colors of text
		gc.setFont(Font.font ("Verdana", 22));
		gc.setFill(Color.BLACK);
		
		Pokemon userPoke = battle.getActiveTrainer().getActiveBattlePokemon();
		Pokemon oppPoke = battle.getOppTrainer().getActiveBattlePokemon();	
		
		gc.fillText(userPoke.getName().toUpperCase(), 15, 283, 220);
		gc.fillText(oppPoke.getName().toUpperCase(), 490, 130, 220);
		
		drawHealthBar(userPoke, oppPoke);
	}
	
	/**
	 * Draws health bar for both pokemon depending on current HP.
	 * @param userPoke the user's pokemon.
	 * @param pooPoke the opponent's pokemon.
	 */
	private void drawHealthBar(Pokemon userPoke, Pokemon oppPoke) {
				
		final double fullBar = 142;
		
		double userHealthPerc = ((double) userPoke.getCurrHP())/userPoke.getMaxHP();
		double oppHealthPerc = ((double) userPoke.getCurrHP())/userPoke.getMaxHP();
		
		//User coordinates for health bar rectangle
		final double ux = 118, uy = 301, uh = 10, ua = 5;
		
		//Opponent coordinates for health bar rectangle
		final double ox = 588, oy = 151, oh = 10, oa = 5;
		
		
		gc.setFill(determineHealthColor(userHealthPerc));
		gc.fillRoundRect(ux, uy, fullBar, uh, ua, ua);	//User's health bar
		
		gc.setFill(determineHealthColor(oppHealthPerc));
		gc.fillRoundRect(ox, oy, fullBar, oh, oa, oa);	//Opponent's health bar
	}
	
	/**
	 * Determines color of health bar based on pokemon health level.
	 * green: above 50%
	 * yellow: between 15 and 50%
	 * red: below 15%
	 * @param healthPercent percentage of remaining HP.
	 */
	private Color determineHealthColor(double healthPercent) {
		
		if (healthPercent > .5) return Color.rgb(71, 198, 111);
		else if ((healthPercent > .15) && (healthPercent < .5)) return Color.rgb(229, 208, 68);
		else return Color.rgb(224, 76, 76);
	}
	
	private void startIdle() {
		
		stopAllActiveTimelines();
		
		Pokemon userPoke = battle.getActiveTrainer().getActiveBattlePokemon(); //User controlled Pokemon
		Pokemon oppPoke = battle.getOppTrainer().getActiveBattlePokemon(); //Opposing Pokemon

		oppPoke.getBattleAnimation().getStandby().play();
		userPoke.getBattleAnimation().getBackStandby().play();
	}
	
	private void startAttack(Attack attack) {
		
		//Stop any active timelines.
		stopAllActiveTimelines();
		
		Pokemon attackPoke = battle.getActiveTrainer().getActiveBattlePokemon();
		Pokemon defendPoke = battle.getOppTrainer().getActiveBattlePokemon();

		// Set on finished handler to re-start standby animations once attack is done.
		attackPoke.getBattleAnimation().getBackAttack().setOnFinished((event) -> {
			startIdle();
		});
		
		defendPoke.getBattleAnimation().getStandby().play();
		attackPoke.getBattleAnimation().getBackAttack().playFromStart();
	}
	
	private void startDefend(Attack attack) {
		
		//Stop any active timelines.
		stopAllActiveTimelines();
		
		Pokemon attackPoke = battle.getOppTrainer().getActiveBattlePokemon();
		Pokemon defendPoke = battle.getActiveTrainer().getActiveBattlePokemon();
		
		int attackNumber = findAttackNumber(attackPoke, attack);
		
		if (attackNumber == 1) currentOppAttack = attackPoke.getBattleAnimation().getFirstAttack();	
		else if (attackNumber == 2) currentOppAttack = attackPoke.getBattleAnimation().getSecondAttack();		
		else if (attackNumber == 3) currentOppAttack = attackPoke.getBattleAnimation().getThirdAttack();	
		else if (attackNumber == 4) currentOppAttack = attackPoke.getBattleAnimation().getFourthAttack();
		
		// Set on finished handler to re-start standby animations once attack is done.
		currentOppAttack.setOnFinished((event) -> {
			startIdle();
		});
		
		currentOppAttack.playFromStart();
		defendPoke.getBattleAnimation().getBackStandby().play();
	}
	
	private void stopAllActiveTimelines() {
		
		Pokemon userPoke = battle.getActiveTrainer().getActiveBattlePokemon(); //User controlled Pokemon
		Pokemon oppPoke = battle.getOppTrainer().getActiveBattlePokemon(); //Opposing Pokemon
		
		//Stop standby timelines
		userPoke.getBattleAnimation().getBackStandby().stop();
		oppPoke.getBattleAnimation().getStandby().stop();
		
		//Stop attack Timelines
		userPoke.getBattleAnimation().getBackAttack().stop();
		if (currentOppAttack != null) currentOppAttack.stop();
	}
	
	private int findAttackNumber(Pokemon poke, Attack attack) {
		
		List<Attack> attackList = poke.getAttackList();
		
		for (int i = 0; i < attackList.size(); i++) {
			if (attackList.get(i).getName().equals(attack.getName())) {
				return i + 1;
			}
		}
		return -1;
	}

}