package items;

import pokemon.Pokemon;

/**
 * Class containing potion that heals a pokemon by 50% of max HP.
 *
 */
public class Potion extends Item{
	
	public Potion() {
		super("file:images/items/potion.png");
	}

	/**
	 * @return the itemType
	 */
	public String getItemType() {
		return "Potion";
	}

	/**
	 * Heals pokemon then decreases quantity of item by 1.
	 * @param poke the pokemon to be healed.
	 * @return true if successful, false otherwise.
	 */
	public boolean applyToPokemon(Pokemon poke) {
		
		if (getQuantity() <= 0) return false;
		
		poke.heal(poke.getMaxHP()/2);
		this.decrementNumItems();
		return true;
	}
}
