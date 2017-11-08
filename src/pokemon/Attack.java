package pokemon;

/**
 * Represents an attack to be used by a pokemon.
 * @author Abdullah Asaad
 *
 */
public abstract class Attack {
	
	private String name;
	private PokeType type;
	private int accuracy;	// Probability of a successful hit (0-100)
//	private Timeline attackAnimation;
	
	public Attack(String name, PokeType type, int accuracy) {
		this.name = name;
		this.type = type;
		if (accuracy > 0 && accuracy < 100) {
			this.accuracy = accuracy;
		}
	}

	/***********************************Getters and Setters****************************************/
	/**
	 * @return name of the attack
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name name of attack
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return elemental type of attack
	 */
	public PokeType getType() {
		return type;
	}

	/**
	 * @param type elemental type of attack
	 */
	public void setType(PokeType type) {
		this.type = type;
	}

	/**
	 * @return the accuracy of an attack
	 */
	public int getAccuracy() {
		return accuracy;
	}

	/**
	 * @param accuracy the accuracy of the attack. Must be between 0 and 100.
	 */
	public void setAccuracy(int accuracy) {
		if (accuracy > 0 && accuracy < 100) {
			this.accuracy = accuracy;
		}
	}
	
	/*********************************************************************************************/
	
	
//	private void animateAttack() {
//		
//	}

}
