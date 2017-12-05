package battle;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import pokemon.Attack;
import pokemon.Pokemon;

public class BattleView extends Canvas implements Observer {
	
	private BattleLogicForView battle;
	private GraphicsContext gc;
	private Timeline currentOppAttack;	//Keeps track of animation for opponent's chosen attack to start/stop animation.
	private Timeline infoTextTimeline;	//Timeline for printing battle updates
	private Timeline healthBarTimeline;	//Timeline for printing health bar
	
	private static Image battleGround;
	private static Image battleMenus;
	
	private static final Font pokeFont = Font.loadFont("file:fonts/pokemon_pixel_font.ttf", 38);
	
	
	public BattleView(BattleLogicForView battle, double width, double height) {
		
		this.setWidth(width);
		this.setHeight(height);
		
		this.battle = battle;
		
		gc = this.getGraphicsContext2D();
		
		battleGround = new Image("file:images/battle/background-scaled.png", false);
		battleMenus = new Image ("file:images/battle/battle-menus.png", false);
		
		currentOppAttack = null;		
	}
	
	
	@Override
	/**
	 * Called by the setChanged/notifyObserver methods in the observable.
	 */
	public void update(Observable battle, Object message) {
		
		this.battle = (BattleLogicForView) battle;
		
		//Draw Background and menus
		drawBackgroundAndHUD();
		drawBattleMenus();
		printBattleStage(message);
	}
	
	private void printBattleStage(Object message) {
		
		if (message instanceof Attack) {
			if (battle.getAttackTrainer() == battle.getActiveTrainer()) {
				String line1 = battle.getAttackTrainer().getActiveBattlePokemon().getName().toUpperCase() + " used";
				String line2 = ((Attack) message).getName().toUpperCase() + "!";
				
				animateBattleText(line1, line2);
				infoTextTimeline.setOnFinished((event) -> {
					startAttack((Attack) message);
				});
			}
			
			else {
				String line1 = battle.getAttackTrainer().getActiveBattlePokemon().getName().toUpperCase() + " used";
				String line2 = ((Attack) message).getName().toUpperCase() + "!";
				
				animateBattleText(line1, line2);
				infoTextTimeline.setOnFinished((event) -> {
					startDefend((Attack) message);
				});				
			}
		}
		
		else {
			startIdle();
		}		
	}
	
	/**
	 * Draws HUD for user containing information about each pokemon.
	 */
	public void drawBackgroundAndHUD() {
		gc.drawImage(battleGround, 0, 0, this.getWidth(), this.getHeight()-144);
		gc.drawImage(battleMenus, 119, 14, 92, 26, 470, 100, 92*3, 26*3); //Opponent Bar
		gc.drawImage(battleMenus, 128, 55, 92, 33, 0, 250, 92*3, 33*3); //Player Bar
		fillHUD();
	}
	
	/**
	 * Draws bottom bars containing selection menu and textual battle updates.
	 */
	public void drawBattleMenus() {
		gc.drawImage(battleMenus, 16, 110, 240, 48, 0, this.getHeight() - 48*3, this.getWidth(), 48*3); //Text Bar
		
		if (battle.getCurrState() == BattleState.IDLE) {
			drawMainSelectMenu(0);
		}
		
		else if (battle.getCurrState() == BattleState.CHOOSE_ATTACK) {
			drawAttackMenu(0);
		}
	}
	
	/**
	 * Draws the main selection menu with following choices:
	 * Fight, Bag, Pokemon, and Run.
	 * 
	 * @param arrowPos current arrow position
	 * 					0: fight, 1: Bag, 2: Pokemon, 3: Run.
	 */
	public void drawMainSelectMenu(int arrowPos) {
		
		if (arrowPos < 0 || arrowPos > 3) return;
		
		int offset = 50 * arrowPos;
		gc.drawImage(battleMenus, 269, 10 + offset, 120, 48, this.getWidth() - 120*3, this.getHeight()-48*3, 120*3, 48*3); //Choice Menu
	}
	
	/**
	 * Fills in pokemon names and health bars.
	 */
	private void fillHUD() {
		
		//Setting fonts and colors of text
		gc.setFont(Font.font ("Verdana", 22));
		gc.setFill(Color.BLACK);
		
		Pokemon userPoke = battle.getActiveTrainer().getActiveBattlePokemon();
		Pokemon oppPoke = battle.getOppTrainer().getActiveBattlePokemon();	
		
		gc.fillText(userPoke.getName().toUpperCase(), 15, 283, 220);
		gc.fillText(oppPoke.getName().toUpperCase(), 490, 130, 220);
		
		drawHealthBar();
	}
	
	/**
	 * Draws health bar for both pokemon depending on current HP.
	 * @param userPoke the user's pokemon.
	 * @param pooPoke the opponent's pokemon.
	 */
	private void drawHealthBar() {
				
		Pokemon userPoke = battle.getActiveTrainer().getActiveBattlePokemon();
		Pokemon oppPoke = battle.getOppTrainer().getActiveBattlePokemon();
		
		final double fullBar = 142;
		
		double userHealthPerc = ((double) userPoke.getCurrHP())/userPoke.getMaxHP();
		double oppHealthPerc = ((double) oppPoke.getCurrHP())/oppPoke.getMaxHP();
		
		//User coordinates for health bar rectangle
		final double ux = 118, uy = 301, uh = 10, ua = 5;
		
		//Opponent coordinates for health bar rectangle
		final double ox = 588, oy = 151, oh = 10, oa = 5;		
		
		gc.setFill(determineHealthColor(userHealthPerc));
		gc.fillRoundRect(ux, uy, userHealthPerc*fullBar, uh, ua, ua);	//User's health bar
		
		gc.setFill(determineHealthColor(oppHealthPerc));
		gc.fillRoundRect(ox, oy, oppHealthPerc*fullBar, oh, oa, oa);	//Opponent's health bar
	}
	
