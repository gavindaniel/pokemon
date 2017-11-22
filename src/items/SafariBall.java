package items;

public class SafariBall extends Item{
	String imagePath;
	public SafariBall() {
		super();
		this.imagePath="file:images/items/SafariBall.png";
	}
	
	@Override
	public String getImagePath() {
		// TODO Auto-generated method stub
		return imagePath;
	}

}
