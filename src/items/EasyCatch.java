package items;


import pokemon.Pokemon;

/**
 * Class containing potion that makes a pokemon easier to catch
 *
 */
public class EasyCatch extends Item{
	
	public EasyCatch() {
		super("file:images/items/hyperpotion.png");
	}

	/**
	 * @return the itemType
	 */
	public String getItemType() {
		return "Easy Catch";
	}


}
