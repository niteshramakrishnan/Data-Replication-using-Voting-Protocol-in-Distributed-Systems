import java.net.*;
import java.io.*;
import java.io.File;  // Import the File class


public class Server5 {
  public static int server_no=5;
  public static boolean if_msg_recieved = false;
  
  public static void main(String[] args) throws Exception {
    try{

      //Change the port number for different servers (9997,9999)
      
      ServerSocket server=new ServerSocket(9995);      // dc01.utdallas.edu 
      
      
      
      System.out.println(">> Server 5 Started ");
      // Files creation
      try {

        // Creating file 1
        
        File f1 = new File("./f1.txt");
        if (f1.createNewFile()) {
          System.out.println("File created: " + f1.getName());
        } else {
          System.out.println( f1.getName() + " already exists.");
        }

        
        
      } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }
      
      //creating each thread for each client socket

      while(true)
      {
        
        Socket serverClient=server.accept();  //server accept the client connection request
        System.out.println(">> " + "Client connected ");
        ServerClientThread sct = new ServerClientThread(serverClient);         //send  the request to a separate thread
        sct.start();
            
      }
      }
      catch(Exception e){
      System.out.println(e);
        }
  }
}
class ServerClientThread extends Thread {
  Socket serverClient;
  int msg_no = 0, clientNo, from_client, from_server, hash1, hash2, hash3, vote=0;
  String  type="", server_to_server_msg= "";
  String[] splited;
  int[] serverports = {9991,9992,9993,9994,9995,9996,9997};
  String client_to_server_msg1 ="", client_to_server_msg2 ="", client_msg ="";
  String server_to_client_msg = Server5.server_no + ",Server Acknowledgement";
      
