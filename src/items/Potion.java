package items;

import java.io.Serializable;

public class Potion extends Item implements Serializable{
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
