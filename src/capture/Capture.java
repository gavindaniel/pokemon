package capture;

import java.util.Scanner;

import items.SafariBall;
import model.Trainer;
import pokemon.Pokemon;
import java.util.Random;

public class Capture {
		public Pokemon currentPoke;
		public Trainer currentTrain;
		
		public Capture(Pokemon currentPoke,Trainer currentTrain) {
			this.currentPoke=currentPoke;
			this.currentTrain=currentTrain;
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
			
			String currentMood=currentPoke.getMood();
			int chanceToRun=currentPoke.getRunRate();
			if(currentMood.equals("Eating")) {
				chanceToRun=chanceToRun/2;
			}
			else if(currentMood.equals("Angry")) {
				chanceToRun=chanceToRun*2;
			}
			if(chanceToRun>retreatChance) {
				System.out.println(currentPoke.getName()+" ran away.");
				return true;
			}
			return false;
		}
		
		
		
		public void throwRock() {
			System.out.println("You threw a rock");
			System.out.println(currentPoke.getName()+" is angry.");
			currentPoke.setMood("Angry");
		}
		
		
		public void throwBait() {
			System.out.println("You threw bait");
			System.out.println(currentPoke.getName()+" is eating.");
			currentPoke.setMood("Eating");	
		}
		
		
		public boolean throwBall(int catchChance) {
			    currentTrain.itemList.remove(SafariBall.class);			
				System.out.println("You threw a Safari Ball");
				String currentMood=currentPoke.getMood();
				int captureRate=currentPoke.getCatchRate();
				if(currentMood.equals("Eating")) {
					if(captureRate*1.2<catchChance) {
						currentTrain.addPokemonToOwned(currentPoke);
						System.out.println(currentPoke.getName()+" Caught");
						return true;
					}
					else {
						System.out.println(currentPoke.getName()+" broke free");
						return false;
					}
				}	
				else if(currentMood.equals("Angry")) {
					if(captureRate*.5<catchChance) {
						currentTrain.addPokemonToOwned(currentPoke);
						System.out.println(currentPoke.getName()+" Caught");
						return true;
					}
					else {
						System.out.println(currentPoke.getName()+" broke free");
						return false;
					}
				}	
				else {
					if(captureRate<catchChance) {
						currentTrain.addPokemonToOwned(currentPoke);
						System.out.println(currentPoke.getName()+" Caught");
						return true;
					}
					else {
						System.out.println(currentPoke.getName()+" broke free");
						return false;
					}
				}
			}
		
		
		public void startCapture() {
			Scanner keyboard = new Scanner(System.in);
			System.out.println("A wild "+currentPoke.getName()+" appeared");
			while(true) {
				System.out.println("Pick 1 to throw a rock, 2 to throw bait, 3 to throw a Safari Ball");
				String choice=keyboard.next();
				if(choice.equals("1")) {
					throwRock();
				}
				else if (choice.equals("2")) {
					throwBait();
				}
				else if (choice.equals("3")){
					if(checkSafariBalls()) {
					if(throwBall(new Random().nextInt(100))){
						break;
					}
					}
					else {
						System.out.println("You have no Safari Balls left");
						break;
					}
				}
				else {
					System.out.println("Invalid choice");
				}
				if(retreat(new Random().nextInt(100))) {
					System.out.println("The Pokemon ran away");
					break;
				}		
				
			}
		}
		 
		
}