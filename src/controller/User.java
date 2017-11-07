package controller;

import java.util.*;
import java.awt.Point;
import java.io.*;

import model.Pokemon;

public class User implements Serializable{

   public String name;
   public String password;
     
   public Pokemon pokemon;
        
   public User(String name, String password){

     this.name=name;
     this.password=password;

   }

   public void setPokemon(Pokemon pokemon){

    this.pokemon=pokemon;

   } 

   public Pokemon getPokemon(){

    return pokemon;

   }


}
