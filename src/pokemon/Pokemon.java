package pokemon;

import java.util.ArrayList;

/**
 * Parent class of all pokemon. Provides name, type, and battle stats for a pokemon.
 * @author Abdullah Asaad
 *
 */
public abstract class Pokemon {
	
	private String name;
	private ArrayList<Attack> attackList;	//4 attacks to be used by pokemon
	private PokeType primaryType;
	private PokeType secondaryType;
	private OccurrenceRate occurRate;
	private int HP;		// Determines hit points for the pokemon
	private int attack;		// Determines strength of physical attacks
	private int defense;	// Determines how much damage is received
	private int special;	// Determines strength of special moves
	private int speed;		// Determines the order of pokemon that can act in battle
	
	/**
	 * Contructor initializing name, elemental type, and commonness of pokemon.
	 * @param name name of pokemon.
	 * @param type elemental type of pokemon.
	 * @param occurRate rarity of pokemon (common, uncommon, or rare).
	 */
	public Pokemon(String name, PokeType primaryType, PokeType secondaryType, OccurrenceRate occurRate) {
		
		this.name = name;
		this.primaryType = primaryType;
		this.secondaryType = secondaryType;
		this.occurRate = occurRate;
		this.attackList = new ArrayList<>(4);
	}

	/***********************************Getters and Setters****************************************/
	/**
	 * @return the pokemon's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the pokemon name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the attackList [attack1, attack2, attack3, attack4]
	 */
	public ArrayList<Attack> getAttackList() {
		return attackList;
	}

	/**
	 * @param attackList the attackList to set, must be four attacks only.
	 */
	public void setAttackList(ArrayList<Attack> attackList) {
		if (attackList.size() == 4) {
			this.attackList = attackList;
		}
	}

	/**
	 * @return the pokemon's primary elemental type
	 */
	public PokeType getPrimaryType() {
		return primaryType;
	}

	/**
	 * @param type the primary elemental type to set for pokemon
	 */
	public void setPrimaryType(PokeType type) {
		this.primaryType = type;
	}
	
	/**
	 * @return the pokemon's primary elemental type
	 */
	public PokeType getSecondaryType() {
		return secondaryType;
	}

	/**
	 * @param type the primary elemental type to set for pokemon
	 */
	public void setSecondaryType(PokeType type) {
		this.secondaryType = type;
	}

	/**
	 * @return the occurrence rate of pokemon (common, uncommon, or rare)
	 */
	public OccurrenceRate getOccurRate() {
		return occurRate;
	}

	/**
	 * @param occurRate the occurrence rate to set for pokemon (common, uncommon, or rare)
	 */
	public void setOccurRate(OccurrenceRate occurRate) {
		this.occurRate = occurRate;
	}

	/**
	 * @return the pokemon's current hit points.
	 */
	public int getHP() {
		return HP;
	}

	/**
	 * @param HP the pokemon's hit points
	 */
	public void setHP(int HP) {
		this.HP = HP;
	}

	/**
	 * @return the pokemon's attack stat
	 */
	public int getAttack() {
		return attack;
	}

	/**
	 * @param attack the attack stat to set for the pokemon
	 */
	public void setAttack(int attack) {
		this.attack = attack;
	}

	/**
	 * @return the pokemon's defense stat
	 */
	public int getDefense() {
		return defense;
	}

	/**
	 * @param defense the defense stat to set for pokemon
	 */
	public void setDefense(int defense) {
		this.defense = defense;
	}

	/**
	 * @return the pokemon's special stat
	 */
	public int getSpecial() {
		return special;
	}

	/**
	 * @param special the special stat to set for pokemon
	 */
	public void setSpecial(int special) {
		this.special = special;
	}

	/**
	 * @return the pokemon's speed stat
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * @param speed the speed stat to set for pokemon
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	/*********************************************************************************************/
	
	/**
	 * Sets the four attacks available to the pokemon.
	 * @param attack1 first attack
	 * @param attack2 second attack
	 * @param attack3 third attack
	 * @param attack4 fourth attack
	 */
	public void initializeAttackList(Attack attack1, Attack attack2, Attack attack3, Attack attack4) {
		attackList.add(attack1);
		attackList.add(attack2);
		attackList.add(attack3);
		attackList.add(attack4);
	}
	/**
	 * Sets the base stats for the pokemon
	 * @param HP 
	 * @param attack
	 * @param defense
	 * @param special
	 * @param speed
	 */
	public void initializeStats(int HP, int attack, int defense, int special, int speed) {
		this.HP = HP;
		this.attack = attack;
		this.defense = defense;
		this.special = special;
		this.speed = speed;
	}
	
	/**
	 * Heals pokemon by the specified amount.
	 * @param pointsToHeal points to restore
	 */
	public void heal(int pointsToHeal) {
		HP += pointsToHeal;
	}
	
	/**
	 * Hurts pokemon by specified amount.
	 * @param damage points to deduct from HP
	 */
	public void takeDamage(int damage) {
		HP -= damage;
	}
	
	/**
	 * Create attacks for specific pokemon.
	 * @return list of 4 attacks
	 */
	public abstract ArrayList<Attack> initializeAttacks();
}
