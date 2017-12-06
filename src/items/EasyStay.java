package items;


import pokemon.Pokemon;

/**
 * Class containing potion that makes a pokemon easier to catch
 *
 */
public class EasyStay extends Item{
	
	public EasyStay() {
		super("file:images/items/hyperpotion.png");
	}

	/**
	 * @return the itemType
	 */
	public String getItemType() {
		return "Easy Stay";
	}


}
