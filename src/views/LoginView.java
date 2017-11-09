package views;

import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

import controller.FileManager;
import controller.GameLoader;
import controller.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;


public class LoginView extends BorderPane implements Observer {
  


        private MenuBar mainMenu;
        private Menu userMenuBar, adminMenuBar;
        private TextField acctT;
        private PasswordField pswdT; 
        private Button loginB, playB, pauseB, stopB, createU;
        private GridPane acctGrid, loginGrid, buttonGrid;
        private Label acctLabl, pswdLabl, loginMsg, userMsg; 
        private FileManager man;
    //    private Pokemon theGame;
        private Observer textView;
        private GameLoader gameLoader;


        public LoginView(FileManager man, GameLoader gameLoader, Observer textView){
 
          this.man=man; 
          this.gameLoader=gameLoader; 
          this.textView=textView;  
          this.setPrefWidth(600);
          this.setPrefHeight(400);
          acctGrid = new GridPane();

          loginGrid = new GridPane();
          mainMenu = new MenuBar();

          setUpLoginGrids();

         }
     




// all grids: buttonGrid loginGrid acctGrid createU(button)

    public void setUpLoginGrids(){

    loginB = new Button("Login");
    loginB.setFont( new Font("Arial", 14));


    acctT = new TextField();
    pswdT = new PasswordField();
    acctLabl = new Label("Account Name");
    pswdLabl = new Label("Password");   
    loginMsg = new Label("Sign In");
    acctLabl.setFont( new Font("Arial", 18));
    pswdLabl.setFont( new Font("Arial", 18));
    loginMsg.setFont( new Font("Arial", 24));


    this.setAlignment(loginMsg, Pos.CENTER); 
    this.setMargin(loginMsg, new Insets(10,10,10,10));
    this.setTop(loginMsg);


    acctT.setMaxHeight(15.0);
    pswdT.setMaxHeight(15.0);
    acctT.setMaxWidth(125.0);
    pswdT.setMaxWidth(125.0);

    acctGrid.add(acctLabl,1,1);
    acctGrid.add(pswdLabl,1,2);
    acctGrid.add(acctT,2,1);
    acctGrid.add(pswdT,2,2);
    acctGrid.setHgap(5); 
    acctGrid.setVgap(5); 
 
    this.setAlignment(acctGrid, Pos.CENTER); 
    this.setMargin(acctGrid, new Insets(10,10,10,10));
    this.setCenter(acctGrid);

    this.setAlignment(loginGrid, Pos.CENTER); 
    this.setMargin(loginGrid, new Insets(10,10,100,125));
    this.setBottom(loginGrid);

    loginGrid.add(loginB,1,1);

    createU = new Button("Create"); 
    this.setAlignment(createU, Pos.CENTER); 
    this.setMargin(createU, new Insets(10,10,100,125));
 

    LoginButtonListener handler1 = new LoginButtonListener();

    loginB.setOnAction(handler1);
    createU.setOnAction(handler1);


    }


  public void setUserMsg(String val){

      this.setCenter(null);
      this.setTop(null);
      this.setBottom(null);
      userMsg = new Label(val);
      userMsg.setFont( new Font("Arial", 24));
      this.setCenter(userMsg);


 }

  private void setViewToAdmin(Observer newView) {
       this.setCenter(null);
       this.setBottom(null);       
       this.setCenter((Node) newView);
   }

  private void setViewTo(Observer newView) {
       this.setCenter(null);
       this.setTop(null);
       this.setBottom(null);       
       this.setCenter((Node) newView);
   }




  // has adminOptions menu
  public void setUpAdminMenus(){
 
    AdminMenuItemListener adminMenuListener = new AdminMenuItemListener();

    MenuItem createUser = new MenuItem("Create User");

    Menu usr = new Menu("Users");


    for( User usu : man.getUserVector() ){

    MenuItem val =  new MenuItem(usu.name);
    val.setOnAction(adminMenuListener);

    usr.getItems().addAll(val);

    }
 
  
    Menu adminOptions = new Menu("Admin");
    
    adminOptions.getItems().addAll(usr,createUser);

    createUser.setOnAction(adminMenuListener);

    mainMenu.getMenus().addAll(adminOptions);

    this.setTop(mainMenu); 
  }





  // menuListener for Admin
  private class AdminMenuItemListener implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent e) {
      // Find out the text of the adminOptions that was just clicked

      String text = ((MenuItem) e.getSource()).getText();
         // creates new User 

         if (text.equals("Create User")){
    
        }
       


     }
 } 



   // handles both login and create user
   private class LoginButtonListener implements EventHandler<ActionEvent>{

      @Override
      public void handle(ActionEvent arg0){
   
        if(loginB==(Button) arg0.getSource()){

         // make one call to check user.... 
           if(acctT.getText().equals("") || pswdT.getText().equals("")){
System.out.println("View:User Error1");
           } else {
               User temp=man.getUser(acctT.getText(),pswdT.getText());
            
               if(temp!=null){
System.out.println("View:Returning User");              
                   // have confirm to load last game
                  Alert alert = new Alert(AlertType.CONFIRMATION);
                  alert.setHeaderText("Load Previous Game?");
                  Optional<ButtonType> result = alert.showAndWait();
   
                  if(result.get() == ButtonType.OK){
System.out.println("Set Pokemon previous for ret User"); 
                  // check player loc in loaded game
System.out.println("Player Previous: "+temp.getSafariZone().getMap().getTrainer().getCurrentLocation().getX()+" "+temp.getSafariZone().getMap().getTrainer().getCurrentLocation().getY());   
                      // load User prev game
                      gameLoader.setSafariZone(null);
                      gameLoader.setSafariZone(temp.getSafariZone());
                      textView = null;
                      textView = new TextView(gameLoader.getSafariZone());
 

                   } else {
System.out.println("Set Pokemon current for ret User"); 
                      // set User Pokemon to current game   
                      temp.setSafariZone(gameLoader.getSafariZone());
                      man.pushUserData();
                   }
                      // set up admin User menu and game view
                      if(temp.name.equals("Merlin") && temp.password.equals("7777777")){
System.out.println("View:Admin");              
                         setUpAdminMenus();
                         setViewToAdmin(textView);
                       } else {
                    // set up current Uesr game view
                         setViewTo(textView);
                       }
//                     setUserMsg("Welcome Back User");               
                  
               } else {
                   man.setUser(acctT.getText(),pswdT.getText());
System.out.println("View:New User");              
System.out.println("Set Pokemon current for new"); 
                   //get user and set Pokemon to current game 
                    man.getUser(acctT.getText(),pswdT.getText()).setSafariZone(gameLoader.getSafariZone());
                    man.pushUserData();
                    setViewTo(textView);
               }
           }
          acctT.setText("");
          pswdT.setText("");
        }
      }

   }


@Override
public void update(Observable o, Object arg) {
	// TODO Auto-generated method stub
	
           

}




}

