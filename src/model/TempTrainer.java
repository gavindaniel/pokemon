package model;

import java.util.*;


import items.Item;

import java.awt.Point;
import java.io.*;

import model.SafariZone;
import pokemon.Pokemon;

public class TempTrainer implements Serializable{

   public String name;
     
   public Point loc;
   public LinkedList<Pokemon> poks;
   public LinkedList<Item> tools; 

        
   public TempTrainer(){

     tools = new LinkedList<Item>();
     poks = new LinkedList<Pokemon>();

     

   }


   public void setLoc(Point loc){

     this.loc=loc;

   }

   public void addPoks(Pokemon pok){ 

     poks.add(pok);
    
   }

   public void addTools(Item tool){ 

     tools.add(tool);
    
   }


 

}
