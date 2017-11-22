package items;

public class HyperPotion extends Item{
	String imagePath;
	public HyperPotion() {
		super();
		this.imagePath="file:images/items/hyperpotion.png";
	}
	
	@Override
	public String getImagePath() {
		// TODO Auto-generated method stub
		return imagePath;
	}

}
