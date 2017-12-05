package capture;

import java.util.Scanner;

import items.SafariBall;
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
		
		
		
		public boolean retreat(int retreatChance) {
			
			System.out.println("Run rate: " +runRate);
			System.out.println("retreatChance: " +retreatChance);
			if(runRate>retreatChance) {
				System.out.println(currentPoke.getName()+" ran away.");
				return true;
			}
			return false;
		}
		
		
		
		public void throwRock() {
			runRate+=5;
			catchRate+=5;
			currentPoke.takeDamage(((int) Math.round(currentPoke.getCurrHP()*.03)));
			checkCap();
			System.out.println("You threw a rock");
			System.out.println(currentPoke.getName()+" is easier to catch, but more likely to run away!");
			System.out.println(currentPoke.getCurrHP());
			System.out.println("Chance to run: "+ runRate+ "%");
			System.out.println("Chance to catch: "+ catchRate+ "%");
		}
		
		
		public void throwBait() {
			runRate-=5;
			catchRate-=5;
			currentPoke.heal((int) Math.round(currentPoke.getCurrHP()*.01));
			System.out.println(currentPoke.getCurrHP());
			checkCap();
			System.out.println("You threw a rock");
			System.out.println(currentPoke.getName()+" is easier to catch, but more likely to run away!");
			System.out.println("Chance to run: "+ runRate+ "%");
			System.out.println("Chance to catch: "+ catchRate+ "%");
		}
		
		public void checkCap() {
			//Set cap for common
			if(currentPoke.getOccurRate()==OccurrenceRate.COMMON) {
				if(catchRate>90) {
					catchRate=90;
				}
				if(catchRate<20) {
					catchRate=20;
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
				if(catchRate>50) {
					catchRate=50;
				}
				if(catchRate<10) {
					catchRate=10;
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
				if(catchRate>15) {
					catchRate=15;
				}
				if(catchRate<1) {
					catchRate=1;
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
				return 5;
			}
			else if(currentPoke.getOccurRate()==OccurrenceRate.UNCOMMON) {
				return 25;
			}
			else {
				return 50;
			}
			
		}
		
		public int getCurrentCatchRate() {
			return catchRate;
		}
		public int getCurrentRunRate() {
			return runRate;
		}
		 
		
}