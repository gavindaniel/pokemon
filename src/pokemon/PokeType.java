package pokemon;

/**
 * Elemental type of pokemon or attack.
 * @author Abdullah Asaad
 *
 */
public enum PokeType {

	FIRE(0),
	WATER(1),
	GRASS(2),
	ELECTRIC(3),
	NORMAL(4),
	POISON(5);
	
	private final int index;
	
	PokeType(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return this.index;
	}
}
