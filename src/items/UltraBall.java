package items;

public class UltraBall extends Item{
	String imagePath;
	public UltraBall() {
		super();
		this.imagePath="file:images/items/hyperpotion.png";
	}
	
	@Override
	public String getImagePath() {
		// TODO Auto-generated method stub
		return imagePath;
	}

}
