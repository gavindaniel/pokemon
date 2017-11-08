package pokemon;

import javafx.animation.Timeline;

public class Attack {
	
	private String name;
	private int damage;		//base damage of attack
	private PokeType type;
//	private Timeline attackAnimation;
	
	public Attack(String name, int damage, PokeType type) {
		this.name = name;
		this.damage = damage;
		this.type = type;
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
	
	/*********************************************************************************************/
	
	
//	private void animateAttack() {
//		
//	}

}
