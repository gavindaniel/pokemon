package items;


/**
 * Abstract class is parent of all items. Contains information on quantity and images of items.
 *
 */
public abstract class Item {

	private String imagePath;
	private int quantity;
	
	/**
	 * default constructor
	 */
	public Item() {
		this.quantity = 0;
	}
	
	/**
	 * Initializes Item with the given quantity
	 * @param quantity the number of desired items 
	 */
	public Item(int quantity) {
		this.quantity = quantity;
	}
	
	/**
	 * Initializes Item with the given image path
	 * @param imagePath path to file containing image of item.
	 */
	public Item(String imagePath) {
		this.quantity = 0;
		this.imagePath = imagePath;
	}

	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		if (quantity >= 0) {
			this.quantity = quantity;
		}
	}

	/**
	 * @return the imagePath
	 */
	public String getImagePath() {
		// TODO Auto-generated method stub
		return imagePath;
	}
	
	/**
	 * @param imagePath the imagePath to set
	 */
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	/**
	 * Increases item count by 1.
	 */
	public void incrementNumItems() {
		this.quantity++;
	}
	
	/**
	 * Decreases item count by 1.
	 */
	public void decrementNumItems() {
		if (quantity > 0) {
			this.quantity--;
		}
	}
}
