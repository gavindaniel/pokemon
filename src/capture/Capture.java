package capture;

import java.util.Scanner;

import items.EasyCatch;
import items.EasyStay;
import items.Item;
import items.MasterBall;
import items.Potion;
import items.SafariBall;
import items.UltraBall;
import model.Trainer;
import pokemon.OccurrenceRate;
import pokemon.Pokemon;
import java.util.Random;

public class Capture {
	
	//New Capture logic
		
		//Affect the health of the pokemon by 3% for each rock
		//Give the pokemon 1% health for the food
		
		//Common
		// 50% chance of getting caught at base
		// 10% retreat chance
		// Increase by 5% for rocks and bait thrown
		// Caps for it are : 90%-20% for capture
		// 5-40 for running
		
		//unCommon
		// 25% chance of getting caught at base
		// 25% chance of running away at base
		// Increase by 5% for rocks and bait thrown
		// Caps for it are : 50%-10% for capture
		// 20-80 for retreat
		
		//rare
		// 5% chance of getting caught at base
		// 35% chance of running away at base
		// Increase by 5% for rocks and bait thrown
		// Caps for it are : 15%-0% for capture
		// 20-80 for retreat
		public Pokemon currentPoke;
		public Trainer currentTrain;
		private int catchRate;
		private int runRate;
		
		public Capture(Pokemon currentPoke,Trainer currentTrain) {
			this.currentPoke=currentPoke;
			this.currentTrain=currentTrain;
			this.catchRate=getCatchRate();
			this.runRate=getRunRate();
		}
		
		public boolean checkSafariBalls() {
			for (Object obj:currentTrain.itemList) {
				if(obj.getClass()==SafariBall.class) {
					return true;
				}
			
		}
			return false;
		}
		
		//Check ultra balls
		public boolean checkUltraBalls() {
			for (Object obj:currentTrain.itemList) {
				if(obj.getClass()==UltraBall.class) {
					return true;
				}
			
		}
			return false;
		}
		
		//Check master balls
				public boolean checkMasterBalls() {
					for (Object obj:currentTrain.itemList) {
						if(obj.getClass()==MasterBall.class) {
							return true;
						}
					
				}
					return false;
				}
		
		
		
		public boolean retreat(int retreatChance) {
			
			System.out.println("Run rate: " +runRate);
			System.out.println("retreatRoll: " +retreatChance);
			
			if(runRate>retreatChance) {
				System.out.println(currentPoke.getName()+" ran away.");
				return true;
			}
			return false;
		}
		
		
		
		public void throwRock() {
			runRate+=5;
			catchRate-=5;
			currentPoke.takeDamage(((int) Math.round(currentPoke.getCurrHP()*.03)));
			checkCap();
			System.out.println("You threw a rock");
			System.out.println(currentPoke.getName()+" is easier to catch, but more likely to run away!");
			System.out.println(currentPoke.getCurrHP());
			System.out.println("Chance to run: "+ runRate+ "%");
			System.out.println("Chance to catch: "+ (100-catchRate)+ "%");
		}
		
		
		public void throwBait() {
			runRate-=5;
			catchRate+=5;
			currentPoke.heal((int) Math.round(currentPoke.getCurrHP()*.01));
			System.out.println(currentPoke.getCurrHP());
			checkCap();
			System.out.println("You threw a rock");
			System.out.println(currentPoke.getName()+" is easier to catch, but more likely to run away!");
			System.out.println("Chance to run: "+ runRate+ "%");
			System.out.println("Chance to catch: "+ (100-catchRate)+ "%");
		}
		
		public void useEasyStay() {
			for(Item item:currentTrain.itemList) {
				if(item.getClass()==EasyStay.class) {
					runRate-=20;
					
				} 
		}
			removeEasyStay();
		}
		
		
		public boolean removeEasyStay() {
			for(Item item:currentTrain.itemList) {
				if(item.getClass()==EasyStay.class) {
					currentTrain.itemList.remove(item);
					return true;
					
				} 
		}
			return false;
		}
		
