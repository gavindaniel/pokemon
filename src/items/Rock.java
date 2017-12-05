package items;

import java.io.Serializable;

public class Rock extends Item implements Serializable{
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
