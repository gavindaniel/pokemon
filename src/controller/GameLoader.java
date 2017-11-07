package controller;




import model.Pokemon;


public class GameLoader{

   Pokemon game;


   public GameLoader(Pokemon game){

    this.game=game;
   }


   public Pokemon getPokemon(){

    return game;

   }

   public void setPokemon(Pokemon game){

    this.game=game;

   }



}
