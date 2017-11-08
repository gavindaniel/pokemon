package controller;

import java.io.Serializable;

import model.PokemonGame;

public class User implements Serializable{

   public String name;
   public String password;
     
   public PokemonGame pokemon;
        
   public User(String name, String password){

     this.name=name;
     this.password=password;

   }

   public void setPokemon(PokemonGame pokemon){

    this.pokemon=pokemon;

   } 

   public PokemonGame getPokemon(){

    return pokemon;

   }


}
