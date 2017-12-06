package items;


import pokemon.Pokemon;

/**
 * Class containing potion that completely heals a pokemon.
 *
 */
public class HyperPotion extends Item{
	
	public HyperPotion() {
		super("file:images/items/hyperpotion.png");
	}

	/**
	 * @return the itemType
	 */
	public String getItemType() {
		return "Hyper Potion";
	}

	/**
	 * 
	 * Heals pokemon then decreases quantity of item by 1.
	 * @param poke the pokemon to be healed.
	 * @return true if successful, false otherwise.
	 */
	public boolean applyToPokemon(Pokemon poke) {
		
		if (getQuantity() <= 0) return false;
		
		poke.setCurrHP(poke.getMaxHP());
		this.decrementNumItems();
		return true;
	}
}