		public void useEasyCatch() {
			for(Item item:currentTrain.itemList) {
				if(item.getClass()==EasyCatch.class) {
					catchRate-=20;
					
				} 
		}
			removeEasyCatch();
		}
		
		
		public boolean removeEasyCatch() {
			for(Item item:currentTrain.itemList) {
				if(item.getClass()==EasyCatch.class) {
					currentTrain.itemList.remove(item);
					return true;
					
				} 
		}
			return false;
		}
		
		
		public void checkCap() {
			//Set cap for common
			if(currentPoke.getOccurRate()==OccurrenceRate.COMMON) {
				if(catchRate<10) {
					catchRate=10;
				}
				if(catchRate>80) {
					catchRate=80;
				}
				if(runRate>40) {
					runRate=40;
				}
				if(runRate<5) {
					runRate=5;
				}
			}
			//Set cap for uncommon
			if(currentPoke.getOccurRate()==OccurrenceRate.UNCOMMON) {
				if(catchRate<50) {
					catchRate=50;
				}
				if(catchRate>90) {
					catchRate=90;
				}
				if(runRate>80) {
					runRate=80;
				}
				if(runRate<20) {
					runRate=20;
				}
			}
			//Set cap for rare
			if(currentPoke.getOccurRate()==OccurrenceRate.RARE) {
				if(catchRate<1) {
					catchRate=1;
				}
				if(catchRate>85) {
					catchRate=85;
				}
				if(runRate>80) {
					runRate=80;
				}
				if(runRate<20) {
					runRate=20;
				}
			}
		}
		
		
		public boolean throwBall(int catchChance) {
			    currentTrain.itemList.remove(SafariBall.class);			
				System.out.println("You threw a Safari Ball");
				System.out.println("Catch rate: " +catchRate);
				System.out.println("catchChance: " +catchChance);
					if(catchRate<catchChance) {
						currentTrain.addPokemonToOwned(currentPoke);
						System.out.println(currentPoke.getName()+" Caught");
						giveRandomItem();
						return true;
					}
					else {
						System.out.println(currentPoke.getName()+" broke free");
						return false;
				
			}
					
		}
		
		//Give random item
		public boolean giveRandomItem() {
			int chance=new Random().nextInt(100)+1;
			if(chance>=70 && chance<=79 ) {
				currentTrain.itemList.add(new Potion());
				return true;
			}
			if(chance>=80 && chance<=89 ) {
				currentTrain.itemList.add(new UltraBall());
				return true;
			}
			if(chance>=90 && chance<=99 ) {
				currentTrain.itemList.add(new EasyCatch());
				return true;
			}
			return false;
			
		}
		
		//THE MAAAAASTER BALL
		public boolean throwMasterBall() {
			System.out.println("You threw a master ball!");
			System.out.println(currentPoke.getName()+" Caught");
			currentTrain.addPokemonToOwned(currentPoke);
			return true;
		}
		
		//Ultra ball
		public boolean throwUltraBall(int catchChance) {
			System.out.println("You threw an ultra ball!");
			System.out.println("Catch rate: " +catchRate);
			System.out.println("catchChance: " +catchChance);
			System.out.println("catchChance with ultraball: " +(catchChance+catchChance*.5));
			if(catchRate<catchChance+(catchChance*.5)) {
				currentTrain.addPokemonToOwned(currentPoke);
				System.out.println(currentPoke.getName()+" Caught");
				return true;
			}
			else {
				System.out.println(currentPoke.getName()+" broke free");
				return false;
		}
		}
		
		
		//Set to return a catch rate depending on the frequency.
		public int getRunRate() {
			if(currentPoke.getOccurRate()==OccurrenceRate.RARE) {
				return 35;
			}
			else if(currentPoke.getOccurRate()==OccurrenceRate.UNCOMMON) {
				return 25;
			}
			else {
				return 10;
			}
			
		}
		public int getCatchRate() {
			if(currentPoke.getOccurRate()==OccurrenceRate.RARE) {
				return 95;
			}
			else if(currentPoke.getOccurRate()==OccurrenceRate.UNCOMMON) {
				return 75;
			}
			else {
				return 50;
			}
			
		}
		
		public int getCurrentCatchRate() {
			checkCap();
			return catchRate;
		}
		public int getCurrentRunRate() {
			checkCap();
			return runRate;
		}
		 
		
}