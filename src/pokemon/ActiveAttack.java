package pokemon;

/**
 * Attacks that cause direct damage to opponent.
 * @author Abdullah Asaad
 *
 */
public class ActiveAttack extends Attack {
	
	private int damage;		// Base damage of attack

	public ActiveAttack(String name, int damage, PokeType type, int accuracy) {
		super(name, type, accuracy);
		this.damage = damage;
	}
	
	/**
	 * @return the base damage of attack
	 */
	public int getDamage() {
		return damage;
	}

	/**
	 * @param damage the base damage of attack
	 */
	public void setDamage(int damage) {
		this.damage = damage;
	}

}