  ServerClientThread(Socket inSocket){
    serverClient = inSocket;
    
  }
  public void run(){
    
    try{

      
      DataInputStream inStream = new DataInputStream(serverClient.getInputStream());
      DataOutputStream outStream = new DataOutputStream(serverClient.getOutputStream());
      
      
      //reading incoming message
      
      while(inStream.available() >0 )
      {
        
        client_to_server_msg1 =inStream.readUTF();

        //parsing the incoming client message

        splited = client_to_server_msg1.split(",");
        msg_no = Integer.parseInt(splited[0]);          
        clientNo = from_client = Integer.parseInt(splited[1]);          
        client_msg = splited[2];          
        hash1 = Integer.parseInt(splited[3]);          
        hash2 = Integer.parseInt(splited[4]);          
        hash3 = Integer.parseInt(splited[5]);          
        type = splited[6];
        vote = 1;
      }

      if(msg_no == 200)
      {
        System.out.println(">> Server 5 recieves: "+ "Server " + from_client + " request with message id:" + msg_no);
        
      }
      else if(msg_no == 100)
      {
        
        System.out.println(">> Server 5 recieves: "+ "Client " + from_client + " request with message id:" + msg_no);
      }     
      
      String server_msg = "200,"+ Server5.server_no + ",Server Request,"+hash1+","+hash2+","+hash3+","+"Vote Request";

      // trying to connect to other servers

      if(msg_no == 100)
      {
        Server5.if_msg_recieved = true;
        
        if(Server5.server_no == hash1 + 1)
        {
          try
          {
            Socket server_socket1=new Socket("127.0.0.1",serverports[hash2]); // server conection using server ports 9997,9998,9999
            DataInputStream inStream1=new DataInputStream(server_socket1.getInputStream());
            DataOutputStream outStream1=new DataOutputStream(server_socket1.getOutputStream());

            Socket server_socket2=new Socket("127.0.0.1",serverports[hash3]); // server conection using server ports 9997,9998,9999
            DataInputStream inStream2=new DataInputStream(server_socket2.getInputStream());
            DataOutputStream outStream2=new DataOutputStream(server_socket2.getOutputStream());

            outStream1.writeUTF(server_msg);
            outStream1.flush(); 
            
            outStream2.writeUTF(server_msg);
            outStream2.flush(); 

            // listning to incoming message from stream1

            String server_return_msg=inStream1.readUTF();
            splited = server_return_msg.split(",");
            from_server = Integer.parseInt(splited[0]);
            server_to_server_msg = splited[1];
            
            if (server_to_server_msg.equals("Server Acknowledgement"))
            {
              vote++;
              System.out.println("Ack recieved from server " + from_server + "\nNumber of votes = "+ vote);

            }
            else if(server_to_server_msg.equals("No Acknowledgement"))
              {
                System.out.println("No Ack recieved from server" + from_server );
              }


            // listning to incoming message from stream2

            server_return_msg=inStream2.readUTF();
            splited = server_return_msg.split(",");
            from_server = Integer.parseInt(splited[0]);
            server_to_server_msg = splited[1];
            
            if (server_to_server_msg.equals("Server Acknowledgement"))
            {
              vote++;
              System.out.println("Ack recieved from server " + from_server + "\nNumber of votes = "+ vote);

            }
            else if(server_to_server_msg.equals("No Acknowledgement"))
              {
                System.out.println("No Ack recieved from server" + from_server );
              }


            outStream1.close();
            outStream2.close();
            inStream1.close();
            inStream2.close();
            server_socket1.close();
            server_socket2.close();
          }catch(Exception e)
          { System.out.println(e);}
        }
        
        if(Server5.server_no == hash2 + 1)
        {
          try
          {
            Socket server_socket1=new Socket("127.0.0.1",serverports[hash1]); // server conection using server ports 9997,9998,9999
            DataInputStream inStream1=new DataInputStream(server_socket1.getInputStream());
            DataOutputStream outStream1=new DataOutputStream(server_socket1.getOutputStream());

            Socket server_socket2=new Socket("127.0.0.1",serverports[hash3]); // server conection using server ports 9997,9998,9999
            DataInputStream inStream2=new DataInputStream(server_socket2.getInputStream());
            DataOutputStream outStream2=new DataOutputStream(server_socket2.getOutputStream());

            outStream1.writeUTF(server_msg);
            outStream1.flush(); 
            
            outStream2.writeUTF(server_msg);
            outStream2.flush(); 

            // listning to incoming message from stream1

            String server_return_msg=inStream1.readUTF();
            splited = server_return_msg.split(",");
            from_server = Integer.parseInt(splited[0]);
            server_to_server_msg = splited[1];
            
            if (server_to_server_msg.equals("Server Acknowledgement"))
            {
              vote++;
              System.out.println("Ack recieved from server " + from_server + "\nNumber of votes = "+ vote);

            }
            else if(server_to_server_msg.equals("No Acknowledgement"))
              {
                System.out.println("No Ack recieved from server" + from_server );
              }


            // listning to incoming message from stream2

            server_return_msg=inStream2.readUTF();
            splited = server_return_msg.split(",");
            from_server = Integer.parseInt(splited[0]);
            server_to_server_msg = splited[1];
            
            if (server_to_server_msg.equals("Server Acknowledgement"))
            {
              vote++;
              System.out.println("Ack recieved from server " + from_server + "\nNumber of votes = "+ vote);

            }
            else if(server_to_server_msg.equals("No Acknowledgement"))
              {
                System.out.println("No Ack recieved from server" + from_server );
              }


            outStream1.close();
            outStream2.close();
            inStream1.close();
            inStream2.close();
            server_socket1.close();
            server_socket2.close();
          }catch(Exception e)
          { System.out.println(e);}
        }

        if(Server5.server_no == hash3 + 1)
        {
          try
          {
            Socket server_socket1=new Socket("127.0.0.1",serverports[hash1]); // server conection using server ports 9997,9998,9999
            DataInputStream inStream1=new DataInputStream(server_socket1.getInputStream());
            DataOutputStream outStream1=new DataOutputStream(server_socket1.getOutputStream());

            Socket server_socket2=new Socket("127.0.0.1",serverports[hash2]); // server conection using server ports 9997,9998,9999
            DataInputStream inStream2=new DataInputStream(server_socket2.getInputStream());
            DataOutputStream outStream2=new DataOutputStream(server_socket2.getOutputStream());

            outStream1.writeUTF(server_msg);
            outStream1.flush(); 
            
            outStream2.writeUTF(server_msg);
            outStream2.flush(); 

            // listning to incoming message from stream1

            String server_return_msg=inStream1.readUTF();
            splited = server_return_msg.split(",");
            from_server = Integer.parseInt(splited[0]);
            server_to_server_msg = splited[1];
            
            if (server_to_server_msg.equals("Server Acknowledgement"))
            {
              vote++;
              System.out.println("Ack recieved from server " + from_server + "\nNumber of votes = "+ vote);

            }
            else if(server_to_server_msg.equals("No Acknowledgement"))
            {
              System.out.println("No Ack recieved from server" + from_server );
            }


            // listning to incoming message from stream2

            server_return_msg=inStream2.readUTF();
            splited = server_return_msg.split(",");
            from_server = Integer.parseInt(splited[0]);
            server_to_server_msg = splited[1];
            
            if (server_to_server_msg.equals("Server Acknowledgement"))
            {
              vote++;
              System.out.println("Ack recieved from server " + from_server + "\nNumber of votes = "+ vote);

            }
            else if(server_to_server_msg.equals("No Acknowledgement"))
            {
              System.out.println("No Ack recieved from server" + from_server );
            }


            outStream1.close();
            outStream2.close();
            inStream1.close();
            inStream2.close();
            server_socket1.close();
            server_socket2.close();
          }catch(Exception e)
          { System.out.println(e);}
        }
        
      }  

      else if(msg_no == 200)
      {
        
        if(Server5.if_msg_recieved)
        {
          //System.out.println(Server5.if_msg_recieved);
          outStream.writeUTF(server_to_client_msg);
          outStream.flush();
        }
        else 
        {
          outStream.writeUTF(Server5.server_no+",No Acknowledgement");
          outStream.flush();
        }
      }
      else if (msg_no == 0)
      {
        Server5.if_msg_recieved = false;
        outStream.writeUTF(Server5.server_no+",No Acknowledgement");
        outStream.flush();

      }

         
      //Reading or writing only if it has more than 1 vote
      if(vote>1)
      {         
        if (type.equals("write"))
        {
          FileWriter fout =  new FileWriter("./f1.txt", true);
          fout.append(client_msg+"\n");
          System.out.println(">> Message written to file successfully" );
          outStream.writeUTF(server_to_client_msg);
          outStream.flush();
          fout.close();
        }
        if (type.equals("read"))
        {
          BufferedReader br = new BufferedReader( new FileReader("./f1.txt"));
          String read_msg = br.readLine();
          String[] temp_msg = read_msg.split("\n");
          read_msg = temp_msg[temp_msg.length - 1];           
            
          if(read_msg == null)
          {
              System.out.println("Error reading/writing message");
          }
          else
          {
              System.out.println(">> Message read from file : "+ read_msg );
              outStream.writeUTF(server_to_client_msg);
              outStream.flush();
              br.close();
          }
        }
      }
      else if( ! type.equals("Vote Request") && msg_no != 0) 
        {
          System.out.println("Not enough votes");
          outStream.writeUTF(server_msg);
          outStream.flush();
        }
        

      inStream.close();
      outStream.close();
      serverClient.close();
    }catch(Exception ex){
      System.out.println(ex);
    }finally{
      System.out.println("Client -" + clientNo + " exited!! ");
    }
  }
}