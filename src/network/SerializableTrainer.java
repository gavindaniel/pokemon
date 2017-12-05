package network;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import items.Item;
import pokemon.Pikachu;
import items.SafariBall;
import pokemon.Pokemon;

import model.Trainer;
import pokemon.Pokemon;



public class SerializableTrainer implements Serializable{


        private String name;
        private Point currentLocation;
	private int zone_number;
	private int numSteps;
	private List<Pokemon> ownedPokemonList;		//List of all owned pokemon
	private List<Pokemon> battlePokemonList;		//List of 3 pokemon to be used in battle
	public List<Item> itemList;
	private Pokemon activeBattlePokemon;		



        public SerializableTrainer( Trainer trainer){

        name=trainer.getName();
        numSteps=trainer.getNumSteps();
        zone_number=trainer.getZone();
        ownedPokemonList=trainer.getOwnedPokemonList();
        battlePokemonList=trainer.getBattlePokemonList();
        itemList=trainer.getItemList();
        currentLocation=trainer.getCurrentLocation();
 

  

        }


        public SerializableTrainer(){


        }


}
