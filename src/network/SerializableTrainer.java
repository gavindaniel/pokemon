package network;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;


import pokemon.Pikachu;
import items.SafariBall;
import pokemon.Pokemon;
import model.Trainer;
import pokemon.Pokemon;
import items.Item;


public class SerializableTrainer implements Serializable{


        private String name;
        public Point currentLocation;
	private int zone_number;
	private int numSteps;
	private List<String> ownedPokemonList;		//List of all owned pokemon
	private List<String> battlePokemonList;		//List of 3 pokemon to be used in battle
	public List<String> itemList;
	private String activeBattlePokemon;		



        public SerializableTrainer( Trainer trainer){

        name=trainer.getName();
        numSteps=trainer.getNumSteps();
        zone_number=trainer.getZone();
        currentLocation=trainer.getCurrentLocation();
      //  activeBattlePokemon=trainer.getActiveBattlePokemon().getName();		



        ownedPokemonList=getPokemonName(trainer.getOwnedPokemonList());
        battlePokemonList=getPokemonName(trainer.getBattlePokemonList());
//        itemList=getItemName(trainer.getItemList());
 

  

        }


        public List getPokemonName(List<Pokemon> list){

          List<String> copy= new LinkedList<String>();
       
          for( Pokemon pokemon : list ){ 

            copy.add(pokemon.getName());

          }

           return copy;
        }


}
