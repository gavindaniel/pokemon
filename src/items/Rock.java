package items;

public class Rock extends Item{
	String imagePath;
	public Rock() {
		super();
		this.imagePath="file:images/items/rock.png";
	}
	
	@Override
	public String getImagePath() {
		// TODO Auto-generated method stub
		return imagePath;
	}
}
