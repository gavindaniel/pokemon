package items;

public class Potion extends Item{
	String imagePath;
	public Potion() {
		super();
		this.imagePath="file:images/items/potion.png";
	}
	
	@Override
	public String getImagePath() {
		// TODO Auto-generated method stub
		return imagePath;
	}

}
