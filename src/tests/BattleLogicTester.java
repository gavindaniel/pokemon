package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import battle.BattleLogic;
import model.Trainer;
import pokemon.Bulbasaur;
import pokemon.Charmander;
import pokemon.Pikachu;

public class BattleLogicTester {

	@Test
	public void testDetermineWhoStarts() {
		
		Trainer trainer1 = new Trainer("player1");
		Trainer trainer2 = new Trainer("player2");
		
		BattleLogic battle = new BattleLogic(trainer1, trainer2);
		
		trainer1.setActiveBattlePokemon(new Pikachu());		//Fast
		trainer2.setActiveBattlePokemon(new Bulbasaur());	//Slow
		
		assertEquals(1, battle.determineWhoStarts());
		
		trainer1.setActiveBattlePokemon(new Bulbasaur());	//Slow
		trainer2.setActiveBattlePokemon(new Charmander());	//Fast
		
		assertEquals(2, battle.determineWhoStarts());
	}
}
