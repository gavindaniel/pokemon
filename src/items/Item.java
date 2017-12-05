package items;

import java.io.Serializable;

public abstract class Item implements Serializable{

	String imagePath;
	public Item() {
		
	}

	public String getImagePath() {
		// TODO Auto-generated method stub
		return imagePath;
	}
}
