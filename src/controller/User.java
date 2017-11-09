package controller;

import java.util.*;
import java.awt.Point;
import java.io.*;

import model.SafariZone;

public class User implements Serializable{

   public String name;
   public String password;
     
   public SafariZone pokemon;
        
   public User(String name, String password){

     this.name=name;
     this.password=password;

   }

   public void setSafariZone(SafariZone pokemon){

    this.pokemon=pokemon;

   } 

   public SafariZone getSafariZone(){

    return pokemon;

   }


}
