package pokemon;

import java.util.List;

/**
 * Applies stat increases to active elemental attacks.
 * @author Abdullah Asaad
 *
 */
public class PassiveAttackBuff extends Attack {

	private int currentDuration;	// Keeps track of how many rounds the buff is in effect.
	private int effectDuration;		// How many rounds the buff is in effect.
	private double damageMultiplier;	// Multiplier for attack effectiveness. (Multiply by base damage)
	private double accuracyModifier;	// Modifier to increase attack accuracy. (Multiply by base accuracy)
	
	public PassiveAttackBuff(String name, PokeType type, int accuracy, int effectDuration, double damageMultiplier, double accuracyModifier) {
		super(name, type, accuracy);
		this.currentDuration = 0;
		this.effectDuration = effectDuration;
		this.damageMultiplier = damageMultiplier;
		this.accuracyModifier = accuracyModifier;
	}

	/**
	 * @return the currentDuration
	 */
	public int getCurrentDuration() {
		return currentDuration;
	}

	/**
	 * @param currentDuration the currentDuration to set
	 */
	public void setCurrentDuration(int currentDuration) {
		this.currentDuration = currentDuration;
	}

	/**
	 * @return the effectDuration
	 */
	public int getEffectDuration() {
		return effectDuration;
	}

	/**
	 * @param effectDuration the effectDuration to set
	 */
	public void setEffectDuration(int effectDuration) {
		this.effectDuration = effectDuration;
	}

	/**
	 * @return the damage multiplier
	 */
	public double getDamageMultiplier() {
		return damageMultiplier;
	}

	/**
	 * @param multiplier the damage multiplier to set
	 */
	public void setDamageMultiplier(double multiplier) {
		this.damageMultiplier = multiplier;
	}
	
	/**
	 * @return the accuracy modifier
	 */
	public double getAccuracyModifier() {
		return this.accuracyModifier;
	}

	/**
	 * 
	 * @param accuracyModifier the accuracy modifier to set
	 */
	public void setAccuracyModifier(double accuracyModifier) {
		this.accuracyModifier = accuracyModifier;
	}
	
	/**
	 * Increments duration of buff.
	 */
	public void incrementCurrDuration() {
		currentDuration++;
	}
	
	/**
	 * Activates buff on attacks of the same type.
	 * @param attackList list of the pokemon's attacks
	 */
	public void activate(List<Attack> attackList) {
		
		for (Attack attack : attackList) {
			
			if (attack.getName().compareTo(getName()) != 0) { //Ensures object is not buffing itself
				if (attack.getType() == this.getType()) {
					if (attack instanceof ActiveAttack) {
						((ActiveAttack) attack).setDamage((int) (((ActiveAttack) attack).getDamage()*damageMultiplier));
						((ActiveAttack) attack).setAccuracy((int) (((ActiveAttack) attack).getAccuracy() * accuracyModifier));
					}
				}
			}
		}
	}
}
