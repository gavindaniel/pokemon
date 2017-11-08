package controller;




import model.PokemonGame;


public class GameLoader{

   PokemonGame game;


   public GameLoader(PokemonGame game){

    this.game=game;
   }


   public PokemonGame getPokemon(){

    return game;

   }

   public void setPokemon(PokemonGame game){

    this.game=game;

   }



}
