package pokemon;


public class Pokemon {
	
	private final Frequency frequency;
	public boolean wild;
	public String mood;
	public String name;
	
	public Pokemon(Frequency frequency,boolean wild,String name) {
		this.frequency=frequency;
		this.wild=wild;
		this.mood="Neutral";
		this.name=name;
	}
	
	
	//Set to return a catch rate depending on the frequency.
	public int getRunRate() {
		if(this.frequency==Frequency.RARE) {
			return 40;
		}
		else if(this.frequency==Frequency.UNCOMMON) {
			return 25;
		}
		else {
			return 10;
		}
		
	}
	public int getCatchRate() {
		if(this.frequency==Frequency.RARE) {
			return 80;
		}
		else if(this.frequency==Frequency.UNCOMMON) {
			return 50;
		}
		else {
			return 25;
		}
		
	}
	
	
	
}
