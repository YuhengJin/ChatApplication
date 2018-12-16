/*package Application;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatInitiateur {
	Socket s;
	User user;
	
	
	public ChatInitiateur(int numPort) {
		
	}
	
	
	public void startClient() {
		try {
			this.s = new Socket("localhost", 10080);
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 
	      
		
	}
	
	public void  closeAll() {
		
		
	}
	
	
	public void sendMesssage() {
		comm.getOutputStream().write("Ceci est mon message\n\n".getBytes());
	    is = comm.getInputStream();
		
	}
	
	

}
*/