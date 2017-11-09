package controller;




import model.SafariZone;


public class GameLoader{

   SafariZone game;


   public GameLoader(SafariZone game){

    this.game=game;
   }


   public SafariZone getSafariZone(){

    return game;

   }

   public void setSafariZone(SafariZone game){

    this.game=game;

   }



}
