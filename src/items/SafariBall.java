package items;

import java.io.Serializable;

public class SafariBall extends Item implements Serializable{
	String imagePath;
	public SafariBall() {
		super();
		this.imagePath="file:images/items/safariball2.png";
	}
	
	@Override
	public String getImagePath() {
		// TODO Auto-generated method stub
		return imagePath;
	}

}