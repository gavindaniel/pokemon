package controller; 

import java.util.*;
import java.io.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;


import controller.User;
 // class to push and pull Vector users

public class FileManager {

   private Vector<User> users;
   

   public FileManager(){

     users = new Vector<User>();

     pullUserData();


   }


/*
   public int checkUser(String logUser, String pswd){

System.out.println("No. users: "+users.size());

      int cnt=0;

      for( TempTrainer user : users ){

        cnt++;
        if(user.name.equals(logUser)){
          cnt--;
          if(user.password.equals(pswd)){


                 if( logUser.equals("Merlin") && pswd.equals("7777777")){
System.out.println("FileMan:Got Merlin");
             return 2;
                           

                 }else{
System.out.println("FileMan:Returning User");
               return 1; 

                 }
            } 
          }
       }

//System.out.println("no2 "+users.size());

      if(cnt==users.size()){

System.out.println("FileMan:New User");
         TempTrainer addUser = new TempTrainer(logUser, pswd);
         users.add(addUser);

         pushUserData();

       return 0;

      }



     return -1;

   }

*/

  @SuppressWarnings("unchecked")
  public void pullUserData(){

    try {
     FileInputStream ufin = new FileInputStream("users.dat");
     ObjectInputStream uois = new ObjectInputStream(ufin);
     users = (Vector) uois.readObject();
     uois.close();
     }
    catch (Exception e) { e.printStackTrace();
    }



  }

  public void pushUserData(){

    try {
       FileOutputStream ufout = new FileOutputStream("users.dat");
       ObjectOutputStream uoos = new ObjectOutputStream(ufout);
       uoos.writeObject(users);
       uoos.close();
        }
     catch (Exception e) { e.printStackTrace();
    }


  }

 public User getUser(String logUser, String pswd){

      for( User user : users ){

        if(user.name.equals(logUser) &&  user.password.equals(pswd)){

            return user;
        }      

      }

    return null;

 }

 public void setUser(String name, String pswd){

    users.add(new User(name,pswd));
    pushUserData();

 }

 public Vector<User> getUserVector(){

   return users;
 }


 public void removeUser(String rmName){

        // finds and removes User
        for( Iterator<User> iter = users.iterator(); iter.hasNext();){

           User usr = iter.next();
           if (rmName.equals(usr.name)){

           Alert alert = new Alert(AlertType.CONFIRMATION);

           alert.setHeaderText("Remove User?");

           Optional<ButtonType> result = alert.showAndWait();
             if (result.get() == ButtonType.OK){

             iter.remove(); 
             pushUserData(); 

System.out.println("removed user"); 

             }
           }

        }

  }



}

