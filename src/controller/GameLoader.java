package controller;




import model.SafariZone;


public class GameLoader{

   SafariZone game;


   public GameLoader(SafariZone game){

    this.game=game;
   }


   public SafariZone getPokemon(){

    return game;

   }

   public void setPokemon(SafariZone game){

    this.game=game;

   }



}
