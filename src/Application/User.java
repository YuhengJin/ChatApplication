package Application;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.io.PrintWriter;


//import Application.Server;
//import Application.Client ;
//import java.lang.String;


public class User {

	//Variables locales
	private boolean connected;
	private String pseudo; 
	private InetAddress addressUser;
	//private int portClientUser;
	private int portServerUser;
	private ArrayList<User> users;
	
	
	private Server server;
	private Client client;

	public User() {
		
	}
	
	
	public User (String pseudo,InetAddress address, int portServer)  {
		this.connected = true;
		this.pseudo = pseudo;	
		//Verification de pseudo dispo
		this.addressUser = address;
		this.portServerUser = portServer;
		
		//this.server = new Server(portServer);
		//this.server.Startlistenning();
	}

	

	
	//Quand un user est créer, il a le droit de modifier le pseudo
	public void set_Name (String pseudo) {
		this.pseudo = pseudo;
		//Verification de pseudo dispo
	}
	
	public String get_Name() {
		return this.pseudo;
	}
	
	public InetAddress get_Address() {
		return this.addressUser;
	}
	
	public int get_Port() {
		return this.portServerUser;
	}
	
	
	public boolean get_StatusConnec() {
		return this.connected;
	}
	
	
	public Client get_Client() {
		return this.client;
	}
	
	
	
	
	
	
	
	
	
	//Connecte avec un autre user
	public void connecter(int numPort) throws Exception {
		//this.server = new Server(numPort);
		this.client = new Client(this.addressUser,numPort);
		//this.connected=true ;
		System.out.println(pseudo+"son port Server  "+portServerUser+"-------connection with port-----"+numPort);
		
		this.client.startClient();	
		//this.client.sendMessage("Hello server! "+ this.client.getPortClient());
	}
	

	
	public void sendMessage(String message) {
		this.client.sendMessage(message);
	}
	
	
	

	


	
	//Shut down the system
	public void disconnect() throws Exception {
		System.out.println("La session de"+this.pseudo+" est fini");
		this.server.closeAll();
		this.client.closeAll();
		this.connected=false ; //A déplacer après le BC
	}


}
