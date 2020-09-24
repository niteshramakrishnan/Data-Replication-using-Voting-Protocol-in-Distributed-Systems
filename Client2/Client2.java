import java.util.Random;
import java.net.*;
import java.io.*;
public class Client2 {
  public  static int msg_count = 0;
  public static int client_no=2;
  public static void main(String[] args) throws Exception {
    int[] serverports = {9991,9992,9993,9994,9995,9996,9997};
    int server_select = 0, msg_select = 0;
    int hash_fn1 = 0;
    int hash_fn2 = 0;
    int hash_fn3 = 0;
    int i =0;
    String[] msg_type = {"read", "write"};
    String  serverMessage1="", serverMessage2="", serverMessage3="";
  while(i<100)
  {

  try{
    
    server_select = (int)(Math.random() * 7);   // Generating random server number  
    msg_select = (int)(Math.random() * 2);      // generating random read/write request
    
    hash_fn1 = server_select;
    hash_fn2 = (hash_fn1 + 1) % 7;
    hash_fn3 = (hash_fn1 + 2) % 7;

    String clientMessage="100,"+ client_no +",Client Request,"+hash_fn1+","+hash_fn2+","+hash_fn3+","+ msg_type[msg_select];
    

    Socket socket1=new Socket("dc11.utdallas.edu",serverports[hash_fn1]); //  Connection to server H(o)
    Socket socket2=new Socket("dc11.utdallas.edu",serverports[hash_fn2]); //  connection to server H(o)+1 %7
    Socket socket3=new Socket("dc11.utdallas.edu",serverports[hash_fn3]); //  connection to server H(o)+2 %7
    
    DataInputStream inStream1=new DataInputStream(socket1.getInputStream());
    DataOutputStream outStream1=new DataOutputStream(socket1.getOutputStream());
    
    DataInputStream inStream2=new DataInputStream(socket2.getInputStream());
    DataOutputStream outStream2=new DataOutputStream(socket2.getOutputStream());
    
    DataInputStream inStream3=new DataInputStream(socket3.getInputStream());
    DataOutputStream outStream3=new DataOutputStream(socket3.getOutputStream());
   
    outStream1.writeUTF(clientMessage);
    outStream1.flush();
    
    outStream2.writeUTF(clientMessage);          
    outStream2.flush();
    
    outStream3.writeUTF(clientMessage);       
    outStream3.flush();
    
    serverMessage1=inStream1.readUTF();
    serverMessage2=inStream2.readUTF();
    serverMessage3=inStream3.readUTF();
    
    String[] splited = serverMessage1.split(",");
    int from = Integer.parseInt(splited[0]);
    String  Server_Message = splited[1];
    
    if (Server_Message.equals("Server Acknowledgement")){
      System.out.println(">> Client 2 recieves a successful ack from server " + from);
      }
    else 
    {
      System.out.println("No Acknowledgement recieved");
    }
    
    
    splited = serverMessage2.split(",");
    from = Integer.parseInt(splited[0]);
    Server_Message = splited[1];
      
    if (Server_Message.equals("Server Acknowledgement")){
        System.out.println(">> Client 2 recieves a successful ack from server " + from);
        }
        else 
        {
          System.out.println("No Acknowledgement recieved");
        }
          
    splited = serverMessage3.split(",");
    from = Integer.parseInt(splited[0]);
    Server_Message = splited[1];
    
    if (Server_Message.equals("Server Acknowledgement")){
      System.out.println(">> Client 2 recieves a successful ack from server " + from);
      }
      else 
      {
        System.out.println("No Acknowledgement recieved");
      }
      
      Thread.sleep(3000);

    outStream1.close();
    outStream2.close();
    outStream3.close();
    socket1.close();
    socket2.close();
    socket3.close();

    System.out.println("End of one iteration :" + (i+1));
     
  }catch(Exception e){
    System.out.println("Connection Disrupted");
  }
  
  i++;
  }
}
}