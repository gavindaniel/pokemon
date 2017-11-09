package pokemon;

/**
 * Applies increases to pokemon base stats.
 * @author Abdullah Asaad
 *
 */
public class PassiveStatBuff extends Attack {

	private int currentDuration;	// Keeps track of how many rounds the buff is in effect.
	private int effectDuration;		// How many rounds the buff is in effect.
	private int HPrestore;		//Restore hit points.
	private double attackMod;		// Attack Modifier to be multiplied by base attack stat
	private double defenseMod;		// Defense Modifier to be multiplied by base defense stat
	private double specialMod;		// Special Modifier to be multiplied by base special stat
	
	public PassiveStatBuff(String name, PokeType type, int accuracy, int effectDuration, int HPrestore, double attackMod, double defenseMod, double specialMod) {
		super(name, type, accuracy);
		this.currentDuration = 0;
		this.effectDuration = effectDuration;
		this.HPrestore = HPrestore;
		this.attackMod = attackMod;
		this.defenseMod = defenseMod;
		this.specialMod = specialMod;
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
	 * @return the hPrestore
	 */
	public int getHPrestore() {
		return HPrestore;
	}

	/**
	 * @param hPrestore the hPrestore to set
	 */
	public void setHPrestore(int HPrestore) {
		HPrestore = HPrestore;
	}

	/**
	 * @return the attackMod
	 */
	public double getAttackMod() {
		return attackMod;
	}

	/**
	 * @param attackMod the attackMod to set
	 */
	public void setAttackMod(double attackMod) {
		this.attackMod = attackMod;
	}

	/**
	 * @return the defenseMod
	 */
	public double getDefenseMod() {
		return defenseMod;
	}

	/**
	 * @param defenseMod the defenseMod to set
	 */
	public void setDefenseMod(double defenseMod) {
		this.defenseMod = defenseMod;
	}

	/**
	 * @return the specialMod
	 */
	public double getSpecialMod() {
		return specialMod;
	}

	/**
	 * @param specialMod the specialMod to set
	 */
	public void setSpecialMod(double specialMod) {
		this.specialMod = specialMod;
	}
	
	/**
	 * Increments duration of buff.
	 */
	public void incrementCurrDuration() {
		currentDuration++;
	}
	
	/**
	 * Activates buff on pokemon
	 * @param p the pokemon to be buffed
	 */
	public void activate (Pokemon p) {
		p.heal(HPrestore);
		p.setAttack((int) (p.getAttack()*attackMod));
		p.setDefense((int) (p.getDefense()*defenseMod));
		p.setSpecial((int) (p.getSpecial()*specialMod));
	}
	

}
