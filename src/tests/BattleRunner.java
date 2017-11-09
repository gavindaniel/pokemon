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
		
		List<Pokemon> pokeList1 = new ArrayList<>();
		pokeList1.add(new Pikachu());
		pokeList1.add(new Charmander());
		pokeList1.add(new Bulbasaur());
		pokeList1.add(new Squirtle());
		
		List<Pokemon> pokeList2 = new ArrayList<>();
		pokeList2.add(new Pikachu());
		pokeList2.add(new Charmander());
		pokeList2.add(new Bulbasaur());
		pokeList2.add(new Squirtle());
		
		Trainer trainer1 = new Trainer("Player1");
		trainer1.setOwnedPokemonList(pokeList1);
		
		Trainer trainer2 = new Trainer("Player2");
		trainer2.setOwnedPokemonList(pokeList2);
		
		BattleLogic battle = new BattleLogic(trainer1, trainer2);
		battle.runBattle();
		
	}

}
