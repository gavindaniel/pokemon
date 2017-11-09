package controller;

import java.io.Serializable;

import model.SafariZone;

public class User implements Serializable{

   public String name;
   public String password;
     
   public SafariZone pokemon;
        
   public User(String name, String password){

     this.name=name;
     this.password=password;

   }

   public void setPokemon(SafariZone pokemon){

    this.pokemon=pokemon;

   } 

   public SafariZone getPokemon(){

    return pokemon;

   }


}
