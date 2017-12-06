package items;

public class MasterBall extends Item{
	String imagePath;
	public MasterBall() {
		super();
		this.imagePath="file:images/items/hyperpotion.png";
	}
	
	@Override
	public String getImagePath() {
		// TODO Auto-generated method stub
		return imagePath;
	}

}
