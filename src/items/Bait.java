package items;

import java.io.Serializable;

public class Bait extends Item implements Serializable{
	String imagePath;
	public Bait() {
		super();
		this.imagePath="file:images/items/bait.png";
	}
	
	@Override
	public String getImagePath() {
		// TODO Auto-generated method stub
		return imagePath;
	}
}