	private void animateUserHealthBar() {
		
		Pokemon userPoke = battle.getActiveTrainer().getActiveBattlePokemon();
		
		final double fullBar = 142;
		
		double userHealthPerc = ((double) userPoke.getCurrHP())/userPoke.getMaxHP();

		//User coordinates for health bar rectangle
		final double ux = 118, uy = 301, uh = 10, ua = 5;
		
		healthBarTimeline = new Timeline(new KeyFrame(Duration.millis(50), new AnimateHealthBar(ux,uy,uh,ua,fullBar,userHealthPerc)));
		healthBarTimeline.setCycleCount((int) (fullBar*(1-userHealthPerc)));
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
	
	/**
	 * Animates the text displayed informing the user of battle events
	 * @param line1 Top line of text.
	 * @param line2 Bottom line of text.
	 */
	private void animateBattleText(String line1, String line2) {
		
		int cycleCnt = 0;
		
		if(line2 == null) cycleCnt = line1.length();
		else cycleCnt = line1.length() + line2.length();
		
		infoTextTimeline = new Timeline(new KeyFrame(Duration.millis(50), new AnimateBattleText(this.getWidth(), line1, line2)));
		infoTextTimeline.setCycleCount(cycleCnt);
		infoTextTimeline.playFromStart();
	}
	
	public void drawAttackMenu(int arrowPos) {
		
		gc.drawImage(battleMenus, 16, 160, 240, 48, 0, this.getHeight() - 48*3, this.getWidth(), 48*3); //Attack Bar
		
		//Setting fonts and colors of text
		gc.setFont(pokeFont);
		gc.setFill(Color.BLACK);
		
		//Get attack names
		List<Attack> attackList = battle.getAttackTrainer().getActiveBattlePokemon().getAttackList();
		String attack1 = attackList.get(0).getName().toUpperCase();
		String attack2 = attackList.get(1).getName().toUpperCase();
		String attack3 = attackList.get(2).getName().toUpperCase();
		String attack4 = attackList.get(3).getName().toUpperCase();
		
		//Draw attack names
		double x = 45;
		double y = 610;
		double xGap = 260;
		double yGap = 45;
		double maxWidth = this.getWidth()/3 - 20;
		gc.fillText(attack1, x, y, maxWidth);
		gc.fillText(attack2, x + xGap, y, maxWidth);
		gc.fillText(attack3, x, y + yGap, maxWidth);
		gc.fillText(attack4, x + xGap, y + yGap, maxWidth);
	
		//Draw Arrow
		if (arrowPos == 0) gc.drawImage(battleMenus, 277, 23, 6, 10, 24, 587, 6*3, 10*3);	//Attack1 highlighted
		else if (arrowPos == 1) gc.drawImage(battleMenus, 277, 23, 6, 10, 24 + xGap, 587, 6*3, 10*3);	//Attack2 highlighted
		else if (arrowPos == 2) gc.drawImage(battleMenus, 277, 23, 6, 10, 24, 587 + yGap, 6*3, 10*3);	//Attack3 highlighted
		else gc.drawImage(battleMenus, 277, 23, 6, 10, 24 + xGap, 587 + yGap, 6*3, 10*3);	//Attack3 highlighted
	}
	
	private void startIdle() {
		
		stopAllActiveTimelines();
		
		battle.setCurrState(BattleState.IDLE);
		drawMainSelectMenu(0);
		
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
	
	private class AnimateBattleText implements EventHandler<ActionEvent> {

		private String line1;
		private String line2;
		private String output1;
		private String output2;
		
		static final double tx = 32;	// text x-coordinate
		static final double ty1 = 610; // line1 y-coordinate
		static final double ty2 = ty1 + 45;	// line2 y-coordinate
		static final double maxWidth = 800;
		
		private int firstIndex, secondIndex;
		
		public AnimateBattleText(double maxWidth, String line1, String line2) {
		
		this.line1 = line1;
		this.line2 = line2;
		
		output1 = "";
		output2 = "";
		
		int firstIndex = 0;
		int secondIndex = 0;
		}
		
		@Override
		public void handle(ActionEvent event) {
			
			//Setting fonts and colors of text
			gc.setFont(Font.font ("Verdana", 30));
			gc.setFill(Color.WHITE);
						
			if (firstIndex < line1.length()) {
				output1 += line1.charAt(firstIndex);
				firstIndex++;
				gc.fillText(output1, tx, ty1, maxWidth - 20);
			}
			
			else {
					output2 += line2.charAt(secondIndex);	//setCycleCount property is used to stop animation 
					secondIndex++;							//before NullPointerException is thrown.
					gc.fillText(output2, tx, ty2, maxWidth - 20);
			}
		}}
	
	
	private class AnimateHealthBar implements EventHandler<ActionEvent> {

		private double x, y, h, a, fullBar, healthPerc;
		private int counter;
		
		public AnimateHealthBar(double x, double y, double h, double a, double fullBar, double healthPerc) {
			
			this.x = x;
			this.y = y;
			this.h = h;
			this.a = a;
			this.fullBar = fullBar;
			this.healthPerc = healthPerc;
			
			int counter = 0;
		}

		@Override
		public void handle(ActionEvent event) {

			gc.setFill(determineHealthColor(healthPerc));
			gc.fillRoundRect(x, y, fullBar - (++counter), h, a, a);	//User's health bar
		}

	}
	
	
	
}