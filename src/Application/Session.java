package Application;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class Session {

	InetAddress group ;
	
	public Session(String adresse) throws Throwable {
		group = InetAddress.getByName(adresse);
	}
    
	public void addToSession(User user) throws IOException {
        user.get_clientUDP().getMCSocket().joinGroup(group);
	    System.out.println("["+user.get_Name()+": abonné au groupe : "+group);

	}
}
