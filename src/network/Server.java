package network;

/*
 * This is the server part of a multi-client server. Since it is 
 * not a JavaFX application, we can starts new threads in a Runnable.
 * The GUI client needs a Task  
 */

import java.io.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Vector;
import java.awt.Point;




public class Server implements Serializable{

  private static ServerSocket serverSocket;
  private static List<ObjectOutputStream> outputStreams = new Vector<>();

  private static Vector<Point> vector = new Vector<Point>();


  public static void main(String[] args) throws IOException {



    serverSocket = new ServerSocket(4001);
    // System.out.println("Server started on port " + SERVER_PORT);

    // Setup the server to accept many clients
    while (true) {
      Socket socket = serverSocket.accept();
      ObjectInputStream inputFromClient = new ObjectInputStream(socket.getInputStream());
      ObjectOutputStream outputToClient = new ObjectOutputStream(socket.getOutputStream());
      
      outputStreams.add(outputToClient);
      // System.out.println("Accepted a new connection from " + socket.getInetAddress());

      // Start the loop that reads any Client's writeObject in the background in a 
      // different Thread so this program can also wait for new Client connections.
      // This thread allows the Server to wait for each client's writing of a String message.
      // TODO 2: Start a new ClientHandler in a new Thread
      ClientHandler clientHandler = new ClientHandler(inputFromClient);


      Thread thread = new Thread(clientHandler);
      thread.start();


    }
  }

  private static class ClientHandler implements Runnable {

 private ObjectInputStream input;
    Point obj;
    int index;

    public ClientHandler(ObjectInputStream input) {
      this.input = input;

    index=outputStreams.size()-1;


    }
 
    @Override
    public void run() {
      // to read any new messages from the server. When a new read
      // happens, write the new message to all Clients

System.out.println("S: new: "+outputStreams.size()+" "+vector.size());


          try{
                 while(true){

                

System.out.println("S: read obj");

                   obj = (Point)input.readObject();
System.out.println("S: point/index "+obj.getX()+" "+obj.getY()+" "+index);


                    writeVectorToClients(obj,index); 

                 }


           } catch (IOException ioe){

           } catch (ClassNotFoundException cnfe){

           }
    
    }

    private void writeVectorToClients(Point object, int index) throws ClassNotFoundException{


            for(int i = 0; i<outputStreams.size();i++){
  

              if(i!=index){
              try{
                 outputStreams.get(i).writeObject((Point)object);
System.out.println("S: write obj");

                  } catch (IOException ioe){


                  }
 

               }
             }

 
      }
  } 
}
