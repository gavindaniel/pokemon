package model;

import java.util.*;
import java.awt.Point;
import java.io.*;

import model.Pokemon;

public class TempTrainer implements Serializable{

   public String name;
     
   public Point loc;
   public LinkedList<String> poks;
   public LinkedList<String> tools; 

        
   public TempTrainer(){

     tools = new LinkedList<String>();
     poks = new LinkedList<String>();

     

   }


   public void setLoc(Point loc){

     this.loc=loc;

   }

   public void addPoks(String pok){ 

     poks.add(pok);
    
   }

   public void addTools(String tool){ 

     tools.add(tool);
    
   }


 

}
