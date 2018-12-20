package Application;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.PrintWriter;


//import Application.Server;
//import Application.Client ;
//import java.lang.String;


public class User {

	//Variables locales
	private boolean connected;
	private String pseudo; 
	private InetAddress addressUser;
	private int portClientUser;
	private int portServerUser;
	
	
	private Server server;
	private Client client;

	public User() {
		
	}
	
	
	public User (String pseudo,InetAddress address, int portServer)  {
		this.connected = false;
		this.pseudo = pseudo;	
		//Verification de pseudo dispo
		this.addressUser = address;
		this.portServerUser = portServer;
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
	
	
	
	
	
	
	
/*	public void connecter(int numPort) throws Exception {
		this.server = new Server(numPort);
		this.client = new Client(numPort);
		System.out.println("Connection,Création deux cote sockets");
	}
	
	
	

	


	
	//Shut down the system
	public void disconnect() throws Exception {
		this.server.closeAll();
		this.client.closeAll();
		this.connected=false ; //A déplacer après le BC
		System.out.println("La session de"+this.pseudo+" est fini");
	}*/


}
