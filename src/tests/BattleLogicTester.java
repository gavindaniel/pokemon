package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import battle.BattleLogic;
import model.Trainer;
import pokemon.ActiveAttack;
import pokemon.Attack;
import pokemon.Bulbasaur;
import pokemon.Charmander;
import pokemon.Pikachu;
import pokemon.Pokemon;
import pokemon.Squirtle;

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

	@Test
	public void testIsPokemonDrained() {
		
		BattleLogic battle = new BattleLogic(null, null);
		Pokemon p = new Pikachu();
		
		assertFalse(battle.isPokemonDrained(p));
		
		p.setCurrHP(0);
		assertTrue(battle.isPokemonDrained(p));
	}
	
	@Test
	public void testAreAllPokemonDrained() {
		
		BattleLogic battle = new BattleLogic(null, null);
		List<Pokemon> battleList = new ArrayList<>(3);
		battleList.add(new Pikachu());
		battleList.add(new Charmander());
		battleList.add(new Squirtle());
		
		assertFalse(battle.areAllPokemonDrained(battleList));
		
		battleList.get(0).setCurrHP(0);
		battleList.get(1).setCurrHP(0);
		
		assertFalse(battle.areAllPokemonDrained(battleList));
		
		battleList.get(2).setCurrHP(0);
		
		assertTrue(battle.areAllPokemonDrained(battleList));
	}
	
	@Test
	public void testIsBattleOver() {
		
		Trainer trainer1 = new Trainer("player1");
		Trainer trainer2 = new Trainer("player2");
		
		BattleLogic battle = new BattleLogic(trainer1, trainer2);
		
		List<Pokemon> battleList1 = trainer1.getBattlePokemonList();
		battleList1.add(new Pikachu());
		battleList1.add(new Charmander());
		battleList1.add(new Squirtle());
		
		List<Pokemon> battleList2 = trainer2.getBattlePokemonList();
		battleList2.add(new Pikachu());
		battleList2.add(new Charmander());
		battleList2.add(new Squirtle());
		
		assertFalse(battle.isBattleOver());
		
		battleList1.get(1).setCurrHP(0);
		battleList1.get(2).setCurrHP(0);
		
		battleList2.get(1).setCurrHP(0);
		battleList2.get(2).setCurrHP(0);
		
		assertFalse(battle.isBattleOver());
		
		battleList1.get(0).setCurrHP(0);
		assertTrue(battle.isBattleOver());
		
		battleList1.get(0).setCurrHP(50);
		assertFalse(battle.isBattleOver());
		
		battleList2.get(0).setCurrHP(0);
		assertTrue(battle.isBattleOver());
	}
	
	@Test
	public void testCalculateDamage() {
		
		BattleLogic battle = new BattleLogic(null, null);
		
		Pokemon attackPokemon = new Charmander();
		Pokemon defendPokemon = new Bulbasaur();
		
		ActiveAttack attack = (ActiveAttack) attackPokemon.getAttackList().get(0);
		
		int calcDamage = battle.calculateDamage(attack, attackPokemon, defendPokemon);
		
		assertTrue(calcDamage <= 199 && calcDamage >=168 );
	}





}
