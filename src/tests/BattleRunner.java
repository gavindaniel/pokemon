package tests;

import java.util.ArrayList;
import java.util.List;

import model.BattleLogic;
import model.Trainer;
import pokemon.Bulbasaur;
import pokemon.Charmander;
import pokemon.Pikachu;
import pokemon.Pokemon;
import pokemon.Squirtle;

public class BattleRunner {
	
	
	public static void main (String [] args) {
		
		List<Pokemon> pokeList = new ArrayList<>();
		pokeList.add(new Pikachu());
		pokeList.add(new Charmander());
		pokeList.add(new Bulbasaur());
		pokeList.add(new Squirtle());
		
		Trainer trainer1 = new Trainer("Player1");
		trainer1.setPokemonList(pokeList);
		
		Trainer trainer2 = new Trainer("Player2");
		trainer2.setPokemonList(pokeList);
		
		BattleLogic battle = new BattleLogic(trainer1, trainer2);
		battle.runBattle();
		
	}

}
