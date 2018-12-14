package Application;

import java.io.IOException;
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
	private Server server;
	private Client client;
	private Communication com ;

	
	public User (String i)  {
		this.connected = false;
		this.pseudo = i;	//Verification de pseudo dispo

		
	}
	
	//Quand un user est créer, il a le droit de modifier le pseudo
	public void modify_Pseudo (String name) {
		this.pseudo = name;
		//Verification de pseudo dispo
	}
	
	
	public void connect_user(int numPort) throws Exception {
		//System.out.println("Entrée connect user");
		//this.server = new Server(numPort);
		//System.out.println("Création socketserv");

		this.client = new Client(numPort);
		System.out.println("Création socketclient");

	}
	
	
	
//	public void connect_user(int numPort, int numPort2) throws Exception {
//		//System.out.println("Entrée connect user");
//		//this.server = new Server(numPort);
//		//System.out.println("Création socketserv");
//
//		this.client = new Client(numPort);
//		System.out.println("Création socketclient");
//
//	}
	

//	public void send_Msg(String msg) {
//	
//			System.out.println("envoi msg A depuis user");
//			try {
//				this.server.send(this.server,msg);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//	}
//	
//	
//	public void receive_Msg() {
//		try {
//			this.client.receive(this.client);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public void disconnect() throws Exception {
//		this.server.closeAll();
//		this.client.closeAll();
//		this.connected=false ; //A déplacer après le BC
//	}


}
