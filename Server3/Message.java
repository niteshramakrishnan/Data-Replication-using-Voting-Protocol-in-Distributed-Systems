
import java.io.Serializable;

class Message implements Serializable {
	
	int msg_no, from_client, from_server, vote;
    String  type="";
    String client_msg ="";
        
	
	public int getmsg_no() {
		return msg_no;
	}

	public String getType() {
		return type;
	}

	public int getFrom_Client() {
		return from_client;
	}

	public int getFrom_Server() {
		return from_server;
	}

	public int getvotes() {
		return vote;
	}
	
	public Message(int counter, String msg_type, int from, int to, String c_msg) 
	{
		msg_no = counter;
		type = msg_type;
		from_client = from;
		from_server = to;
		client_msg = c_msg;
	}
}
